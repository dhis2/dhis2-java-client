package org.hisp.dhis;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.Validate;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.hisp.dhis.response.HttpResponseMessage;
import org.hisp.dhis.response.job.JobInfo;
import org.hisp.dhis.response.job.JobInfoResponseMessage;
import org.hisp.dhis.response.job.JobNotification;
import org.hisp.dhis.util.HttpUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Dhis2AsyncRequest
{
    private static final int TIMEOUT_S = 3600;
    private static final int DELAY_S = 2;
    private static final int ATTEMPTS_MAX = TIMEOUT_S / DELAY_S;

    private final Dhis2Config config;

    private final CloseableHttpClient httpClient;

    private final ObjectMapper objectMapper;

    public Dhis2AsyncRequest( Dhis2Config config, CloseableHttpClient httpClient, ObjectMapper objectMapper )
    {
        Validate.notNull( config );
        Validate.notNull( httpClient );
        Validate.notNull( objectMapper );
        this.config = config;
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    /**
     * Executes the given HTTP POST request. The request must be a DHIS 2 async
     * request. The method will use the DHIS 2 tasks and task summary API endpoints
     * to poll for the task status, and eventually return a task summary when the
     * task is complete.
     *
     * @param request the {@link HttpPost}.
     * @param klass the class type.
     * @param <T> the class type.
     * @return a response message.
     * @throws IOException if the POST operation failed.
     */
    public <T extends HttpResponseMessage> T post( HttpPost request, Class<T> klass )
        throws IOException
    {
        JobInfoResponseMessage message = postAsyncRequest( request );

        JobInfo jobInfo = message.getResponse();

        log.info( "Push response: '{}', '{}', job: '{}'", message.getHttpStatus(), message.getMessage(), jobInfo );

        JobNotification notification = waitForCompletion( jobInfo );

        log.info( "Job completed: '{}'", notification );

        T summary = getSummary( jobInfo, klass );

        log.debug( "Summary: '{}'", summary );

        return summary;
    }

    // -------------------------------------------------------------------------
    // Supportive methods
    // -------------------------------------------------------------------------

    /**
     * Executes the given POST request.
     *
     * @param request the {@link HttpPost}.
     * @return a {link JobInfoResponseMessage}.
     * @throws IOException if the POST operation failed.
     */
    private JobInfoResponseMessage postAsyncRequest( HttpPost request )
        throws IOException
    {
        try ( CloseableHttpResponse response = httpClient.execute( request ) )
        {
            String body = EntityUtils.toString( response.getEntity() );

            JobInfoResponseMessage message = objectMapper.readValue( body, JobInfoResponseMessage.class );

            if ( !message.getHttpStatus().is2xxSuccessful() )
            {
                throw new IOException( String.format( "Request failed, status: %s, code: %d, message: %s",
                    message.getHttpStatus(), message.getHttpStatusCode(), message.getMessage() ) );
            }

            return message;
        }
    }

    /**
     * Waits for the task to complete. Returns the first job notification
     * which indicates that the task is complete.
     *
     * @param jobInfo the {@link JobInfo} identifying the task.
     * @return a {@link JobNotification}.
     * @throws IOException if the GET operation failed.
     */
    private JobNotification waitForCompletion( JobInfo jobInfo )
    {
        URI statusUrl = HttpUtils.build( config.getResolvedUriBuilder()
            .pathSegment( "system" )
            .pathSegment( "tasks" )
            .pathSegment( jobInfo.getJobType().name() )
            .pathSegment( jobInfo.getId() ) );

        JobNotification notification = null;
        boolean completed = false;
        int attempts = 0;

        while ( !completed && attempts++ < ATTEMPTS_MAX )
        {
            notification = getLastNotification( statusUrl );
            completed = notification.isCompleted();

            log.info( "Complete check URL: '{}', complete: {}", statusUrl, completed );

            if ( !completed )
            {
                sleepForSeconds( 2 );
            }
        }

        return notification;
    }

    /**
     * Returns a task summary.
     *
     * @param jobInfo the {@link JobInfo} identifying the task.
     * @param klass the class type of the task summary.
     * @return a task summary.
     * @throws IOException if the GET operation failed.
     */
    private <T> T getSummary( JobInfo jobInfo, Class<T> klass )
        throws IOException
    {
        URI summaryUrl = HttpUtils.build( config.getResolvedUriBuilder()
            .pathSegment( "system" )
            .pathSegment( "taskSummaries" )
            .pathSegment( jobInfo.getJobType().name() )
            .pathSegment( jobInfo.getId() ) );

        log.info( "Task summary URL: '{}'", summaryUrl );

        String summary = getForBody( summaryUrl );

        return objectMapper.readValue( summary, klass );
    }

    /**
     * Returns the currently last task notification.
     *
     * @param url the URL.
     * @return a {@link JobNotification}.
     */
    private JobNotification getLastNotification( URI url )
    {
        try
        {
            String response = getForBody( url );

            JobNotification[] notificationArray = objectMapper.readValue( response, JobNotification[].class );

            List<JobNotification> notifications = new ArrayList<>( Arrays.asList( notificationArray ) );

            return !notifications.isEmpty() ? notifications.get( 0 ) : new JobNotification();
        }
        catch ( IOException ex )
        {
            throw new UncheckedIOException( ex );
        }
    }

    /**
     * Retrieves the response entity from a GET request to the given
     * URL as a string.
     *
     * @param url the URL.
     * @return the response entity string.
     * @throws IOException
     */
    private String getForBody( URI url )
        throws IOException
    {
        HttpGet request = HttpUtils.withBasicAuth( new HttpGet( url ), config );

        try ( CloseableHttpResponse response = httpClient.execute( request ) )
        {
            return EntityUtils.toString( response.getEntity() );
        }
    }

    /**
     * Makes the current thread sleep for the given timeout in seconds.
     *
     * @param timeout the timeout in seconds.
     */
    private void sleepForSeconds( long timeout )
    {
        try
        {
            TimeUnit.SECONDS.sleep( timeout );
        }
        catch ( InterruptedException ex )
        {
            throw new RuntimeException( "Thread interrupted", ex );
        }
    }
}
