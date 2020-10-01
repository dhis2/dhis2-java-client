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

    public <T extends HttpResponseMessage> T post( HttpPost request, Class<T> klass )
        throws IOException
    {
        JobInfoResponseMessage message = postAsyncRequest( request );

        JobInfo jobInfo = message.getResponse();

        log.info( "Push response: '{}', '{}', job: '{}'", message.getHttpStatus(), message.getMessage(), jobInfo );

        JobNotification notification = waitForCompletion( jobInfo );

        log.info( "Job completed: '{}'", notification );

        T summary = getSummary( jobInfo, klass );

        log.info( "Summary: '{}'", summary );

        return summary;
    }

    // -------------------------------------------------------------------------
    // Supportive methods
    // -------------------------------------------------------------------------

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

    private JobNotification waitForCompletion( JobInfo jobInfo )
        throws IOException
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

    private JobNotification getLastNotification( URI url )
        throws IOException
    {
        String response = getForBody( url );

        JobNotification[] notificationArray = objectMapper.readValue( response, JobNotification[].class );

        List<JobNotification> notifications = new ArrayList<>( Arrays.asList( notificationArray ) );

        return notifications != null && !notifications.isEmpty() ? notifications.get( 0 ) : new JobNotification();
    }

    private String getForBody( URI url )
    {
        HttpGet request = HttpUtils.withBasicAuth( new HttpGet( url ), config );

        try ( CloseableHttpResponse response = httpClient.execute( request ) )
        {
            return EntityUtils.toString( response.getEntity() );
        }
        catch ( IOException ex )
        {
            throw new UncheckedIOException( ex );
        }
    }

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
