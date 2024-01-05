package org.hisp.dhis;

import static org.hisp.dhis.util.CollectionUtils.asList;
import static org.hisp.dhis.util.CollectionUtils.list;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.apache.hc.client5.http.HttpResponseException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.FileEntity;
import org.apache.hc.core5.http.io.entity.InputStreamEntity;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.hisp.dhis.auth.AccessTokenAuthentication;
import org.hisp.dhis.auth.BasicAuthentication;
import org.hisp.dhis.auth.CookieAuthentication;
import org.hisp.dhis.model.Category;
import org.hisp.dhis.model.CategoryCombo;
import org.hisp.dhis.model.CategoryOption;
import org.hisp.dhis.model.CategoryOptionCombo;
import org.hisp.dhis.model.CategoryOptionGroup;
import org.hisp.dhis.model.CategoryOptionGroupSet;
import org.hisp.dhis.model.Dashboard;
import org.hisp.dhis.model.DataElement;
import org.hisp.dhis.model.DataElementGroup;
import org.hisp.dhis.model.DataElementGroupSet;
import org.hisp.dhis.model.DataSet;
import org.hisp.dhis.model.Dimension;
import org.hisp.dhis.model.ImportStrategy;
import org.hisp.dhis.model.Indicator;
import org.hisp.dhis.model.IndicatorType;
import org.hisp.dhis.model.Me;
import org.hisp.dhis.model.Objects;
import org.hisp.dhis.model.OptionSet;
import org.hisp.dhis.model.OrgUnit;
import org.hisp.dhis.model.OrgUnitGroup;
import org.hisp.dhis.model.OrgUnitGroupSet;
import org.hisp.dhis.model.OrgUnitLevel;
import org.hisp.dhis.model.PeriodType;
import org.hisp.dhis.model.Program;
import org.hisp.dhis.model.ProgramIndicator;
import org.hisp.dhis.model.SystemInfo;
import org.hisp.dhis.model.SystemSettings;
import org.hisp.dhis.model.TableHook;
import org.hisp.dhis.model.datastore.DataStoreEntries;
import org.hisp.dhis.model.datastore.EntryMetadata;
import org.hisp.dhis.model.datavalueset.DataValueSet;
import org.hisp.dhis.model.datavalueset.DataValueSetImportOptions;
import org.hisp.dhis.model.event.Event;
import org.hisp.dhis.model.event.Events;
import org.hisp.dhis.model.event.EventsResult;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.query.analytics.AnalyticsQuery;
import org.hisp.dhis.query.datavalue.DataValueSetQuery;
import org.hisp.dhis.query.event.EventsQuery;
import org.hisp.dhis.request.orgunit.OrgUnitMergeRequest;
import org.hisp.dhis.request.orgunit.OrgUnitSplitRequest;
import org.hisp.dhis.response.Dhis2ClientException;
import org.hisp.dhis.response.HttpStatus;
import org.hisp.dhis.response.Response;
import org.hisp.dhis.response.datavalueset.DataValueSetResponse;
import org.hisp.dhis.response.event.EventResponse;
import org.hisp.dhis.response.job.JobCategory;
import org.hisp.dhis.response.job.JobNotification;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.response.objects.ObjectsResponse;
import org.hisp.dhis.util.HttpUtils;

/**
 * DHIS 2 API client for HTTP requests and responses. Request and response
 * bodies are in JSON format. Client is tread-safe and suitable for reuse.
 *
 * @author Lars Helge Overland
 */
public class Dhis2
    extends BaseDhis2
{
    /**
     * Authority which provides superuser authorization in DHIS 2.
     */
    public static final String SUPER_AUTH = "ALL";

    public Dhis2( Dhis2Config config )
    {
        super( config );
    }

    /**
     * Creates a {@link Dhis2} instance configured for basic authentication.
     *
     * @param url the URL to the DHIS 2 instance, do not include the
     *        {@code /api} part or a trailing {@code /}.
     * @param username the DHIS 2 account username.
     * @param password the DHIS 2 account password.
     * @return a {@link Dhis2} instance.
     */
    public static Dhis2 withBasicAuth( String url, String username, String password )
    {
        return new Dhis2( new Dhis2Config( url, new BasicAuthentication( username, password ) ) );
    }

    /**
     * Creates a {@link Dhis2} instance configured for access token (PAT)
     * authentication.
     *
     * @param url the URL to the DHIS 2 instance, do not include the
     *        {@code /api} part or a trailing {@code /}.
     * @param accessToken the access token (PAT).
     * @return a {@link Dhis2} instance.
     */
    public static Dhis2 withAccessTokenAuth( String url, String accessToken )
    {
        return new Dhis2( new Dhis2Config( url, new AccessTokenAuthentication( accessToken ) ) );
    }

    /**
     * Creates a {@link Dhis2} instance configured for cookie authentication.
     *
     * @param url the URL to the DHIS 2 instance, do not include the
     *        {@code /api} part or a trailing {@code /}.
     * @param sessionId the session identifier.
     * @return a {@link Dhis2} instance.
     */
    public static Dhis2 withCookieAuth( String url, String sessionId )
    {
        return new Dhis2( new Dhis2Config( url, new CookieAuthentication( sessionId ) ) );
    }

    // -------------------------------------------------------------------------
    // Generic methods
    // -------------------------------------------------------------------------

    /**
     * Checks the status of the DHIS 2 instance. Returns various status codes
     * describing the status:
     *
     * <ul>
     * <li>{@link HttpStatus#OK} if instance is available and authentication isf
     * successful.</li>
     * <li>{@link HttpStatus#UNAUTHORIZED} if the username and password
     * combination is not valid.</li>
     * <li>{@link HttpStatus#NOT_FOUND} if the URL is not pointing to a DHIS 2
     * instance or the DHIS 2 instance is not available.</li>
     * </ul>
     *
     * @return the HTTP status code of the response.
     */
    public HttpStatus getStatus()
    {
        URI url = HttpUtils.build( config.getResolvedUriBuilder()
            .appendPath( "system" )
            .appendPath( "info" ) );

        HttpGet request = withAuth( new HttpGet( url ) );

        try ( CloseableHttpResponse response = httpClient.execute( request ) )
        {
            int statusCode = response.getCode();
            return HttpStatus.valueOf( statusCode );
        }
        catch ( IOException ex )
        {
            // Return status code for exception of type HttpResponseException

            if ( ex instanceof HttpResponseException )
            {
                int statusCode = ((HttpResponseException) ex).getStatusCode();
                return HttpStatus.valueOf( statusCode );
            }

            throw new Dhis2ClientException( "Failed to get system info", ex );
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
     * Indicates whether an object exists at the given URL path using HTTP HEAD.
     *
     * @param path the URL path relative to the API end point.
     * @return true if the object exists.
     */
    public boolean objectExists( String path )
    {
        return objectExists( config.getResolvedUriBuilder()
            .appendPath( path ) );
    }

    // -------------------------------------------------------------------------
    // System info
    // -------------------------------------------------------------------------

    /**
     * Retrieves the {@link SystemInfo}.
     *
     * @return the {@link SystemInfo}.
     */
    public SystemInfo getSystemInfo()
    {
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "system" )
            .appendPath( "info" ), Query.instance(), SystemInfo.class );
    }

    // -------------------------------------------------------------------------
    // User and authorization
    // -------------------------------------------------------------------------

    /**
     * Retrieves the list of authorities granted to the authenticated user.
     *
     * @return a list of authorities.
     */
    @SuppressWarnings( "unchecked" )
    public List<String> getUserAuthorization()
    {
        return getObject( "me/authorization", List.class );
    }

    /**
     * Retrieves information about the current authenticated user.
     *
     * @return the current authenticated user.
     */
    public Me getMe()
    {
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "me" )
            .addParameter( FIELDS_PARAM, ME_FIELDS ), Query.instance(), Me.class );
    }

    // -------------------------------------------------------------------------
    // Data store
    // -------------------------------------------------------------------------

    /**
     * Saves a data store entry.
     *
     * @param namespace the namespace.
     * @param key the key.
     * @param object the object.
     * @param <T> the object type.
     * @return {@link Response} holding information about the operation.
     */
    public <T> Response saveDataStoreEntry( String namespace, String key, T object )
    {
        return saveObject( getDataStorePath( namespace, key ), object, Response.class );
    }

    /**
     * Updates a data store entry.
     *
     * @param namespace the namespace.
     * @param key the key.
     * @param object the object.
     * @param <T> the object type.
     * @return {@link Response} holding information about the operation.
     */
    public <T> Response updateDataStoreEntry( String namespace, String key, T object )
    {
        return updateObject( getDataStorePath( namespace, key ), object, Response.class );
    }

    /**
     * Retrieves all data store namespaces.
     *
     * @return the list of namespaces.
     */
    @SuppressWarnings( "unchecked" )
    public List<String> getDataStoreNamespaces()
    {
        return getObject( "dataStore", List.class );
    }

    /**
     * Retrieves all data store keys for the given namespace.
     *
     * @param namespace the namespace.
     * @return the list of keys.
     */
    @SuppressWarnings( "unchecked" )
    public List<String> getDataStoreKeys( String namespace )
    {
        return getObject( String.format( "dataStore/%s", namespace ), List.class );
    }

    /**
     * Retrieves a data store entry.
     *
     * @param namespace the namespace.
     * @param key the key.
     * @param type the class type of the object to retrieve.
     * @param <T> the object type.
     * @return the object associated with the given namespace and key.
     */
    public <T> T getDataStoreEntry( String namespace, String key, Class<T> type )
    {
        return getObject( getDataStorePath( namespace, key ), type );
    }

    /**
     * Retrieves a list of data store entries.
     *
     * @param namespace the namespace.
     * @param fields the list of fields, must not be empty.
     * @param type the class type of the object to retrieve.
     * @return a list of data store entries.
     */
    public List<Map<String, Object>> getDatastoreEntries( String namespace, List<String> fields )
    {
        Validate.notEmpty( fields );

        String fieldsValue = String.join( ",", fields );

        return getObject( config.getResolvedUriBuilder()
            .appendPath( "dataStore" )
            .appendPath( namespace )
            .addParameter( FIELDS_PARAM, fieldsValue ),
            Query.instance()
                // Temporary solution for consistent response format, see
                // DHIS2-16422
                .setPaging( 1, 100_000 ),
            DataStoreEntries.class )
                .getEntries();
    }

    /**
     * Retrieves metadata for a data store entry.
     *
     * @param namespace the namespace.
     * @param key the key.
     * @return the {@link EntryMetadata}.
     */
    public EntryMetadata getDataStoreEntryMetadata( String namespace, String key )
    {
        String path = String.format( "dataStore/%s/%s/metaData", namespace, key );

        return getObject( path, EntryMetadata.class );
    }

    /**
     * Removes a data store entry.
     *
     * @param namespace the namespace.
     * @param key the key.
     * @return {@link Response} holding information about the operation.
     */
    public Response removeDataStoreEntry( String namespace, String key )
    {
        return removeObject( getDataStorePath( namespace, key ), Response.class );
    }

    /**
     * Removes a namespace including all entries.
     *
     * @param namespace the namespace.
     * @return {@link Response} holding information about the operation.
     */
    public Response removeDataStoreNamespace( String namespace )
    {
        return removeObject( String.format( "dataStore/%s", namespace ), Response.class );
    }

    /**
     * Returns the path to a data store entry.
     *
     * @param namespace the namespace.
     * @param key the key.
     * @return the path to a data store entry.
     */
    private String getDataStorePath( String namespace, String key )
    {
        return String.format( "dataStore/%s/%s", namespace, key );
    }

    // -------------------------------------------------------------------------
    // Org unit
    // -------------------------------------------------------------------------

    /**
     * Saves a {@link OrgUnit}.
     *
     * @param orgUnit the object to save.
     * @return {@link ObjectResponse} holding information about the operation.
     */
    public ObjectResponse saveOrgUnit( OrgUnit orgUnit )
    {
        return saveMetadataObject( "organisationUnits", orgUnit );
    }

    /**
     * Saves or updates the list of {@link OrgUnit}.
     *
     * @param orgUnits the list of {@link OrgUnit}.
     * @return {@link ObjectsResponse} holding information about the operation.
     */
    public ObjectsResponse saveOrgUnits( List<OrgUnit> orgUnits )
    {
        return saveMetadataObjects( new Objects().setOrganisationUnits( orgUnits ) );
    }

    /**
     * Updates a {@link OrgUnit}.
     *
     * @param orgUnit the object to update.
     * @return {@link ObjectResponse} holding information about the operation.
     */
    public ObjectResponse updateOrgUnit( OrgUnit orgUnit )
    {
        return updateMetadataObject( String.format( "organisationUnits/%s", orgUnit.getId() ), orgUnit );
    }

    /**
     * Removes a {@link OrgUnit}.
     *
     * @param id the identifier of the object to remove.
     * @return {@link ObjectResponse} holding information about the operation.
     */
    public ObjectResponse removeOrgUnit( String id )
    {
        return removeMetadataObject( String.format( "organisationUnits/%s", id ) );
    }

    /**
     * Retrieves an {@link OrgUnit}.
     *
     * @param id the object identifier.
     * @return the {@link OrgUnit}.
     * @throws Dhis2ClientException if the object does not exist.
     */
    public OrgUnit getOrgUnit( String id )
    {
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "organisationUnits" )
            .appendPath( id )
            .addParameter( FIELDS_PARAM, ORG_UNIT_FIELDS ), Query.instance(), OrgUnit.class );
    }

    /**
     * Indicates whether an {@link OrgUnit} exists.
     *
     * @param id the object identifier.
     * @return true if the object exists.
     */
    public boolean isOrgUnit( String id )
    {
        return objectExists( config.getResolvedUriBuilder()
            .appendPath( "organisationUnits" )
            .appendPath( id ) );
    }

    /**
     * Retrieves a list of {@link OrgUnit} which represents the sub-hierarchy of
     * the given parent org unit.
     *
     * @param id the identifier of the parent org unit.
     * @param level the org unit level, relative to the level of the parent org
     *        unit, where 1 is the level immediately below.
     * @param query the {@link Query}.
     * @return list of {@link OrgUnit}.
     */
    public List<OrgUnit> getOrgUnitSubHierarchy( String id, Integer level, Query query )
    {
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "organisationUnits" )
            .appendPath( id )
            .addParameter( FIELDS_PARAM, ORG_UNIT_FIELDS )
            .addParameter( "level", String.valueOf( level ) ), query, Objects.class )
                .getOrganisationUnits();
    }

    /**
     * Retrieves a list of {@link OrgUnit}.
     *
     * @param query the {@link Query}.
     * @return list of {@link OrgUnit}.
     */
    public List<OrgUnit> getOrgUnits( Query query )
    {
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "organisationUnits" )
            .addParameter( FIELDS_PARAM, ORG_UNIT_FIELDS ), query, Objects.class )
                .getOrganisationUnits();
    }

    // -------------------------------------------------------------------------
    // Org unit merge and split
    // -------------------------------------------------------------------------

    /**
     * Performs an org unit split operation.
     *
     * @param request the {@link OrgUnitSplitRequest}.
     * @return the {@link Response} holding information about the operation.
     */
    public Response splitOrgUnit( OrgUnitSplitRequest request )
    {
        URI url = config.getResolvedUrl( "organisationUnits/split" );

        return executeJsonPostPutRequest( new HttpPost( url ), request, Response.class );
    }

    /**
     * Performs an org unit merge operation.
     *
     * @param request the {@link OrgUnitMergeRequest request}.
     * @return the {@link Response} holding information about the operation.
     */
    public Response mergeOrgUnits( OrgUnitMergeRequest request )
    {
        URI url = config.getResolvedUrl( "organisationUnits/merge" );

        return executeJsonPostPutRequest( new HttpPost( url ), request, Response.class );
    }

    // -------------------------------------------------------------------------
    // Org unit group
    // -------------------------------------------------------------------------

    /**
     * Saves a {@link OrgUnitGroup}.
     *
     * @param orgUnitGroup the object to save.
     * @return {@link ObjectResponse} holding information about the operation.
     */
    public ObjectResponse saveOrgUnitGroup( OrgUnitGroup orgUnitGroup )
    {
        return saveMetadataObject( "organisationUnitGroups", orgUnitGroup );
    }

    /**
     * Saves or updates the list of {@link OrgUnitGroup}.
     *
     * @param orgUnitGroups the list of {@link OrgUnitGroup}.
     * @return {@link ObjectsResponse} holding information about the operation.
     */
    public ObjectsResponse saveOrgUnitGroups( List<OrgUnitGroup> orgUnitGroups )
    {
        return saveMetadataObjects( new Objects().setOrganisationUnitGroups( orgUnitGroups ) );
    }

    /**
     * Updates a {@link OrgUnitGroup}.
     *
     * @param orgUnitGroup the object to update.
     * @return {@link ObjectResponse} holding information about the operation.
     */
    public ObjectResponse updateOrgUnitGroup( OrgUnitGroup orgUnitGroup )
    {
        return updateMetadataObject( String.format( "organisationUnitGroups/%s", orgUnitGroup.getId() ), orgUnitGroup );
    }

    /**
     * Removes a {@link OrgUnitGroup}.
     *
     * @param id the identifier of the object to remove.
     * @return {@link ObjectResponse} holding information about the operation.
     */
    public ObjectResponse removeOrgUnitGroup( String id )
    {
        return removeMetadataObject( String.format( "organisationUnitGroups/%s", id ) );
    }

    /**
     * Retrieves an {@link OrgUnitGroup}.
     *
     * @param id the object identifier.
     * @return the {@link OrgUnitGroup}.
     * @throws Dhis2ClientException if the object does not exist.
     */
    public OrgUnitGroup getOrgUnitGroup( String id )
    {
        String fieldsParams = String.format(
            "%1$s,organisationUnits[%2$s]", NAME_FIELDS, ORG_UNIT_FIELDS );

        return getObject( config.getResolvedUriBuilder()
            .appendPath( "organisationUnitGroups" )
            .appendPath( id )
            .addParameter( FIELDS_PARAM, fieldsParams ), Query.instance(), OrgUnitGroup.class );
    }

    /**
     * Retrieves a list of {@link OrgUnitGroup}.
     *
     * @param query the {@link Query}.
     * @return list of {@link OrgUnitGroup}.
     */
    public List<OrgUnitGroup> getOrgUnitGroups( Query query )
    {
        String fieldsParams = query.isExpandAssociations() ? String.format(
            "%1$s,organisationUnits[id,code,name]", NAME_FIELDS )
            : NAME_FIELDS;

        return getObject( config.getResolvedUriBuilder()
            .appendPath( "organisationUnitGroups" )
            .addParameter( FIELDS_PARAM, fieldsParams ), query, Objects.class )
                .getOrganisationUnitGroups();
    }

    /**
     * Adds the program to the org unit.
     *
     * @param orgUnitGroup the org unit group identifier.
     * @param orgUnit the org unit identifier.
     * @return a {@link Response}.
     */
    public Response addOrgUnitToOrgUnitGroup( String orgUnitGroup, String orgUnit )
    {
        return addToCollection( "organisationUnitGroups", orgUnitGroup, "organisationUnits", orgUnit );
    }

    // -------------------------------------------------------------------------
    // Org unit group set
    // -------------------------------------------------------------------------

    /**
     * Saves a {@link OrgUnitGroupSet}.
     *
     * @param orgUnitGroupSet the object to save.
     * @return {@link ObjectResponse} holding information about the operation.
     */
    public ObjectResponse saveOrgUnitGroupSet( OrgUnitGroupSet orgUnitGroupSet )
    {
        return saveMetadataObject( "organisationUnitGroupSets", orgUnitGroupSet );
    }

    /**
     * Saves or updates the list of {@link OrgUnitGroupSet}.
     *
     * @param orgUnitGroupSets the list of {@link OrgUnitGroupSet}.
     * @return {@link ObjectsResponse} holding information about the operation.
     */
    public ObjectsResponse saveOrgUnitGroupSets( List<OrgUnitGroupSet> orgUnitGroupSets )
    {
        return saveMetadataObjects( new Objects().setOrganisationUnitGroupSets( orgUnitGroupSets ) );
    }

    /**
     * Updates a {@link OrgUnitGroupSet}.
     *
     * @param orgUnitGroupSet the object to update.
     * @return {@link ObjectResponse} holding information about the operation.
     */
    public ObjectResponse updateOrgUnitGroupSet( OrgUnitGroupSet orgUnitGroupSet )
    {
        return updateMetadataObject( String.format( "organisationUnitGroupSets/%s", orgUnitGroupSet.getId() ),
            orgUnitGroupSet );
    }

    /**
     * Removes a {@link OrgUnitGroupSet}.
     *
     * @param id the identifier of the object to remove.
     * @return {@link ObjectResponse} holding information about the operation.
     */
    public ObjectResponse removeOrgUnitGroupSet( String id )
    {
        return removeMetadataObject( String.format( "organisationUnitGroupSets/%s", id ) );
    }

    /**
     * Retrieves an {@link OrgUnitGroupSet}.
     *
     * @param id the object identifier.
     * @return the {@link OrgUnitGroupSet}.
     * @throws Dhis2ClientException if the object does not exist.
     */
    public OrgUnitGroupSet getOrgUnitGroupSet( String id )
    {
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "organisationUnitGroupSets" )
            .appendPath( id )
            .addParameter( FIELDS_PARAM, ORG_UNIT_GROUP_SET_FIELDS ),
            Query.instance(), OrgUnitGroupSet.class );
    }

    /**
     * Retrieves a list of {@link OrgUnitGroupSet}.
     *
     * @param query the {@link Query}.
     * @return list of {@link OrgUnitGroupSet}.
     */
    public List<OrgUnitGroupSet> getOrgUnitGroupSets( Query query )
    {
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "organisationUnitGroupSets" )
            .addParameter( FIELDS_PARAM, ORG_UNIT_GROUP_SET_FIELDS ),
            query, Objects.class )
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
     * @throws Dhis2ClientException if the object does not exist.
     */
    public OrgUnitLevel getOrgUnitLevel( String id )
    {
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "organisationUnitLevels" )
            .appendPath( id )
            .addParameter( FIELDS_PARAM, String.format( "%s,level", ID_FIELDS ) ), Query.instance(),
            OrgUnitLevel.class );
    }

    /**
     * Retrieves a list of {@link OrgUnitLevel}.
     *
     * @param query the {@link Query}.
     * @return list of {@link OrgUnitLevel}.
     */
    public List<OrgUnitLevel> getOrgUnitLevels( Query query )
    {
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "organisationUnitLevels" )
            .addParameter( FIELDS_PARAM, String.format( "%s,level", ID_FIELDS ) ), query, Objects.class )
                .getOrganisationUnitLevels();
    }

    /**
     * Retrieves a list of "filled" {@link OrgUnitLevel}, meaning any gaps in
     * the persisted levels will be inserted by generated levels.
     *
     * @return list of {@link OrgUnitLevel}.
     */
    public List<OrgUnitLevel> getFilledOrgUnitLevels()
    {
        // Using array, DHIS 2 should have used a wrapper entity

        return asList( getObject( config.getResolvedUriBuilder()
            .appendPath( "filledOrganisationUnitLevels" ), Query.instance(), OrgUnitLevel[].class ) );
    }

    // -------------------------------------------------------------------------
    // Category option
    // -------------------------------------------------------------------------

    /**
     * Saves a {@link CategoryOption}.
     *
     * @param categoryOption the object to save.
     * @return {@link ObjectResponse} holding information about the operation.
     */
    public ObjectResponse saveCategoryOption( CategoryOption categoryOption )
    {
        return saveMetadataObject( "categoryOptions", categoryOption );
    }

    /**
     * Saves or updates the list of {@link CategoryOption}.
     *
     * @param categoryOptions the list of {@link CategoryOption}.
     * @return {@link ObjectsResponse} holding information about the operation.
     */
    public ObjectsResponse saveCategoryOptions( List<CategoryOption> categoryOptions )
    {
        return saveMetadataObjects( new Objects().setCategoryOptions( categoryOptions ) );
    }

    /**
     * Updates a {@link CategoryOption}.
     *
     * @param categoryOption the object to update.
     * @return {@link ObjectResponse} holding information about the operation.
     */
    public ObjectResponse updateCategoryOption( CategoryOption categoryOption )
    {
        return updateMetadataObject( String.format( "categoryOptions/%s", categoryOption.getId() ), categoryOption );
    }

    /**
     * Removes a {@link CategoryOption}.
     *
     * @param id the identifier of the object to remove.
     * @return {@link ObjectResponse} holding information about the operation.
     */
    public ObjectResponse removeCategoryOption( String id )
    {
        return removeMetadataObject( String.format( "categoryOptions/%s", id ) );
    }

    /**
     * Retrieves an {@link CategoryOption}.
     *
     * @param id the object identifier.
     * @return the {@link CategoryOption}.
     * @throws Dhis2ClientException if the object does not exist.
     */
    public CategoryOption getCategoryOption( String id )
    {
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "categoryOptions" )
            .appendPath( id )
            .addParameter( FIELDS_PARAM, CATEGORY_OPTION_FIELDS ), Query.instance(), CategoryOption.class );
    }

    /**
     * Retrieves a list of {@link CategoryOption}.
     *
     * @param query the {@link Query}.
     * @return list of {@link CategoryOption}.
     */
    public List<CategoryOption> getCategoryOptions( Query query )
    {
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "categoryOptions" )
            .addParameter( FIELDS_PARAM, CATEGORY_OPTION_FIELDS ), query, Objects.class )
                .getCategoryOptions();
    }

    // -------------------------------------------------------------------------
    // Category
    // -------------------------------------------------------------------------

    /**
     * Saves a {@link Category}.
     *
     * @param category the object to save.
     * @return {@link ObjectResponse} holding information about the operation.
     */
    public ObjectResponse saveCategory( Category category )
    {
        return saveMetadataObject( "categories", category );
    }

    /**
     * Saves or updates the list of {@link Category}.
     *
     * @param categories the list of {@link Category}.
     * @return {@link ObjectsResponse} holding information about the operation.
     */
    public ObjectsResponse saveCategories( List<Category> categories )
    {
        return saveMetadataObjects( new Objects().setCategories( categories ) );
    }

    /**
     * Updates a {@link Category}.
     *
     * @param category the object to update.
     * @return {@link ObjectResponse} holding information about the operation.
     */
    public ObjectResponse updateCategory( Category category )
    {
        return updateMetadataObject( String.format( "categories/%s", category.getId() ), category );
    }

    /**
     * Removes a {@link Category}.
     *
     * @param id the identifier of the object to remove.
     * @return {@link ObjectResponse} holding information about the operation.
     */
    public ObjectResponse removeCategory( String id )
    {
        return removeMetadataObject( String.format( "categories/%s", id ) );
    }

    /**
     * Retrieves an {@link Category}.
     *
     * @param id the object identifier.
     * @return the {@link Category}.
     * @throws Dhis2ClientException if the object does not exist.
     */
    public Category getCategory( String id )
    {
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "categories" )
            .appendPath( id )
            .addParameter( FIELDS_PARAM, CATEGORY_FIELDS ), Query.instance(), Category.class );
    }

    /**
     * Indicates whether a {@link Category} exists.
     *
     * @param id the object identifier.
     * @return true if the object exists.
     */
    public boolean isCategory( String id )
    {
        return objectExists( config.getResolvedUriBuilder()
            .appendPath( "categories" )
            .appendPath( id ) );
    }

    /**
     * Retrieves a list of {@link Category}.
     *
     * @param query the {@link Query}.
     * @return list of {@link Category}.
     */
    public List<Category> getCategories( Query query )
    {
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "categories" )
            .addParameter( FIELDS_PARAM, CATEGORY_FIELDS ), query, Objects.class )
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
     * @throws Dhis2ClientException if the object does not exist.
     */
    public CategoryCombo getCategoryCombo( String id )
    {
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "categoryCombos" )
            .appendPath( id )
            .addParameter( FIELDS_PARAM, CATEGORY_COMBO_FIELDS ), Query.instance(), CategoryCombo.class );
    }

    /**
     * Retrieves a list of {@link CategoryCombo}.
     *
     * @param query the {@link Query}.
     * @return list of {@link CategoryCombo}.
     */
    public List<CategoryCombo> getCategoryCombos( Query query )
    {
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "categoryCombos" )
            .addParameter( FIELDS_PARAM, CATEGORY_COMBO_FIELDS ), query, Objects.class )
                .getCategoryCombos();
    }

    // -------------------------------------------------------------------------
    // Category option combo
    // -------------------------------------------------------------------------

    /**
     * Retrieves an {@link CategoryOptionCombo}.
     *
     * @param id the object identifier.
     * @return the {@link CategoryOptionCombo}.
     * @throws Dhis2ClientException if the object does not exist.
     */
    public CategoryOptionCombo getCategoryOptionCombo( String id )
    {
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "categoryOptionCombos" )
            .appendPath( id )
            .addParameter( FIELDS_PARAM, CATEGORY_OPTION_COMBO_FIELDS ), Query.instance(), CategoryOptionCombo.class );
    }

    /**
     * Retrieves a list of {@link CategoryOptionCombo}.
     *
     * @param query the {@link Query}.
     * @return list of {@link CategoryOptionCombo}.
     */
    public List<CategoryOptionCombo> getCategoryOptionCombos( Query query )
    {
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "categoryOptionCombos" )
            .addParameter( FIELDS_PARAM, CATEGORY_OPTION_COMBO_FIELDS ), query, Objects.class )
                .getCategoryOptionCombos();
    }

    // -------------------------------------------------------------------------
    // Data element
    // -------------------------------------------------------------------------

    /**
     * Saves a {@link DataElement}.
     *
     * @param dataElement the object to save.
     * @return {@link ObjectResponse} holding information about the operation.
     */
    public ObjectResponse saveDataElement( DataElement dataElement )
    {
        return saveMetadataObject( "dataElements", dataElement );
    }

    /**
     * Saves or updates the list of {@link DataElement}.
     *
     * @param dataElements the list of {@link DataElement}.
     * @return {@link ObjectsResponse} holding information about the operation.
     */
    public ObjectsResponse saveDataElements( List<DataElement> dataElements )
    {
        return saveMetadataObjects( new Objects().setDataElements( dataElements ) );
    }

    /**
     * Updates a {@link DataElement}.
     *
     * @param dataElement the object to update.
     * @return {@link ObjectResponse} holding information about the operation.
     */
    public ObjectResponse updateDataElement( DataElement dataElement )
    {
        return updateMetadataObject( String.format( "dataElements/%s", dataElement.getId() ), dataElement );
    }

    /**
     * Removes a {@link DataElement}.
     *
     * @param id the identifier of the object to remove.
     * @return {@link ObjectResponse} holding information about the operation.
     */
    public ObjectResponse removeDataElement( String id )
    {
        return removeMetadataObject( String.format( "dataElements/%s", id ) );
    }

    /**
     * Retrieves an {@link DataElement}.
     *
     * @param id the object identifier.
     * @return the {@link DataElement}.
     * @throws Dhis2ClientException if the object does not exist.
     */
    public DataElement getDataElement( String id )
    {
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "dataElements" )
            .appendPath( id )
            .addParameter( FIELDS_PARAM, DATA_ELEMENT_FIELDS ), Query.instance(), DataElement.class );
    }

    /**
     * Indicates whether a {@link DataElement} exists.
     *
     * @param id the object identifier.
     * @return true if the object exists.
     */
    public boolean isDataElement( String id )
    {
        return objectExists( config.getResolvedUriBuilder()
            .appendPath( "dataElements" )
            .appendPath( id ) );
    }

    /**
     * Retrieves a list of {@link DataElement}.
     *
     * @param query the {@link Query}.
     * @return list of {@link DataElement}.
     */
    public List<DataElement> getDataElements( Query query )
    {
        String fieldsParam = query.isExpandAssociations()
            ? String.format( "%1$s,dataElementGroups[id,code,name,groupSets[id,code,name]],"
                + "dataSetElements[dataSet[id,name,periodType,workflow[id,name]]]", DATA_ELEMENT_FIELDS )
            : DATA_ELEMENT_FIELDS;
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "dataElements" )
            .addParameter( FIELDS_PARAM, fieldsParam ), query, Objects.class )
                .getDataElements();
    }

    // -------------------------------------------------------------------------
    // Data element group
    // -------------------------------------------------------------------------

    /**
     * Saves a {@link DataElement}.
     *
     * @param dataElementGroup the object to save.
     * @return {@link ObjectResponse} holding information about the operation.
     */
    public ObjectResponse saveDataElementGroup( DataElementGroup dataElementGroup )
    {
        return saveMetadataObject( "dataElementGroups", dataElementGroup );
    }

    /**
     * Saves or updates the list of {@link DataElementGroup}.
     *
     * @param dataElementGroups the list of {@link DataElementGroup}.
     * @return {@link ObjectsResponse} holding information about the operation.
     */
    public ObjectsResponse saveDataElementGroups( List<DataElementGroup> dataElementGroups )
    {
        return saveMetadataObjects( new Objects().setDataElementGroups( dataElementGroups ) );
    }

    /**
     * Updates a {@link DataElementGroup}.
     *
     * @param dataElementGroup the object to update.
     * @return {@link ObjectResponse} holding information about the operation.
     */
    public ObjectResponse updateDataElementGroup( DataElementGroup dataElementGroup )
    {
        return updateMetadataObject( String.format( "dataElementGroups/%s", dataElementGroup.getId() ),
            dataElementGroup );
    }

    /**
     * Removes a {@link DataElementGroup}.
     *
     * @param id the identifier of the object to remove.
     * @return {@link ObjectResponse} holding information about the operation.
     */
    public ObjectResponse removeDataElementGroup( String id )
    {
        return removeMetadataObject( String.format( "dataElementGroups/%s", id ) );
    }

    /**
     * Retrieves an {@link DataElementGroup}.
     *
     * @param id the object identifier.
     * @return the {@link DataElementGroup}.
     * @throws Dhis2ClientException if the object does not exist.
     */
    public DataElementGroup getDataElementGroup( String id )
    {
        String fieldsParams = String.format(
            "%1$s,dataElements[%2$s]", NAME_FIELDS, DATA_ELEMENT_FIELDS );

        return getObject( config.getResolvedUriBuilder()
            .appendPath( "dataElementGroups" )
            .appendPath( id )
            .addParameter( FIELDS_PARAM, fieldsParams ), Query.instance(), DataElementGroup.class );
    }

    /**
     * Retrieves a list of {@link DataElementGroup}.
     *
     * @param query the {@link Query}.
     * @return list of {@link DataElementGroup}.
     */
    public List<DataElementGroup> getDataElementGroups( Query query )
    {
        String fieldsParams = query.isExpandAssociations() ? String.format(
            "%1$s,dataElements[%2$s]", NAME_FIELDS, DATA_ELEMENT_FIELDS )
            : NAME_FIELDS;
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "dataElementGroups" )
            .addParameter( FIELDS_PARAM, fieldsParams ), query, Objects.class )
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
            .appendPath( "dataElementGroupSets" )
            .appendPath( id )
            .addParameter( FIELDS_PARAM, DATA_ELEMENT_GROUP_SET_FIELDS ), Query.instance(), DataElementGroupSet.class );
    }

    /**
     * Retrieves a list of {@link DataElementGroupSet}.
     *
     * @param query the {@link Query}.
     * @return list of {@link DataElementGroupSet}.
     */
    public List<DataElementGroupSet> getDataElementGroupSets( Query query )
    {
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "dataElementGroupSets" )
            .addParameter( FIELDS_PARAM, DATA_ELEMENT_GROUP_SET_FIELDS ), query, Objects.class )
                .getDataElementGroupSets();
    }

    // -------------------------------------------------------------------------
    // Indicator
    // -------------------------------------------------------------------------

    /**
     * Retrieves an {@link Indicator}.
     *
     * @param id the object identifier.
     * @return the {@link Indicator}.
     */
    public Indicator getIndicator( String id )
    {
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "indicators" )
            .appendPath( id )
            .addParameter( FIELDS_PARAM, INDICATOR_FIELDS ), Query.instance(), Indicator.class );
    }

    /**
     * Retrieves a list of {@link Indicator}.
     *
     * @param query the {@link Query}.
     * @return list of {@link Indicator}.
     */
    public List<Indicator> getIndicators( Query query )
    {
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "indicators" )
            .addParameter( FIELDS_PARAM, INDICATOR_FIELDS ), query, Objects.class )
                .getIndicators();
    }

    // -------------------------------------------------------------------------
    // Indicator Type
    // -------------------------------------------------------------------------

    /**
     * Retrieves an {@link IndicatorType}.
     *
     * @param id the object identifier.
     * @return the {@link IndicatorType}.
     */
    public IndicatorType getIndicatorType( String id )
    {
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "indicatorTypes" )
            .appendPath( id )
            .addParameter( FIELDS_PARAM, INDICATOR_TYPE_FIELDS ), Query.instance(), IndicatorType.class );
    }

    /**
     * Retrieves a list of {@link IndicatorType}.
     *
     * @param query the {@link Query}.
     * @return list of {@link IndicatorType}.
     */
    public List<IndicatorType> getIndicatorTypes( Query query )
    {
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "indicators" )
            .addParameter( FIELDS_PARAM, INDICATOR_FIELDS ), query, Objects.class )
                .getIndicatorTypes();
    }

    // -------------------------------------------------------------------------
    // DataSet
    // -------------------------------------------------------------------------

    /**
     * Retrieves an {@link DataSet}.
     *
     * @param id the object identifier.
     * @return the {@link DataSet}.
     */
    public DataSet getDataSet( String id )
    {
        String fieldsParam = String.format(
            "%1$s,organisationUnits[%2$s],workflow[%2$s],indicators[%2$s],sections[%2$s],legendSets[%2$s]",
            DATA_SET_FIELDS, NAME_FIELDS );

        return getObject( config.getResolvedUriBuilder()
            .appendPath( "dataSets" )
            .appendPath( id )
            .addParameter( FIELDS_PARAM, fieldsParam ), Query.instance(), DataSet.class );
    }

    /**
     * Retrieves a list of {@link DataSet}.
     *
     * @param query the {@link Query}.
     * @return list of {@link DataSet}.
     */
    public List<DataSet> getDataSets( Query query )
    {
        String fieldsParam = query.isExpandAssociations()
            ? String.format( "%1$s,organisationUnits[%2$s],workflow[%2$s],indicators[%2$s],sections[%2$s]," +
                "legendSets[%2$s]", DATA_SET_FIELDS, NAME_FIELDS )
            : DATA_SET_FIELDS;

        return getObject( config.getResolvedUriBuilder()
            .appendPath( "dataSets" )
            .addParameter( FIELDS_PARAM, fieldsParam ), query, Objects.class )
                .getDataSets();
    }

    // -------------------------------------------------------------------------
    // Program
    // -------------------------------------------------------------------------

    /**
     * Retrieves a {@link Program}.
     *
     * @param id the object identifier.
     * @return the {@link Program}.
     * @throws Dhis2ClientException if the object does not exist.
     */
    public Program getProgram( String id )
    {
        String fieldsParam = String.format(
            "%1$s,programType,categoryCombo[%1$s,categories[%2$s]]," +
                "programStages[%1$s,programStageDataElements[%1$s,dataElement[%3$s]]]," +
                "programTrackedEntityAttributes[id,code,name,trackedEntityAttribute[%4$s]]",
            NAME_FIELDS, CATEGORY_FIELDS, DATA_ELEMENT_FIELDS, TE_ATTRIBUTE_FIELDS );

        return getObject( config.getResolvedUriBuilder()
            .appendPath( "programs" )
            .appendPath( id )
            .addParameter( FIELDS_PARAM, fieldsParam ), Query.instance(), Program.class );
    }

    /**
     * Indicates whether a {@link Program} exists.
     *
     * @param id the object identifier.
     * @return true if the object exists.
     */
    public boolean isProgram( String id )
    {
        return objectExists( config.getResolvedUriBuilder()
            .appendPath( "programs" )
            .appendPath( id ) );
    }

    /**
     * Retrieves a list of {@link Program}.
     *
     * @param query the {@link Query}.
     * @return list of {@link Program}.
     */
    public List<Program> getPrograms( Query query )
    {
        String fieldsParam = query.isExpandAssociations() ? String.format(
            "%1$s,programType,categoryCombo[%1$s,categories[%2$s]]," +
                "programStages[%1$s,programStageDataElements[%1$s,dataElement[%3$s]]]," +
                "programTrackedEntityAttributes[id,code,name,trackedEntityAttribute[%4$s]]",
            NAME_FIELDS, CATEGORY_FIELDS, DATA_ELEMENT_FIELDS, TE_ATTRIBUTE_FIELDS )
            : String.format(
                "%1$s,programType,categoryCombo[%1$s],programStages[%1$s],programTrackedEntityAttributes[%1$s]",
                NAME_FIELDS );

        return getObject( config.getResolvedUriBuilder()
            .appendPath( "programs" )
            .addParameter( FIELDS_PARAM, fieldsParam ), query, Objects.class )
                .getPrograms();
    }

    /**
     * Adds the program to the org unit.
     *
     * @param program the program identifier.
     * @param orgUnit the org unit identifier.
     * @return a {@link Response}.
     */
    public Response addOrgUnitToProgram( String program, String orgUnit )
    {
        return addToCollection( "programs", program, "organisationUnits", orgUnit );
    }

    // -------------------------------------------------------------------------
    // Program indicators.
    // -------------------------------------------------------------------------

    /**
     * Retrieves a {@link ProgramIndicator}.
     *
     * @param id the object identifier.
     * @return the {@link ProgramIndicator}.
     */
    public ProgramIndicator getProgramIndicator( String id )
    {
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "programIndicators" )
            .appendPath( id )
            .addParameter( FIELDS_PARAM, NAME_FIELDS ), Query.instance(), ProgramIndicator.class );
    }

    /**
     * Retrieves a list of {@link ProgramIndicator}.
     *
     * @param query the {@link Query}.
     * @return list of {@link ProgramIndicator}.
     */
    public List<ProgramIndicator> getProgramIndicators( Query query )
    {
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "programIndicators" )
            .addParameter( FIELDS_PARAM, NAME_FIELDS ), query, Objects.class )
                .getProgramIndicators();
    }

    // -------------------------------------------------------------------------
    // Category option group
    // -------------------------------------------------------------------------

    /**
     * Retrieves an {@link CategoryOptionGroup}.
     *
     * @param id the object identifier.
     * @return the {@link CategoryOptionGroup}.
     * @throws Dhis2ClientException if the object does not exist.
     */
    public CategoryOptionGroup getCategoryOptionGroup( String id )
    {
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "categoryOptionGroups" )
            .appendPath( id )
            .addParameter( FIELDS_PARAM, CATEGORY_OPTION_GROUP_FIELDS ), Query.instance(), CategoryOptionGroup.class );
    }

    /**
     * Retrieves a list of {@link CategoryOptionGroup}.
     *
     * @param query the {@link Query}.
     * @return list of {@link CategoryOptionGroup}.
     */
    public List<CategoryOptionGroup> getCategoryOptionGroups( Query query )
    {
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "categoryOptionGroups" )
            .addParameter( FIELDS_PARAM, CATEGORY_OPTION_GROUP_FIELDS ), query, Objects.class )
                .getCategoryOptionGroups();
    }

    // -------------------------------------------------------------------------
    // Category option group set
    // -------------------------------------------------------------------------

    /**
     * Retrieves an {@link CategoryOptionGroupSet}.
     *
     * @param id the object identifier.
     * @return the {@link CategoryOptionGroupSet}.
     * @throws Dhis2ClientException if the object does not exist.
     */
    public CategoryOptionGroupSet getCategoryOptionGroupSet( String id )
    {
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "categoryOptionGroupSets" )
            .appendPath( id )
            .addParameter( FIELDS_PARAM, CATEGORY_OPTION_GROUP_SET_FIELDS ), Query.instance(),
            CategoryOptionGroupSet.class );
    }

    /**
     * Retrieves a list of {@link CategoryOptionGroupSet}.
     *
     * @param query the {@link Query}.
     * @return list of {@link CategoryOptionGroupSet}.
     */
    public List<CategoryOptionGroupSet> getCategoryOptionGroupSets( Query query )
    {
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "categoryOptionGroupSets" )
            .addParameter( FIELDS_PARAM, CATEGORY_OPTION_GROUP_SET_FIELDS ), query, Objects.class )
                .getCategoryOptionGroupSets();
    }

    // -------------------------------------------------------------------------
    // Option sets
    // -------------------------------------------------------------------------

    /**
     * Retrieves an {@link OptionSet}.
     *
     * @param id the object identifier.
     * @return the {@link OptionSet}.
     * @throws Dhis2ClientException if the object does not exist.
     */
    public OptionSet getOptionSet( String id )
    {
        String fieldsParam = String.format( "%1$s,options[%2$s]", OPTION_SET_FIELDS, NAME_FIELDS );

        return getObject( config.getResolvedUriBuilder()
            .appendPath( "optionSets" )
            .appendPath( id )
            .addParameter( FIELDS_PARAM, fieldsParam ), Query.instance(), OptionSet.class );
    }

    /**
     * Retrieves a list of {@link OptionSet}.
     *
     * @param query the {@link Query}.
     * @return list of {@link OptionSet}.
     */
    public List<OptionSet> getOptionSets( Query query )
    {
        String fieldsParam = query.isExpandAssociations() ? String.format(
            "%1$s,options[%2$s]", OPTION_SET_FIELDS, NAME_FIELDS )
            : OPTION_SET_FIELDS;

        return getObject( config.getResolvedUriBuilder()
            .appendPath( "optionSets" )
            .addParameter( FIELDS_PARAM, fieldsParam ), query, Objects.class )
                .getOptionSets();
    }

    // -------------------------------------------------------------------------
    // Table hook
    // -------------------------------------------------------------------------

    /**
     * Saves a {@link TableHook}.
     *
     * @param tableHook the object to save.
     * @return {@link ObjectResponse} holding information about the operation.
     */
    public ObjectResponse saveTableHook( TableHook tableHook )
    {
        return saveMetadataObject( "analyticsTableHooks", tableHook );
    }

    /**
     * Saves or updates the list of {@link TableHook}.
     *
     * @param tableHooks the list of {@link TableHook}.
     * @return {@link ObjectsResponse} holding information about the operation.
     */
    public ObjectsResponse saveTableHooks( List<TableHook> tableHooks )
    {
        return saveMetadataObjects( new Objects().setAnalyticsTableHooks( tableHooks ) );
    }

    /**
     * Updates a {@link TableHook}.
     *
     * @param tableHook the object to update.
     * @return {@link ObjectResponse} holding information about the operation.
     */
    public ObjectResponse updateTableHook( TableHook tableHook )
    {
        return updateMetadataObject( String.format( "analyticsTableHooks/%s", tableHook.getId() ), tableHook );
    }

    /**
     * Removes a {@link TableHook}.
     *
     * @param id the identifier of the object to remove.
     * @return {@link ObjectResponse} holding information about the operation.
     */
    public ObjectResponse removeTableHook( String id )
    {
        return removeMetadataObject( String.format( "analyticsTableHooks/%s", id ) );
    }

    /**
     * Retrieves an {@link TableHook}.
     *
     * @param id the identifier of the table hook.
     * @return the {@link TableHook}.
     * @throws Dhis2ClientException if the object does not exist.
     */
    public TableHook getTableHook( String id )
    {
        return getObject( "analyticsTableHooks", id, TableHook.class );
    }

    /**
     * Retrieves a list of {@link TableHook}.
     *
     * @param query the {@link Query}.
     * @return list of {@link TableHook}.
     */
    public List<TableHook> getTableHooks( Query query )
    {
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "analyticsTableHooks" )
            .addParameter( FIELDS_PARAM, ID_FIELDS ), query, Objects.class )
                .getAnalyticsTableHooks();
    }

    // -------------------------------------------------------------------------
    // Dashboard
    // -------------------------------------------------------------------------

    public Dashboard getDashboard( String id )
    {
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "dashboards" )
            .appendPath( id )
            .addParameter( FIELDS_PARAM, NAME_FIELDS ), Query.instance(), Dashboard.class );
    }

    public List<Dashboard> getDashboards( Query query )
    {
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "dashboards" )
            .addParameter( FIELDS_PARAM, NAME_FIELDS ), query, Objects.class )
                .getDashboards();
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
            .appendPath( "dimensions" )
            .appendPath( id )
            .addParameter( FIELDS_PARAM, String.format( "%s,dimensionType", ID_FIELDS ) ), Query.instance(),
            Dimension.class );
    }

    /**
     * Retrieves a list of {@link Dimension}.
     *
     * @param query the {@link Query}.
     * @return list of {@link Dimension}.
     */
    public List<Dimension> getDimensions( Query query )
    {
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "dimensions" )
            .addParameter( FIELDS_PARAM, String.format( "%s,dimensionType", ID_FIELDS ) ), query, Objects.class )
                .getDimensions();
    }

    // -------------------------------------------------------------------------
    // Period type
    // -------------------------------------------------------------------------

    /**
     * Retrieves a list of {@link PeriodType}.
     *
     * @param query the {@link Query}.
     * @return list of {@link PeriodType}.
     */
    public List<PeriodType> getPeriodTypes( Query query )
    {
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "periodTypes" )
            .addParameter( FIELDS_PARAM, "frequencyOrder,name,isoDuration,isoFormat" ), query, Objects.class )
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
            .appendPath( "systemSettings" ), Query.instance(), SystemSettings.class );
    }

    // -------------------------------------------------------------------------
    // Data value set
    // -------------------------------------------------------------------------

    /**
     * Saves a {@link DataValueSet}.
     *
     * @param dataValueSet the {@link DataValueSet} to save.
     * @param options the {@link DataValueSetImportOptions}.
     * @return {@link DataValueSetResponse} holding information about the
     *         operation.
     */
    public DataValueSetResponse saveDataValueSet( DataValueSet dataValueSet, DataValueSetImportOptions options )
    {
        URI url = getDataValueSetImportQuery( config.getResolvedUriBuilder()
            .appendPath( "dataValueSets" ), options );

        HttpPost request = getPostRequest( url,
            new StringEntity( toJsonString( dataValueSet ), StandardCharsets.UTF_8 ) );

        Dhis2AsyncRequest asyncRequest = new Dhis2AsyncRequest( config, httpClient, objectMapper );

        return asyncRequest.post( request, DataValueSetResponse.class );
    }

    /**
     * Saves a data value set payload in JSON format represented by the given
     * file.
     *
     * @param file the file representing the data value set JSON payload.
     * @param options the {@link DataValueSetImportOptions}.
     * @return {@link DataValueSetResponse} holding information about the
     *         operation.
     */
    public DataValueSetResponse saveDataValueSet( File file, DataValueSetImportOptions options )
    {
        URI url = getDataValueSetImportQuery( config.getResolvedUriBuilder()
            .appendPath( "dataValueSets" ), options );

        HttpPost request = getPostRequest( url, new FileEntity( file, ContentType.APPLICATION_JSON ) );

        Dhis2AsyncRequest asyncRequest = new Dhis2AsyncRequest( config, httpClient, objectMapper );

        return asyncRequest.post( request, DataValueSetResponse.class );
    }

    /**
     * Saves a data value set payload in JSON format represented by the given
     * input stream.
     *
     * @param inputStream the input stream representing the data value set JSON
     *        payload.
     * @param options the {@link DataValueSetImportOptions}.
     * @return {@link DataValueSetResponse} holding information about the
     *         operation.
     */
    public DataValueSetResponse saveDataValueSet( InputStream inputStream, DataValueSetImportOptions options )
    {
        URI url = getDataValueSetImportQuery( config.getResolvedUriBuilder()
            .appendPath( "dataValueSets" ), options );

        HttpPost request = getPostRequest( url, new InputStreamEntity( inputStream, ContentType.APPLICATION_JSON ) );

        Dhis2AsyncRequest asyncRequest = new Dhis2AsyncRequest( config, httpClient, objectMapper );

        return asyncRequest.post( request, DataValueSetResponse.class );
    }

    /**
     * Retrieves a {@link DataValueSet}.
     *
     * @param query the {@link AnalyticsQuery}.
     * @return {@link DataValueSet}.
     */
    public DataValueSet getDataValueSet( DataValueSetQuery query )
    {
        return getDataValueSetResponse( config.getResolvedUriBuilder()
            .appendPath( "dataValueSets.json" ), query, DataValueSet.class );
    }

    // -------------------------------------------------------------------------
    // Analytics data value set
    // -------------------------------------------------------------------------

    /**
     * Retrieves a {@link DataValueSet}.
     *
     * @param query the {@link AnalyticsQuery}.
     * @return {@link DataValueSet}.
     */
    public DataValueSet getAnalyticsDataValueSet( AnalyticsQuery query )
    {
        return getAnalyticsResponse( config.getResolvedUriBuilder()
            .appendPath( "analytics" )
            .appendPath( "dataValueSet.json" ), query, DataValueSet.class );
    }

    /**
     * Retrieves a {@link DataValueSet} and writes it to the given file.
     *
     * @param query the {@link AnalyticsQuery}.
     * @param file the {@link File}.
     */
    public void writeAnalyticsDataValueSet( AnalyticsQuery query, File file )
    {
        URI url = getAnalyticsQuery( config.getResolvedUriBuilder()
            .appendPath( "analytics" )
            .appendPath( "dataValueSet.json" ), query );

        CloseableHttpResponse response = getJsonHttpResponse( url );

        writeToFile( response, file );
    }

    // -------------------------------------------------------------------------
    // Event
    // -------------------------------------------------------------------------

    /**
     * Saves an {@link Events}. The operation is synchronous.
     * <p>
     * Requires DHIS 2 version 2.36 or later.
     *
     * @param events the {@link Events}.
     * @return {@link EventResponse} holding information about the operation.
     */
    public EventResponse saveEvents( Events events )
    {
        return saveObject( config.getResolvedUriBuilder()
            .appendPath( "tracker" )
            .setParameter( "async", "false" )
            .setParameter( "importStrategy", ImportStrategy.CREATE_AND_UPDATE.name() ), events, EventResponse.class );
    }

    /**
     * Retrieves an {@link Event}.
     * <p>
     * Requires DHIS 2 version 2.36 or later.
     *
     * @param id the event identifier.
     * @return the {@link Event}.
     */
    public Event getEvent( String id )
    {
        return getObject( config.getResolvedUriBuilder()
            .appendPath( "tracker" )
            .appendPath( "events" )
            .appendPath( id ), Query.instance(), Event.class );
    }

    /**
     * Retrieves an {@link Events}.
     * <p>
     * Requires DHIS 2 version 2.36 or later.
     *
     * @param query the {@link EventsQuery}.
     * @return the {@link Events}.
     */
    public EventsResult getEvents( EventsQuery query )
    {
        return getEventsResponse( config.getResolvedUriBuilder()
            .appendPath( "tracker" )
            .appendPath( "events" ), query );
    }

    /**
     * Removes an {@link Events}. The operation is synchronous.
     * <p>
     * Requires DHIS 2 version 2.36 or later.
     *
     * @param events the {@link Events}.
     * @return {@link EventResponse} holding information about the operation.
     */
    public EventResponse removeEvents( Events events )
    {
        return saveObject( config.getResolvedUriBuilder()
            .appendPath( "tracker" )
            .setParameter( "async", "false" )
            .setParameter( "importStrategy", ImportStrategy.DELETE.name() ), events, EventResponse.class );
    }

    /**
     * Removes an {@link Event}.
     * <p>
     * Requires DHIS 2 version 2.36 or later.
     *
     * @param event the {@link Event}.
     * @return {@link EventResponse} holding information about the operation.
     */
    public EventResponse removeEvent( Event event )
    {
        Validate.notNull( event.getId(), "Event identifier must be specified" );

        Events events = new Events( list( event ) );

        return saveObject( config.getResolvedUriBuilder()
            .appendPath( "tracker" )
            .setParameter( "async", "false" )
            .setParameter( "importStrategy", "DELETE" ), events, EventResponse.class );
    }

    // -------------------------------------------------------------------------
    // Job notification
    // -------------------------------------------------------------------------

    /**
     * Retrieves a list of {@link JobNotification}.
     *
     * @param category the {@link JobCategory}.
     * @param id the job identifier.
     * @return list of {@link JobNotification}.
     */
    public List<JobNotification> getJobNotifications( JobCategory category, String id )
    {
        JobNotification[] response = getObject( config.getResolvedUriBuilder()
            .appendPath( "system" )
            .appendPath( "tasks" )
            .appendPath( category.name() )
            .appendPath( id ), Query.instance(), JobNotification[].class );

        return new ArrayList<>( Arrays.asList( response ) );
    }
}
