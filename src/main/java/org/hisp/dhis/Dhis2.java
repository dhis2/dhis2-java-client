/*
 * Copyright (c) 2004-2024, University of Oslo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the HISP project nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.hisp.dhis;

import static org.hisp.dhis.ApiFields.CATEGORY_OPTION_COMBO_FIELDS;
import static org.hisp.dhis.ApiFields.DATA_SET_VALIDATION_FIELDS;
import static org.hisp.dhis.ApiFields.DIMENSION_FIELDS;
import static org.hisp.dhis.ApiFields.DOCUMENT_FIELDS;
import static org.hisp.dhis.ApiFields.FILE_RESOURCE_FIELDS;
import static org.hisp.dhis.ApiFields.ID_FIELDS;
import static org.hisp.dhis.ApiFields.INDICATOR_FIELDS;
import static org.hisp.dhis.ApiFields.INDICATOR_GROUP_EXT_FIELDS;
import static org.hisp.dhis.ApiFields.INDICATOR_GROUP_SET_FIELDS;
import static org.hisp.dhis.ApiFields.INDICATOR_TYPE_FIELDS;
import static org.hisp.dhis.ApiFields.MAP_FIELDS;
import static org.hisp.dhis.ApiFields.ME_FIELDS;
import static org.hisp.dhis.ApiFields.NAME_FIELDS;
import static org.hisp.dhis.ApiFields.OPTION_SET_EXT_FIELDS;
import static org.hisp.dhis.ApiFields.OPTION_SET_FIELDS;
import static org.hisp.dhis.ApiFields.ORG_UNIT_FIELDS;
import static org.hisp.dhis.ApiFields.ORG_UNIT_GROUP_EXT_FIELDS;
import static org.hisp.dhis.ApiFields.ORG_UNIT_GROUP_SET_FIELDS;
import static org.hisp.dhis.ApiFields.PROGRAM_EXT_FIELDS;
import static org.hisp.dhis.ApiFields.PROGRAM_FIELDS;
import static org.hisp.dhis.ApiFields.PROGRAM_INDICATOR_FIELDS;
import static org.hisp.dhis.ApiFields.RELATIONSHIP_TYPE_FIELDS;
import static org.hisp.dhis.ApiFields.TRACKED_ENTITY_ATTRIBUTE_FIELDS;
import static org.hisp.dhis.ApiFields.TRACKED_ENTITY_FIELDS;
import static org.hisp.dhis.ApiFields.TRACKED_ENTITY_TYPE_FIELDS;
import static org.hisp.dhis.ApiFields.USER_FIELDS;
import static org.hisp.dhis.ApiFields.USER_GROUP_FIELDS;
import static org.hisp.dhis.ApiFields.USER_ROLE_FIELDS;
import static org.hisp.dhis.ApiFields.VALIDATION_RULE_FIELDS;
import static org.hisp.dhis.ApiFields.VISUALIZATION_FIELDS;
import static org.hisp.dhis.Constants.SUPER_ADMIN_AUTH;
import static org.hisp.dhis.util.CollectionUtils.asList;
import static org.hisp.dhis.util.CollectionUtils.list;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.Validate;
import org.apache.hc.client5.http.HttpResponseException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.FileEntity;
import org.apache.hc.core5.http.io.entity.InputStreamEntity;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.net.URIBuilder;
import org.hisp.dhis.auth.AccessTokenAuthentication;
import org.hisp.dhis.auth.BasicAuthentication;
import org.hisp.dhis.auth.CookieAuthentication;
import org.hisp.dhis.model.AnalyticsTableHook;
import org.hisp.dhis.model.Attribute;
import org.hisp.dhis.model.Category;
import org.hisp.dhis.model.CategoryCombo;
import org.hisp.dhis.model.CategoryOption;
import org.hisp.dhis.model.CategoryOptionCombo;
import org.hisp.dhis.model.CategoryOptionGroup;
import org.hisp.dhis.model.CategoryOptionGroupSet;
import org.hisp.dhis.model.DataElement;
import org.hisp.dhis.model.DataElementGroup;
import org.hisp.dhis.model.DataElementGroupSet;
import org.hisp.dhis.model.DataSet;
import org.hisp.dhis.model.Dhis2Objects;
import org.hisp.dhis.model.Dimension;
import org.hisp.dhis.model.Document;
import org.hisp.dhis.model.FileResource;
import org.hisp.dhis.model.GeoMap;
import org.hisp.dhis.model.IdentifiableObject;
import org.hisp.dhis.model.ImportStrategy;
import org.hisp.dhis.model.Indicator;
import org.hisp.dhis.model.IndicatorGroup;
import org.hisp.dhis.model.IndicatorGroupSet;
import org.hisp.dhis.model.IndicatorType;
import org.hisp.dhis.model.Me;
import org.hisp.dhis.model.MetadataEntity;
import org.hisp.dhis.model.Option;
import org.hisp.dhis.model.OptionSet;
import org.hisp.dhis.model.OrgUnit;
import org.hisp.dhis.model.OrgUnitGroup;
import org.hisp.dhis.model.OrgUnitGroupSet;
import org.hisp.dhis.model.OrgUnitLevel;
import org.hisp.dhis.model.PeriodType;
import org.hisp.dhis.model.Program;
import org.hisp.dhis.model.ProgramIndicator;
import org.hisp.dhis.model.ProgramSection;
import org.hisp.dhis.model.ProgramStage;
import org.hisp.dhis.model.ProgramStageSection;
import org.hisp.dhis.model.SystemInfo;
import org.hisp.dhis.model.SystemSettings;
import org.hisp.dhis.model.Visualization;
import org.hisp.dhis.model.analytics.AnalyticsData;
import org.hisp.dhis.model.completedatasetregistration.CompleteDataSetRegistration;
import org.hisp.dhis.model.completedatasetregistration.CompleteDataSetRegistrationImportOptions;
import org.hisp.dhis.model.dashboard.Dashboard;
import org.hisp.dhis.model.datastore.DataStoreEntries;
import org.hisp.dhis.model.datastore.EntryMetadata;
import org.hisp.dhis.model.datavalueset.DataValue;
import org.hisp.dhis.model.datavalueset.DataValueSet;
import org.hisp.dhis.model.datavalueset.DataValueSetImportOptions;
import org.hisp.dhis.model.enrollment.Enrollment;
import org.hisp.dhis.model.enrollment.EnrollmentsResult;
import org.hisp.dhis.model.event.Event;
import org.hisp.dhis.model.event.Events;
import org.hisp.dhis.model.event.EventsResult;
import org.hisp.dhis.model.relationship.RelationshipType;
import org.hisp.dhis.model.relationship.RelationshipsResult;
import org.hisp.dhis.model.trackedentity.TrackedEntitiesResult;
import org.hisp.dhis.model.trackedentity.TrackedEntity;
import org.hisp.dhis.model.trackedentity.TrackedEntityAttribute;
import org.hisp.dhis.model.trackedentity.TrackedEntityType;
import org.hisp.dhis.model.tracker.TrackedEntityObjects;
import org.hisp.dhis.model.user.User;
import org.hisp.dhis.model.user.UserGroup;
import org.hisp.dhis.model.user.UserRole;
import org.hisp.dhis.model.validation.Period;
import org.hisp.dhis.model.validation.Validation;
import org.hisp.dhis.model.validation.ValidationRule;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.query.analytics.AnalyticsQuery;
import org.hisp.dhis.query.completedatasetregistration.CompleteDataSetRegistrationQuery;
import org.hisp.dhis.query.datavalue.DataValueQuery;
import org.hisp.dhis.query.datavalue.DataValueSetQuery;
import org.hisp.dhis.query.enrollment.EnrollmentQuery;
import org.hisp.dhis.query.event.EventQuery;
import org.hisp.dhis.query.relationship.RelationshipQuery;
import org.hisp.dhis.query.trackedentity.TrackedEntityQuery;
import org.hisp.dhis.query.tracker.TrackedEntityImportParams;
import org.hisp.dhis.query.validations.DataSetValidationQuery;
import org.hisp.dhis.request.orgunit.OrgUnitMergeRequest;
import org.hisp.dhis.request.orgunit.OrgUnitSplitRequest;
import org.hisp.dhis.response.Dhis2ClientException;
import org.hisp.dhis.response.HttpStatus;
import org.hisp.dhis.response.Response;
import org.hisp.dhis.response.completedatasetregistration.CompleteDataSetRegistrationResponse;
import org.hisp.dhis.response.datavalueset.DataValueSetResponse;
import org.hisp.dhis.response.event.EventResponse;
import org.hisp.dhis.response.job.JobCategory;
import org.hisp.dhis.response.job.JobNotification;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.response.objects.ObjectsResponse;
import org.hisp.dhis.response.trackedentity.TrackedEntityResponse;
import org.hisp.dhis.util.HttpUtils;

/**
 * DHIS2 API client for HTTP requests and responses. Request and response bodies are in JSON format.
 * Client is tread-safe and suitable for reuse.
 *
 * <p>Methods generally throw {@link Dhis2ClientException} for status code {@code 401}, {@code 403}
 * and {@code 404} in HTTP responses.
 *
 * @author Lars Helge Overland
 */
public class Dhis2 extends BaseDhis2 {

  public Dhis2(Dhis2Config config) {
    super(config);
  }

  /**
   * Creates a {@link Dhis2} instance configured for basic authentication.
   *
   * @param url the URL to the DHIS2 instance, do not include the {@code /api} part or a trailing
   *     {@code /}.
   * @param username the DHIS2 account username.
   * @param password the DHIS2 account password.
   * @return a {@link Dhis2} instance.
   */
  public static Dhis2 withBasicAuth(String url, String username, String password) {
    return new Dhis2(new Dhis2Config(url, new BasicAuthentication(username, password)));
  }

  /**
   * Creates a {@link Dhis2} instance configured for access token (PAT) authentication.
   *
   * @param url the URL to the DHIS2 instance, do not include the {@code /api} part or a trailing
   *     {@code /}.
   * @param accessToken the access token (PAT).
   * @return a {@link Dhis2} instance.
   */
  public static Dhis2 withAccessTokenAuth(String url, String accessToken) {
    return new Dhis2(new Dhis2Config(url, new AccessTokenAuthentication(accessToken)));
  }

  /**
   * Creates a {@link Dhis2} instance configured for cookie authentication.
   *
   * @param url the URL to the DHIS2 instance, do not include the {@code /api} part or a trailing
   *     {@code /}.
   * @param sessionId the session identifier.
   * @return a {@link Dhis2} instance.
   */
  public static Dhis2 withCookieAuth(String url, String sessionId) {
    return new Dhis2(new Dhis2Config(url, new CookieAuthentication(sessionId)));
  }

  // -------------------------------------------------------------------------
  // Generic methods
  // -------------------------------------------------------------------------

  /**
   * Checks the status of the DHIS2 instance. Returns various status codes describing the status:
   *
   * <ul>
   *   <li>{@link HttpStatus#OK} if instance is available and authentication isf successful.
   *   <li>{@link HttpStatus#UNAUTHORIZED} if the username and password combination is not valid.
   *   <li>{@link HttpStatus#NOT_FOUND} if the URL is not pointing to a DHIS2 instance or the DHIS 2
   *       instance is not available.
   * </ul>
   *
   * @return the HTTP status code of the response.
   */
  public HttpStatus getStatus() {
    URI url =
        HttpUtils.build(config.getResolvedUriBuilder().appendPath("system").appendPath("info"));

    HttpGet request = withAuth(new HttpGet(url));

    try (CloseableHttpResponse response = httpClient.execute(request)) {
      int statusCode = response.getCode();
      return HttpStatus.valueOf(statusCode);
    } catch (IOException ex) {
      // Return status code for exception of type HttpResponseException
      if (ex instanceof HttpResponseException) {
        int statusCode = ((HttpResponseException) ex).getStatusCode();
        return HttpStatus.valueOf(statusCode);
      }

      throw new Dhis2ClientException("Failed to get system info", ex);
    }
  }

  /**
   * Returns the URL of the DHIS2 configuration.
   *
   * @return the URL of the DHIS2 configuration.
   */
  public String getDhis2Url() {
    return config.getUrl();
  }

  /**
   * Indicates whether an object exists at the given URL path using HTTP HEAD.
   *
   * @param path the URL path relative to the API end point.
   * @return true if the object exists.
   */
  public boolean objectExists(String path) {
    return objectExists(config.getResolvedUriBuilder().appendPath(path));
  }

  // -------------------------------------------------------------------------
  // System info
  // -------------------------------------------------------------------------

  /**
   * Retrieves the {@link SystemInfo}.
   *
   * @return the {@link SystemInfo}.
   */
  public SystemInfo getSystemInfo() {
    return getObject(
        config.getResolvedUriBuilder().appendPath("system").appendPath("info"),
        Query.instance(),
        SystemInfo.class);
  }

  // -------------------------------------------------------------------------
  // User and authorization
  // -------------------------------------------------------------------------

  /**
   * Retrieves the list of authorities granted to the authenticated user.
   *
   * @return a list of authorities.
   * @throws Dhis2ClientException if unauthorized or access denied.
   */
  @SuppressWarnings("unchecked")
  public List<String> getUserAuthorization() {
    return getObject("me/authorization", List.class);
  }

  /**
   * Indicates whether the current authenticated user has the given authority.
   *
   * @param auth the authority to check.
   * @return true if the user has the authority, false otherwise.
   * @throws Dhis2ClientException if unauthorized or access denied.
   */
  public boolean hasAuth(String auth) {
    List<String> auths = getUserAuthorization();
    return auths.contains(SUPER_ADMIN_AUTH) || auths.contains(auth);
  }

  /**
   * Retrieves information about the current authenticated user.
   *
   * @return the current authenticated user.
   * @throws Dhis2ClientException if unauthorized or access denied.
   */
  public Me getMe() {
    return getObject(
        config.getResolvedUriBuilder().appendPath("me").addParameter(FIELDS_PARAM, ME_FIELDS),
        Query.instance(),
        Me.class);
  }

  /**
   * Indicates whether the current user authentication is valid.
   *
   * @return true if the user authentication is valid, false otherwise.
   */
  public boolean isValidAuth() {
    try {
      getMe();
      return true;
    } catch (Dhis2ClientException ex) {
      return false;
    }
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
  public <T> Response saveDataStoreEntry(String namespace, String key, T object) {
    return saveObject(getDataStorePath(namespace, key), object, Response.class);
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
  public <T> Response updateDataStoreEntry(String namespace, String key, T object) {
    return updateObject(getDataStorePath(namespace, key), Map.of(), object, Response.class);
  }

  /**
   * Retrieves all data store namespaces.
   *
   * @return the list of namespaces.
   */
  @SuppressWarnings("unchecked")
  public List<String> getDataStoreNamespaces() {
    return getObject("dataStore", List.class);
  }

  /**
   * Retrieves all data store keys for the given namespace.
   *
   * @param namespace the namespace.
   * @return the list of keys.
   */
  @SuppressWarnings("unchecked")
  public List<String> getDataStoreKeys(String namespace) {
    return getObject(String.format("dataStore/%s", namespace), List.class);
  }

  /**
   * Retrieves a data store entry.
   *
   * @param namespace the namespace.
   * @param key the key.
   * @param type the class type of the object to retrieve.
   * @param <T> the object type.
   * @return the object associated with the given namespace and key.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public <T> T getDataStoreEntry(String namespace, String key, Class<T> type) {
    return getObject(getDataStorePath(namespace, key), type);
  }

  /**
   * Retrieves a list of data store entries.
   *
   * @param namespace the namespace.
   * @param fields the list of fields, must not be empty.
   * @return a list of data store entries.
   */
  public List<Map<String, Object>> getDatastoreEntries(String namespace, List<String> fields) {
    Validate.notEmpty(fields);

    String fieldsValue = String.join(",", fields);

    return getObject(
            config
                .getResolvedUriBuilder()
                .appendPath("dataStore")
                .appendPath(namespace)
                .addParameter(FIELDS_PARAM, fieldsValue),
            Query.instance()
                // Temporary solution consistent response format see DHIS2-16422
                .setPaging(1, 100_000),
            DataStoreEntries.class)
        .getEntries();
  }

  /**
   * Retrieves metadata for a data store entry.
   *
   * @param namespace the namespace.
   * @param key the key.
   * @return the {@link EntryMetadata}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public EntryMetadata getDataStoreEntryMetadata(String namespace, String key) {
    String path = String.format("dataStore/%s/%s/metaData", namespace, key);

    return getObject(path, EntryMetadata.class);
  }

  /**
   * Removes a data store entry.
   *
   * @param namespace the namespace.
   * @param key the key.
   * @return {@link Response} holding information about the operation.
   */
  public Response removeDataStoreEntry(String namespace, String key) {
    return removeObject(getDataStorePath(namespace, key), Response.class);
  }

  /**
   * Removes a namespace including all entries.
   *
   * @param namespace the namespace.
   * @return {@link Response} holding information about the operation.
   */
  public Response removeDataStoreNamespace(String namespace) {
    return removeObject(String.format("dataStore/%s", namespace), Response.class);
  }

  /**
   * Returns the path to a data store entry.
   *
   * @param namespace the namespace.
   * @param key the key.
   * @return the path to a data store entry.
   */
  private String getDataStorePath(String namespace, String key) {
    return String.format("dataStore/%s/%s", namespace, key);
  }

  // -------------------------------------------------------------------------
  // Metadata
  // -------------------------------------------------------------------------

  /**
   * Saves or updates metadata objects.
   *
   * @param objects the {@link Dhis2Objects}.
   * @return {@link ObjectsResponse} holding information about the operation.
   */
  public ObjectsResponse saveMetadataObjects(Dhis2Objects objects) {
    URI url = config.getResolvedUrl("metadata");

    return executeJsonPostPutRequest(new HttpPost(url), objects, ObjectsResponse.class);
  }

  /**
   * Saves a metadata object using HTTP POST.
   *
   * @param object the object to save.
   * @return {@link ObjectResponse} holding information about the operation.
   * @throws Dhis2ClientException if unauthorized, access denied or resource not found.
   */
  public ObjectResponse saveMetadataObject(IdentifiableObject object) {
    MetadataEntity entity = MetadataEntity.from(object);
    String path = entity.getPath();

    return saveObject(path, object, ObjectResponse.class);
  }

  /**
   * Updates an object using HTTP PUT.
   *
   * @param object the object to save.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse updateMetadataObject(IdentifiableObject object) {
    MetadataEntity entity = MetadataEntity.from(object);
    String path = String.format("%s/%s", entity.getPath(), object.getId());
    Map<String, String> params = Map.of(SKIP_SHARING_PARAM, "true");

    return updateObject(path, params, object, ObjectResponse.class);
  }

  /**
   * Retrieves a metadata object using HTTP GET.
   *
   * @param entity the {@link MetadataEntity}.
   * @param id the object identifier.
   * @param <T> the type.
   * @return the metadata object.
   */
  @SuppressWarnings("unchecked")
  public <T extends IdentifiableObject> T getMetadataObject(MetadataEntity entity, String id) {
    // Unchecked cast is safe as all metadata entities extend identifiable object
    String path = entity.getPath();
    String fields = entity.getExtFields();
    Class<T> type = (Class<T>) entity.getType();

    return getObject(
        config
            .getResolvedUriBuilder()
            .appendPath(path)
            .appendPath(id)
            .addParameter(FIELDS_PARAM, fields),
        Query.instance(),
        type);
  }

  /**
   * Returns a collection of metadata objects based on the given query wrapped in {@link
   * Dhis2Objects}.
   *
   * @param entity the {@link MetadataEntity}.
   * @param query the {@link Query}.
   * @return a {@link Dhis2Objects}.
   */
  public Dhis2Objects getMetadataObjects(MetadataEntity entity, Query query) {
    String path = entity.getPath();
    String fields = query.isExpandAssociations() ? entity.getExtFields() : entity.getFields();

    return getObject(
        config.getResolvedUriBuilder().appendPath(path).addParameter(FIELDS_PARAM, fields),
        query,
        Dhis2Objects.class);
  }

  /**
   * Removes an object using HTTP DELETE.
   *
   * @param entity the {@link MetadataEntity}.
   * @param id the object identifier.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  protected ObjectResponse removeMetadataObject(MetadataEntity entity, String id) {
    String path = String.format("%s/%s", entity.getPath(), id);

    return removeObject(path, ObjectResponse.class);
  }

  // -------------------------------------------------------------------------
  // Analytics table hook
  // -------------------------------------------------------------------------

  /**
   * Saves a {@link AnalyticsTableHook}.
   *
   * @param hook the object to save.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse saveAnalyticsTableHook(AnalyticsTableHook hook) {
    return saveMetadataObject(hook);
  }

  /**
   * Saves or updates the list of {@link AnalyticsTableHook}.
   *
   * @param hooks the list of {@link AnalyticsTableHook}.
   * @return {@link ObjectsResponse} holding information about the operation.
   */
  public ObjectsResponse saveAnalyticsTableHook(List<AnalyticsTableHook> hooks) {
    return saveMetadataObjects(new Dhis2Objects().setAnalyticsTableHooks(hooks));
  }

  /**
   * Updates a {@link AnalyticsTableHook}.
   *
   * @param hook the object to update.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse updateAnalyticsTableHook(AnalyticsTableHook hook) {
    return updateMetadataObject(hook);
  }

  /**
   * Removes a {@link AnalyticsTableHook}.
   *
   * @param id the identifier of the object to remove.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse removeAnalyticsTableHook(String id) {
    return removeMetadataObject(MetadataEntity.ANALYTICS_TABLE_HOOK, id);
  }

  /**
   * Retrieves an {@link AnalyticsTableHook}.
   *
   * @param id the identifier of the table hook.
   * @return the {@link AnalyticsTableHook}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public AnalyticsTableHook getAnalyticsTableHook(String id) {
    return getMetadataObject(MetadataEntity.ANALYTICS_TABLE_HOOK, id);
  }

  /**
   * Indicates whether a {@link AnalyticsTableHook} exists.
   *
   * @param id the object identifier.
   * @return true if the object exists.
   */
  public boolean isAnalyticsTableHook(String id) {
    return objectExists(MetadataEntity.ANALYTICS_TABLE_HOOK, id);
  }

  /**
   * Retrieves a list of {@link AnalyticsTableHook}.
   *
   * @param query the {@link Query}.
   * @return list of {@link AnalyticsTableHook}.
   */
  public List<AnalyticsTableHook> getAnalyticsTableHooks(Query query) {
    return getMetadataObjects(MetadataEntity.ANALYTICS_TABLE_HOOK, query).getAnalyticsTableHooks();
  }

  // -------------------------------------------------------------------------
  // Attribute
  // -------------------------------------------------------------------------

  /**
   * Saves a {@link Attribute}.
   *
   * @param attribute the object to save.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse saveAttribute(Attribute attribute) {
    return saveMetadataObject(attribute);
  }

  /**
   * Saves or updates the list of {@link Attribute}.
   *
   * @param attributes the list of {@link Attribute}.
   * @return {@link ObjectsResponse} holding information about the operation.
   */
  public ObjectsResponse saveAttributes(List<Attribute> attributes) {
    return saveMetadataObjects(new Dhis2Objects().setAttributes(attributes));
  }

  /**
   * Updates a {@link Attribute}.
   *
   * @param attribute the object to update.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse updateAttribute(Attribute attribute) {
    return updateMetadataObject(attribute);
  }

  /**
   * Retrieves a {@link Attribute}.
   *
   * @param id the object identifier.
   * @return the {@link Attribute}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public Attribute getAttribute(String id) {
    return getMetadataObject(MetadataEntity.ATTRIBUTE, id);
  }

  /**
   * Indicates whether a {@link Attribute} exists.
   *
   * @param id the object identifier.
   * @return true if the object exists.
   */
  public boolean isAttribute(String id) {
    return objectExists(MetadataEntity.ATTRIBUTE, id);
  }

  /**
   * Retrieves a {@link Attribute}.
   *
   * @param query the {@link Query}.
   * @return a list of {@link Attribute}.
   */
  public List<Attribute> getAttributes(Query query) {
    return getMetadataObjects(MetadataEntity.ATTRIBUTE, query).getAttributes();
  }

  /**
   * Removes a {@link Attribute}.
   *
   * @param id the identifier of the object to remove.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse removeAttribute(String id) {
    return removeMetadataObject(MetadataEntity.ATTRIBUTE, id);
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
  public ObjectResponse saveCategoryOption(CategoryOption categoryOption) {
    return saveMetadataObject(categoryOption);
  }

  /**
   * Saves or updates the list of {@link CategoryOption}.
   *
   * @param categoryOptions the list of {@link CategoryOption}.
   * @return {@link ObjectsResponse} holding information about the operation.
   */
  public ObjectsResponse saveCategoryOptions(List<CategoryOption> categoryOptions) {
    return saveMetadataObjects(new Dhis2Objects().setCategoryOptions(categoryOptions));
  }

  /**
   * Updates a {@link CategoryOption}.
   *
   * @param categoryOption the object to update.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse updateCategoryOption(CategoryOption categoryOption) {
    return updateMetadataObject(categoryOption);
  }

  /**
   * Retrieves an {@link CategoryOption}.
   *
   * @param id the object identifier.
   * @return the {@link CategoryOption}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public CategoryOption getCategoryOption(String id) {
    return getMetadataObject(MetadataEntity.CATEGORY_OPTION, id);
  }

  /**
   * Indicates whether a {@link CategoryOption} exists.
   *
   * @param id the object identifier.
   * @return true if the object exists.
   */
  public boolean isCategoryOption(String id) {
    return objectExists(MetadataEntity.CATEGORY_OPTION, id);
  }

  /**
   * Retrieves a list of {@link CategoryOption}.
   *
   * @param query the {@link Query}.
   * @return list of {@link CategoryOption}.
   */
  public List<CategoryOption> getCategoryOptions(Query query) {
    return getMetadataObjects(MetadataEntity.CATEGORY_OPTION, query).getCategoryOptions();
  }

  /**
   * Removes a {@link CategoryOption}.
   *
   * @param id the identifier of the object to remove.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse removeCategoryOption(String id) {
    return removeMetadataObject(MetadataEntity.CATEGORY_OPTION, id);
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
  public ObjectResponse saveCategory(Category category) {
    return saveMetadataObject(category);
  }

  /**
   * Saves or updates the list of {@link Category}.
   *
   * @param categories the list of {@link Category}.
   * @return {@link ObjectsResponse} holding information about the operation.
   */
  public ObjectsResponse saveCategories(List<Category> categories) {
    return saveMetadataObjects(new Dhis2Objects().setCategories(categories));
  }

  /**
   * Updates a {@link Category}.
   *
   * @param category the object to update.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse updateCategory(Category category) {
    return updateMetadataObject(category);
  }

  /**
   * Retrieves an {@link Category}.
   *
   * @param id the object identifier.
   * @return the {@link Category}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public Category getCategory(String id) {
    return getMetadataObject(MetadataEntity.CATEGORY, id);
  }

  /**
   * Indicates whether a {@link Category} exists.
   *
   * @param id the object identifier.
   * @return true if the object exists.
   */
  public boolean isCategory(String id) {
    return objectExists(MetadataEntity.CATEGORY, id);
  }

  /**
   * Retrieves a list of {@link Category}.
   *
   * @param query the {@link Query}.
   * @return list of {@link Category}.
   */
  public List<Category> getCategories(Query query) {
    return getMetadataObjects(MetadataEntity.CATEGORY, query).getCategories();
  }

  /**
   * Removes a {@link Category}.
   *
   * @param id the identifier of the object to remove.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse removeCategory(String id) {
    return removeMetadataObject(MetadataEntity.CATEGORY, id);
  }

  // -------------------------------------------------------------------------
  // Category combo
  // -------------------------------------------------------------------------

  /**
   * Saves a {@link CategoryCombo}.
   *
   * @param categoryCombo the object to save.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse saveCategoryCombo(CategoryCombo categoryCombo) {
    return saveMetadataObject(categoryCombo);
  }

  /**
   * Updates a {@link CategoryCombo}.
   *
   * @param categoryCombo the object to update.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse updateCategoryCombo(CategoryCombo categoryCombo) {
    return updateMetadataObject(categoryCombo);
  }

  /**
   * Retrieves an {@link CategoryCombo}.
   *
   * @param id the object identifier.
   * @return the {@link CategoryCombo}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public CategoryCombo getCategoryCombo(String id) {
    return getMetadataObject(MetadataEntity.CATEGORY_COMBO, id);
  }

  /**
   * Indicates whether a {@link CategoryCombo} exists.
   *
   * @param id the object identifier.
   * @return true if the object exists.
   */
  public boolean isCategoryCombo(String id) {
    return objectExists(MetadataEntity.CATEGORY_COMBO, id);
  }

  /**
   * Retrieves a list of {@link CategoryCombo}.
   *
   * @param query the {@link Query}.
   * @return list of {@link CategoryCombo}.
   */
  public List<CategoryCombo> getCategoryCombos(Query query) {
    return getMetadataObjects(MetadataEntity.CATEGORY_COMBO, query).getCategoryCombos();
  }

  /**
   * Removes a {@link CategoryCombo}.
   *
   * @param id the identifier of the object to remove.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse removeCategoryCombo(String id) {
    return removeMetadataObject(MetadataEntity.CATEGORY_COMBO, id);
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
  public CategoryOptionCombo getCategoryOptionCombo(String id) {
    return getMetadataObject(MetadataEntity.CATEGORY_OPTION_COMBO, id);
  }

  /**
   * Indicates whether a {@link CategoryOptionCombo} exists.
   *
   * @param id the object identifier.
   * @return true if the object exists.
   */
  public boolean isCategoryOptionCombo(String id) {
    return objectExists(MetadataEntity.CATEGORY_OPTION_COMBO, id);
  }

  /**
   * Retrieves a list of {@link CategoryOptionCombo}.
   *
   * @param query the {@link Query}.
   * @return list of {@link CategoryOptionCombo}.
   */
  public List<CategoryOptionCombo> getCategoryOptionCombos(Query query) {
    return getObject(
            config
                .getResolvedUriBuilder()
                .appendPath("categoryOptionCombos")
                .addParameter(FIELDS_PARAM, CATEGORY_OPTION_COMBO_FIELDS),
            query,
            Dhis2Objects.class)
        .getCategoryOptionCombos();
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
  public CategoryOptionGroup getCategoryOptionGroup(String id) {
    return getMetadataObject(MetadataEntity.CATEGORY_OPTION_GROUP, id);
  }

  /**
   * Indicates whether a {@link CategoryOptionGroup} exists.
   *
   * @param id the object identifier.
   * @return true if the object exists.
   */
  public boolean isCategoryOptionGroup(String id) {
    return objectExists(MetadataEntity.CATEGORY_OPTION_GROUP, id);
  }

  /**
   * Retrieves a list of {@link CategoryOptionGroup}.
   *
   * @param query the {@link Query}.
   * @return list of {@link CategoryOptionGroup}.
   */
  public List<CategoryOptionGroup> getCategoryOptionGroups(Query query) {
    return getMetadataObjects(MetadataEntity.CATEGORY_OPTION_GROUP, query)
        .getCategoryOptionGroups();
  }

  /**
   * Removes a {@link CategoryOptionGroup}.
   *
   * @param id the identifier of the object to remove.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse removeCategoryOptionGroup(String id) {
    return removeMetadataObject(MetadataEntity.CATEGORY_OPTION_GROUP, id);
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
  public CategoryOptionGroupSet getCategoryOptionGroupSet(String id) {
    return getMetadataObject(MetadataEntity.CATEGORY_OPTION_GROUP_SET, id);
  }

  /**
   * Indicates whether a {@link CategoryOptionGroupSet} exists.
   *
   * @param id the object identifier.
   * @return true if the object exists.
   */
  public boolean isCategoryOptionGroupSet(String id) {
    return objectExists(MetadataEntity.CATEGORY_OPTION_GROUP_SET, id);
  }

  /**
   * Retrieves a list of {@link CategoryOptionGroupSet}.
   *
   * @param query the {@link Query}.
   * @return list of {@link CategoryOptionGroupSet}.
   */
  public List<CategoryOptionGroupSet> getCategoryOptionGroupSets(Query query) {
    return getMetadataObjects(MetadataEntity.CATEGORY_OPTION_GROUP_SET, query)
        .getCategoryOptionGroupSets();
  }

  /**
   * Removes a {@link CategoryOptionGroupSet}.
   *
   * @param id the identifier of the object to remove.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse removeCategoryOptionGroupSet(String id) {
    return removeMetadataObject(MetadataEntity.CATEGORY_OPTION_GROUP_SET, id);
  }

  // -------------------------------------------------------------------------
  // Dashboard
  // -------------------------------------------------------------------------

  /**
   * Retrieves a {@link Dashboard}.
   *
   * @param id the object identifier.
   * @return the {@link Dashboard}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public Dashboard getDashboard(String id) {
    return getMetadataObject(MetadataEntity.DASHBOARD, id);
  }

  /**
   * Indicates whether a {@link Dashboard} exists.
   *
   * @param id the object identifier.
   * @return true if the object exists.
   */
  public boolean isDashboard(String id) {
    return objectExists(MetadataEntity.DASHBOARD, id);
  }

  /**
   * Retrieves a list of {@link Dashboard}.
   *
   * @param query the {@link Query}.
   * @return list of {@link Dashboard}.
   */
  public List<Dashboard> getDashboards(Query query) {
    return getMetadataObjects(MetadataEntity.DASHBOARD, query).getDashboards();
  }

  /**
   * Removes a {@link Dashboard}.
   *
   * @param id the identifier of the object to remove.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse removeDashboard(String id) {
    return removeMetadataObject(MetadataEntity.DASHBOARD, id);
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
  public ObjectResponse saveDataElement(DataElement dataElement) {
    return saveMetadataObject(dataElement);
  }

  /**
   * Saves or updates the list of {@link DataElement}.
   *
   * @param dataElements the list of {@link DataElement}.
   * @return {@link ObjectsResponse} holding information about the operation.
   */
  public ObjectsResponse saveDataElements(List<DataElement> dataElements) {
    return saveMetadataObjects(new Dhis2Objects().setDataElements(dataElements));
  }

  /**
   * Updates a {@link DataElement}.
   *
   * @param dataElement the object to update.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse updateDataElement(DataElement dataElement) {
    return updateMetadataObject(dataElement);
  }

  /**
   * Retrieves an {@link DataElement}.
   *
   * @param id the object identifier.
   * @return the {@link DataElement}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public DataElement getDataElement(String id) {
    return getMetadataObject(MetadataEntity.DATA_ELEMENT, id);
  }

  /**
   * Indicates whether a {@link DataElement} exists.
   *
   * @param id the object identifier.
   * @return true if the object exists.
   */
  public boolean isDataElement(String id) {
    return objectExists(MetadataEntity.DATA_ELEMENT, id);
  }

  /**
   * Retrieves a list of {@link DataElement}.
   *
   * @param query the {@link Query}.
   * @return list of {@link DataElement}.
   */
  public List<DataElement> getDataElements(Query query) {
    return getMetadataObjects(MetadataEntity.DATA_ELEMENT, query).getDataElements();
  }

  /**
   * Removes a {@link DataElement}.
   *
   * @param id the identifier of the object to remove.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse removeDataElement(String id) {
    return removeMetadataObject(MetadataEntity.DATA_ELEMENT, id);
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
  public ObjectResponse saveDataElementGroup(DataElementGroup dataElementGroup) {
    return saveMetadataObject(dataElementGroup);
  }

  /**
   * Saves or updates the list of {@link DataElementGroup}.
   *
   * @param dataElementGroups the list of {@link DataElementGroup}.
   * @return {@link ObjectsResponse} holding information about the operation.
   */
  public ObjectsResponse saveDataElementGroups(List<DataElementGroup> dataElementGroups) {
    return saveMetadataObjects(new Dhis2Objects().setDataElementGroups(dataElementGroups));
  }

  /**
   * Updates a {@link DataElementGroup}.
   *
   * @param dataElementGroup the object to update.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse updateDataElementGroup(DataElementGroup dataElementGroup) {
    return updateMetadataObject(dataElementGroup);
  }

  /**
   * Retrieves an {@link DataElementGroup}.
   *
   * @param id the object identifier.
   * @return the {@link DataElementGroup}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public DataElementGroup getDataElementGroup(String id) {
    return getMetadataObject(MetadataEntity.DATA_ELEMENT_GROUP, id);
  }

  /**
   * Indicates whether a {@link DataElementGroup} exists.
   *
   * @param id the object identifier.
   * @return true if the object exists.
   */
  public boolean isDataElementGroup(String id) {
    return objectExists(MetadataEntity.DATA_ELEMENT_GROUP, id);
  }

  /**
   * Retrieves a list of {@link DataElementGroup}.
   *
   * @param query the {@link Query}.
   * @return list of {@link DataElementGroup}.
   */
  public List<DataElementGroup> getDataElementGroups(Query query) {
    return getMetadataObjects(MetadataEntity.DATA_ELEMENT_GROUP, query).getDataElementGroups();
  }

  /**
   * Removes a {@link DataElementGroup}.
   *
   * @param id the identifier of the object to remove.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse removeDataElementGroup(String id) {
    return removeMetadataObject(MetadataEntity.DATA_ELEMENT_GROUP, id);
  }

  // -------------------------------------------------------------------------
  // Data element group set
  // -------------------------------------------------------------------------

  /**
   * Saves a {@link DataElementGroupSet}.
   *
   * @param dataElementGroupSet the object to save.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse saveDataElementGroupSet(DataElementGroupSet dataElementGroupSet) {
    return saveMetadataObject(dataElementGroupSet);
  }

  /**
   * Saves or updates the list of {@link DataElementGroupSet}.
   *
   * @param dataElementGroupSets the list of {@link DataElementGroupSet}.
   * @return {@link ObjectsResponse} holding information about the operation.
   */
  public ObjectsResponse saveDataElementGroupSets(List<DataElementGroupSet> dataElementGroupSets) {
    return saveMetadataObjects(new Dhis2Objects().setDataElementGroupSets(dataElementGroupSets));
  }

  /**
   * Updates a {@link DataElementGroupSet}.
   *
   * @param dataElementGroupSet the object to update.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse updateDataElementGroupSet(DataElementGroupSet dataElementGroupSet) {
    return updateMetadataObject(dataElementGroupSet);
  }

  /**
   * Retrieves an {@link DataElementGroupSet}.
   *
   * @param id the object identifier.
   * @return the {@link DataElementGroupSet}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public DataElementGroupSet getDataElementGroupSet(String id) {
    return getMetadataObject(MetadataEntity.DATA_ELEMENT_GROUP_SET, id);
  }

  /**
   * Indicates whether a {@link DataElementGroupSet} exists.
   *
   * @param id the object identifier.
   * @return true if the object exists.
   */
  public boolean isDataElementGroupSet(String id) {
    return objectExists(MetadataEntity.DATA_ELEMENT_GROUP_SET, id);
  }

  /**
   * Retrieves a list of {@link DataElementGroupSet}.
   *
   * @param query the {@link Query}.
   * @return list of {@link DataElementGroupSet}.
   */
  public List<DataElementGroupSet> getDataElementGroupSets(Query query) {
    return getMetadataObjects(MetadataEntity.DATA_ELEMENT_GROUP_SET, query)
        .getDataElementGroupSets();
  }

  /**
   * Removes a {@link DataElementGroupSet}.
   *
   * @param id the identifier of the object to remove.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse removeDataElementGroupSet(String id) {
    return removeMetadataObject(MetadataEntity.DATA_ELEMENT_GROUP_SET, id);
  }

  // -------------------------------------------------------------------------
  // DataSet
  // -------------------------------------------------------------------------

  /**
   * Retrieves an {@link DataSet}.
   *
   * @param id the object identifier.
   * @return the {@link DataSet}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public DataSet getDataSet(String id) {
    return getMetadataObject(MetadataEntity.DATA_SET, id);
  }

  /**
   * Indicates whether a {@link DataSet} exists.
   *
   * @param id the object identifier.
   * @return true if the object exists.
   */
  public boolean isDataSet(String id) {
    return objectExists(MetadataEntity.DATA_SET, id);
  }

  /**
   * Retrieves a list of {@link DataSet}.
   *
   * @param query the {@link Query}.
   * @return list of {@link DataSet}.
   */
  public List<DataSet> getDataSets(Query query) {
    return getMetadataObjects(MetadataEntity.DATA_SET, query).getDataSets();
  }

  /**
   * Removes a {@link DataSet}.
   *
   * @param id the identifier of the object to remove.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse removeDataSet(String id) {
    return removeMetadataObject(MetadataEntity.DATA_SET, id);
  }

  // -------------------------------------------------------------------------
  // Dimension
  // -------------------------------------------------------------------------

  /**
   * Retrieves a {@link Dimension}.
   *
   * @param id the identifier of the dimension.
   * @return the {@link Dimension}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public Dimension getDimension(String id) {
    return getObject(
        config
            .getResolvedUriBuilder()
            .appendPath("dimensions")
            .appendPath(id)
            .addParameter(FIELDS_PARAM, DIMENSION_FIELDS),
        Query.instance(),
        Dimension.class);
  }

  /**
   * Indicates whether a {@link Dimension} exists.
   *
   * @param id the object identifier.
   * @return true if the object exists.
   */
  public boolean isDimension(String id) {
    return objectExists(String.format("dimensions/%s", id));
  }

  /**
   * Retrieves a list of {@link Dimension}.
   *
   * @param query the {@link Query}.
   * @return list of {@link Dimension}.
   */
  public List<Dimension> getDimensions(Query query) {
    return getObject(
            config
                .getResolvedUriBuilder()
                .appendPath("dimensions")
                .addParameter(FIELDS_PARAM, String.format("%s,dimensionType", ID_FIELDS)),
            query,
            Dhis2Objects.class)
        .getDimensions();
  }

  // -------------------------------------------------------------------------
  // Document
  // -------------------------------------------------------------------------

  /**
   * Saves a {@link Document}.
   *
   * @param document the object to save.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse saveDocument(Document document) {
    return saveMetadataObject(document);
  }

  /**
   * Retrieves a {@link Document}.
   *
   * @param id the object identifier.
   * @return the {@link Document}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public Document getDocument(String id) {
    return getMetadataObject(MetadataEntity.DOCUMENT, id);
  }

  /**
   * Indicates whether a {@link Document} exists.
   *
   * @param id the object identifier.
   * @return true if the object exists.
   */
  public boolean isDocument(String id) {
    return objectExists(MetadataEntity.DOCUMENT, id);
  }

  /**
   * Retrieves a list of {@link Document}.
   *
   * @param query the {@link Query}.
   * @return list of {@link Document}.
   */
  public List<Document> getDocuments(Query query) {
    return getObject(
            config
                .getResolvedUriBuilder()
                .appendPath("documents")
                .addParameter(FIELDS_PARAM, DOCUMENT_FIELDS),
            query,
            Dhis2Objects.class)
        .getDocuments();
  }

  /**
   * Writes the data for the {@link Document} to the given {@link OutputStream}.
   *
   * @param id the document identifier.
   * @param out the {@link OutputStream} to write data to.
   */
  public void writeDocumentData(String id, OutputStream out) {
    URI uri =
        HttpUtils.build(
            config
                .getResolvedUriBuilder()
                .appendPath("documents")
                .appendPath(id)
                .appendPath("data"));

    CloseableHttpResponse response = getHttpResponse(uri, List.of());

    writeToStream(response, out);
  }

  /**
   * Removes a {@link Document}.
   *
   * @param id the identifier of the object to remove.
   * @return {@link Document} holding information about the operation.
   */
  public ObjectResponse removeDocument(String id) {
    return removeMetadataObject(MetadataEntity.DOCUMENT, id);
  }

  // -------------------------------------------------------------------------
  // Indicator
  // -------------------------------------------------------------------------

  /**
   * Saves a {@link Indicator}.
   *
   * @param indicator the indicator object to save.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse saveIndicator(Indicator indicator) {
    return saveMetadataObject(indicator);
  }

  /**
   * Saves or updates the list of {@link Indicator}.
   *
   * @param indicators the list of {@link Indicator}.
   * @return {@link ObjectsResponse} holding information about the operation.
   */
  public ObjectsResponse saveIndicators(List<Indicator> indicators) {
    return saveMetadataObjects(new Dhis2Objects().setIndicators(indicators));
  }

  /**
   * Updates a {@link Indicator}.
   *
   * @param indicator the indicator object to update.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse updateIndicator(Indicator indicator) {
    return updateMetadataObject(indicator);
  }

  /**
   * Retrieves an {@link Indicator}.
   *
   * @param id the object identifier.
   * @return the {@link Indicator}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public Indicator getIndicator(String id) {
    return getMetadataObject(MetadataEntity.INDICATOR, id);
  }

  /**
   * Indicates whether a {@link Indicator} exists.
   *
   * @param id the object identifier.
   * @return true if the object exists.
   */
  public boolean isIndicator(String id) {
    return objectExists(MetadataEntity.INDICATOR, id);
  }

  /**
   * Retrieves a list of {@link Indicator}.
   *
   * @param query the {@link Query}.
   * @return list of {@link Indicator}.
   */
  public List<Indicator> getIndicators(Query query) {
    return getObject(
            config
                .getResolvedUriBuilder()
                .appendPath("indicators")
                .addParameter(FIELDS_PARAM, INDICATOR_FIELDS),
            query,
            Dhis2Objects.class)
        .getIndicators();
  }

  /**
   * Removes an {@link Indicator}.
   *
   * @param id the identifier of the object to remove.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse removeIndicator(String id) {
    return removeMetadataObject(MetadataEntity.INDICATOR, id);
  }

  // -------------------------------------------------------------------------
  // Indicator group
  // -------------------------------------------------------------------------

  /**
   * Saves a {@link IndicatorGroup}.
   *
   * @param indicatorGroup the indicator group object to save.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse saveIndicatorGroup(IndicatorGroup indicatorGroup) {
    return saveMetadataObject(indicatorGroup);
  }

  /**
   * Saves or updates the list of {@link IndicatorGroup}.
   *
   * @param indicatorGroups the list of {@link IndicatorGroup}.
   * @return {@link ObjectsResponse} holding information about the operation.
   */
  public ObjectsResponse saveIndicatorGroups(List<IndicatorGroup> indicatorGroups) {
    return saveMetadataObjects(new Dhis2Objects().setIndicatorGroups(indicatorGroups));
  }

  /**
   * Updates a {@link IndicatorGroup}.
   *
   * @param indicatorGroup the indicator group object to update.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse updateIndicatorGroup(IndicatorGroup indicatorGroup) {
    return updateMetadataObject(indicatorGroup);
  }

  /**
   * Retrieves an {@link IndicatorGroup}.
   *
   * @param id the object identifier.
   * @return the {@link IndicatorGroup}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public IndicatorGroup getIndicatorGroup(String id) {
    return getMetadataObject(MetadataEntity.INDICATOR_GROUP, id);
  }

  /**
   * Indicates whether a {@link IndicatorGroup} exists.
   *
   * @param id the object identifier.
   * @return true if the object exists.
   */
  public boolean isIndicatorGroup(String id) {
    return objectExists(MetadataEntity.INDICATOR_GROUP, id);
  }

  /**
   * Retrieves a list of {@link IndicatorGroup}.
   *
   * @param query the {@link Query}.
   * @return list of {@link IndicatorGroup}.
   */
  public List<IndicatorGroup> getIndicatorGroups(Query query) {
    String fieldsParams = query.isExpandAssociations() ? INDICATOR_GROUP_EXT_FIELDS : NAME_FIELDS;

    return getObject(
            config
                .getResolvedUriBuilder()
                .appendPath("indicatorGroups")
                .addParameter(FIELDS_PARAM, fieldsParams),
            query,
            Dhis2Objects.class)
        .getIndicatorGroups();
  }

  /**
   * Removes an {@link IndicatorGroup}.
   *
   * @param id the identifier of the object to remove.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse removeIndicatorGroup(String id) {
    return removeMetadataObject(MetadataEntity.INDICATOR_GROUP, id);
  }

  // -------------------------------------------------------------------------
  // Indicator group set
  // -------------------------------------------------------------------------

  /**
   * Saves a {@link IndicatorGroupSet}.
   *
   * @param indicatorGroupSet the indicator group set object to save.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse saveIndicatorGroupSet(IndicatorGroupSet indicatorGroupSet) {
    return saveMetadataObject(indicatorGroupSet);
  }

  /**
   * Saves or updates the list of {@link IndicatorGroupSet}.
   *
   * @param indicatorGroupSets the list of {@link IndicatorGroupSet}.
   * @return {@link ObjectsResponse} holding information about the operation.
   */
  public ObjectsResponse saveIndicatorGroupSets(List<IndicatorGroupSet> indicatorGroupSets) {
    return saveMetadataObjects(new Dhis2Objects().setIndicatorGroupSets(indicatorGroupSets));
  }

  /**
   * Updates a {@link IndicatorGroupSet}.
   *
   * @param indicatorGroupSet the indicator group set object to update.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse updateIndicatorGroupSet(IndicatorGroupSet indicatorGroupSet) {
    return updateMetadataObject(indicatorGroupSet);
  }

  /**
   * Retrieves an {@link IndicatorGroupSet}.
   *
   * @param id the object identifier.
   * @return the {@link IndicatorGroupSet}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public IndicatorGroupSet getIndicatorGroupSet(String id) {
    return getMetadataObject(MetadataEntity.INDICATOR_GROUP_SET, id);
  }

  /**
   * Indicates whether a {@link IndicatorGroupSet} exists.
   *
   * @param id the object identifier.
   * @return true if the object exists.
   */
  public boolean isIndicatorGroupSet(String id) {
    return objectExists(MetadataEntity.INDICATOR_GROUP_SET, id);
  }

  /**
   * Retrieves a list of {@link IndicatorGroupSet}.
   *
   * @param query the {@link Query}.
   * @return list of {@link IndicatorGroupSet}.
   */
  public List<IndicatorGroupSet> getIndicatorGroupSets(Query query) {
    return getObject(
            config
                .getResolvedUriBuilder()
                .appendPath("indicatorGroupSets")
                .addParameter(FIELDS_PARAM, INDICATOR_GROUP_SET_FIELDS),
            query,
            Dhis2Objects.class)
        .getIndicatorGroupSets();
  }

  /**
   * Removes an {@link IndicatorGroupSet}.
   *
   * @param id the identifier of the object to remove.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse removeIndicatorGroupSet(String id) {
    return removeMetadataObject(MetadataEntity.INDICATOR_GROUP_SET, id);
  }

  // -------------------------------------------------------------------------
  // Indicator Type
  // -------------------------------------------------------------------------

  /**
   * Saves a {@link IndicatorType}.
   *
   * @param indicatorType the indicator type object to save.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse saveIndicatorType(IndicatorType indicatorType) {
    return saveMetadataObject(indicatorType);
  }

  /**
   * Saves or updates the list of {@link IndicatorType}.
   *
   * @param indicatorTypes the list of {@link IndicatorType}.
   * @return {@link ObjectsResponse} holding information about the operation.
   */
  public ObjectsResponse saveIndicatorTypes(List<IndicatorType> indicatorTypes) {
    return saveMetadataObjects(new Dhis2Objects().setIndicatorTypes(indicatorTypes));
  }

  /**
   * Updates a {@link IndicatorType}.
   *
   * @param indicatorType the indicator type object to update.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse updateIndicatorType(IndicatorType indicatorType) {
    return updateMetadataObject(indicatorType);
  }

  /**
   * Retrieves an {@link IndicatorType}.
   *
   * @param id the object identifier.
   * @return the {@link IndicatorType}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public IndicatorType getIndicatorType(String id) {
    return getMetadataObject(MetadataEntity.INDICATOR_TYPE, id);
  }

  /**
   * Indicates whether a {@link IndicatorType} exists.
   *
   * @param id the object identifier.
   * @return true if the object exists.
   */
  public boolean isIndicatorType(String id) {
    return objectExists(MetadataEntity.INDICATOR_TYPE, id);
  }

  /**
   * Retrieves a list of {@link IndicatorType}.
   *
   * @param query the {@link Query}.
   * @return list of {@link IndicatorType}.
   */
  public List<IndicatorType> getIndicatorTypes(Query query) {
    return getObject(
            config
                .getResolvedUriBuilder()
                .appendPath("indicatorTypes")
                .addParameter(FIELDS_PARAM, INDICATOR_TYPE_FIELDS),
            query,
            Dhis2Objects.class)
        .getIndicatorTypes();
  }

  /**
   * Removes an {@link IndicatorType}.
   *
   * @param id the identifier of the object to remove.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse removeIndicatorType(String id) {
    return removeMetadataObject(MetadataEntity.INDICATOR_TYPE, id);
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
  public ObjectResponse saveOrgUnit(OrgUnit orgUnit) {
    return saveMetadataObject(orgUnit);
  }

  /**
   * Saves or updates the list of {@link OrgUnit}.
   *
   * @param orgUnits the list of {@link OrgUnit}.
   * @return {@link ObjectsResponse} holding information about the operation.
   */
  public ObjectsResponse saveOrgUnits(List<OrgUnit> orgUnits) {
    return saveMetadataObjects(new Dhis2Objects().setOrganisationUnits(orgUnits));
  }

  /**
   * Updates a {@link OrgUnit}.
   *
   * @param orgUnit the object to update.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse updateOrgUnit(OrgUnit orgUnit) {
    return updateMetadataObject(orgUnit);
  }

  /**
   * Retrieves an {@link OrgUnit}.
   *
   * @param id the object identifier.
   * @return the {@link OrgUnit}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public OrgUnit getOrgUnit(String id) {
    return getMetadataObject(MetadataEntity.ORG_UNIT, id);
  }

  /**
   * Indicates whether an {@link OrgUnit} exists.
   *
   * @param id the object identifier.
   * @return true if the object exists.
   */
  public boolean isOrgUnit(String id) {
    return objectExists(MetadataEntity.ORG_UNIT, id);
  }

  /**
   * Retrieves a list of {@link OrgUnit} which represents the sub-hierarchy of the given parent org
   * unit.
   *
   * @param id the identifier of the parent org unit.
   * @param level the org unit level, relative to the level of the parent org unit, where 1 is the
   *     level immediately below.
   * @param query the {@link Query}.
   * @return list of {@link OrgUnit}.
   */
  public List<OrgUnit> getOrgUnitSubHierarchy(String id, Integer level, Query query) {
    return getObject(
            config
                .getResolvedUriBuilder()
                .appendPath("organisationUnits")
                .appendPath(id)
                .addParameter(FIELDS_PARAM, ORG_UNIT_FIELDS)
                .addParameter("level", String.valueOf(level)),
            query,
            Dhis2Objects.class)
        .getOrganisationUnits();
  }

  /**
   * Retrieves a list of {@link OrgUnit}.
   *
   * @param query the {@link Query}.
   * @return list of {@link OrgUnit}.
   */
  public List<OrgUnit> getOrgUnits(Query query) {
    return getObject(
            config
                .getResolvedUriBuilder()
                .appendPath("organisationUnits")
                .addParameter(FIELDS_PARAM, ORG_UNIT_FIELDS),
            query,
            Dhis2Objects.class)
        .getOrganisationUnits();
  }

  /**
   * Removes a {@link OrgUnit}.
   *
   * @param id the identifier of the object to remove.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse removeOrgUnit(String id) {
    return removeMetadataObject(MetadataEntity.ORG_UNIT, id);
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
  public Response splitOrgUnit(OrgUnitSplitRequest request) {
    URI url = config.getResolvedUrl("organisationUnits/split");

    return executeJsonPostPutRequest(new HttpPost(url), request, Response.class);
  }

  /**
   * Performs an org unit merge operation.
   *
   * @param request the {@link OrgUnitMergeRequest request}.
   * @return the {@link Response} holding information about the operation.
   */
  public Response mergeOrgUnits(OrgUnitMergeRequest request) {
    URI url = config.getResolvedUrl("organisationUnits/merge");

    return executeJsonPostPutRequest(new HttpPost(url), request, Response.class);
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
  public ObjectResponse saveOrgUnitGroup(OrgUnitGroup orgUnitGroup) {
    return saveMetadataObject(orgUnitGroup);
  }

  /**
   * Saves or updates the list of {@link OrgUnitGroup}.
   *
   * @param orgUnitGroups the list of {@link OrgUnitGroup}.
   * @return {@link ObjectsResponse} holding information about the operation.
   */
  public ObjectsResponse saveOrgUnitGroups(List<OrgUnitGroup> orgUnitGroups) {
    return saveMetadataObjects(new Dhis2Objects().setOrganisationUnitGroups(orgUnitGroups));
  }

  /**
   * Updates a {@link OrgUnitGroup}.
   *
   * @param orgUnitGroup the object to update.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse updateOrgUnitGroup(OrgUnitGroup orgUnitGroup) {
    return updateMetadataObject(orgUnitGroup);
  }

  /**
   * Retrieves an {@link OrgUnitGroup}.
   *
   * @param id the object identifier.
   * @return the {@link OrgUnitGroup}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public OrgUnitGroup getOrgUnitGroup(String id) {
    return getMetadataObject(MetadataEntity.ORG_UNIT_GROUP, id);
  }

  /**
   * Indicates whether an {@link OrgUnitGroup} exists.
   *
   * @param id the object identifier.
   * @return true if the object exists.
   */
  public boolean isOrgUnitGroup(String id) {
    return objectExists(MetadataEntity.ORG_UNIT_GROUP, id);
  }

  /**
   * Retrieves a list of {@link OrgUnitGroup}.
   *
   * @param query the {@link Query}.
   * @return list of {@link OrgUnitGroup}.
   */
  public List<OrgUnitGroup> getOrgUnitGroups(Query query) {
    String fieldsParams = query.isExpandAssociations() ? ORG_UNIT_GROUP_EXT_FIELDS : NAME_FIELDS;

    return getObject(
            config
                .getResolvedUriBuilder()
                .appendPath("organisationUnitGroups")
                .addParameter(FIELDS_PARAM, fieldsParams),
            query,
            Dhis2Objects.class)
        .getOrganisationUnitGroups();
  }

  /**
   * Adds the program to the org unit.
   *
   * @param orgUnitGroup the org unit group identifier.
   * @param orgUnit the org unit identifier.
   * @return a {@link Response}.
   */
  public Response addOrgUnitToOrgUnitGroup(String orgUnitGroup, String orgUnit) {
    return addToCollection("organisationUnitGroups", orgUnitGroup, "organisationUnits", orgUnit);
  }

  /**
   * Removes a {@link OrgUnitGroup}.
   *
   * @param id the identifier of the object to remove.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse removeOrgUnitGroup(String id) {
    return removeMetadataObject(MetadataEntity.ORG_UNIT_GROUP, id);
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
  public ObjectResponse saveOrgUnitGroupSet(OrgUnitGroupSet orgUnitGroupSet) {
    return saveMetadataObject(orgUnitGroupSet);
  }

  /**
   * Saves or updates the list of {@link OrgUnitGroupSet}.
   *
   * @param orgUnitGroupSets the list of {@link OrgUnitGroupSet}.
   * @return {@link ObjectsResponse} holding information about the operation.
   */
  public ObjectsResponse saveOrgUnitGroupSets(List<OrgUnitGroupSet> orgUnitGroupSets) {
    return saveMetadataObjects(new Dhis2Objects().setOrganisationUnitGroupSets(orgUnitGroupSets));
  }

  /**
   * Updates a {@link OrgUnitGroupSet}.
   *
   * @param orgUnitGroupSet the object to update.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse updateOrgUnitGroupSet(OrgUnitGroupSet orgUnitGroupSet) {
    return updateMetadataObject(orgUnitGroupSet);
  }

  /**
   * Retrieves an {@link OrgUnitGroupSet}.
   *
   * @param id the object identifier.
   * @return the {@link OrgUnitGroupSet}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public OrgUnitGroupSet getOrgUnitGroupSet(String id) {
    return getMetadataObject(MetadataEntity.ORG_UNIT_GROUP_SET, id);
  }

  /**
   * Indicates whether an {@link OrgUnitGroupSet} exists.
   *
   * @param id the object identifier.
   * @return true if the object exists.
   */
  public boolean isOrgUnitGroupSet(String id) {
    return objectExists(MetadataEntity.ORG_UNIT_GROUP_SET, id);
  }

  /**
   * Retrieves a list of {@link OrgUnitGroupSet}.
   *
   * @param query the {@link Query}.
   * @return list of {@link OrgUnitGroupSet}.
   */
  public List<OrgUnitGroupSet> getOrgUnitGroupSets(Query query) {
    return getObject(
            config
                .getResolvedUriBuilder()
                .appendPath("organisationUnitGroupSets")
                .addParameter(FIELDS_PARAM, ORG_UNIT_GROUP_SET_FIELDS),
            query,
            Dhis2Objects.class)
        .getOrganisationUnitGroupSets();
  }

  /**
   * Removes a {@link OrgUnitGroupSet}.
   *
   * @param id the identifier of the object to remove.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse removeOrgUnitGroupSet(String id) {
    return removeMetadataObject(MetadataEntity.ORG_UNIT_GROUP_SET, id);
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
  public OrgUnitLevel getOrgUnitLevel(String id) {
    return getMetadataObject(MetadataEntity.ORG_UNIT_LEVEL, id);
  }

  /**
   * Retrieves a list of {@link OrgUnitLevel}.
   *
   * @param query the {@link Query}.
   * @return list of {@link OrgUnitLevel}.
   */
  public List<OrgUnitLevel> getOrgUnitLevels(Query query) {
    return getObject(
            config
                .getResolvedUriBuilder()
                .appendPath("organisationUnitLevels")
                .addParameter(FIELDS_PARAM, String.format("%s,level", ID_FIELDS)),
            query,
            Dhis2Objects.class)
        .getOrganisationUnitLevels();
  }

  /**
   * Retrieves a list of "filled" {@link OrgUnitLevel}, meaning any gaps in the persisted levels
   * will be inserted by generated levels.
   *
   * @return list of {@link OrgUnitLevel}.
   */
  public List<OrgUnitLevel> getFilledOrgUnitLevels() {
    // Using array, DHIS2 should have used a wrapper entity

    return asList(
        getObject(
            config.getResolvedUriBuilder().appendPath("filledOrganisationUnitLevels"),
            Query.instance(),
            OrgUnitLevel[].class));
  }

  // -------------------------------------------------------------------------
  // Maps
  // -------------------------------------------------------------------------

  /**
   * Removes a {@link GeoMap}.
   *
   * @param id the identifier of the object to remove.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse removeMap(String id) {
    return removeMetadataObject(MetadataEntity.MAP, id);
  }

  /**
   * Retrieves a {@link GeoMap}.
   *
   * @param id the object identifier.
   * @return the {@link GeoMap}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public GeoMap getMap(String id) {
    return getMetadataObject(MetadataEntity.MAP, id);
  }

  /**
   * Retrieves a list of {@link GeoMap}.
   *
   * @param query the {@link Query}.
   * @return list of {@link GeoMap}.
   */
  public List<GeoMap> getMaps(Query query) {
    return getObject(
            config
                .getResolvedUriBuilder()
                .appendPath("maps")
                .addParameter(FIELDS_PARAM, MAP_FIELDS),
            query,
            Dhis2Objects.class)
        .getMaps();
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
  public OptionSet getOptionSet(String id) {
    return getMetadataObject(MetadataEntity.OPTION_SET, id);
  }

  /**
   * Indicates whether a {@link OptionSet} exists.
   *
   * @param id the object identifier.
   * @return true if the object exists.
   */
  public boolean isOptionSet(String id) {
    return objectExists(MetadataEntity.OPTION_SET, id);
  }

  /**
   * Retrieves a list of {@link OptionSet}.
   *
   * @param query the {@link Query}.
   * @return list of {@link OptionSet}.
   */
  public List<OptionSet> getOptionSets(Query query) {
    String fieldsParam = query.isExpandAssociations() ? OPTION_SET_EXT_FIELDS : OPTION_SET_FIELDS;

    return getObject(
            config
                .getResolvedUriBuilder()
                .appendPath("optionSets")
                .addParameter(FIELDS_PARAM, fieldsParam),
            query,
            Dhis2Objects.class)
        .getOptionSets();
  }

  /**
   * Removes an {@link OptionSet}.
   *
   * @param id the identifier of the object to remove.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse removeOptionSet(String id) {
    return removeMetadataObject(MetadataEntity.OPTION_SET, id);
  }

  // -------------------------------------------------------------------------
  // Options
  // -------------------------------------------------------------------------

  /**
   * Retrieves an {@link Option}.
   *
   * @param id the object identifier.
   * @return the {@link Option}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public Option getOption(String id) {
    return getMetadataObject(MetadataEntity.OPTION, id);
  }

  /**
   * Indicates whether a {@link Option} exists.
   *
   * @param id the object identifier.
   * @return true if the object exists.
   */
  public boolean isOption(String id) {
    return objectExists(MetadataEntity.OPTION, id);
  }

  /**
   * Retrieves a list of {@link Option}.
   *
   * @param query the {@link Query}.
   * @return list of {@link Option}.
   */
  public List<Option> getOptions(Query query) {
    return getObject(
            config
                .getResolvedUriBuilder()
                .appendPath("options")
                .addParameter(FIELDS_PARAM, ID_FIELDS),
            query,
            Dhis2Objects.class)
        .getOptions();
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
  public Program getProgram(String id) {
    return getMetadataObject(MetadataEntity.PROGRAM, id);
  }

  /**
   * Indicates whether a {@link Program} exists.
   *
   * @param id the object identifier.
   * @return true if the object exists.
   */
  public boolean isProgram(String id) {
    return objectExists(MetadataEntity.PROGRAM, id);
  }

  /**
   * Retrieves a list of {@link Program}.
   *
   * @param query the {@link Query}.
   * @return list of {@link Program}.
   */
  public List<Program> getPrograms(Query query) {
    String fieldsParam = query.isExpandAssociations() ? PROGRAM_EXT_FIELDS : PROGRAM_FIELDS;

    return getObject(
            config
                .getResolvedUriBuilder()
                .appendPath("programs")
                .addParameter(FIELDS_PARAM, fieldsParam),
            query,
            Dhis2Objects.class)
        .getPrograms();
  }

  /**
   * Adds the program to the org unit.
   *
   * @param program the program identifier.
   * @param orgUnit the org unit identifier.
   * @return a {@link Response}.
   */
  public Response addOrgUnitToProgram(String program, String orgUnit) {
    return addToCollection("programs", program, "organisationUnits", orgUnit);
  }

  /**
   * Removes a {@link Program}.
   *
   * @param id the identifier of the object to remove.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse removeProgram(String id) {
    return removeMetadataObject(MetadataEntity.PROGRAM, id);
  }

  // -------------------------------------------------------------------------
  // Program section
  // -------------------------------------------------------------------------

  /**
   * Retrieves a {@link ProgramSection}.
   *
   * @param id the object identifier.
   * @return the {@link ProgramStage}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public ProgramSection getProgramSection(String id) {
    return getMetadataObject(MetadataEntity.PROGRAM_SECTION, id);
  }

  // -------------------------------------------------------------------------
  // Program stage
  // -------------------------------------------------------------------------

  /**
   * Retrieves a {@link ProgramStage}.
   *
   * @param id the object identifier.
   * @return the {@link ProgramStage}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public ProgramStage getProgramStage(String id) {
    return getMetadataObject(MetadataEntity.PROGRAM_STAGE, id);
  }

  // -------------------------------------------------------------------------
  // Program stage section
  // -------------------------------------------------------------------------

  /**
   * Retrieves a {@link ProgramStageSection}.
   *
   * @param id the object identifier.
   * @return the {@link ProgramStageSection}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public ProgramStageSection getProgramStageSection(String id) {
    return getMetadataObject(MetadataEntity.PROGRAM_STAGE_SECTION, id);
  }

  // -------------------------------------------------------------------------
  // Program indicators
  // -------------------------------------------------------------------------

  /**
   * Retrieves a {@link ProgramIndicator}.
   *
   * @param id the object identifier.
   * @return the {@link ProgramIndicator}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public ProgramIndicator getProgramIndicator(String id) {
    return getMetadataObject(MetadataEntity.PROGRAM_INDICATOR, id);
  }

  /**
   * Indicates whether a {@link ProgramIndicator} exists.
   *
   * @param id the object identifier.
   * @return true if the object exists.
   */
  public boolean isProgramIndicator(String id) {
    return objectExists(MetadataEntity.PROGRAM_INDICATOR, id);
  }

  /**
   * Retrieves a list of {@link ProgramIndicator}.
   *
   * @param query the {@link Query}.
   * @return list of {@link ProgramIndicator}.
   */
  public List<ProgramIndicator> getProgramIndicators(Query query) {
    return getObject(
            config
                .getResolvedUriBuilder()
                .appendPath("programIndicators")
                .addParameter(FIELDS_PARAM, PROGRAM_INDICATOR_FIELDS),
            query,
            Dhis2Objects.class)
        .getProgramIndicators();
  }

  /**
   * Removes a {@link ProgramIndicator}.
   *
   * @param id the identifier of the object to remove.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse removeProgramIndicator(String id) {
    return removeMetadataObject(MetadataEntity.PROGRAM_INDICATOR, id);
  }

  // -------------------------------------------------------------------------
  // Relationship type
  // -------------------------------------------------------------------------

  /**
   * Saves a {@link RelationshipType}.
   *
   * @param relationshipType the object to save.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse saveRelationshipType(RelationshipType relationshipType) {
    return saveMetadataObject(relationshipType);
  }

  /**
   * Saves or updates the list of {@link RelationshipType}.
   *
   * @param relationshipTypes the list of {@link RelationshipType}.
   * @return {@link ObjectsResponse} holding information about the operation.
   */
  public ObjectsResponse saveRelationshipTypes(List<RelationshipType> relationshipTypes) {
    return saveMetadataObjects(new Dhis2Objects().setRelationshipTypes(relationshipTypes));
  }

  /**
   * Updates a {@link RelationshipType}.
   *
   * @param relationshipType the object to update.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse updateRelationshipType(RelationshipType relationshipType) {
    return updateMetadataObject(relationshipType);
  }

  /**
   * Retrieves a {@link RelationshipType}.
   *
   * @param id the object identifier.
   * @return the {@link RelationshipType}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public RelationshipType getRelationshipType(String id) {
    return getMetadataObject(MetadataEntity.RELATIONSHIP_TYPE, id);
  }

  /**
   * Indicates whether a {@link RelationshipType} exists.
   *
   * @param id the object identifier.
   * @return true if the object exists.
   */
  public boolean isRelationshipType(String id) {
    return objectExists(MetadataEntity.RELATIONSHIP_TYPE, id);
  }

  /**
   * Retrieves a {@link RelationshipType}.
   *
   * @param query the {@link Query}.
   * @return a list of {@link RelationshipType}.
   */
  public List<RelationshipType> getRelationshipTypes(Query query) {
    return getObject(
            config
                .getResolvedUriBuilder()
                .appendPath("relationshipTypes")
                .addParameter(FIELDS_PARAM, RELATIONSHIP_TYPE_FIELDS),
            query,
            Dhis2Objects.class)
        .getRelationshipTypes();
  }

  /**
   * Removes a {@link RelationshipType}.
   *
   * @param id the identifier of the object to remove.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse removeRelationshipType(String id) {
    return removeMetadataObject(MetadataEntity.RELATIONSHIP_TYPE, id);
  }

  // -------------------------------------------------------------------------
  // Tracked entity type
  // -------------------------------------------------------------------------

  /**
   * Saves a {@link TrackedEntityType}.
   *
   * @param type the object to save.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse saveTrackedEntityType(TrackedEntityType type) {
    return saveMetadataObject(type);
  }

  /**
   * Updates a {@link TrackedEntityType}.
   *
   * @param type the object to update.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse updateTrackedEntityType(TrackedEntityType type) {
    return updateMetadataObject(type);
  }

  /**
   * Retrieves a {@link TrackedEntityType}.
   *
   * @param id the object identifier.
   * @return the {@link TrackedEntityType}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public TrackedEntityType getTrackedEntityType(String id) {
    return getMetadataObject(MetadataEntity.TRACKED_ENTITY_TYPE, id);
  }

  /**
   * Indicates whether a {@link TrackedEntityType} exists.
   *
   * @param id the object identifier.
   * @return true if the object exists.
   */
  public boolean isTrackedEntityType(String id) {
    return objectExists(MetadataEntity.TRACKED_ENTITY_TYPE, id);
  }

  /**
   * Retrieves a list of {@link TrackedEntityType}.
   *
   * @param query the {@link Query}.
   * @return list of {@link TrackedEntityType}.
   */
  public List<TrackedEntityType> getTrackedEntityTypes(Query query) {
    return getObject(
            config
                .getResolvedUriBuilder()
                .appendPath("trackedEntityTypes")
                .addParameter(FIELDS_PARAM, TRACKED_ENTITY_TYPE_FIELDS),
            query,
            Dhis2Objects.class)
        .getTrackedEntityTypes();
  }

  /**
   * Removes an {@link TrackedEntityType}.
   *
   * @param id the identifier of the object to remove.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse removeTrackedEntityType(String id) {
    return removeMetadataObject(MetadataEntity.TRACKED_ENTITY_TYPE, id);
  }

  // -------------------------------------------------------------------------
  // Tracked entity attributes
  // -------------------------------------------------------------------------

  /**
   * Saves a {@link TrackedEntityAttribute}.
   *
   * @param attribute the tracked entity attribute object to save.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse saveTrackedEntityAttribute(TrackedEntityAttribute attribute) {
    return saveMetadataObject(attribute);
  }

  /**
   * Saves or updates the list of {@link TrackedEntityAttribute}.
   *
   * @param attributes the list of {@link TrackedEntityAttribute}.
   * @return {@link ObjectsResponse} holding information about the operation.
   */
  public ObjectsResponse saveTrackedEntityAttributes(List<TrackedEntityAttribute> attributes) {
    return saveMetadataObjects(new Dhis2Objects().setTrackedEntityAttributes(attributes));
  }

  /**
   * Updates a {@link TrackedEntityAttribute}.
   *
   * @param attribute the tracked entity attribute object to update.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse updateTrackedEntityAttribute(TrackedEntityAttribute attribute) {
    return updateMetadataObject(attribute);
  }

  /**
   * Removes a {@link TrackedEntityAttribute}.
   *
   * @param id the identifier of the tracked entity attribute to remove.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse removeTrackedEntityAttribute(String id) {
    return removeMetadataObject(MetadataEntity.TRACKED_ENTITY_ATTRIBUTE, id);
  }

  /**
   * Retrieves an {@link TrackedEntityAttribute}.
   *
   * @param id the identifier of the tracked entity attribute.
   * @return the {@link TrackedEntityAttribute}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public TrackedEntityAttribute getTrackedEntityAttribute(String id) {
    return getMetadataObject(MetadataEntity.TRACKED_ENTITY_ATTRIBUTE, id);
  }

  /**
   * Retrieves a list of {@link TrackedEntityAttribute}.
   *
   * @param query the {@link Query}.
   * @return list of {@link TrackedEntityAttribute}.
   */
  public List<TrackedEntityAttribute> getTrackedEntityAttributes(Query query) {
    return getObject(
            config
                .getResolvedUriBuilder()
                .appendPath("trackedEntityAttributes")
                .addParameter(FIELDS_PARAM, TRACKED_ENTITY_ATTRIBUTE_FIELDS),
            query,
            Dhis2Objects.class)
        .getTrackedEntityAttributes();
  }

  // -------------------------------------------------------------------------
  // User
  // -------------------------------------------------------------------------

  /**
   * Retrieves a {@link User}.
   *
   * @param id the object identifier.
   * @return the {@link User}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public User getUser(String id) {
    return getMetadataObject(MetadataEntity.USER, id);
  }

  /**
   * Retrieves a list of {@link User}.
   *
   * @param query the {@link Query}.
   * @return list of {@link User}.
   */
  public List<User> getUsers(Query query) {
    return getObject(
            config
                .getResolvedUriBuilder()
                .appendPath("users")
                .addParameter(FIELDS_PARAM, USER_FIELDS),
            query,
            Dhis2Objects.class)
        .getUsers();
  }

  // -------------------------------------------------------------------------
  // User groups
  // -------------------------------------------------------------------------

  /**
   * Saves a {@link UserGroup}.
   *
   * @param userGroup the object to save.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse saveUserGroup(UserGroup userGroup) {
    return saveMetadataObject(userGroup);
  }

  /**
   * Saves or updates the list of {@link UserGroup}.
   *
   * @param userGroups the list of {@link UserGroup}.
   * @return {@link ObjectsResponse} holding information about the operation.
   */
  public ObjectsResponse saveUserGroups(List<UserGroup> userGroups) {
    return saveMetadataObjects(new Dhis2Objects().setUserGroups(userGroups));
  }

  /**
   * Updates a {@link UserGroup}.
   *
   * @param userGroup the object to update.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse updateUserGroup(UserGroup userGroup) {
    return updateMetadataObject(userGroup);
  }

  /**
   * Retrieves a {@link UserGroup}.
   *
   * @param id the object identifier.
   * @return the {@link UserGroup}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public UserGroup getUserGroup(String id) {
    return getMetadataObject(MetadataEntity.USER_GROUP, id);
  }

  /**
   * Indicates whether a {@link UserGroup} exists.
   *
   * @param id the object identifier.
   * @return true if the object exists.
   */
  public boolean isUserGroup(String id) {
    return objectExists(MetadataEntity.USER_GROUP, id);
  }

  /**
   * Retrieves a list of {@link UserGroup}.
   *
   * @param query the {@link Query}.
   * @return list of {@link UserGroup}.
   */
  public List<UserGroup> getUserGroups(Query query) {
    return getObject(
            config
                .getResolvedUriBuilder()
                .appendPath("userGroups")
                .addParameter(FIELDS_PARAM, USER_GROUP_FIELDS),
            query,
            Dhis2Objects.class)
        .getUserGroups();
  }

  /**
   * Removes a {@link UserGroup}.
   *
   * @param id the identifier of the object to remove.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse removeUserGroup(String id) {
    return removeMetadataObject(MetadataEntity.USER_GROUP, id);
  }

  // -------------------------------------------------------------------------
  // User roles
  // -------------------------------------------------------------------------

  /**
   * Saves a {@link UserRole}.
   *
   * @param userRole the object to save.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse saveUserRole(UserRole userRole) {
    return saveMetadataObject(userRole);
  }

  /**
   * Saves or updates the list of {@link UserRole}.
   *
   * @param userRoles the list of {@link UserRole}.
   * @return {@link ObjectsResponse} holding information about the operation.
   */
  public ObjectsResponse saveUserRoles(List<UserRole> userRoles) {
    return saveMetadataObjects(new Dhis2Objects().setUserRoles(userRoles));
  }

  /**
   * Updates a {@link UserRole}.
   *
   * @param userRole the object to update.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse updateUserRole(UserRole userRole) {
    return updateMetadataObject(userRole);
  }

  /**
   * Retrieves a {@link UserRole}.
   *
   * @param id the object identifier.
   * @return the {@link UserRole}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public UserRole getUserRole(String id) {
    return getMetadataObject(MetadataEntity.USER_ROLE, id);
  }

  /**
   * Indicates whether a {@link UserRole} exists.
   *
   * @param id the object identifier.
   * @return true if the object exists.
   */
  public boolean isUserRole(String id) {
    return objectExists(MetadataEntity.USER_ROLE, id);
  }

  /**
   * Retrieves a list of {@link UserRole}.
   *
   * @param query the {@link Query}.
   * @return list of {@link UserRole}.
   */
  public List<UserRole> getUserRoles(Query query) {
    return getObject(
            config
                .getResolvedUriBuilder()
                .appendPath("userRoles")
                .addParameter(FIELDS_PARAM, USER_ROLE_FIELDS),
            query,
            Dhis2Objects.class)
        .getUserRoles();
  }

  /**
   * Removes a {@link UserRole}.
   *
   * @param id the identifier of the object to remove.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse removeUserRole(String id) {
    return removeMetadataObject(MetadataEntity.USER_ROLE, id);
  }

  // -------------------------------------------------------------------------
  // Visualization
  // -------------------------------------------------------------------------

  /**
   * Retrieves a {@link Visualization}.
   *
   * @param id the object identifier.
   * @return the {@link Visualization}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public Visualization getVisualization(String id) {
    return getMetadataObject(MetadataEntity.VISUALIZATION, id);
  }

  /**
   * Indicates whether a {@link Visualization} exists.
   *
   * @param id the object identifier.
   * @return true if the object exists.
   */
  public boolean isVisualization(String id) {
    return objectExists(MetadataEntity.VISUALIZATION, id);
  }

  /**
   * Retrieves a list of {@link Visualization}.
   *
   * @param query the {@link Query}.
   * @return list of {@link Visualization}.
   */
  public List<Visualization> getVisualizations(Query query) {
    return getObject(
            config
                .getResolvedUriBuilder()
                .appendPath("visualizations")
                .addParameter(FIELDS_PARAM, VISUALIZATION_FIELDS),
            query,
            Dhis2Objects.class)
        .getVisualizations();
  }

  /**
   * Removes a {@link Visualization}.
   *
   * @param id the identifier of the object to remove.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  public ObjectResponse removeVisualization(String id) {
    return removeMetadataObject(MetadataEntity.VISUALIZATION, id);
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
  public List<PeriodType> getPeriodTypes(Query query) {
    return getObject(
            config
                .getResolvedUriBuilder()
                .appendPath("periodTypes")
                .addParameter(FIELDS_PARAM, "frequencyOrder,name,isoDuration,isoFormat"),
            query,
            Dhis2Objects.class)
        .getPeriodTypes();
  }

  // -------------------------------------------------------------------------
  // File resource
  // -------------------------------------------------------------------------

  /**
   * Retrieves a {@link FileResource}.
   *
   * @param id the object identifier.
   * @return the {@link FileResource}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public FileResource getFileResource(String id) {
    return getObject(
        config
            .getResolvedUriBuilder()
            .appendPath("fileResources")
            .appendPath(id)
            .addParameter(FIELDS_PARAM, FILE_RESOURCE_FIELDS),
        Query.instance(),
        FileResource.class);
  }

  /**
   * Retrieves a {@link FileResource}.
   *
   * @param query the {@link Query}.
   * @return a list of {@link FileResource}.
   */
  public List<FileResource> getFileResources(Query query) {
    return getObject(
            config
                .getResolvedUriBuilder()
                .appendPath("fileResources")
                .addParameter(FIELDS_PARAM, FILE_RESOURCE_FIELDS),
            query,
            Dhis2Objects.class)
        .getFileResources();
  }

  // -------------------------------------------------------------------------
  // System settings
  // -------------------------------------------------------------------------

  /**
   * Retrieves {@link SystemSettings}.
   *
   * @return system settings.
   */
  public SystemSettings getSystemSettings() {
    return getObject(
        config.getResolvedUriBuilder().appendPath("systemSettings"),
        Query.instance(),
        SystemSettings.class);
  }

  // -------------------------------------------------------------------------
  // Data value set
  // -------------------------------------------------------------------------

  /**
   * Saves a {@link DataValueSet}.
   *
   * @param dataValueSet the {@link DataValueSet} to save.
   * @param options the {@link DataValueSetImportOptions}.
   * @return {@link DataValueSetResponse} holding information about the operation.
   */
  public DataValueSetResponse saveDataValueSet(
      DataValueSet dataValueSet, DataValueSetImportOptions options) {
    URIBuilder builder = config.getResolvedUriBuilder().appendPath("dataValueSets");

    URI url = getDataValueSetImportQuery(builder, options);

    HttpPost request =
        getPostRequest(url, new StringEntity(toJsonString(dataValueSet), StandardCharsets.UTF_8));

    Dhis2AsyncRequest asyncRequest = new Dhis2AsyncRequest(config, httpClient, objectMapper);

    return asyncRequest.post(request, DataValueSetResponse.class);
  }

  /**
   * Saves a data value set payload in JSON format represented by the given file.
   *
   * @param file the file representing the data value set JSON payload.
   * @param options the {@link DataValueSetImportOptions}.
   * @return {@link DataValueSetResponse} holding information about the operation.
   */
  public DataValueSetResponse saveDataValueSet(File file, DataValueSetImportOptions options) {
    URIBuilder builder = config.getResolvedUriBuilder().appendPath("dataValueSets");

    URI url = getDataValueSetImportQuery(builder, options);

    HttpPost request = getPostRequest(url, new FileEntity(file, ContentType.APPLICATION_JSON));

    Dhis2AsyncRequest asyncRequest = new Dhis2AsyncRequest(config, httpClient, objectMapper);

    return asyncRequest.post(request, DataValueSetResponse.class);
  }

  /**
   * Saves a data value set payload in JSON format represented by the given input stream.
   *
   * @param inputStream the input stream representing the data value set JSON payload.
   * @param options the {@link DataValueSetImportOptions}.
   * @return {@link DataValueSetResponse} holding information about the operation.
   */
  public DataValueSetResponse saveDataValueSet(
      InputStream inputStream, DataValueSetImportOptions options) {
    URIBuilder builder = config.getResolvedUriBuilder().appendPath("dataValueSets");

    URI url = getDataValueSetImportQuery(builder, options);

    HttpPost request =
        getPostRequest(url, new InputStreamEntity(inputStream, ContentType.APPLICATION_JSON));

    Dhis2AsyncRequest asyncRequest = new Dhis2AsyncRequest(config, httpClient, objectMapper);

    return asyncRequest.post(request, DataValueSetResponse.class);
  }

  /**
   * Retrieves a {@link DataValueSet}.
   *
   * @param query the {@link AnalyticsQuery}.
   * @return {@link DataValueSet}.
   */
  public DataValueSet getDataValueSet(DataValueSetQuery query) {
    return getDataValueSetResponse(
        config.getResolvedUriBuilder().appendPath("dataValueSets.json"), query);
  }

  /**
   * Retrieves the content of a file resource from a {@link DataValue}.
   *
   * @param query the {@link DataValueQuery}.
   * @return the content of a file resource referenced in a {@link DataValue}.
   */
  public String getDataValueFile(DataValueQuery query) {
    return getDataValueFileResponse(
        config.getResolvedUriBuilder().appendPath("dataValues/files"), query);
  }

  // -------------------------------------------------------------------------
  // Analytics data
  // -------------------------------------------------------------------------

  /**
   * Retrieves a {@link AnalyticsData}.
   *
   * @param query the {@link AnalyticsQuery}.
   * @return {@link AnalyticsData}.
   */
  public AnalyticsData getAnalyticsData(AnalyticsQuery query) {
    return getAnalyticsResponse(
        config.getResolvedUriBuilder().appendPath("analytics"), query, AnalyticsData.class);
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
  public DataValueSet getAnalyticsDataValueSet(AnalyticsQuery query) {
    return getAnalyticsResponse(
        config.getResolvedUriBuilder().appendPath("analytics").appendPath("dataValueSet.json"),
        query,
        DataValueSet.class);
  }

  /**
   * Retrieves a {@link DataValueSet} and writes it to the given file.
   *
   * @param query the {@link AnalyticsQuery}.
   * @param file the {@link File}.
   */
  public void writeAnalyticsDataValueSet(AnalyticsQuery query, File file) {
    URI url =
        getAnalyticsQuery(
            config.getResolvedUriBuilder().appendPath("analytics").appendPath("dataValueSet.json"),
            query);

    CloseableHttpResponse response = getJsonHttpResponse(url);

    writeToFile(response, file);
  }

  // -------------------------------------------------------------------------
  // CompleteDataSetRegistration
  // -------------------------------------------------------------------------

  /**
   * Retrieves a list of {@link CompleteDataSetRegistration}.
   *
   * @param query the {@link Query}.
   * @return list of {@link CompleteDataSetRegistration}.
   */
  public List<CompleteDataSetRegistration> getCompleteDataSetRegistrations(
      CompleteDataSetRegistrationQuery query) {
    Objects.requireNonNull(query, "query must be specified");

    URIBuilder uriBuilder =
        config.getResolvedUriBuilder().appendPath("completeDataSetRegistrations");

    URI uri = getCompleteDataSetRegistrationQuery(uriBuilder, query);

    return getObjectFromUrl(uri, Dhis2Objects.class).getCompleteDataSetRegistrations();
  }

  /**
   * Saves or updates the list of {@link CompleteDataSetRegistration}.
   *
   * @param completeDataSetRegistrations the list of {@link CompleteDataSetRegistration}.
   * @param options import options {@link CompleteDataSetRegistrationImportOptions}.
   * @return {@link CompleteDataSetRegistrationResponse} holding information about the operation.
   */
  public CompleteDataSetRegistrationResponse saveCompleteDataSetRegistrations(
      List<CompleteDataSetRegistration> completeDataSetRegistrations,
      CompleteDataSetRegistrationImportOptions options) {
    Dhis2Objects entityObject =
        new Dhis2Objects().setCompleteDataSetRegistrations(completeDataSetRegistrations);

    StringEntity entity = new StringEntity(toJsonString(entityObject), StandardCharsets.UTF_8);

    return saveCompleteDataSetRegistrations(entity, options);
  }

  /**
   * Saves a complete data set registration payload in JSON format represented by the given file.
   *
   * @param file the file representing the complete data set registration JSON payload.
   * @param options the {@link CompleteDataSetRegistrationImportOptions}.
   * @return {@link CompleteDataSetRegistrationResponse} holding information about the operation.
   */
  public CompleteDataSetRegistrationResponse saveCompleteDataSetRegistrations(
      File file, CompleteDataSetRegistrationImportOptions options) {
    FileEntity fileEntity = new FileEntity(file, ContentType.APPLICATION_JSON);

    return saveCompleteDataSetRegistrations(fileEntity, options);
  }

  /**
   * Saves a complete data set registration payload in JSON format represented by the given input
   * stream.
   *
   * @param inputStream the input stream representing the complete data set registration JSON
   *     payload.
   * @param options the {@link CompleteDataSetRegistrationImportOptions}.
   * @return {@link CompleteDataSetRegistrationResponse} holding information about the operation.
   */
  public CompleteDataSetRegistrationResponse saveCompleteDataSetRegistrations(
      InputStream inputStream, CompleteDataSetRegistrationImportOptions options) {
    InputStreamEntity inputStreamEntity =
        new InputStreamEntity(inputStream, ContentType.APPLICATION_JSON);

    return saveCompleteDataSetRegistrations(inputStreamEntity, options);
  }

  // -------------------------------------------------------------------------
  // Event
  // -------------------------------------------------------------------------

  /**
   * Saves an {@link Events}. The operation is synchronous.
   *
   * <p>Requires DHIS2 version 2.36 or later.
   *
   * @param events the {@link Events}.
   * @return {@link EventResponse} holding information about the operation.
   */
  public EventResponse saveEvents(Events events) {
    return saveObject(
        config
            .getResolvedUriBuilder()
            .appendPath("tracker")
            .setParameter("async", "false")
            .setParameter("importStrategy", ImportStrategy.CREATE_AND_UPDATE.name()),
        events,
        EventResponse.class);
  }

  /**
   * Saves an {@link Events}. The operation is synchronous.
   *
   * <p>Requires DHIS2 version 2.40 or later.
   *
   * @param inputStream the input stream representing the data value set JSON payload.
   * @return {@link EventResponse} holding information about the operation.
   */
  public EventResponse saveEvents(InputStream inputStream) {
    URIBuilder builder = config.getResolvedUriBuilder().appendPath("tracker");

    HttpPost request =
        getPostRequest(
            HttpUtils.build(builder),
            new InputStreamEntity(inputStream, ContentType.APPLICATION_JSON));

    Dhis2AsyncRequest asyncRequest = new Dhis2AsyncRequest(config, httpClient, objectMapper);

    return asyncRequest.postEvent(request, EventResponse.class);
  }

  /**
   * Retrieves an {@link Event}.
   *
   * <p>Requires DHIS2 version 2.36 or later.
   *
   * @param id the event identifier.
   * @return the {@link Event}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public Event getEvent(String id) {
    return getObject(
        config.getResolvedUriBuilder().appendPath("tracker").appendPath("events").appendPath(id),
        Query.instance(),
        Event.class);
  }

  /**
   * Retrieves an {@link EventsResult}.
   *
   * <p>Requires DHIS2 version 2.36 or later.
   *
   * @param query the {@link EventQuery}.
   * @return the {@link EventsResult}.
   */
  public EventsResult getEvents(EventQuery query) {
    return getEventsResult(
        config.getResolvedUriBuilder().appendPath("tracker").appendPath("events"), query);
  }

  /**
   * Removes an {@link Events}. The operation is synchronous.
   *
   * <p>Requires DHIS2 version 2.36 or later.
   *
   * @param events the {@link Events}.
   * @return the {@link EventResponse} holding information about the operation.
   */
  public EventResponse removeEvents(Events events) {
    return saveObject(
        config
            .getResolvedUriBuilder()
            .appendPath("tracker")
            .setParameter("async", "false")
            .setParameter("importStrategy", ImportStrategy.DELETE.name()),
        events,
        EventResponse.class);
  }

  /**
   * Removes an {@link Event}.
   *
   * <p>Requires DHIS2 version 2.36 or later.
   *
   * @param event the {@link Event}.
   * @return the {@link EventResponse} holding information about the operation.
   */
  public EventResponse removeEvent(Event event) {
    Objects.requireNonNull(event.getId(), "Event identifier must be specified");

    Events events = new Events(list(event));

    return saveObject(
        config
            .getResolvedUriBuilder()
            .appendPath("tracker")
            .setParameter("async", "false")
            .setParameter("importStrategy", "DELETE"),
        events,
        EventResponse.class);
  }

  // -------------------------------------------------------------------------
  // Tracked entity
  // -------------------------------------------------------------------------

  /**
   * Saves {@link TrackedEntityObjects}. The operation is synchronous.
   *
   * <p>Requires DHIS2 version 2.36 or later.
   *
   * @param trackedEntityObjects the {@link TrackedEntityObjects}.
   * @param params the {@link TrackedEntityImportParams}.
   * @return the {@link TrackedEntityResponse}.
   */
  public TrackedEntityResponse saveTrackedEntityObjects(
      TrackedEntityObjects trackedEntityObjects, TrackedEntityImportParams params) {
    URIBuilder uriBuilder =
        getTrackedEntityImportParams(
            config.getResolvedUriBuilder().appendPath("tracker").addParameter("async", "false"),
            params);

    return saveObject(uriBuilder, trackedEntityObjects, TrackedEntityResponse.class);
  }

  /**
   * Retrieves an {@link TrackedEntity}. Includes attribute values for attributes associated with
   * the tracked entity type only.
   *
   * <p>Requires DHIS2 version 2.36 or later.
   *
   * @param id the event identifier.
   * @return the {@link TrackedEntity}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public TrackedEntity getTrackedEntity(String id) {
    return getObject(
        config
            .getResolvedUriBuilder()
            .appendPath("tracker")
            .appendPath("trackedEntities")
            .appendPath(id)
            .addParameter(FIELDS_PARAM, TRACKED_ENTITY_FIELDS),
        Query.instance(),
        TrackedEntity.class);
  }

  /**
   * Retrieves a {@link TrackedEntitiesResult}. Includes attribute values for attributes associated
   * with programs which the tracked entity is enrolled in.
   *
   * <p>Requires DHIS2 version 2.36 or later.
   *
   * @param query the {@link TrackedEntityQuery}.
   * @return the {@link TrackedEntitiesResult}.
   */
  public TrackedEntitiesResult getTrackedEntities(TrackedEntityQuery query) {
    return getTrackedEntitiesResult(
        config
            .getResolvedUriBuilder()
            .appendPath("tracker")
            .appendPath("trackedEntities")
            .addParameter(FIELDS_PARAM, TRACKED_ENTITY_FIELDS),
        query);
  }

  // -------------------------------------------------------------------------
  // Enrollment
  // -------------------------------------------------------------------------
  /**
   * Retrieves an {@link Enrollment}.
   *
   * <p>Requires DHIS2 version 2.36 or later.
   *
   * @param id the enrollment identifier.
   * @return the {@link Enrollment}.
   * @throws Dhis2ClientException if the object does not exist.
   */
  public Enrollment getEnrollment(String id) {
    return getObject(
        config
            .getResolvedUriBuilder()
            .appendPath("tracker")
            .appendPath("enrollments")
            .appendPath(id),
        Query.instance(),
        Enrollment.class);
  }

  /**
   * Retrieves a {@link EnrollmentsResult}.
   *
   * <p>Requires DHIS2 version 2.36 or later.
   *
   * @param query the {@link EnrollmentQuery}.
   * @return the {@link EnrollmentsResult}.
   */
  public EnrollmentsResult getEnrollments(EnrollmentQuery query) {
    return getEnrollmentResult(
        config.getResolvedUriBuilder().appendPath("tracker").appendPath("enrollments"), query);
  }

  // -------------------------------------------------------------------------
  // Relationship
  // -------------------------------------------------------------------------

  /**
   * Retrieves a {@link RelationshipsResult}.
   *
   * <p>Requires DHIS2 version 2.36 or later.
   *
   * @param query the {@link RelationshipQuery}.
   * @return the {@link RelationshipsResult}.
   */
  public RelationshipsResult getRelationships(RelationshipQuery query) {
    URIBuilder uriBuilder =
        config.getResolvedUriBuilder().appendPath("tracker").appendPath("relationships");

    URI url = getRelationshipQuery(uriBuilder, query);

    return getObjectFromUrl(url, RelationshipsResult.class);
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
  public List<JobNotification> getJobNotifications(JobCategory category, String id) {
    JobNotification[] response =
        getObject(
            config
                .getResolvedUriBuilder()
                .appendPath("system")
                .appendPath("tasks")
                .appendPath(category.name())
                .appendPath(id),
            Query.instance(),
            JobNotification[].class);

    return new ArrayList<>(Arrays.asList(response));
  }

  /**
   * Retrieves the validation results of a {@link DataSet} in a particular {@link Period} and {@link
   * OrgUnit}.
   *
   * @param query the {@link DataSetValidationQuery}.
   * @return the validation results of the {@link DataSet} in the specified {@link Period} and
   *     {@link OrgUnit}.
   */
  public Validation getDataSetValidation(DataSetValidationQuery query) {
    return getDataSetValidationResponse(
        config
            .getResolvedUriBuilder()
            .appendPath("validation/dataSet")
            .appendPath(query.getDataSet())
            .addParameter(FIELDS_PARAM, DATA_SET_VALIDATION_FIELDS),
        query);
  }

  /**
   * Retrieves a list of {@link ValidationRule}.
   *
   * @param dataSet the object identifier of the Data Set where the validations apply.
   * @return list of {@link ValidationRule}.
   */
  public List<ValidationRule> getValidationRules(String dataSet) {
    return getObject(
            config
                .getResolvedUriBuilder()
                .appendPath("validationRules")
                .addParameter("dataSet", dataSet)
                .addParameter(FIELDS_PARAM, VALIDATION_RULE_FIELDS),
            Query.instance(),
            Dhis2Objects.class)
        .getValidationRules();
  }
}
