package org.hisp.dhis;

import java.util.Base64;
import java.util.List;

import org.hisp.dhis.model.Category;
import org.hisp.dhis.model.CategoryCombo;
import org.hisp.dhis.model.CategoryOptionGroupSet;
import org.hisp.dhis.model.DataElement;
import org.hisp.dhis.model.DataElementGroupSet;
import org.hisp.dhis.model.DataValueSet;
import org.hisp.dhis.model.Dimension;
import org.hisp.dhis.model.Objects;
import org.hisp.dhis.model.OrgUnit;
import org.hisp.dhis.model.OrgUnitGroup;
import org.hisp.dhis.model.OrgUnitGroupSet;
import org.hisp.dhis.model.OrgUnitLevel;
import org.hisp.dhis.model.PeriodType;
import org.hisp.dhis.model.Program;
import org.hisp.dhis.model.SystemInfo;
import org.hisp.dhis.model.TableHook;
import org.hisp.dhis.query.Filter;
import org.hisp.dhis.query.Order;
import org.hisp.dhis.query.Paging;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.response.Dhis2ClientException;
import org.hisp.dhis.response.ResponseMessage;
import org.hisp.dhis.response.datavalueset.DataValueSetResponseMessage;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * DHIS 2 API client for HTTP requests and responses. Request and
 * response bodies are in JSON format.
 */
public class Dhis2
{
    private static final String ID_FIELDS = "id,code,name,created,lastUpdated";
    private static final String NAME_FIELDS = String.format( "%s,shortName,description", ID_FIELDS );
    private static final String DATA_ELEMENT_FIELDS = String.format( "%1$s,aggregationType,valueType,domainType,legendSets[%1$s]", NAME_FIELDS );
    private static final String CATEGORY_FIELDS = String.format( "%s,dataDimensionType,dataDimension", NAME_FIELDS );
    private static final String RESOURCE_SYSTEM_INFO = "system/info";

    private Dhis2Config dhis2Config;

    private RestTemplate restTemplate;

    public Dhis2( Dhis2Config dhis2Config, RestTemplate restTemplate )
    {
        this.dhis2Config = dhis2Config;
        this.restTemplate = restTemplate;
    }

    // -------------------------------------------------------------------------
    // Generic methods
    // -------------------------------------------------------------------------

    /**
     * Checks the status of the DHIS 2 instance. Returns {@link HttpStatus#OK}
     * if available and everything is okay. Returns {@link HttpStatus#NOT_FOUND} if the
     * URL to the DHIS 2 instance is invalid or the DHIS 2 instance is not available. Returns
     * a 500 series error if the DHIS 2 instance had an internal error.
     *
     * @return the {@link HttpStatus} of the response from DHIS 2.
     */
    public HttpStatus getStatus()
    {
        try
        {
            HttpHeaders headers =  getBasicAuthAcceptJsonHeaders();
            String url = dhis2Config.getResolvedUrl( RESOURCE_SYSTEM_INFO );
            ResponseEntity<SystemInfo> response = restTemplate.exchange( url, HttpMethod.GET, new HttpEntity<>( headers ), SystemInfo.class );
            return response.getStatusCode();
        }
        catch ( HttpClientErrorException | HttpServerErrorException ex )
        {
            return ex.getStatusCode();
        }
    }

    /**
     * Saves an object using HTTP POST.
     *
     * @param path the URL path relative to the API end point.
     * @param object the object to save.
     * @return a {@link ResponseMessage} holding information about the operation.
     * @param <T> type.
     * @throws Dhis2ClientException if the save operation failed due to client side error.
     */
    public <T> ResponseMessage saveMetadataObject( String path, T object )
    {
        String url = dhis2Config.getResolvedUrl( path );

        HttpHeaders headers = getBasicAuthAcceptJsonHeaders();

        HttpEntity<T> requestEntity = new HttpEntity<>( object, headers );

        try
        {
            ResponseEntity<ResponseMessage> response = restTemplate.exchange( url, HttpMethod.POST, requestEntity, ResponseMessage.class );

            ResponseMessage message = response.getBody();
            message.setHeaders( headers );
            return message;
        }
        catch ( HttpClientErrorException ex )
        {
            String message = String.format( "Saving metadata object failed with status code: %s", ex.getStatusCode() );

            throw new Dhis2ClientException( message, ex.getCause(), ex.getStatusCode(), ex.getResponseHeaders(), ex.getResponseBodyAsString() );
        }
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

        HttpHeaders headers = getBasicAuthAcceptJsonHeaders();

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
        HttpHeaders headers =  getBasicAuthAcceptJsonHeaders();

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

        HttpHeaders headers =  getBasicAuthAcceptJsonHeaders();

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
        return saveMetadataObject( "organisationUnits", orgUnit );
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
     * @param id the object identifier.
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
        return saveMetadataObject( "organisationUnitGroups", orgUnitGroup );
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
     * @param id the object identifier.
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
        return saveMetadataObject( "organisationUnitGroupSets", orgUnitGroupSet );
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
     * @param id the object identifier.
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
     * Retrieves an {@link OrgUnitLevel}.
     *
     * @param id the object identifier.
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
    // Category
    // -------------------------------------------------------------------------

    /**
     * Retrieves an {@link Category}.
     *
     * @param id the object identifier.
     * @return the {@link Category}.
     */
    public Category getCategory( String id )
    {
        return getObject( dhis2Config.getResolvedUriBuilder()
            .pathSegment( "categories" )
            .pathSegment( id )
            .queryParam( "fields", CATEGORY_FIELDS ), Query.instance(), Category.class );
    }

    /**
     * Retrieves a list of {@link Category}.
     *
     * @param query the {@link Query}.
     * @return a list of {@link Category}.
     */
    public List<Category> getCategories( Query query )
    {
        return getObject( dhis2Config.getResolvedUriBuilder()
            .pathSegment( "categories" )
            .queryParam( "fields", CATEGORY_FIELDS ), query, Objects.class )
            .getCategories();
    }

    // -------------------------------------------------------------------------
    // Category combo
    // -------------------------------------------------------------------------

    /**
     * Retrieves an {@link CategoryCombo}.
     *
     * @param id the object identifier.
     * @return the {@link CategoryCombo}.
     */
    public CategoryCombo getCategoryCombo( String id )
    {
        return getObject( dhis2Config.getResolvedUriBuilder()
            .pathSegment( "categoryCombos" )
            .pathSegment( id )
            .queryParam( "fields", NAME_FIELDS ), Query.instance(), CategoryCombo.class );
    }

    /**
     * Retrieves a list of {@link CategoryCombo}.
     *
     * @param query the {@link Query}.
     * @return a list of {@link CategoryCombo}.
     */
    public List<CategoryCombo> getCategoryCombos( Query query )
    {
        return getObject( dhis2Config.getResolvedUriBuilder()
            .pathSegment( "categoryCombos" )
            .queryParam( "fields", NAME_FIELDS ), query, Objects.class )
            .getCategoryCombos();
    }

    // -------------------------------------------------------------------------
    // Data element
    // -------------------------------------------------------------------------

    /**
     * Retrieves an {@link DataElement}.
     *
     * @param id the object identifier.
     * @return the {@link DataElement}.
     */
    public DataElement getDataElement( String id )
    {
        return getObject( dhis2Config.getResolvedUriBuilder()
            .pathSegment( "dataElements" )
            .pathSegment( id )
            .queryParam( "fields", DATA_ELEMENT_FIELDS ), Query.instance(), DataElement.class );
    }

    /**
     * Retrieves a list of {@link DataElement}.
     *
     * @param query the {@link Query}.
     * @return a list of {@link DataElement}.
     */
    public List<DataElement> getDataElements( Query query )
    {
        return getObject( dhis2Config.getResolvedUriBuilder()
            .pathSegment( "dataElements" )
            .queryParam( "fields", DATA_ELEMENT_FIELDS ), query, Objects.class )
            .getDataElements();
    }

    // -------------------------------------------------------------------------
    // Data element group set
    // -------------------------------------------------------------------------

    /**
     * Retrieves an {@link DataElementGroupSet}.
     *
     * @param id the object identifier.
     * @return the {@link DataElementGroupSet}.
     */
    public DataElementGroupSet getDataElementGroupSet( String id )
    {
        return getObject( dhis2Config.getResolvedUriBuilder()
            .pathSegment( "dataElementGroupSets" )
            .pathSegment( id )
            .queryParam( "fields", NAME_FIELDS ), Query.instance(), DataElementGroupSet.class );
    }

    /**
     * Retrieves a list of {@link DataElementGroupSet}.
     *
     * @param query the {@link Query}.
     * @return a list of {@link DataElementGroupSet}.
     */
    public List<DataElementGroupSet> getDataElementGroupSets( Query query )
    {
        return getObject( dhis2Config.getResolvedUriBuilder()
            .pathSegment( "dataElementGroupSets" )
            .queryParam( "fields", NAME_FIELDS ), query, Objects.class )
            .getDataElementGroupSets();
    }

    // -------------------------------------------------------------------------
    // Program
    // -------------------------------------------------------------------------

    /**
     * Retrieves a {@link Program}.
     *
     * @param id the object identifier.
     * @return the {@link Program}.
     */
    public Program getProgram( String id )
    {
        return getObject( dhis2Config.getResolvedUriBuilder()
            .pathSegment( "programs" )
            .pathSegment( id )
            .queryParam( "fields", String.format( "%1$s,programType,categoryCombo[%1$s,categories[%2$s]],programStages[%1$s,programStageDataElements[%1$s,dataElement[%3$s]]]",
                NAME_FIELDS, CATEGORY_FIELDS, DATA_ELEMENT_FIELDS ) ), Query.instance(), Program.class );
    }

    /**
     * Retrieves a list of {@link Program}.
     *
     * @param query the {@link Query}.
     * @return a list of {@link Program}.
     */
    public List<Program> getPrograms( Query query )
    {
        return getObject( dhis2Config.getResolvedUriBuilder()
            .pathSegment( "programs" )
            .queryParam( "fields", String.format( "%1$s,programType,categoryCombo[%1$s,categories[%2$s]],programStages[%1$s,programStageDataElements[%1$s,dataElement[%3$s]]]",
                NAME_FIELDS, CATEGORY_FIELDS, DATA_ELEMENT_FIELDS ) ), query, Objects.class )
            .getPrograms();
    }

    // -------------------------------------------------------------------------
    // Category option group set
    // -------------------------------------------------------------------------

    /**
     * Retrieves an {@link CategoryOptionGroupSet}.
     *
     * @param id the object identifier.
     * @return the {@link CategoryOptionGroupSet}.
     */
    public CategoryOptionGroupSet getCategoryOptionGroupSet( String id )
    {
        return getObject( dhis2Config.getResolvedUriBuilder()
            .pathSegment( "categoryOptionGroupSets" )
            .pathSegment( id )
            .queryParam( "fields", NAME_FIELDS ), Query.instance(), CategoryOptionGroupSet.class );
    }

    /**
     * Retrieves a list of {@link CategoryOptionGroupSet}.
     *
     * @param query the {@link Query}.
     * @return a list of {@link CategoryOptionGroupSet}.
     */
    public List<CategoryOptionGroupSet> getCategoryOptionGroupSets( Query query )
    {
        return getObject( dhis2Config.getResolvedUriBuilder()
            .pathSegment( "categoryOptionGroupSets" )
            .queryParam( "fields", NAME_FIELDS ), query, Objects.class )
            .getCategoryOptionGroupSets();
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
        return saveMetadataObject( "analyticsTableHooks", tableHook );
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
    // Dimension
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
            .queryParam( "fields", String.format( "%s,dimensionType", ID_FIELDS ) ), Query.instance(), Dimension.class );
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
    // Period type
    // -------------------------------------------------------------------------

    /**
     * Retrieves a list of {@link PeriodType}.
     *
     * @param query the {@link Query}.
     * @return a list of {@link PeriodType}.
     */
    public List<PeriodType> getPeriodTypes( Query query )
    {
        return getObject( dhis2Config.getResolvedUriBuilder()
            .pathSegment( "periodTypes" )
            .queryParam( "fields", "frequencyOrder,name,isoDuration,isoFormat" ), query, Objects.class )
            .getPeriodTypes();
    }

    // -------------------------------------------------------------------------
    // Data value set
    // -------------------------------------------------------------------------

    /**
     * Saves a {@link DataValueSet}.
     *
     * @param dataValueSet the {@link DataValueSet} to save.
     * @return a {@link DataValueSetResponseMessage} holding information about the operation.
     * @throws Dhis2ClientException if the save operation failed due to a client side error.
     */
    public DataValueSetResponseMessage saveDataValueSet( DataValueSet dataValueSet )
    {
        String url = dhis2Config.getResolvedUrl( "dataValueSets" );

        HttpHeaders headers = getBasicAuthAcceptJsonHeaders();

        HttpEntity<DataValueSet> requestEntity = new HttpEntity<>( dataValueSet, headers );

        try
        {
            ResponseEntity<DataValueSetResponseMessage> response = restTemplate.exchange( url, HttpMethod.POST, requestEntity, DataValueSetResponseMessage.class );

            DataValueSetResponseMessage message = response.getBody();
            message.setHttpStatusCode( response.getStatusCodeValue() );
            message.setHeaders( response.getHeaders() );
            return message;
        }
        catch ( HttpClientErrorException ex )
        {
            String message = String.format( "Saving data value set failed with status code: %s", ex.getStatusCode() );

            throw new Dhis2ClientException( message, ex.getCause(), ex.getStatusCode(), ex.getResponseHeaders(), ex.getResponseBodyAsString() );
        }
    }

    // -------------------------------------------------------------------------
    // Supportive methods
    // -------------------------------------------------------------------------

    /**
     * Returns a HTTP headers instance with basic authentication and Accept
     * {@value application/json} headers.
     *
     * @return a HTTP headers instance.
     */
    private HttpHeaders getBasicAuthAcceptJsonHeaders()
    {
        HttpHeaders headers = new HttpHeaders();
        headers.set( HttpHeaders.AUTHORIZATION, getBasicAuthString( dhis2Config.getUsername(), dhis2Config.getPassword() ) );
        headers.set( HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE );
        return headers;
    }

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
