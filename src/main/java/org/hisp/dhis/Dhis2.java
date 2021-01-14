package org.hisp.dhis;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.hc.client5.http.HttpResponseException;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpHead;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.FileEntity;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.hisp.dhis.model.Category;
import org.hisp.dhis.model.CategoryCombo;
import org.hisp.dhis.model.CategoryOption;
import org.hisp.dhis.model.CategoryOptionGroupSet;
import org.hisp.dhis.model.DataElement;
import org.hisp.dhis.model.DataElementGroup;
import org.hisp.dhis.model.DataElementGroupSet;
import org.hisp.dhis.model.Dimension;
import org.hisp.dhis.model.Objects;
import org.hisp.dhis.model.OrgUnit;
import org.hisp.dhis.model.OrgUnitGroup;
import org.hisp.dhis.model.OrgUnitGroupSet;
import org.hisp.dhis.model.OrgUnitLevel;
import org.hisp.dhis.model.PeriodType;
import org.hisp.dhis.model.Program;
import org.hisp.dhis.model.SystemSettings;
import org.hisp.dhis.model.TableHook;
import org.hisp.dhis.model.datavalueset.DataValueSet;
import org.hisp.dhis.model.datavalueset.DataValueSetImportOptions;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.query.analytics.AnalyticsQuery;
import org.hisp.dhis.response.Dhis2ClientException;
import org.hisp.dhis.response.HttpStatus;
import org.hisp.dhis.response.datavalueset.DataValueSetResponseMessage;
import org.hisp.dhis.response.job.JobCategory;
import org.hisp.dhis.response.job.JobNotification;
import org.hisp.dhis.response.metadata.MetadataResponseMessage;
import org.hisp.dhis.util.HttpUtils;

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
        super( dhis2Config );
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
     * <li>{@link HttpStatus#UNAUTHORIZED} if the username and password combination is not valid.</li>
     * <li>{@link HttpStatus#NOT_FOUND} if the URL is not pointing to a DHIS 2 instance or the
     *     DHIS 2 instance is not available.</li>
     * </ul>
     *
     * @return the HTTP status code of the response.
     */
    public HttpStatus getStatus()
    {
        try
        {
            URI url = HttpUtils.build( config.getResolvedUriBuilder()
                .pathSegment( "system" )
                .pathSegment( "info" ) );

            HttpGet request = withBasicAuth( new HttpGet( url ) );
            CloseableHttpResponse response = httpClient.execute( request );
            int statusCode = response.getCode();
            return HttpStatus.valueOf( statusCode );
        }
        catch ( IOException ex )
        {
            // Return status code in case of unexpected exception of type HttpResponseException

            if ( ex instanceof HttpResponseException )
            {
                int statusCode = ((HttpResponseException) ex).getStatusCode();
                return HttpStatus.valueOf( statusCode );
            }

            throw new UncheckedIOException( ex );
        }
    }

    /**
     * Returns the URL of the DHIS 2 configuration.
     *
     * @return the URL of the DHIS 2 configuration.
     */
    public String getDhis2Url()
    {
        return config.getUrl();
    }

    /**
     * Returns the username of the DHIS 2 configuration.
     *
     * @return the username of the DHIS 2 configuration.
     */
    public String getDhis2Username()
    {
        return config.getUsername();
    }

    /**
     * Saves an object using HTTP POST.
     *
     * @param path the URL path relative to the API end point.
     * @param object the object to save.
     * @return a {@link MetadataResponseMessage} holding information about the operation.
     * @throws Dhis2ClientException if the save operation failed due to client side error.
     */
    public MetadataResponseMessage saveMetadataObject( String path, Object object )
    {
        URI url = config.getResolvedUrl( path );

        return executeJsonPostPutRequest( new HttpPost( url ), object, MetadataResponseMessage.class );
    }

    /**
     * Updates an object using HTTP PUT.
     *
     * @param path the URL path relative to the API end point.
     * @param object the object to save.
     * @return a {@link MetadataResponseMessage} holding information about the operation.
     */
    public MetadataResponseMessage updateMetadataObject( String path, Object object )
    {
        URI url = config.getResolvedUrl( path );

        return executeJsonPostPutRequest( new HttpPut( url ), object, MetadataResponseMessage.class );
    }

    /**
     * Updates an object using HTTP DELETE.
     *
     * @param path the URL path relative to the API end point.
     * @param object the object to save.
     * @return a {@link MetadataResponseMessage} holding information about the operation.
     */
    public MetadataResponseMessage removeMetadataObject( String path )
    {
        URI url = config.getResolvedUrl( path );

        return executeJsonPostPutRequest( new HttpDelete( url ), null, MetadataResponseMessage.class );
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
        return getObjectFromUrl( config.getResolvedUrl( path ), klass );
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
        URI url = config.getResolvedUrl( path );

        HttpHead request = withBasicAuth( new HttpHead( url ) );

        try ( CloseableHttpResponse response = httpClient.execute( request ) )
        {
            return HttpStatus.OK.value() == response.getCode();
        }
        catch ( IOException ex )
        {
            return false;
        }
    }

    // -------------------------------------------------------------------------
    // Org unit
    // -------------------------------------------------------------------------

    /**
     * Saves a {@link OrgUnit}.
     *
     * @param orgUnit the object to save.
     * @return a {@link MetadataResponseMessage} holding information about the operation.
     */
    public MetadataResponseMessage saveOrgUnit( OrgUnit orgUnit )
    {
        return saveMetadataObject( "organisationUnits", orgUnit );
    }

    /**
     * Updates a {@link OrgUnit}.
     *
     * @param orgUnit the object to update.
     * @return a {@link MetadataResponseMessage} holding information about the operation.
     */
    public MetadataResponseMessage updateOrgUnit( OrgUnit orgUnit )
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

        return getObject( config.getResolvedUriBuilder()
            .pathSegment( "organisationUnits" )
            .pathSegment( id )
            .addParameter( "fields", String.format( "%s,parent[%s]", fields, fields ) ), Query.instance(), OrgUnit.class );
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

        return getObject( config.getResolvedUriBuilder()
            .pathSegment( "organisationUnits" )
            .addParameter( "fields", String.format( "%s,parent[%s]", fields, fields ) ), query, Objects.class )
            .getOrganisationUnits();
    }

    // -------------------------------------------------------------------------
    // Org unit group
    // -------------------------------------------------------------------------

    /**
     * Saves a {@link OrgUnitGroup}.
     *
     * @param orgUnitGroup the object to save.
     * @return a {@link MetadataResponseMessage} holding information about the operation.
     */
    public MetadataResponseMessage saveOrgUnitGroup( OrgUnitGroup orgUnitGroup )
    {
        return saveMetadataObject( "organisationUnitGroups", orgUnitGroup );
    }

    /**
     * Updates a {@link OrgUnitGroup}.
     *
     * @param orgUnitGroup the object to update.
     * @return a {@link MetadataResponseMessage} holding information about the operation.
     */
    public MetadataResponseMessage updateOrgUnitGroup( OrgUnitGroup orgUnitGroup )
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
        return getObject( config.getResolvedUriBuilder()
            .pathSegment( "organisationUnitGroups" )
            .pathSegment( id )
            .addParameter( "fields", NAME_FIELDS ), Query.instance(), OrgUnitGroup.class );
    }

    /**
     * Retrieves a list of {@link OrgUnitGroup}.
     *
     * @param query the {@link Query}.
     * @return a list of {@link OrgUnitGroup}.
     */
    public List<OrgUnitGroup> getOrgUnitGroups( Query query )
    {
        return getObject( config.getResolvedUriBuilder()
            .pathSegment( "organisationUnitGroups" )
            .addParameter( "fields", NAME_FIELDS ), query, Objects.class )
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
    public MetadataResponseMessage saveOrgUnitGroupSet( OrgUnitGroupSet orgUnitGroupSet )
    {
        return saveMetadataObject( "organisationUnitGroupSets", orgUnitGroupSet );
    }

    /**
     * Updates a {@link OrgUnitGroupSet}.
     *
     * @param orgUnitGroupSet the object to update.
     * @return a {@link ResponseMessage} holding information about the operation.
     */
    public MetadataResponseMessage updateOrgUnitGroupSet( OrgUnitGroupSet orgUnitGroupSet )
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
        return getObject( config.getResolvedUriBuilder()
            .pathSegment( "organisationUnitGroupSets" )
            .pathSegment( id )
            .addParameter( "fields", String.format( "%s,organisationUnitGroups[%s]", NAME_FIELDS, NAME_FIELDS ) ), Query.instance(), OrgUnitGroupSet.class );
    }

    /**
     * Retrieves a list of {@link OrgUnitGroupSet}.
     *
     * @param query the {@link Query}.
     * @return a list of {@link OrgUnitGroupSet}.
     */
    public List<OrgUnitGroupSet> getOrgUnitGroupSets( Query query )
    {
        return getObject( config.getResolvedUriBuilder()
            .pathSegment( "organisationUnitGroupSets" )
            .addParameter( "fields", String.format( "%s,organisationUnitGroups[%s]", NAME_FIELDS, NAME_FIELDS ) ), query, Objects.class )
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
        return getObject( config.getResolvedUriBuilder()
            .pathSegment( "organisationUnitLevels" )
            .pathSegment( id )
            .addParameter( "fields", String.format( "%s,level", ID_FIELDS ) ), Query.instance(), OrgUnitLevel.class );
    }

    /**
     * Retrieves a list of {@link OrgUnitLevel}.
     *
     * @param query the {@link Query}.
     * @return a list of {@link OrgUnitLevel}.
     */
    public List<OrgUnitLevel> getOrgUnitLevels( Query query )
    {
        return getObject( config.getResolvedUriBuilder()
            .pathSegment( "organisationUnitLevels" )
            .addParameter( "fields", String.format( "%s,level", ID_FIELDS ) ), query, Objects.class )
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

        return asList( getObject( config.getResolvedUriBuilder()
            .pathSegment( "filledOrganisationUnitLevels" ), Query.instance(), OrgUnitLevel[].class ) );
    }

    // -------------------------------------------------------------------------
    // Category option
    // -------------------------------------------------------------------------

    /**
     * Saves a {@link CategoryOption}.
     *
     * @param categoryOption the object to save.
     * @return a {@link MetadataResponseMessage} holding information about the operation.
     */
    public MetadataResponseMessage saveCategoryOption( CategoryOption categoryOption )
    {
        return saveMetadataObject( "categoryOptions", categoryOption );
    }

    /**
     * Updates a {@link CategoryOption}.
     *
     * @param categoryOption the object to update.
     * @return a {@link MetadataResponseMessage} holding information about the operation.
     */
    public MetadataResponseMessage updateCategoryOption( CategoryOption categoryOption )
    {
        return updateMetadataObject( String.format( "categoryOptions/%s", categoryOption.getId() ), categoryOption );
    }

    /**
     * Removes a {@link CategoryOption}.
     *
     * @param categoryOption the object to remove.
     * @return a {@link MetadataResponseMessage} holding information about the operation.
     */
    public MetadataResponseMessage removeCategoryOption( String id )
    {
        return removeMetadataObject( String.format( "categoryOptions/%s", id ) );
    }

    /**
     * Retrieves an {@link CategoryOption}.
     *
     * @param id the object identifier.
     * @return the {@link CategoryOption}.
     */
    public CategoryOption getCategoryOption( String id )
    {
        return getObject( config.getResolvedUriBuilder()
            .pathSegment( "categoryOptions" )
            .pathSegment( id )
            .addParameter( "fields", CATEGORY_OPTION_FIELDS ), Query.instance(), CategoryOption.class );
    }

    /**
     * Retrieves a list of {@link CategoryOption}.
     *
     * @param query the {@link Query}.
     * @return a list of {@link CategoryOption}.
     */
    public List<Category> getCategoryOptions( Query query )
    {
        return getObject( config.getResolvedUriBuilder()
            .pathSegment( "categoryOptions" )
            .addParameter( "fields", CATEGORY_OPTION_FIELDS ), query, Objects.class )
            .getCategories();
    }

    // -------------------------------------------------------------------------
    // Category
    // -------------------------------------------------------------------------

    /**
     * Saves a {@link Category}.
     *
     * @param category the object to save.
     * @return a {@link MetadataResponseMessage} holding information about the operation.
     */
    public MetadataResponseMessage saveCategory( Category category )
    {
        return saveMetadataObject( "categories", category );
    }

    /**
     * Updates a {@link Category}.
     *
     * @param category the object to update.
     * @return a {@link MetadataResponseMessage} holding information about the operation.
     */
    public MetadataResponseMessage updateCategory( CategoryOption categoryOption )
    {
        return updateMetadataObject( String.format( "categories/%s", categoryOption.getId() ), categoryOption );
    }

    /**
     * Removes a {@link Category}.
     *
     * @param category the object to remove.
     * @return a {@link MetadataResponseMessage} holding information about the operation.
     */
    public MetadataResponseMessage removeCategory( String id )
    {
        return removeMetadataObject( String.format( "categories/%s", id ) );
    }

    /**
     * Retrieves an {@link Category}.
     *
     * @param id the object identifier.
     * @return the {@link Category}.
     */
    public Category getCategory( String id )
    {
        return getObject( config.getResolvedUriBuilder()
            .pathSegment( "categories" )
            .pathSegment( id )
            .addParameter( "fields", CATEGORY_FIELDS ), Query.instance(), Category.class );
    }

    /**
     * Retrieves a list of {@link Category}.
     *
     * @param query the {@link Query}.
     * @return a list of {@link Category}.
     */
    public List<Category> getCategories( Query query )
    {
        return getObject( config.getResolvedUriBuilder()
            .pathSegment( "categories" )
            .addParameter( "fields", CATEGORY_FIELDS ), query, Objects.class )
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
        return getObject( config.getResolvedUriBuilder()
            .pathSegment( "categoryCombos" )
            .pathSegment( id )
            .addParameter( "fields", NAME_FIELDS ), Query.instance(), CategoryCombo.class );
    }

    /**
     * Retrieves a list of {@link CategoryCombo}.
     *
     * @param query the {@link Query}.
     * @return a list of {@link CategoryCombo}.
     */
    public List<CategoryCombo> getCategoryCombos( Query query )
    {
        return getObject( config.getResolvedUriBuilder()
            .pathSegment( "categoryCombos" )
            .addParameter( "fields", NAME_FIELDS ), query, Objects.class )
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
        return getObject( config.getResolvedUriBuilder()
            .pathSegment( "dataElements" )
            .pathSegment( id )
            .addParameter( "fields", DATA_ELEMENT_FIELDS ), Query.instance(), DataElement.class );
    }

    /**
     * Retrieves a list of {@link DataElement}.
     *
     * @param query the {@link Query}.
     * @return a list of {@link DataElement}.
     */
    public List<DataElement> getDataElements( Query query )
    {
        return getObject( config.getResolvedUriBuilder()
            .pathSegment( "dataElements" )
            .addParameter( "fields", DATA_ELEMENT_FIELDS ), query, Objects.class )
            .getDataElements();
    }

    // -------------------------------------------------------------------------
    // Data element group
    // -------------------------------------------------------------------------

    /**
     * Saves a {@link DataElement}.
     *
     * @param dataElementGroup the object to save.
     * @return a {@link MetadataResponseMessage} holding information about the operation.
     */
    public MetadataResponseMessage saveDataElementGroup( DataElementGroup dataElementGroup )
    {
        return saveMetadataObject( "dataElementGroups", dataElementGroup );
    }

    /**
     * Updates a {@link DataElementGroup}.
     *
     * @param dataElementGroup the object to update.
     * @return a {@link MetadataResponseMessage} holding information about the operation.
     */
    public MetadataResponseMessage updateDataElementGroup( DataElementGroup dataElementGroup )
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
        return getObject( config.getResolvedUriBuilder()
            .pathSegment( "dataElementGroups" )
            .pathSegment( id )
            .addParameter( "fields", NAME_FIELDS ), Query.instance(), DataElementGroup.class );
    }

    /**
     * Retrieves a list of {@link DataElementGroup}.
     *
     * @param query the {@link Query}.
     * @return a list of {@link DataElementGroup}.
     */
    public List<DataElementGroup> getDataElementGroups( Query query )
    {
        return getObject( config.getResolvedUriBuilder()
            .pathSegment( "dataElementGroups" )
            .addParameter( "fields", NAME_FIELDS ), query, Objects.class )
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
        return getObject( config.getResolvedUriBuilder()
            .pathSegment( "dataElementGroupSets" )
            .pathSegment( id )
            .addParameter( "fields", NAME_FIELDS ), Query.instance(), DataElementGroupSet.class );
    }

    /**
     * Retrieves a list of {@link DataElementGroupSet}.
     *
     * @param query the {@link Query}.
     * @return a list of {@link DataElementGroupSet}.
     */
    public List<DataElementGroupSet> getDataElementGroupSets( Query query )
    {
        return getObject( config.getResolvedUriBuilder()
            .pathSegment( "dataElementGroupSets" )
            .addParameter( "fields", NAME_FIELDS ), query, Objects.class )
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
        return getObject( config.getResolvedUriBuilder()
            .pathSegment( "programs" )
            .pathSegment( id )
            .addParameter( "fields", String.format( "%1$s,programType,categoryCombo[%1$s,categories[%2$s]],programStages[%1$s,programStageDataElements[%1$s,dataElement[%3$s]]]",
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

        return getObject( config.getResolvedUriBuilder()
            .pathSegment( "programs" )
            .addParameter( "fields", fieldsParam ), query, Objects.class )
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
        return getObject( config.getResolvedUriBuilder()
            .pathSegment( "categoryOptionGroupSets" )
            .pathSegment( id )
            .addParameter( "fields", NAME_FIELDS ), Query.instance(), CategoryOptionGroupSet.class );
    }

    /**
     * Retrieves a list of {@link CategoryOptionGroupSet}.
     *
     * @param query the {@link Query}.
     * @return a list of {@link CategoryOptionGroupSet}.
     */
    public List<CategoryOptionGroupSet> getCategoryOptionGroupSets( Query query )
    {
        return getObject( config.getResolvedUriBuilder()
            .pathSegment( "categoryOptionGroupSets" )
            .addParameter( "fields", NAME_FIELDS ), query, Objects.class )
            .getCategoryOptionGroupSets();
    }

    // -------------------------------------------------------------------------
    // Table hook
    // -------------------------------------------------------------------------

    /**
     * Saves a {@link TableHook}.
     *
     * @param tableHook the object to save.
     * @return a {@link MetadataResponseMessage} holding information about the operation.
     */
    public MetadataResponseMessage saveTableHook( TableHook tableHook )
    {
        return saveMetadataObject( "analyticsTableHooks", tableHook );
    }

    /**
     * Updates a {@link TableHook}.
     *
     * @param tableHook the object to update.
     * @return a {@link MetadataResponseMessage} holding information about the operation.
     */
    public MetadataResponseMessage updateTableHook( TableHook tableHook )
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
        return getObject( config.getResolvedUriBuilder()
            .pathSegment( "analyticsTableHooks" )
            .addParameter( "fields", ID_FIELDS ), query, Objects.class )
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
        return getObject( config.getResolvedUriBuilder()
            .pathSegment( "dimensions" )
            .pathSegment( id )
            .addParameter( "fields", String.format( "%s,dimensionType", ID_FIELDS ) ), Query.instance(), Dimension.class );
    }

    /**
     * Retrieves a list of {@link Dimension}.
     *
     * @param query the {@link Query}.
     * @return a list of {@link Dimension}.
     */
    public List<Dimension> getDimensions( Query query )
    {
        return getObject( config.getResolvedUriBuilder()
            .pathSegment( "dimensions" )
            .addParameter( "fields", String.format( "%s,dimensionType", ID_FIELDS ) ), query, Objects.class )
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
        return getObject( config.getResolvedUriBuilder()
            .pathSegment( "periodTypes" )
            .addParameter( "fields", "frequencyOrder,name,isoDuration,isoFormat" ), query, Objects.class )
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
        return getObject( config.getResolvedUriBuilder()
            .pathSegment( "systemSettings" ), Query.instance(), SystemSettings.class );
    }

    // -------------------------------------------------------------------------
    // Data value set
    // -------------------------------------------------------------------------

    /**
     * Saves a {@link DataValueSet}.
     *
     * @param dataValueSet the {@link DataValueSet} to save.
     * @param options the {@link DataValueSetImportOptions}.
     * @return a {@link DataValueSetResponseMessage} holding information about the operation.
     * @throws IOException if the save process failed.
     */
    public DataValueSetResponseMessage saveDataValueSet( DataValueSet dataValueSet, DataValueSetImportOptions options )
        throws IOException
    {
        URI url = getDataValueSetImportQuery( config.getResolvedUriBuilder()
            .pathSegment( "dataValueSets" ), options );

        HttpPost request = getPostRequest( url, new StringEntity( toJsonString( dataValueSet ), StandardCharsets.UTF_8 ) );

        Dhis2AsyncRequest asyncRequest = new Dhis2AsyncRequest( config, httpClient, objectMapper );

        return asyncRequest.post( request, DataValueSetResponseMessage.class );
    }

    /**
     * Saves a data value set payload in JSON format represented by the given file.
     *
     * @param file the file representing the data value set JSON payload.
     * @param options the {@link DataValueSetImportOptions}.
     * @return a {@link DataValueSetResponseMessage} holding information about the operation.
     * @throws IOException if the save process failed.
     */
    public DataValueSetResponseMessage saveDataValueSet( File file, DataValueSetImportOptions options )
        throws IOException
    {
        URI url = getDataValueSetImportQuery( config.getResolvedUriBuilder()
            .pathSegment( "dataValueSets" ), options );

        HttpPost request = getPostRequest( url, new FileEntity( file, ContentType.APPLICATION_JSON ) );

        Dhis2AsyncRequest asyncRequest = new Dhis2AsyncRequest( config, httpClient, objectMapper );

        return asyncRequest.post( request, DataValueSetResponseMessage.class );
    }

    // -------------------------------------------------------------------------
    // Analytics data value set
    // -------------------------------------------------------------------------

    /**
     * Retrieves a {@link DataValueSet}.
     *
     * @param query the {@link AnalyticsQuery}.
     * @return a {@link DataValueSet}.
     */
    public DataValueSet getAnalyticsDataValueSet( AnalyticsQuery query )
    {
        return getAnalyticsResponse( config.getResolvedUriBuilder()
            .pathSegment( "analytics" )
            .pathSegment( "dataValueSet.json" ), query, DataValueSet.class );
    }

    /**
     * Retrieves a {@link DataValueSet} and writes it to the given file.
     *
     * @param query the {@link AnalyticsQuery}.
     * @param file the {@link File}.
     * @throws IOException if writing the response to file failed.
     */
    public void writeAnalyticsDataValueSet( AnalyticsQuery query, File file )
        throws IOException
    {
        URI url = getAnalyticsQuery( config.getResolvedUriBuilder()
            .pathSegment( "analytics" )
            .pathSegment( "dataValueSet.json" ), query );

        CloseableHttpResponse response = getJsonHttpResponse( url );

        writeToFile( response, file );
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
        JobNotification[] response = getObject( config.getResolvedUriBuilder()
            .pathSegment( "system" )
            .pathSegment( "tasks" )
            .pathSegment( category.name() )
            .pathSegment( id ), Query.instance(), JobNotification[].class );

        return new ArrayList<>( Arrays.asList( response ) );
    }
}
