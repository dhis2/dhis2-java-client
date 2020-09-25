package org.hisp.dhis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hisp.dhis.model.Category;
import org.hisp.dhis.model.CategoryCombo;
import org.hisp.dhis.model.CategoryOptionGroupSet;
import org.hisp.dhis.model.DataElement;
import org.hisp.dhis.model.DataElementGroup;
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
import org.hisp.dhis.model.SystemSettings;
import org.hisp.dhis.model.TableHook;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.response.Dhis2ClientException;
import org.hisp.dhis.response.ResponseMessage;
import org.hisp.dhis.response.datavalueset.DataValueSetResponseMessage;
import org.hisp.dhis.response.job.JobCategory;
import org.hisp.dhis.response.job.JobNotification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * DHIS 2 API client for HTTP requests and responses. Request and
 * response bodies are in JSON format.
 *
 * @author Lars Helge Overland
 */
public class Dhis2
    extends BaseDhis2
{
    public Dhis2( Dhis2Config dhis2Config )
    {
        this( dhis2Config, new RestTemplate() );
    }

    public Dhis2( Dhis2Config dhis2Config, RestTemplate restTemplate )
    {
        super( dhis2Config, restTemplate );
    }

    // -------------------------------------------------------------------------
    // Generic methods
    // -------------------------------------------------------------------------

    /**
     * Checks the status of the DHIS 2 instance. Returns various status codes describing
     * the status:
     *
     * <ul>
     * <li>{@link HttpStatus#OK} if instance is available and authentication is successful.</li>
     * <li>{@link HttpStatus#UNAUTHORIZED} if the username and password combination is
     *     not valid.</li>
     * <li>{@link HttpStatus#NOT_FOUND} if the URL is not pointing to a DHIS 2 instance
     *     or the DHIS 2 instance is not available.</li>
     * <li>A 500 series error if the DHIS 2 instance had an internal error.</li>
     * </ul>
     *
     * @return the {@link HttpStatus} of the response from DHIS 2.
     */
    public HttpStatus getStatus()
    {
        try
        {
            HttpHeaders headers = getBasicAuthAcceptJsonHeaders();
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
     * Returns the URL of the DHIS 2 configuration.
     *
     * @return the URL of the DHIS 2 configuration.
     */
    public String getDhis2Url()
    {
        return dhis2Config.getUrl();
    }

    /**
     * Returns the username of the DHIS 2 configuration.
     *
     * @return the username of the DHIS 2 configuration.
     */
    public String getDhis2Username()
    {
        return dhis2Config.getUsername();
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
    public <T> ResponseMessage updateMetadataObject( String path, T object )
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
        return updateMetadataObject( String.format( "organisationUnits/%s", orgUnit.getId() ), orgUnit );
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
        return updateMetadataObject( String.format( "organisationUnitGroups/%s", orgUnitGroup.getId() ), orgUnitGroup );
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
        return updateMetadataObject( String.format( "organisationUnitGroupSets/%s", orgUnitGroupSet.getId() ), orgUnitGroupSet );
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
     * Retrieves a list of {@link OrgUnitLevel}.
     *
     * @param query the {@link Query}.
     * @return a list of {@link OrgUnitLevel}.
     */
    public List<OrgUnitLevel> getOrgUnitLevels( Query query )
    {
        return getObject( dhis2Config.getResolvedUriBuilder()
            .pathSegment( "organisationUnitLevels" )
            .queryParam( "fields", String.format( "%s,level", ID_FIELDS ) ), query, Objects.class )
            .getOrganisationUnitLevels();
    }

    /**
     * Retrieves a list of "filled" {@link OrgUnitLevel}, meaning
     * any gaps in the persisted levels will be inserted by generated
     * levels.
     *
     * @return a list of {@link OrgUnitLevel}.
     */
    public List<OrgUnitLevel> getFilledOrgUnitLevels()
    {
        // Using array, DHIS 2 should have used a wrapper entity for the response

        return asList( getObject( dhis2Config.getResolvedUriBuilder()
            .pathSegment( "filledOrganisationUnitLevels" ), Query.instance(), OrgUnitLevel[].class ) );
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
    // Data element group
    // -------------------------------------------------------------------------

    /**
     * Saves a {@link DataElement}.
     *
     * @param dataElementGroup the object to save.
     * @return a {@link ResponseMessage} holding information about the operation.
     */
    public ResponseMessage saveDataElementGroup( DataElementGroup dataElementGroup )
    {
        return saveMetadataObject( "dataElementGroups", dataElementGroup );
    }

    /**
     * Updates a {@link DataElementGroup}.
     *
     * @param dataElementGroup the object to update.
     * @return a {@link ResponseMessage} holding information about the operation.
     */
    public ResponseMessage updateDataElementGroup( DataElementGroup dataElementGroup )
    {
        return updateMetadataObject( String.format( "dataElementGroups/%s", dataElementGroup.getId() ), dataElementGroup );
    }

    /**
     * Retrieves an {@link DataElementGroup}.
     *
     * @param id the object identifier.
     * @return the {@link DataElementGroup}.
     */
    public DataElementGroup getDataElementGroup( String id )
    {
        return getObject( dhis2Config.getResolvedUriBuilder()
            .pathSegment( "dataElementGroups" )
            .pathSegment( id )
            .queryParam( "fields", NAME_FIELDS ), Query.instance(), DataElementGroup.class );
    }

    /**
     * Retrieves a list of {@link DataElementGroup}.
     *
     * @param query the {@link Query}.
     * @return a list of {@link DataElementGroup}.
     */
    public List<DataElementGroup> getDataElementGroups( Query query )
    {
        return getObject( dhis2Config.getResolvedUriBuilder()
            .pathSegment( "dataElementGroups" )
            .queryParam( "fields", NAME_FIELDS ), query, Objects.class )
            .getDataElementGroups();
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
        String fieldsParam = query.isExpandAssociations() ?
            String.format( "%1$s,programType,categoryCombo[%1$s,categories[%2$s]],programStages[%1$s,programStageDataElements[%1$s,dataElement[%3$s]]]",
            NAME_FIELDS, CATEGORY_FIELDS, DATA_ELEMENT_FIELDS ) :
            String.format( "%1$s,programType,categoryCombo[%1$s],programStages[%1$s]", NAME_FIELDS );

        return getObject( dhis2Config.getResolvedUriBuilder()
            .pathSegment( "programs" )
            .queryParam( "fields", fieldsParam ), query, Objects.class )
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
        return updateMetadataObject( String.format( "analyticsTableHooks/%s", tableHook.getId() ), tableHook );
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
    // System settings
    // -------------------------------------------------------------------------

    /**
     * Retrieves {@link SystemSettings}.
     *
     * @return system settings.
     */
    public SystemSettings getSystemSettings()
    {
        return getObject( dhis2Config.getResolvedUriBuilder()
            .pathSegment( "systemSettings" ), Query.instance(), SystemSettings.class );
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
    // Job notifications
    // -------------------------------------------------------------------------

    /**
     * Retrieves a list of {@link JobNotification}.
     *
     * @param category the {@link JobCategory}.
     * @param id the job identifier.
     * @return a list of {@link JobNotification}.
     */
    public List<JobNotification> getJobNotifications( JobCategory category, String id )
    {
        JobNotification[] response = getObject( dhis2Config.getResolvedUriBuilder()
            .pathSegment( "system" )
            .pathSegment( "tasks" )
            .pathSegment( category.name() )
            .pathSegment( id ), Query.instance(), JobNotification[].class );

        return new ArrayList<>( Arrays.asList( response ) );
    }
}
