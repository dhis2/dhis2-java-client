package org.hisp.dhis;

import java.util.Base64;
import java.util.List;

import org.hisp.dhis.model.Dimension;
import org.hisp.dhis.model.Objects;
import org.hisp.dhis.model.OrgUnit;
import org.hisp.dhis.model.OrgUnitGroup;
import org.hisp.dhis.model.OrgUnitGroupSet;
import org.hisp.dhis.model.OrgUnitLevel;
import org.hisp.dhis.model.TableHook;
import org.hisp.dhis.query.Filter;
import org.hisp.dhis.query.Order;
import org.hisp.dhis.query.Paging;
import org.hisp.dhis.query.Query;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * DHIS 2 API client for HTTP requests and responses. Request and
 * response bodies are in JSON format.
 */
public class Dhis2
{
    private static final String ID_FIELDS = "id,code,name,created,lastUpdated";
    private static final String NAME_FIELDS = ID_FIELDS + ",shortName,description";

    private Dhis2Config dhis2Config;

    private RestTemplate restTemplate;

    public Dhis2( Dhis2Config dhis2Config )
    {
        this.dhis2Config = dhis2Config;
        this.restTemplate = new RestTemplate();
    }

    public Dhis2( Dhis2Config dhis2Config, RestTemplate restTemplate )
    {
        this.dhis2Config = dhis2Config;
        this.restTemplate = restTemplate;
    }

    // -------------------------------------------------------------------------
    // Generic methods
    // -------------------------------------------------------------------------

    /**
     * Saves an object using HTTP POST.
     *
     * @param path the URL path relative to the API end point.
     * @param object the object to save.
     * @param <T> type.
     * @return a {@link ResponseMessage} holding information about the operation.
     */
    public <T> ResponseMessage saveObject( String path, T object )
    {
        String url = dhis2Config.getResolvedUrl( path );

        HttpHeaders headers = new HttpHeaders();
        headers.set( HttpHeaders.AUTHORIZATION, getBasicAuthString( dhis2Config.getUsername(), dhis2Config.getPassword() ) );
        headers.set( HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE );

        HttpEntity<T> requestEntity = new HttpEntity<>( object, headers );

        ResponseEntity<ResponseMessage> response = restTemplate.exchange( url, HttpMethod.POST, requestEntity, ResponseMessage.class );

        ResponseMessage message = response.getBody();
        message.setHeaders( headers );
        return message;
    }

    /**
     * Updates an object using HTTP PUT.
     *
     * @param path the URL path relative to the API end point.
     * @param object the object to save.
     * @param <T> type.
     * @return a {@link ResponseMessage} holding information about the operation.
     */
    public <T> ResponseMessage updateObject( String path, T object )
    {
        String url = dhis2Config.getResolvedUrl( path );

        HttpHeaders headers = new HttpHeaders();
        headers.set( HttpHeaders.AUTHORIZATION, getBasicAuthString( dhis2Config.getUsername(), dhis2Config.getPassword() ) );
        headers.set( HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE );

        HttpEntity<T> requestEntity = new HttpEntity<>( object, headers );

        ResponseEntity<ResponseMessage> response = restTemplate.exchange( url, HttpMethod.PUT, requestEntity, ResponseMessage.class );

        ResponseMessage message = response.getBody();
        message.setHeaders( headers );
        return message;
    }

    /**
     * Retrieves an object using HTTP GET.
     *
     * @param path the URL path relative to the API end point.
     * @param klass the class type of the object.
     * @param <T> type.
     * @return the object.
     */
    public <T> T getObject( String path, Class<T> klass )
    {
        return getObjectFromUrl( dhis2Config.getResolvedUrl( path ), klass );
    }

    /**
     * Retrieves an object.
     *
     * @param path the URL path.
     * @param id the object identifier.
     * @param klass the class type of the object.
     * @return the object.
     */
    private <T> T getObject( String path, String id, Class<T> klass )
    {
        String url = dhis2Config.getResolvedUriBuilder()
            .pathSegment( path )
            .pathSegment( id ).build().toUriString();

        return getObjectFromUrl( url, klass );
    }

    /**
     * Retrieves an object using HTTP GET.
     *
     * @param uriBuilder the URI builder.
     * @param filters the filters to apply to the query.
     * @param klass the class type of the object.
     * @param <T> type.
     * @return the object.
     */
    private <T> T getObject( UriComponentsBuilder uriBuilder, Query filters, Class<T> klass )
    {
        for ( Filter filter : filters.getFilters() )
        {
            String filterValue = filter.getProperty() + ":" + filter.getOperator().value() + ":" + filter.getValue();

            uriBuilder.queryParam( "filter", filterValue );
        }

        Paging paging = filters.getPaging();

        if ( paging.hasPaging() )
        {
            if ( paging.hasPage() )
            {
                uriBuilder.queryParam( "page", paging.getPage() );
            }

            if ( paging.hasPageSize() )
            {
                uriBuilder.queryParam( "pageSize", paging.getPageSize() );
            }
        }
        else
        {
            uriBuilder.queryParam( "paging", "false" );
        }

        Order order = filters.getOrder();

        if ( order.hasOrder() )
        {
            String orderValue = order.getProperty() + ":" + order.getDirection().name().toLowerCase();

            uriBuilder.queryParam( "order", orderValue );
        }

        String url = uriBuilder.build().toUriString();

        return getObjectFromUrl( url , klass );
    }

    /**
     * Retrieves an object using HTTP GET.
     *
     * @param url the fully qualified URL.
     * @param klass the class type of the object.
     * @param <T> type.
     * @return the object.
     */
    private <T> T getObjectFromUrl( String url, Class<T> klass )
    {
        HttpHeaders headers = new HttpHeaders();
        headers.set( HttpHeaders.AUTHORIZATION, getBasicAuthString( dhis2Config.getUsername(), dhis2Config.getPassword() ) );
        headers.set( HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE );

        ResponseEntity<T> response = restTemplate.exchange( url, HttpMethod.GET, new HttpEntity<>( headers ), klass );

        return response.getBody();
    }

    /**
     * Indicates whether an object exists at the given URL path
     * using HTTP HEAD.
     *
     * @param path the URL path relative to the API end point.
     * @return true if the object exists.
     */
    public boolean objectExists( String path )
    {
        String url = dhis2Config.getResolvedUrl( path );

        HttpHeaders headers = new HttpHeaders();
        headers.set( HttpHeaders.AUTHORIZATION, getBasicAuthString( dhis2Config.getUsername(), dhis2Config.getPassword() ) );

        try
        {
            ResponseEntity<Object> response = restTemplate.exchange( url, HttpMethod.HEAD, new HttpEntity<>( headers ), Object.class );

            return HttpStatus.OK == response.getStatusCode();
        }
        catch ( HttpClientErrorException ex )
        {
            if ( HttpStatus.NOT_FOUND == ex.getStatusCode() )
            {
                return false;
            }
            else
            {
                throw ex;
            }
        }
    }

    // -------------------------------------------------------------------------
    // Org unit
    // -------------------------------------------------------------------------

    /**
     * Saves a {@link OrgUnit}.
     *
     * @param orgUnit the object to save.
     * @return a {@link ResponseMessage} holding information about the operation.
     */
    public ResponseMessage saveOrgUnit( OrgUnit orgUnit )
    {
        return saveObject( "organisationUnits", orgUnit );
    }

    /**
     * Updates a {@link OrgUnit}.
     *
     * @param orgUnit the object to update.
     * @return a {@link ResponseMessage} holding information about the operation.
     */
    public ResponseMessage updateOrgUnit( OrgUnit orgUnit )
    {
        return updateObject( String.format( "organisationUnits/%s", orgUnit.getId() ), orgUnit );
    }

    /**
     * Retrieves an {@link OrgUnit}.
     *
     * @param id the identifier of the org unit.
     * @return the {@link OrgUnit}.
     */
    public OrgUnit getOrgUnit( String id )
    {
        String fields = NAME_FIELDS + ",path,level";

        return getObject( dhis2Config.getResolvedUriBuilder()
            .pathSegment( "organisationUnits" )
            .pathSegment( id )
            .queryParam( "fields", String.format( "%s,parent[%s]", fields, fields ) ), Query.instance(), OrgUnit.class );
    }

    /**
     * Retrieves a list of {@link OrgUnit}.
     *
     * @return a list of {@link OrgUnit}.
     */
    public List<OrgUnit> getOrgUnits()
    {
        return getOrgUnits( Query.instance() );
    }

    /**
     * Retrieves a list of {@link OrgUnit}.
     *
     * @param query the {@link Query}.
     * @return a list of {@link OrgUnit}.
     */
    public List<OrgUnit> getOrgUnits( Query query )
    {
        String fields = NAME_FIELDS + ",path,level";

        return getObject( dhis2Config.getResolvedUriBuilder()
            .pathSegment( "organisationUnits" )
            .queryParam( "fields", String.format( "%s,parent[%s]", fields, fields ) ), query, Objects.class )
            .getOrganisationUnits();
    }

    // -------------------------------------------------------------------------
    // Org unit group
    // -------------------------------------------------------------------------

    /**
     * Saves a {@link OrgUnitGroup}.
     *
     * @param orgUnitGroup the object to save.
     * @return a {@link ResponseMessage} holding information about the operation.
     */
    public ResponseMessage saveOrgUnitGroup( OrgUnitGroup orgUnitGroup )
    {
        return saveObject( "organisationUnitGroups", orgUnitGroup );
    }

    /**
     * Updates a {@link OrgUnitGroup}.
     *
     * @param orgUnitGroup the object to update.
     * @return a {@link ResponseMessage} holding information about the operation.
     */
    public ResponseMessage updateOrgUnitGroup( OrgUnitGroup orgUnitGroup )
    {
        return updateObject( String.format( "organisationUnitGroups/%s", orgUnitGroup.getId() ), orgUnitGroup );
    }

    /**
     * Retrieves an {@link OrgUnitGroup}.
     *
     * @param id the identifier of the org unit group.
     * @return the {@link OrgUnitGroup}.
     */
    public OrgUnitGroup getOrgUnitGroup( String id )
    {
        return getObject( dhis2Config.getResolvedUriBuilder()
            .pathSegment( "organisationUnitGroups" )
            .pathSegment( id )
            .queryParam( "fields", NAME_FIELDS ), Query.instance(), OrgUnitGroup.class );
    }

    /**
     * Retrieves a list of {@link OrgUnitGroup}.
     *
     * @return a list of {@link OrgUnitGroup}.
     */
    public List<OrgUnitGroup> getOrgUnitGroups()
    {
        return getOrgUnitGroups( Query.instance() );
    }

    /**
     * Retrieves a list of {@link OrgUnitGroup}.
     *
     * @param query the {@link Query}.
     * @return a list of {@link OrgUnitGroup}.
     */
    public List<OrgUnitGroup> getOrgUnitGroups( Query query )
    {
        return getObject( dhis2Config.getResolvedUriBuilder()
            .pathSegment( "organisationUnitGroups" )
            .queryParam( "fields", NAME_FIELDS ), query, Objects.class )
            .getOrganisationUnitGroups();
    }

    // -------------------------------------------------------------------------
    // Org unit group set
    // -------------------------------------------------------------------------

    /**
     * Saves a {@link OrgUnitGroupSet}.
     *
     * @param orgUnitGroupSet the object to save.
     * @return a {@link ResponseMessage} holding information about the operation.
     */
    public ResponseMessage saveOrgUnitGroupSet( OrgUnitGroupSet orgUnitGroupSet )
    {
        return saveObject( "organisationUnitGroupSets", orgUnitGroupSet );
    }

    /**
     * Updates a {@link OrgUnitGroupSet}.
     *
     * @param orgUnitGroupSet the object to update.
     * @return a {@link ResponseMessage} holding information about the operation.
     */
    public ResponseMessage updateOrgUnitGroupSet( OrgUnitGroupSet orgUnitGroupSet )
    {
        return updateObject( String.format( "organisationUnitGroupSets/%s", orgUnitGroupSet.getId() ), orgUnitGroupSet );
    }

    /**
     * Retrieves an {@link OrgUnitGroupSet}.
     *
     * @param id the identifier of the org unit group set.
     * @return the {@link OrgUnitGroupSet}.
     */
    public OrgUnitGroupSet getOrgUnitGroupSet( String id )
    {
        return getObject( dhis2Config.getResolvedUriBuilder()
            .pathSegment( "organisationUnitGroupSets" )
            .pathSegment( id )
            .queryParam( "fields", String.format( "%s,organisationUnitGroups[%s]", NAME_FIELDS, NAME_FIELDS ) ), Query.instance(), OrgUnitGroupSet.class );
    }

    /**
     * Retrieves a list of {@link OrgUnitGroupSet}.
     *
     * @return a list of {@link OrgUnitGroupSet}.
     */
    public List<OrgUnitGroupSet> getOrgUnitGroupSets()
    {
        return getOrgUnitGroupSets( Query.instance() );
    }

    /**
     * Retrieves a list of {@link OrgUnitGroupSet}.
     *
     * @param query the {@link Query}.
     * @return a list of {@link OrgUnitGroupSet}.
     */
    public List<OrgUnitGroupSet> getOrgUnitGroupSets( Query query )
    {
        return getObject( dhis2Config.getResolvedUriBuilder()
            .pathSegment( "organisationUnitGroupSets" )
            .queryParam( "fields", String.format( "%s,organisationUnitGroups[%s]", NAME_FIELDS, NAME_FIELDS ) ), query, Objects.class )
            .getOrganisationUnitGroupSets();
    }

    // -------------------------------------------------------------------------
    // Org unit level
    // -------------------------------------------------------------------------

    /**
     * Saves a {@link OrgUnitLevel}.
     *
     * @param orgUnitLevel the object to save.
     * @return a {@link ResponseMessage} holding information about the operation.
     */
    public ResponseMessage saveOrgUnitLevel( OrgUnitLevel orgUnitLevel )
    {
        return saveObject( "organisationUnitLevels", orgUnitLevel );
    }

    /**
     * Updates a {@link OrgUnitLevel}.
     *
     * @param orgUnitLevel the object to update.
     * @return a {@link ResponseMessage} holding information about the operation.
     */
    public ResponseMessage updateOrgUnitLevel( OrgUnitLevel orgUnitLevel )
    {
        return updateObject( String.format( "organisationUnitLevels/%s", orgUnitLevel.getId() ), orgUnitLevel );
    }

    /**
     * Retrieves an {@link OrgUnitLevel}.
     *
     * @param id the identifier of the org unit level.
     * @return the {@link OrgUnitLevel}.
     */
    public OrgUnitLevel getOrgUnitLevel( String id )
    {
        return getObject( dhis2Config.getResolvedUriBuilder()
            .pathSegment( "organisationUnitLevels" )
            .pathSegment( id )
            .queryParam( "fields", String.format( "%s,level", ID_FIELDS ) ), Query.instance(), OrgUnitLevel.class );
    }

    /**
     * Retrieves a list of {@link OrgUnitLevel}.
     *
     * @return a list of {@link OrgUnitLevel}.
     */
    public List<OrgUnitLevel> getOrgUnitLevels()
    {
        return getOrgUnitLevels( Query.instance() );
    }

    /**
     * Retrieves a list of {@link OrgUnitGroupSet}.
     *
     * @param query the {@link Query}.
     * @return a list of {@link OrgUnitGroupSet}.
     */
    public List<OrgUnitLevel> getOrgUnitLevels( Query query )
    {
        return getObject( dhis2Config.getResolvedUriBuilder()
            .pathSegment( "organisationUnitLevels" )
            .queryParam( "fields", String.format( "%s,level", ID_FIELDS ) ), query, Objects.class )
            .getOrganisationUnitLevels();
    }

    // -------------------------------------------------------------------------
    // Table hook
    // -------------------------------------------------------------------------

    /**
     * Saves a {@link TableHook}.
     *
     * @param tableHook the object to save.
     * @return a {@link ResponseMessage} holding information about the operation.
     */
    public ResponseMessage saveTableHook( TableHook tableHook )
    {
        return saveObject( "analyticsTableHooks", tableHook );
    }

    /**
     * Updates a {@link TableHook}.
     *
     * @param tableHook the object to update.
     * @return a {@link ResponseMessage} holding information about the operation.
     */
    public ResponseMessage updateTableHook( TableHook tableHook )
    {
        return updateObject( String.format( "analyticsTableHooks/%s", tableHook.getId() ), tableHook );
    }

    /**
     * Retrieves an {@link TableHook}.
     *
     * @param id the identifier of the table hook.
     * @return the {@link TableHook}.
     */
    public TableHook getTableHook( String id )
    {
        return getObject( "analyticsTableHooks", id, TableHook.class );
    }

    /**
     * Retrieves a list of {@link TableHook}.
     *
     * @return a list of {@link TableHook}.
     */
    public List<TableHook> getTableHooks()
    {
        return getTableHooks( Query.instance() );
    }

    /**
     * Retrieves a list of {@link TableHook}.
     *
     * @param query the {@link Query}.
     * @return a list of {@link TableHook}.
     */
    public List<TableHook> getTableHooks( Query query )
    {
        return getObject( dhis2Config.getResolvedUriBuilder()
            .pathSegment( "analyticsTableHooks" )
            .queryParam( "fields", ID_FIELDS ), query, Objects.class )
            .getAnalyticsTableHooks();
    }

    // -------------------------------------------------------------------------
    // Dimensional object
    // -------------------------------------------------------------------------

    /**
     * Retrieves a {@link Dimension}.
     *
     * @param id the identifier of the dimension.
     * @return the {@link Dimension}.
     */
    public Dimension getDimension( String id )
    {
        return getObject( dhis2Config.getResolvedUriBuilder()
            .pathSegment( "dimensions" )
            .pathSegment( id )
            .queryParam( "fields", ID_FIELDS ), Query.instance(), Dimension.class );
    }

    /**
     * Retrieves a list of {@link Dimension}.
     *
     * @return a list of {@link Dimension}.
     */
    public List<Dimension> getDimensions()
    {
        return getDimensions( Query.instance() );
    }

    /**
     * Retrieves a list of {@link Dimension}.
     *
     * @param query the {@link Query}.
     * @return a list of {@link Dimension}.
     */
    public List<Dimension> getDimensions( Query query )
    {
        return getObject( dhis2Config.getResolvedUriBuilder()
            .pathSegment( "dimensions" )
            .queryParam( "fields", String.format( "%s,dimensionType", ID_FIELDS ) ), query, Objects.class )
            .getDimensions();
    }

    // -------------------------------------------------------------------------
    // Supportive methods
    // -------------------------------------------------------------------------

    /**
     * Returns a basic authentication string which is generated by prepending
     * "Basic " and base64-encoding username:password.
     *
     * @param username the username to use for authentication.
     * @param password the password to use for authentication.
     * @return the encoded string.
     */
    private static String getBasicAuthString( String username, String password )
    {
        String string = username + ":" + password;

        return "Basic " + Base64.getEncoder().encodeToString( string.getBytes() );
    }
}
