package org.hisp.dhis;

import static org.apache.hc.core5.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.hc.core5.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.hc.core5.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hisp.dhis.util.CollectionUtils.newImmutableSet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.apache.hc.client5.http.HttpResponseException;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.net.URIBuilder;
import org.hisp.dhis.model.IdentifiableObject;
import org.hisp.dhis.model.Objects;
import org.hisp.dhis.model.datavalueset.DataValueSetImportOptions;
import org.hisp.dhis.query.Filter;
import org.hisp.dhis.query.Operator;
import org.hisp.dhis.query.Order;
import org.hisp.dhis.query.Paging;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.query.analytics.AnalyticsQuery;
import org.hisp.dhis.query.analytics.Dimension;
import org.hisp.dhis.response.BaseHttpResponse;
import org.hisp.dhis.response.Dhis2ClientException;
import org.hisp.dhis.response.Response;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.response.objects.ObjectsResponse;
import org.hisp.dhis.util.HttpUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Lars Helge Overland
 */
@Slf4j
public class BaseDhis2
{
    protected static final String ID_FIELDS = "id,code,name,created,lastUpdated,attributeValues";

    protected static final String NAME_FIELDS = String.format(
        "%s,shortName,description", ID_FIELDS );

    protected static final String DATA_ELEMENT_FIELDS = String.format(
        "%1$s,aggregationType,valueType,domainType,legendSets[%1$s]", NAME_FIELDS );

    protected static final String CATEGORY_OPTION_FIELDS = String.format(
        "%1$s,shortName,startDate,endDate,formName", ID_FIELDS );

    protected static final String CATEGORY_FIELDS = String.format(
        "%s,dataDimensionType,dataDimension", NAME_FIELDS );

    protected static final String ORG_UNIT_FIELDS = String.format(
        "%s,path,level,parent[%s],openingDate,closedDate,comment," +
            "url,contactPerson,address,email,phoneNumber",
        NAME_FIELDS, NAME_FIELDS );

    protected static final String TE_ATTRIBUTE_FIELDS = String.format(
        "%s,valueType,confidential,unique", NAME_FIELDS );

    protected static final String RESOURCE_SYSTEM_INFO = "system/info";

    protected static final String DATE_FORMAT = "yyyy-MM-dd";

    private static final Set<Integer> ERROR_STATUS_CODES = newImmutableSet(
        SC_UNAUTHORIZED, SC_FORBIDDEN, SC_NOT_FOUND );

    protected final Dhis2Config config;

    protected final ObjectMapper objectMapper;

    protected final CloseableHttpClient httpClient;

    public BaseDhis2( Dhis2Config config )
    {
        Validate.notNull( config, "Config must be specified" );

        this.config = config;

        this.objectMapper = new ObjectMapper();
        objectMapper.disable( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES );
        objectMapper.setSerializationInclusion( Include.NON_NULL );
        objectMapper.setDateFormat( new SimpleDateFormat( DATE_FORMAT ) );

        this.httpClient = HttpClients.createDefault();
    }

    /**
     * Retrieves an object using HTTP GET.
     *
     * @param uriBuilder the URI builder.
     * @param query the {@link Query} filters to apply.
     * @param type the class type of the object.
     * @param <T> type.
     * @return the object.
     */
    protected <T> T getObject( URIBuilder uriBuilder, Query query, Class<T> type )
    {
        URI url = getQuery( uriBuilder, query );

        return getObjectFromUrl( url, type );
    }

    /**
     * Returns a {@link URI} based on the given query.
     *
     * @param uriBuilder the URI builder.
     * @param query the {@link Query} filters to apply.
     * @return a URI.
     */
    protected URI getQuery( URIBuilder uriBuilder, Query query )
    {
        for ( Filter filter : query.getFilters() )
        {
            String filterValue = filter.getProperty() + ":" + filter.getOperator().value() + ":"
                + getQueryValue( filter );

            uriBuilder.addParameter( "filter", filterValue );
        }

        Paging paging = query.getPaging();

        if ( paging.hasPaging() )
        {
            if ( paging.hasPage() )
            {
                uriBuilder.addParameter( "page", String.valueOf( paging.getPage() ) );
            }

            if ( paging.hasPageSize() )
            {
                uriBuilder.addParameter( "pageSize", String.valueOf( paging.getPageSize() ) );
            }
        }
        else
        {
            uriBuilder.addParameter( "paging", "false" );
        }

        Order order = query.getOrder();

        if ( order.hasOrder() )
        {
            String orderValue = order.getProperty() + ":" + order.getDirection().name().toLowerCase();

            uriBuilder.addParameter( "order", orderValue );
        }

        return HttpUtils.build( uriBuilder );
    }

    /**
     * Retrieves an analytics object using HTTP GET.
     *
     * @param uriBuilder the URI builder.
     * @param query the {@link AnalyticsQuery} filters to apply.
     * @param type the class type of the object.
     * @param <T> type.
     * @return the object.
     */
    protected <T> T getAnalyticsResponse( URIBuilder uriBuilder, AnalyticsQuery query, Class<T> type )
    {
        URI url = getAnalyticsQuery( uriBuilder, query );

        return getObjectFromUrl( url, type );
    }

    /**
     * Returns a {@link URI} based on the given analytics query.
     *
     * @param uriBuilder the URI builder.
     * @param query the {@link AnalyticsQuery} filters to apply.
     * @return a URI.
     */
    protected URI getAnalyticsQuery( URIBuilder uriBuilder, AnalyticsQuery query )
    {
        for ( Dimension dimension : query.getDimensions() )
        {
            uriBuilder.addParameter( "dimension", dimension.getDimensionValue() );
        }

        for ( Dimension filter : query.getFilters() )
        {
            uriBuilder.addParameter( "filter", filter.getDimensionValue() );
        }

        if ( query.getAggregationType() != null )
        {
            uriBuilder.addParameter( "aggregationType", query.getAggregationType().name() );
        }

        if ( query.getStartDate() != null )
        {
            uriBuilder.addParameter( "startDate", query.getStartDate() );
        }

        if ( query.getEndDate() != null )
        {
            uriBuilder.addParameter( "endDate", query.getEndDate() );
        }

        if ( query.getSkipMeta() != null )
        {
            uriBuilder.addParameter( "skipMeta", query.getSkipMeta().toString() );
        }

        if ( query.getSkipData() != null )
        {
            uriBuilder.addParameter( "skipData", query.getSkipData().toString() );
        }

        if ( query.getSkipRounding() != null )
        {
            uriBuilder.addParameter( "skipRounding", query.getSkipRounding().toString() );
        }

        if ( query.getIgnoreLimit() != null )
        {
            uriBuilder.addParameter( "ignoreLimit", query.getIgnoreLimit().toString() );
        }

        if ( query.getOutputIdScheme() != null )
        {
            uriBuilder.addParameter( "outputIdScheme", query.getOutputIdScheme().name() );
        }

        if ( query.getInputIdScheme() != null )
        {
            uriBuilder.addParameter( "inputIdScheme", query.getInputIdScheme().name() );
        }

        return HttpUtils.build( uriBuilder );
    }

    /**
     * Returns a {@link URI} based on the given data value set import options.
     *
     * @param uriBuilder the URI builder.
     * @param options the {@link DataValueSetImportOptions} to apply.
     * @return a URI.
     */
    protected URI getDataValueSetImportQuery( URIBuilder uriBuilder, DataValueSetImportOptions options )
    {
        uriBuilder.addParameter( "async", "true" ); // Always use async

        if ( options.getDataElementIdScheme() != null )
        {
            uriBuilder.addParameter( "dataElementIdScheme", options.getDataElementIdScheme().name() );
        }

        if ( options.getOrgUnitIdScheme() != null )
        {
            uriBuilder.addParameter( "orgUnitIdScheme", options.getOrgUnitIdScheme().name() );
        }

        if ( options.getCategoryOptionComboIdScheme() != null )
        {
            uriBuilder.addParameter( "categoryOptionComboIdScheme", options.getCategoryOptionComboIdScheme().name() );
        }

        if ( options.getIdScheme() != null )
        {
            uriBuilder.addParameter( "idScheme", options.getIdScheme().name() );
        }

        if ( options.getSkipAudit() != null )
        {
            uriBuilder.addParameter( "skipAudit", options.getSkipAudit().toString() );
        }

        return HttpUtils.build( uriBuilder );
    }

    /**
     * Converts the given filter to a query value.
     *
     * @param filter the {@link Filter}.
     * @return a query value.
     */
    private Object getQueryValue( Filter filter )
    {
        if ( Operator.IN == filter.getOperator() )
        {
            return "[" + filter.getValue() + "]";
        }
        else
        {
            return filter.getValue();
        }
    }

    /**
     * Retrieves an object.
     *
     * @param path the URL path.
     * @param id the object identifier.
     * @param type the class type of the object.
     * @param <T> type.
     * @return the object.
     * @throws Dhis2ClientException if access was denied or resource was not
     *         found.
     */
    protected <T> T getObject( String path, String id, Class<T> type )
    {
        try
        {
            URI url = config.getResolvedUriBuilder()
                .appendPath( path )
                .appendPath( id )
                .build();

            return getObjectFromUrl( url, type );
        }
        catch ( URISyntaxException ex )
        {
            throw new RuntimeException( ex );
        }
    }

    /**
     * Executes the given {@link HttpUriRequestBase} request, which may be a
     * POST or PUT request.
     *
     * @param request the request.
     * @param object the object to pass as JSON in the request body.
     * @param type the class type for the response entity.
     * @param <T> class.
     * @return a {@link Response}.
     * @throws Dhis2ClientException if access denied or resource not found.
     */
    protected <T extends BaseHttpResponse> T executeJsonPostPutRequest( HttpUriRequestBase request, Object object,
        Class<T> type )
    {
        validateRequestObject( object );

        String requestBody = toJsonString( object );

        HttpEntity entity = new StringEntity( requestBody, StandardCharsets.UTF_8 );

        request.setHeader( HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType() );
        request.setEntity( entity );

        return executeRequest( request, type );
    }

    /**
     * Executes the given request and returns a response message.
     *
     * @param request the request.
     * @param type the class type for the response entity.
     * @return a response message.
     * @throws Dhis2ClientException if access denied or resource not found.
     */
    private <T extends BaseHttpResponse> T executeRequest( HttpUriRequestBase request, Class<T> type )
    {
        withAuth( request );

        try ( CloseableHttpResponse response = httpClient.execute( request ) )
        {
            handleErrors( response );

            String responseBody = EntityUtils.toString( response.getEntity() );

            T responseMessage = objectMapper.readValue( responseBody, type );

            responseMessage.setHeaders( asList( response.getHeaders() ) );
            responseMessage.setHttpStatusCode( response.getCode() );

            return responseMessage;
        }
        catch ( IOException ex )
        {
            throw newDhis2ClientException( ex );
        }
        catch ( ParseException ex )
        {
            throw new Dhis2ClientException( "HTTP headers could not be parsed", ex );
        }
    }

    /**
     * Returns a HTTP post request with JSON content type for the given URL and
     * entity.
     *
     * @param url the {@link URI}.
     * @param entity the {@link HttpEntity}.
     * @return a {@link HttpPost} request.
     */
    protected HttpPost getPostRequest( URI url, HttpEntity entity )
    {
        HttpPost request = withAuth( new HttpPost( url ) );
        request.setHeader( HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType() );
        request.setEntity( entity );
        return request;
    }

    /**
     * Retrieves an object using HTTP GET.
     *
     * @param url the fully qualified URL.
     * @param type the class type of the object.
     * @param <T> type.
     * @return the object.
     * @throws Dhis2ClientException if access denied or resource not found.
     */
    protected <T> T getObjectFromUrl( URI url, Class<T> type )
    {
        try ( CloseableHttpResponse response = getJsonHttpResponse( url ) )
        {
            handleErrors( response );

            String responseBody = EntityUtils.toString( response.getEntity() );

            return objectMapper.readValue( responseBody, type );
        }
        catch ( IOException ex )
        {
            throw new UncheckedIOException( "Failed to fetch object", ex );
        }
        catch ( ParseException ex )
        {
            throw new Dhis2ClientException( "HTTP headers could not be parsed", ex );
        }
    }

    /**
     * Gets a {@link CloseableHttpResponse} for the given URL.
     *
     * @param url the URL.
     * @return a {@link CloseableHttpResponse}.
     */
    protected CloseableHttpResponse getJsonHttpResponse( URI url )
    {
        HttpGet request = withAuth( new HttpGet( url ) );
        request.setHeader( HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType() );

        log.debug( "GET request URL: '{}'", HttpUtils.asString( url ) );

        try
        {
            return httpClient.execute( request );
        }
        catch ( IOException ex )
        {
            throw new UncheckedIOException( "Request failed", ex );
        }
    }

    /**
     * Handles error status codes, currently <code>401/403</code> and
     * <code>404</code>.
     *
     * @param response {@link HttpResponse}.
     * @throws Dhis2ClientException
     */
    private void handleErrors( HttpResponse response )
    {
        final int code = response.getCode();

        if ( ERROR_STATUS_CODES.contains( code ) )
        {
            String message = String.format( "%s (%d)", getErrorMessage( code ), code );

            throw new Dhis2ClientException( message, code );
        }
    }

    /**
     * Return an error message for the given status code.
     *
     * @param code the status code.
     * @return an error message.
     */
    private String getErrorMessage( int code )
    {
        if ( 401 == code )
            return "Authentication failed";
        if ( 403 == code )
            return "Access was denied";
        if ( 404 == code )
            return "Object not found";
        if ( 409 == code )
            return "Conflict";
        if ( code >= 400 && code < 500 )
            return "Client error";
        if ( code >= 500 && code < 600 )
            return "Server error";
        else
            return "Error";
    }

    /**
     * Validates a request object.
     *
     * @param object the request object to validate.
     * @throws Dhis2ClientException
     */
    private void validateRequestObject( Object object )
    {
        if ( object == null )
        {
            throw new Dhis2ClientException( "Request object is null", HttpStatus.SC_BAD_REQUEST );
        }
    }

    /**
     * Write the given {@link HttpResponse} to the given {@link File}.
     *
     * @param response the response.
     * @param file the file to write the response to.
     * @throws IOException if the write operation failed.
     */
    protected void writeToFile( CloseableHttpResponse response, File file )
        throws IOException
    {
        try ( FileOutputStream fileOut = FileUtils.openOutputStream( file );
            InputStream in = response.getEntity().getContent() )
        {
            IOUtils.copy( in, fileOut );
        }
    }

    /**
     * Adds authentication to the given request.
     *
     * @param request the {@link HttpUriRequestBase}.
     * @param <T> class.
     * @return the request.
     */
    protected <T extends HttpUriRequestBase> T withAuth( T request )
    {
        return HttpUtils.withAuth( request, config );
    }

    /**
     * Serializes the given object to a JSON string.
     *
     * @param object the object to serialize.
     * @return a JSON string representation of the object.
     * @throws UncheckedIOException if the serialization failed.
     */
    protected String toJsonString( Object object )
    {
        try
        {
            String json = objectMapper.writeValueAsString( object );

            log.trace( "Object JSON: '{}'", json );

            return json;
        }
        catch ( IOException ex )
        {
            throw new UncheckedIOException( ex );
        }
    }

    /**
     * Returns a {@link Dhis2ClientException} based on the given exception.
     *
     * @param ex the exception.
     * @return a {@link Dhis2ClientException}.
     */
    protected Dhis2ClientException newDhis2ClientException( IOException ex )
    {
        int statusCode = -1;

        if ( ex instanceof HttpResponseException )
        {
            statusCode = ((HttpResponseException) ex).getStatusCode();
        }

        return new Dhis2ClientException( ex.getMessage(), ex.getCause(), statusCode );
    }

    /**
     * Converts the given array to a {@link ArrayList}.
     *
     * @param array the array.
     * @param <T> class.
     * @return a list.
     */
    protected static <T> ArrayList<T> asList( T[] array )
    {
        return new ArrayList<>( Arrays.asList( array ) );
    }

    // -------------------------------------------------------------------------
    // Protected generic object methods
    // -------------------------------------------------------------------------

    /**
     * Saves a metadata object using HTTP POST.
     *
     * @param path the URL path relative to the API end point.
     * @param object the object to save.
     * @return {@link ObjectResponse} holding information about the operation.
     * @throws Dhis2ClientException if the save operation failed due to client
     *         side error.
     */
    protected ObjectResponse saveMetadataObject( String path, IdentifiableObject object )
    {
        return saveObject( path, object, ObjectResponse.class );
    }

    /**
     * Saves an object using HTTP POST.
     *
     * @param path the URL path relative to the API end point.
     * @param object the object to save.
     * @param type the class type for the response entity.
     * @param <T> class.
     * @return object holding information about the operation.
     * @throws Dhis2ClientException if the save operation failed due to client
     *         side error.
     */
    protected <T extends BaseHttpResponse> T saveObject( String path, Object object, Class<T> type )
    {
        URI url = config.getResolvedUrl( path );

        return executeJsonPostPutRequest( new HttpPost( url ), object, type );
    }

    /**
     * Saves an object using HTTP POST.
     *
     * @param uriBuilder the URI builder.
     * @param object the object to save.
     * @param type the class type for the response entity.
     * @param <T> class.
     * @return object holding information about the operation.
     * @throws Dhis2ClientException if the save operation failed due to client
     *         side error.
     */
    protected <T extends BaseHttpResponse> T saveObject( URIBuilder uriBuilder, Object object, Class<T> type )
    {
        URI url = HttpUtils.build( uriBuilder );

        return executeJsonPostPutRequest( new HttpPost( url ), object, type );
    }

    /**
     * Saves or updates metadata objects.
     *
     * @param objects the {@link Objects}.
     * @return {@link ObjectsResponse} holding information about the operation.
     */
    protected ObjectsResponse saveMetadataObjects( Objects objects )
    {
        URI url = config.getResolvedUrl( "metadata" );

        return executeJsonPostPutRequest( new HttpPost( url ), objects, ObjectsResponse.class );
    }

    /**
     * Updates an object using HTTP PUT.
     *
     * @param path the URL path relative to the API end point.
     * @param object the object to save.
     * @return {@link ObjectResponse} holding information about the operation.
     */
    protected ObjectResponse updateMetadataObject( String path, IdentifiableObject object )
    {
        return updateObject( path, object, ObjectResponse.class );
    }

    /**
     * Updates an object using HTTP PUT.
     *
     * @param path the URL path relative to the API end point.
     * @param object the object to save.
     * @param type the class type for the response entity.
     * @param <T> class.
     * @return object holding information about the operation.
     */
    protected <T extends BaseHttpResponse> T updateObject( String path, Object object, Class<T> type )
    {
        URI url = config.getResolvedUrl( path );

        return executeJsonPostPutRequest( new HttpPut( url ), object, type );
    }

    /**
     * Removes an object using HTTP DELETE.
     *
     * @param path the URL path relative to the API end point.
     * @return {@link ObjectResponse} holding information about the operation.
     */
    protected ObjectResponse removeMetadataObject( String path )
    {
        return removeObject( path, ObjectResponse.class );
    }

    /**
     * Removes an object using HTTP DELETE.
     *
     * @param path the URL path relative to the API end point.
     * @param type the class type for the response entity.
     * @param <T> class.
     * @return object holding information about the operation.
     */
    protected <T extends BaseHttpResponse> T removeObject( String path, Class<T> type )
    {
        URI url = config.getResolvedUrl( path );

        return executeRequest( new HttpDelete( url ), type );
    }

    /**
     * Retrieves an object using HTTP GET.
     *
     * @param path the URL path relative to the API end point.
     * @param type the class type of the object.
     * @param <T> type.
     * @return the object.
     */
    protected <T> T getObject( String path, Class<T> type )
    {
        return getObjectFromUrl( config.getResolvedUrl( path ), type );
    }
}
