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

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.hc.core5.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.hc.core5.http.HttpStatus.SC_CONFLICT;
import static org.apache.hc.core5.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.hc.core5.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.apache.hc.core5.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hisp.dhis.util.CollectionUtils.asList;
import static org.hisp.dhis.util.CollectionUtils.toCommaSeparated;
import static org.hisp.dhis.util.HttpUtils.getUriAsString;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.HttpResponseException;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.hisp.dhis.model.MetadataEntity;
import org.hisp.dhis.model.completedatasetregistration.CompleteDataSetRegistrationImportOptions;
import org.hisp.dhis.model.datavalueset.DataValueSet;
import org.hisp.dhis.model.datavalueset.DataValueSetImportOptions;
import org.hisp.dhis.model.enrollment.EnrollmentsResult;
import org.hisp.dhis.model.event.EventsResult;
import org.hisp.dhis.model.trackedentity.TrackedEntitiesResult;
import org.hisp.dhis.model.validation.Validation;
import org.hisp.dhis.query.BaseQuery;
import org.hisp.dhis.query.Filter;
import org.hisp.dhis.query.Operator;
import org.hisp.dhis.query.Order;
import org.hisp.dhis.query.Paging;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.query.RootJunction;
import org.hisp.dhis.query.analytics.AnalyticsQuery;
import org.hisp.dhis.query.analytics.Dimension;
import org.hisp.dhis.query.completedatasetregistration.CompleteDataSetRegistrationQuery;
import org.hisp.dhis.query.datavalue.DataValueQuery;
import org.hisp.dhis.query.datavalue.DataValueSetQuery;
import org.hisp.dhis.query.enrollment.EnrollmentQuery;
import org.hisp.dhis.query.event.EventQuery;
import org.hisp.dhis.query.relationship.RelationshipQuery;
import org.hisp.dhis.query.trackedentity.TrackedEntityQuery;
import org.hisp.dhis.query.tracker.TrackedEntityImportParams;
import org.hisp.dhis.query.validations.DataSetValidationQuery;
import org.hisp.dhis.response.BaseHttpResponse;
import org.hisp.dhis.response.Dhis2ClientException;
import org.hisp.dhis.response.HttpStatus;
import org.hisp.dhis.response.Response;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.completedatasetregistration.CompleteDataSetRegistrationResponse;
import org.hisp.dhis.util.DateTimeUtils;
import org.hisp.dhis.util.HttpUtils;
import org.hisp.dhis.util.JacksonUtils;

/**
 * @author Lars Helge Overland
 */
@Slf4j
public class BaseDhis2 {
  private static final String SEP_DIM = ";";

  // Paths

  /** Metadata API path. */
  protected static final String PATH_METADATA = "metadata";

  /** Tracker API path. */
  protected static final String PATH_TRACKER = "tracker";

  /** Analytics API path. */
  protected static final String PATH_ANALYTICS = "analytics";

  // Params

  /** Fields parameter. */
  protected static final String FIELDS_PARAM = "fields";

  /** Skip sharing parameter. */
  protected static final String SKIP_SHARING_PARAM = "skipSharing";

  // Log levels

  /** Log level system property. */
  private static final String LOG_LEVEL_SYSTEM_PROPERTY = "log.level.dhis2";

  /** Info log level. */
  private static final String LOG_LEVEL_INFO = "info";

  /** Warn log level. */
  private static final String LOG_LEVEL_WARN = "warn";

  /** Override current log level for debugging here. */
  private static final Optional<String> LOG_LEVEL = Optional.empty();

  // Status codes

  /** Error status codes. */
  private static final Set<Integer> ERROR_STATUS_CODES =
      Set.of(SC_UNAUTHORIZED, SC_FORBIDDEN, SC_NOT_FOUND);

  /** Error status codes for GET queries. */
  private static final Set<Integer> GET_ERROR_STATUS_CODES = Set.of(SC_BAD_REQUEST, SC_CONFLICT);

  // Fields

  // Headers

  protected static final Header HEADER_CONTENT_TYPE_JSON =
      new BasicHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());

  protected static final Header HEADER_ACCEPT_JSON =
      new BasicHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

  // Properties

  protected final Dhis2Config config;

  protected final ObjectMapper objectMapper;

  protected final CloseableHttpClient httpClient;

  public BaseDhis2(Dhis2Config config) {
    Objects.requireNonNull(config, "Config must be specified");
    this.config = config;
    this.objectMapper = JacksonUtils.getObjectMapper();
    this.httpClient = HttpClients.createDefault();
  }

  /**
   * Retrieves an object using HTTP GET.
   *
   * @param <T> the type.
   * @param uriBuilder the URI builder.
   * @param query the {@link Query} filters to apply.
   * @param type the class type of the object.
   * @return the object.
   * @throws Dhis2ClientException if unauthorized, access denied or resource not found.
   */
  protected <T> T getObject(URIBuilder uriBuilder, Query query, Class<T> type) {
    URI url = getObjectQuery(uriBuilder, query);

    return getObjectFromUrl(url, type);
  }

  /**
   * Retrieves an object.
   *
   * @param <T> the type.
   * @param path the URL path.
   * @param id the object identifier.
   * @param type the class type of the object.
   * @return the object.
   * @throws Dhis2ClientException if unauthorized, access denied or resource not found.
   */
  protected <T> T getObject(String path, String id, Class<T> type) {
    try {
      URI url = config.getResolvedUriBuilder().appendPath(path).appendPath(id).build();

      return getObjectFromUrl(url, type);
    } catch (URISyntaxException ex) {
      throw new RuntimeException(ex);
    }
  }

  /**
   * Indicates whether an object exists using HTTP HEAD.
   *
   * @param entity the {@link MetadataEntity}.
   * @param id the object identifier.
   * @return true if the object exists.
   */
  public boolean objectExists(MetadataEntity entity, String id) {
    return objectExists(config.getResolvedUriBuilder().appendPath(entity.getPath()).appendPath(id));
  }

  /**
   * Indicates whether an object exists using HTTP GET.
   *
   * <p>Note that using HTTP HEAD is more efficient but not supported by DHIS2 personal access
   * tokens per 01.06.2025.
   *
   * @param uriBuilder the URI builder.
   * @return true if the object exists.
   */
  protected boolean objectExists(URIBuilder uriBuilder) {
    HttpGet request = withAuth(new HttpGet(HttpUtils.build(uriBuilder)));
    request.setHeader(HEADER_ACCEPT_JSON);

    try {
      return httpClient.execute(
          request,
          response -> {
            log.info("Response status code: {}", response.getCode());
            return SC_OK == response.getCode();
          });
    } catch (IOException ex) {
      return false;
    }
  }

  /**
   * Returns a {@link URI} based on the given query.
   *
   * @param uriBuilder the URI builder.
   * @param query the {@link Query} filters to apply.
   * @return a URI.
   */
  protected URI getObjectQuery(URIBuilder uriBuilder, Query query) {
    for (Filter filter : query.getFilters()) {
      Object value = getQueryValue(filter);
      String filterValue =
          String.format("%s:%s:%s", filter.getProperty(), filter.getOperator().value(), value);
      uriBuilder.addParameter("filter", filterValue);
    }

    if (isNotEmpty(query.getFilters()) && query.getRootJunction() == RootJunction.OR) {
      uriBuilder.addParameter("rootJunction", "OR");
    }

    addPaging(uriBuilder, query);
    addOrder(uriBuilder, query);

    return HttpUtils.build(uriBuilder);
  }

  /**
   * Adds order related parameter to the given {@link URIBuilder} based on the given {@link
   * BaseQuery}.
   *
   * @param uriBuilder the {@link URIBuilder}.
   * @param query the {@link BaseQuery}.
   */
  protected void addOrder(URIBuilder uriBuilder, BaseQuery query) {
    List<Order> orders = query.getOrder();

    if (isNotEmpty(orders)) {
      String orderValue =
          query.getOrder().stream().map(Order::toValue).collect(Collectors.joining(","));

      uriBuilder.addParameter("order", orderValue);
    }
  }

  /**
   * Adds paging related parameters to the given {@link URIBuilder} based on the given {@link
   * BaseQuery}.
   *
   * @param uriBuilder the {@link URIBuilder}.
   * @param query the {@link BaseQuery}.
   */
  protected void addPaging(URIBuilder uriBuilder, BaseQuery query) {
    Paging paging = query.getPaging();

    if (paging.hasPaging()) {
      if (paging.hasPage()) {
        uriBuilder.addParameter("page", String.valueOf(paging.getPage()));
      }

      if (paging.hasPageSize()) {
        uriBuilder.addParameter("pageSize", String.valueOf(paging.getPageSize()));
      }
    } else {
      uriBuilder.addParameter("paging", "false");
    }
  }

  /**
   * Retrieves a dataValueSet object using HTTP GET.
   *
   * @param uriBuilder the URI builder.
   * @param query the {@link DataValueSetQuery} filters to apply.
   * @return the object.
   */
  protected DataValueSet getDataValueSetResponse(URIBuilder uriBuilder, DataValueSetQuery query) {
    URI url = getDataValueSetQuery(uriBuilder, query);
    return getObjectFromUrl(url, DataValueSet.class);
  }

  /**
   * Retrieves a {@link Validation} object using HTTP GET.
   *
   * @param uriBuilder the URI builder.
   * @param query the {@link DataSetValidationQuery} filters to apply.
   * @return the object.
   */
  protected Validation getDataSetValidationResponse(
      URIBuilder uriBuilder, DataSetValidationQuery query) {
    URI url = getDataSetValidationQuery(uriBuilder, query);
    return getObjectFromUrl(url, Validation.class);
  }

  /**
   * Retrieves a dataValue object using HTTP GET.
   *
   * @param uriBuilder the URI builder.
   * @param query the {@link DataValueSetQuery} filters to apply.
   * @return the object.
   */
  protected String getDataValueFileResponse(URIBuilder uriBuilder, DataValueQuery query) {
    URI url = getDataValueQuery(uriBuilder, query);
    return getObjectFromUrl(url, String.class);
  }

  /**
   * Retrieves an analytics object using HTTP GET.
   *
   * @param <T> the type.
   * @param uriBuilder the URI builder.
   * @param query the {@link AnalyticsQuery} filters to apply.
   * @param type the class type of the object.
   * @return the object.
   */
  protected <T> T getAnalyticsResponse(URIBuilder uriBuilder, AnalyticsQuery query, Class<T> type) {
    URI url = getAnalyticsQuery(uriBuilder, query);

    return getObjectFromUrl(url, type);
  }

  /**
   * Returns a {@link URI} based on the given analytics query.
   *
   * @param uriBuilder the URI builder.
   * @param query the {@link AnalyticsQuery} filters to apply.
   * @return a {@link URI}.
   */
  protected URI getAnalyticsQuery(URIBuilder uriBuilder, AnalyticsQuery query) {
    for (Dimension dimension : query.getDimensions()) {
      addParameter(uriBuilder, "dimension", dimension.getDimensionValue());
    }

    for (Dimension filter : query.getFilters()) {
      addParameter(uriBuilder, "filter", filter.getDimensionValue());
    }

    addParameter(uriBuilder, "aggregationType", query.getAggregationType());
    addParameter(uriBuilder, "startDate", query.getStartDate());
    addParameter(uriBuilder, "endDate", query.getEndDate());
    addParameter(uriBuilder, "skipMeta", query.getSkipMeta());
    addParameter(uriBuilder, "skipData", query.getSkipData());
    addParameter(uriBuilder, "skipRounding", query.getSkipRounding());
    addParameter(uriBuilder, "ignoreLimit", query.getIgnoreLimit());
    addParameter(uriBuilder, "tableLayout", query.getTableLayout());
    addParameter(uriBuilder, "showHierarchy", query.getShowHierarchy());
    addParameter(uriBuilder, "includeNumDen", query.getIncludeNumDen());
    addParameter(uriBuilder, "includeMetadataDetails", query.getIncludeMetadataDetails());
    addParameter(uriBuilder, "outputIdScheme", query.getOutputIdScheme());
    addParameter(uriBuilder, "outputOrgUnitIdScheme", query.getOutputOrgUnitIdScheme());
    addParameter(uriBuilder, "outputDataElementIdScheme", query.getOutputDataElementIdScheme());
    addParameter(uriBuilder, "inputIdScheme", query.getInputIdScheme());

    if (query.hasColumns()) {
      addParameter(uriBuilder, "columns", String.join(SEP_DIM, query.getColumns()));
    }

    if (query.hasRows()) {
      addParameter(uriBuilder, "rows", String.join(SEP_DIM, query.getRows()));
    }

    return HttpUtils.build(uriBuilder);
  }

  /**
   * Returns a {@link URI} based on the given data value set import options.
   *
   * @param uriBuilder the URI builder.
   * @param options the {@link DataValueSetImportOptions} to apply.
   * @return a {@link URI}.
   */
  protected URI getDataValueSetImportQuery(
      URIBuilder uriBuilder, DataValueSetImportOptions options) {
    addParameter(uriBuilder, "async", "true"); // Always use async
    addParameter(uriBuilder, "dataElementIdScheme", options.getDataElementIdScheme());
    addParameter(uriBuilder, "orgUnitIdScheme", options.getOrgUnitIdScheme());
    addParameter(
        uriBuilder, "categoryOptionComboIdScheme", options.getCategoryOptionComboIdScheme());
    addParameter(uriBuilder, "idScheme", options.getIdScheme());
    addParameter(uriBuilder, "dryRun", options.getDryRun());
    addParameter(uriBuilder, "importStrategy", options.getImportStrategy());
    addParameter(uriBuilder, "preheatCache", options.getPreheatCache());
    addParameter(uriBuilder, "skipAudit", options.getSkipAudit());

    return HttpUtils.build(uriBuilder);
  }

  /**
   * Save complete data set registrations according to complete data set registration import
   * options.
   *
   * @param entity the POST body {@link HttpEntity}.
   * @param options the {@link CompleteDataSetRegistrationImportOptions} import options.
   * @return a {@link CompleteDataSetRegistrationResponse}.
   */
  protected CompleteDataSetRegistrationResponse saveCompleteDataSetRegistrations(
      HttpEntity entity, CompleteDataSetRegistrationImportOptions options) {
    URIBuilder builder = config.getResolvedUriBuilder().appendPath("completeDataSetRegistrations");

    URI url = getCompleteDataSetRegistrationsImportQuery(builder, options);

    HttpPost request = getPostRequest(url, entity);

    Dhis2AsyncRequest asyncRequest = new Dhis2AsyncRequest(config, httpClient, objectMapper);

    return asyncRequest.post(request, CompleteDataSetRegistrationResponse.class);
  }

  /**
   * Retrieves events using HTTP GET.
   *
   * @param uriBuilder the URI builder.
   * @param query the {@link EventQuery}.
   * @return an {@link EventsResult}.
   */
  protected EventsResult getEventsResult(URIBuilder uriBuilder, EventQuery query) {
    URI url = getEventsQuery(uriBuilder, query);
    return getObjectFromUrl(url, EventsResult.class);
  }

  /**
   * Returns a {@link URI} based on the given event query.
   *
   * @param uriBuilder the URI builder.
   * @param query the {@link EventQuery}.
   * @return an {@link URI}.
   */
  protected URI getEventsQuery(URIBuilder uriBuilder, EventQuery query) {
    addParameter(uriBuilder, "program", query.getProgram());
    addParameter(uriBuilder, "programStage", query.getProgramStage());
    addParameter(uriBuilder, "programStatus", query.getProgramStatus());
    addParameter(uriBuilder, "followUp", query.getFollowUp());
    addParameter(uriBuilder, "trackedEntity", query.getTrackedEntityInstance());
    addParameter(uriBuilder, "orgUnit", query.getOrgUnit());
    addParameter(uriBuilder, "orgUnitMode", query.getOuMode());
    addParameter(uriBuilder, "status", query.getStatus());
    addParameter(uriBuilder, "occuredAfter", query.getOccurredAfter());
    addParameter(uriBuilder, "occuredBefore", query.getOccurredBefore());
    addParameter(uriBuilder, "scheduledAfter", query.getScheduledAfter());
    addParameter(uriBuilder, "scheduledBefore", query.getScheduledBefore());
    addParameter(uriBuilder, "updatedAfter", query.getUpdatedAfter());
    addParameter(uriBuilder, "updatedBefore", query.getUpdatedBefore());
    addParameter(uriBuilder, "dataElementIdScheme", query.getDataElementIdScheme());
    addParameter(uriBuilder, "categoryOptionComboIdScheme", query.getCategoryOptionComboIdScheme());
    addParameter(uriBuilder, "orgUnitIdScheme", query.getOrgUnitIdScheme());
    addParameter(uriBuilder, "programIdScheme", query.getProgramIdScheme());
    addParameter(uriBuilder, "programStageIdScheme", query.getProgramStageIdScheme());
    addParameter(uriBuilder, "idScheme", query.getIdScheme());

    addTrackerFilters(uriBuilder, query);
    addPaging(uriBuilder, query);
    addOrder(uriBuilder, query);

    return HttpUtils.build(uriBuilder);
  }

  /**
   * Retrieves tracked entities using HTTP GET.
   *
   * @param uriBuilder the URI builder.
   * @param query the {@link TrackedEntityQuery}.
   * @return a {@link TrackedEntitiesResult}.
   */
  protected TrackedEntitiesResult getTrackedEntitiesResult(
      URIBuilder uriBuilder, TrackedEntityQuery query) {
    URI url = getTrackedEntityQuery(uriBuilder, query);
    return getObjectFromUrl(url, TrackedEntitiesResult.class);
  }

  /**
   * Returns a {@link URIBuilder} based on the given tracked entity query.
   *
   * @param uriBuilder the URI builder.
   * @param query the {@link TrackedEntityQuery}.
   * @return a {@link URIBuilder}.
   */
  protected URI getTrackedEntityQuery(URIBuilder uriBuilder, TrackedEntityQuery query) {
    addParameterList(uriBuilder, "orgUnits", query.getOrgUnits());
    addParameter(uriBuilder, "orgUnitMode", query.getOrgUnitMode());
    addParameter(uriBuilder, "program", query.getProgram());
    addParameter(uriBuilder, "programStage", query.getProgramStage());
    addParameter(uriBuilder, "followUp", query.getFollowUp());
    addParameter(uriBuilder, "updatedAfter", query.getUpdatedAfter());
    addParameter(uriBuilder, "updatedBefore", query.getUpdatedBefore());
    addParameter(uriBuilder, "enrollmentStatus", query.getEnrollmentStatus());
    addParameter(uriBuilder, "enrollmentEnrolledAfter", query.getEnrollmentEnrolledAfter());
    addParameter(uriBuilder, "enrollmentEnrolledBefore", query.getEnrollmentEnrolledBefore());
    addParameter(uriBuilder, "enrollmentOccurredAfter", query.getEnrollmentOccurredBefore());
    addParameter(uriBuilder, "enrollmentOccurredBefore", query.getEnrollmentOccurredBefore());
    addParameter(uriBuilder, "trackedEntityType", query.getTrackedEntityType());
    addParameterList(uriBuilder, "trackedEntities", query.getTrackedEntities());
    addParameter(uriBuilder, "eventStatus", query.getEventStatus());
    addParameter(uriBuilder, "eventOccurredAfter", query.getEventOccurredAfter());
    addParameter(uriBuilder, "eventOccurredBefore", query.getEventOccurredBefore());
    addParameter(uriBuilder, "includeDeleted", query.getIncludeDeleted());
    addParameter(uriBuilder, "potentialDuplicate", query.getPotentialDuplicate());
    addParameter(uriBuilder, "idScheme", query.getIdScheme());
    addParameter(uriBuilder, "orgUnitIdScheme", query.getOrgUnitIdScheme());

    addTrackerFilters(uriBuilder, query);
    addPaging(uriBuilder, query);
    addOrder(uriBuilder, query);

    return HttpUtils.build(uriBuilder);
  }

  /**
   * Adds a filter property to the given {@link URIBuilder} based on the given {@link BaseQuery}.
   *
   * @param uriBuilder the {@link URIBuilder}.
   * @param query the {@link BaseQuery}.
   */
  protected void addTrackerFilters(URIBuilder uriBuilder, BaseQuery query) {
    for (Filter filter : query.getFilters()) {
      Object value = getTrackerApiQueryValue(filter);
      String filterValue =
          String.format("%s:%s:%s", filter.getProperty(), filter.getOperator().value(), value);
      uriBuilder.addParameter("filter", filterValue);
    }
  }

  /**
   * Retrieves enrollments using HTTP GET.
   *
   * @param uriBuilder the URI builder.
   * @param query the {@link EnrollmentQuery}.
   * @return a {@link EnrollmentsResult}.
   */
  protected EnrollmentsResult getEnrollmentResult(URIBuilder uriBuilder, EnrollmentQuery query) {
    URI url = getEnrollmentQuery(uriBuilder, query);
    return getObjectFromUrl(url, EnrollmentsResult.class);
  }

  /**
   * Returns a {@link URI} based on the given enrollment query.
   *
   * @param uriBuilder the URI builder.
   * @param query the {@link EnrollmentQuery}.
   * @return a {@link URI}.
   */
  protected URI getEnrollmentQuery(URIBuilder uriBuilder, EnrollmentQuery query) {
    addParameterList(uriBuilder, "orgUnits", query.getOrgUnits());
    addParameter(uriBuilder, "orgUnitMode", query.getOrgUnitMode());
    addParameter(uriBuilder, "program", query.getProgram());
    addParameter(uriBuilder, "status", query.getStatus());
    addParameter(uriBuilder, "followUp", query.getFollowUp());
    addParameter(uriBuilder, "updatedAfter", query.getUpdatedAfter());
    addParameter(uriBuilder, "updatedWithin", query.getUpdatedWithin());
    addParameter(uriBuilder, "enrolledAfter", query.getEnrolledAfter());
    addParameter(uriBuilder, "enrolledBefore", query.getEnrolledBefore());
    addParameter(uriBuilder, "trackedEntityType", query.getTrackedEntityType());
    addParameter(uriBuilder, "trackedEntity", query.getTrackedEntity());
    addParameterList(uriBuilder, "order", query.getOrder());
    addParameterList(uriBuilder, "enrollments", query.getEnrollments());
    addParameter(uriBuilder, "includeDeleted", query.getIncludeDeleted());

    return HttpUtils.build(uriBuilder);
  }

  /**
   * Returns a {@link URI} based on the given relationship query.
   *
   * @param uriBuilder the URI builder.
   * @param query the {@link RelationshipQuery}.
   * @return a {@link URI}.
   */
  protected URI getRelationshipQuery(URIBuilder uriBuilder, RelationshipQuery query) {
    addParameter(uriBuilder, "trackedEntity", query.getTrackedEntity());
    addParameter(uriBuilder, "enrollment", query.getEnrollment());
    addParameter(uriBuilder, "event", query.getEvent());
    addParameter(uriBuilder, "fields", query.getFields());
    addParameterList(uriBuilder, "order", query.getOrder());
    addParameter(uriBuilder, "includeDeleted", query.getIncludeDeleted());

    return HttpUtils.build(uriBuilder);
  }

  /**
   * Returns a {@link URIBuilder} based on the given tracked entity import parameters.
   *
   * @param uriBuilder the URI builder.
   * @param query the {@link TrackedEntityImportParams}.
   * @return a {@link URI}.
   */
  protected URIBuilder getTrackedEntityImportParams(
      URIBuilder uriBuilder, TrackedEntityImportParams query) {
    addParameter(uriBuilder, "reportMode", query.getReportMode());
    addParameter(uriBuilder, "importMode", query.getImportMode());
    addParameter(uriBuilder, "idScheme", query.getIdScheme());
    addParameter(uriBuilder, "dataElementIdScheme", query.getDataElementIdScheme());
    addParameter(uriBuilder, "orgUnitIdScheme", query.getOrgUnitIdScheme());
    addParameter(uriBuilder, "programIdScheme", query.getProgramIdScheme());
    addParameter(uriBuilder, "programStageIdScheme", query.getProgramStageIdScheme());
    addParameter(uriBuilder, "categoryOptionComboIdScheme", query.getCategoryOptionComboIdScheme());
    addParameter(uriBuilder, "categoryOptionIdScheme", query.getCategoryOptionIdScheme());
    addParameter(uriBuilder, "importStrategy", query.getImportStrategy());
    addParameter(uriBuilder, "atomicMode", query.getAtomicMode());
    addParameter(uriBuilder, "flushMode", query.getFlushMode());
    addParameter(uriBuilder, "validationMode", query.getValidationMode());
    addParameter(uriBuilder, "skipPatternValidation", query.getSkipPatternValidation());
    addParameter(uriBuilder, "skipSideEffects", query.getSkipSideEffects());
    addParameter(uriBuilder, "skipRuleEngine", query.getSkipRuleEngine());

    return uriBuilder;
  }

  /**
   * Returns a {@link URI} based on the given dataValueSet query.
   *
   * @param uriBuilder the URI builder.
   * @param query the {@link DataValueSetQuery}.
   * @return a URI.
   */
  protected URI getDataValueSetQuery(URIBuilder uriBuilder, DataValueSetQuery query) {
    for (String dataElement : query.getDataElements()) {
      addParameter(uriBuilder, "dataElement", dataElement);
    }

    for (String orgUnit : query.getOrgUnits()) {
      addParameter(uriBuilder, "orgUnit", orgUnit);
    }

    for (String period : query.getPeriods()) {
      addParameter(uriBuilder, "period", period);
    }

    for (String dataSet : query.getDataSets()) {
      addParameter(uriBuilder, "dataSet", dataSet);
    }

    for (String dataElementGroup : query.getDataElementGroups()) {
      addParameter(uriBuilder, "dataElementGroup", dataElementGroup);
    }

    for (String orgUnitGroup : query.getOrgUnitGroups()) {
      addParameter(uriBuilder, "orgUnitGroup", orgUnitGroup);
    }

    for (String attributeOptionCombo : query.getAttributeOptionCombos()) {
      addParameter(uriBuilder, "attributeOptionCombo", attributeOptionCombo);
    }

    addParameter(uriBuilder, "startDate", query.getStartDate());
    addParameter(uriBuilder, "endDate", query.getEndDate());
    addParameter(uriBuilder, "children", query.getChildren());
    addParameter(uriBuilder, "includeDeleted", query.getIncludeDeleted());
    addParameter(uriBuilder, "lastUpdated", query.getLastUpdated());
    addParameter(uriBuilder, "lastUpdatedDuration", query.getLastUpdatedDuration());
    addParameter(uriBuilder, "limit", query.getLimit());
    addParameter(uriBuilder, "dataElementIdScheme", query.getDataElementIdScheme());
    addParameter(uriBuilder, "orgUnitIdScheme", query.getOrgUnitIdScheme());
    addParameter(uriBuilder, "categoryOptionComboIdScheme", query.getCategoryOptionComboIdScheme());
    addParameter(
        uriBuilder, "attributeOptionComboIdScheme", query.getAttributeOptionComboIdScheme());
    addParameter(uriBuilder, "dataSetIdScheme", query.getDataSetIdScheme());
    addParameter(uriBuilder, "categoryIdScheme", query.getCategoryIdScheme());
    addParameter(uriBuilder, "categoryOptionIdScheme", query.getCategoryOptionIdScheme());
    addParameter(uriBuilder, "idScheme", query.getIdScheme());
    addParameter(uriBuilder, "inputOrgUnitIdScheme", query.getInputOrgUnitIdScheme());
    addParameter(uriBuilder, "inputDataSetIdScheme", query.getInputDataSetIdScheme());
    addParameter(
        uriBuilder, "inputDataElementGroupIdScheme", query.getInputDataElementGroupIdScheme());
    addParameter(uriBuilder, "inputDataElementIdScheme", query.getInputDataElementIdScheme());
    addParameter(uriBuilder, "inputIdScheme", query.getInputIdScheme());

    return HttpUtils.build(uriBuilder);
  }

  /**
   * Returns a {@link URI} based on the given dataValue query.
   *
   * @param uriBuilder the URI builder.
   * @param query the {@link DataValueQuery}.
   * @return a URI.
   */
  protected URI getDataValueQuery(URIBuilder uriBuilder, DataValueQuery query) {

    addParameter(uriBuilder, "de", query.getDe());
    addParameter(uriBuilder, "pe", query.getPe());
    addParameter(uriBuilder, "ou", query.getOu());
    addParameter(uriBuilder, "co", query.getCo());
    addParameter(uriBuilder, "cc", query.getCc());

    if (!query.getCp().isEmpty()) {
      addParameter(uriBuilder, "cp", String.join(";", query.getCp()));
    }

    addParameter(uriBuilder, "ds", query.getDs());

    return HttpUtils.build(uriBuilder);
  }

  /**
   * Returns a {@link URI} based on the given dataSetValidation query.
   *
   * @param uriBuilder the URI builder.
   * @param query the {@link DataSetValidationQuery}.
   * @return a URI.
   */
  protected URI getDataSetValidationQuery(URIBuilder uriBuilder, DataSetValidationQuery query) {
    addParameter(uriBuilder, "pe", query.getPe());
    addParameter(uriBuilder, "ou", query.getOu());

    return HttpUtils.build(uriBuilder);
  }

  /**
   * Returns a {@link URI} based on the given complete dataset registration query.
   *
   * @param uriBuilder the URI builder.
   * @param query the {@link CompleteDataSetRegistrationQuery}.
   * @return a URI.
   */
  protected URI getCompleteDataSetRegistrationQuery(
      URIBuilder uriBuilder, CompleteDataSetRegistrationQuery query) {
    for (String dataSet : query.getDataSets()) {
      addParameter(uriBuilder, "dataSet", dataSet);
    }

    for (String period : query.getPeriods()) {
      addParameter(uriBuilder, "period", period);
    }

    addParameter(uriBuilder, "startDate", query.getStartDate());
    addParameter(uriBuilder, "endDate", query.getEndDate());
    addParameter(uriBuilder, "created", query.getCreated());
    addParameter(uriBuilder, "createdDuration", query.getCreatedDuration());

    for (String orgUnit : query.getOrgUnits()) {
      addParameter(uriBuilder, "orgUnit", orgUnit);
    }

    addParameter(uriBuilder, "orgUnitGroup", query.getOrgUnitGroup());
    addParameter(uriBuilder, "children", query.getChildren());
    addParameter(uriBuilder, "limit", query.getLimit());
    addParameter(uriBuilder, "idScheme", query.getIdScheme());
    addParameter(uriBuilder, "orgUnitIdScheme", query.getOrgUnitIdScheme());
    addParameter(uriBuilder, "dataSetIdScheme", query.getDataSetIdScheme());
    addParameter(
        uriBuilder, "attributeOptionComboIdScheme", query.getAttributeOptionComboIdScheme());

    return HttpUtils.build(uriBuilder);
  }

  /**
   * Returns a {@link URI} based on the complete data set registration import options.
   *
   * @param uriBuilder the URI builder.
   * @param options the {@link CompleteDataSetRegistrationImportOptions} to apply.
   * @return a URI.
   */
  private URI getCompleteDataSetRegistrationsImportQuery(
      URIBuilder uriBuilder, CompleteDataSetRegistrationImportOptions options) {
    addParameter(uriBuilder, "async", "true"); // Always use async
    addParameter(uriBuilder, "dataSetIdScheme", options.getDataSetIdScheme());
    addParameter(uriBuilder, "orgUnitIdScheme", options.getOrgUnitIdScheme());
    addParameter(
        uriBuilder, "attributeOptionComboIdScheme", options.getAttributeOptionComboIdScheme());
    addParameter(uriBuilder, "idScheme", options.getIdScheme());
    addParameter(uriBuilder, "preheatCache", options.getPreheatCache());
    addParameter(uriBuilder, "dryRun", options.getDryRun());
    addParameter(uriBuilder, "importStrategy", options.getImportStrategy());
    addParameter(uriBuilder, "skipExistingCheck", options.getSkipExistingCheck());

    return HttpUtils.build(uriBuilder);
  }

  /**
   * Adds a query parameter to the given {@link URIBuilder} if the given parameter value is not
   * null.
   *
   * @param uriBuilder the {@link URIBuilder}.
   * @param parameter the query parameter.
   * @param value the query parameter value.
   */
  private void addParameter(URIBuilder uriBuilder, String parameter, Object value) {
    if (value != null) {
      uriBuilder.addParameter(parameter, value.toString());
    }
  }

  /**
   * Adds a Date query parameter to the given {@link URIBuilder} if the given parameter value is not
   * null.
   *
   * @param uriBuilder the {@link URIBuilder}.
   * @param parameter the query parameter.
   * @param value the query parameter Date value.
   */
  private void addParameter(URIBuilder uriBuilder, String parameter, Date value) {
    if (value != null) {
      uriBuilder.addParameter(parameter, DateTimeUtils.getDateTimeString(value));
    }
  }

  /**
   * Adds a query parameter to the given {@link URIBuilder} if the given parameter list is not null
   * and not empty. The parameter value is the comma separated values of the string representation
   * of the items in the list.
   *
   * @param uriBuilder the {@link URIBuilder}.
   * @param parameter the query parameter.
   * @param values the collection of query parameter values.
   */
  private void addParameterList(URIBuilder uriBuilder, String parameter, List<String> values) {
    if (isNotEmpty(values)) {
      uriBuilder.addParameter(parameter, toCommaSeparated(values));
    }
  }

  /**
   * Converts the given filter to a query value.
   *
   * @param filter the {@link Filter}.
   * @return a query value.
   */
  @SuppressWarnings("unchecked")
  protected Object getQueryValue(Filter filter) {
    if (Operator.IN == filter.getOperator()) {
      List<String> values = (List<String>) filter.getValue();
      String value = StringUtils.join(values, ',');
      return String.format("[%s]", value);
    } else {
      return filter.getValue();
    }
  }

  /**
   * Converts the given tracked entity filter to a query value. Note that the tracker API uses
   * <code>;</code> separated values for <code>IN</code> filters.
   *
   * @param filter the {@link Filter}.
   * @return a query value.
   */
  @SuppressWarnings("unchecked")
  protected Object getTrackerApiQueryValue(Filter filter) {
    if (Operator.IN == filter.getOperator()) {
      List<String> values = (List<String>) filter.getValue();
      return StringUtils.join(values, ';');
    } else {
      return filter.getValue();
    }
  }

  /**
   * Executes the given {@link HttpUriRequestBase} request, which may be a POST or PUT request.
   *
   * @param <T> the type.
   * @param request the request.
   * @param object the object to pass as JSON in the request body.
   * @param type the class type for the response entity.
   * @return a {@link Response}.
   * @throws Dhis2ClientException if unauthorized, access denied or resource not found.
   */
  protected <T extends BaseHttpResponse> T executeJsonPostPutRequest(
      HttpUriRequestBase request, Object object, Class<T> type) {
    validateRequestObject(object);

    String requestBody = toJsonString(object);

    log("Request URI: '{}', body: '{}'", getUriAsString(request), requestBody);

    HttpEntity entity = new StringEntity(requestBody, StandardCharsets.UTF_8);

    request.setHeader(HEADER_CONTENT_TYPE_JSON);
    request.setEntity(entity);

    return executeRequest(request, type);
  }

  /**
   * Executes the given request, parses the response body and returns a response message.
   *
   * @param <T> the type.
   * @param request the request.
   * @param type the class type for the response entity.
   * @return a response message.
   * @throws Dhis2ClientException if unauthorized, access denied or resource not found.
   */
  private <T extends BaseHttpResponse> T executeRequest(HttpUriRequestBase request, Class<T> type) {
    withAuth(request);

    try {
      return httpClient.execute(
          request,
          response -> {
            handleErrors(response, request.getRequestUri());

            int code = response.getCode();
            String responseBody = EntityUtils.toString(response.getEntity());

            log(code, "Response body: '{}'", responseBody);

            T responseMessage = objectMapper.readValue(responseBody, type);

            responseMessage.setHeaders(asList(response.getHeaders()));
            responseMessage.setHttpStatusCode(response.getCode());

            return responseMessage;
          });
    } catch (IOException ex) {
      throw newDhis2ClientException(ex);
    }
  }

  /**
   * Executes the given request without attempting to parse a response body and returns a response
   * message.
   *
   * @param request the request.
   * @return a response message.
   * @throws Dhis2ClientException if unauthorized, access denied or resource not found.
   */
  protected Response executeRequest(HttpUriRequestBase request) {
    withAuth(request);

    try {
      return httpClient.execute(
          request,
          response -> {
            handleErrors(response, request.getRequestUri());

            HttpStatus httpStatus = HttpStatus.valueOf(response.getCode());
            Status status =
                httpStatus != null && httpStatus.is2xxSuccessful() ? Status.OK : Status.ERROR;

            Response resp = new Response();
            resp.setHeaders(asList(response.getHeaders()));
            resp.setStatus(status);
            resp.setHttpStatusCode(response.getCode());
            return resp;
          });
    } catch (IOException ex) {
      throw newDhis2ClientException(ex);
    }
  }

  /**
   * Returns a HTTP post request with JSON content type for the given URL and entity.
   *
   * @param url the {@link URI}.
   * @param entity the {@link HttpEntity}.
   * @return a {@link HttpPost} request.
   */
  protected HttpPost getPostRequest(URI url, HttpEntity entity) {
    HttpPost request = withAuth(new HttpPost(url));
    request.setHeader(HEADER_CONTENT_TYPE_JSON);
    request.setEntity(entity);
    return request;
  }

  /**
   * Retrieves an object using HTTP GET.
   *
   * @param <T> the type.
   * @param url the fully qualified URL.
   * @param type the class type of the object.
   * @return the object.
   * @throws Dhis2ClientException if unauthorized, access denied or resource not found.
   */
  @SuppressWarnings("unchecked")
  protected <T> T getObjectFromUrl(URI url, Class<T> type) {
    log("Get URL: '{}'", url.toString());

    HttpGet request = getJsonHttpGetRequest(url);

    try {
      return httpClient.execute(
          request,
          response -> {
            handleErrors(response, url.toString());
            handleErrorsForGet(response, url.toString());

            String responseBody = EntityUtils.toString(response.getEntity());

            log("Response body: '{}'", responseBody);

            if (type == String.class) {
              return (T) responseBody;
            }

            return readValue(responseBody, type);
          });
    } catch (IOException ex) {
      throw new Dhis2ClientException("Failed to fetch object", ex);
    }
  }

  /**
   * Creates a HTTP GET request with the given URL and the <code>Accept</code> HTTP header set to
   * <code>application/json</code>.
   *
   * @param url the {@link URI}.
   * @param headers the list of {@link Header}.
   * @return an {@link HttpGet} request.
   */
  protected HttpGet getJsonHttpGetRequest(URI url) {
    return getHttpGetRequest(url, List.of(HEADER_ACCEPT_JSON));
  }

  /**
   * Creates a HTTP GET request with the given URL and headers.
   *
   * @param url the {@link URI}.
   * @param headers the list of {@link Header}.
   * @return an {@link HttpGet} request.
   */
  protected HttpGet getHttpGetRequest(URI url, List<Header> headers) {
    HttpGet request = withAuth(new HttpGet(url));
    headers.forEach(header -> request.setHeader(header));
    return request;
  }

  /**
   * Handles error status codes.
   *
   * <ul>
   *   <li>401 Unauthorized
   *   <li>403 Forbidden
   *   <li>404 Not found
   * </ul>
   *
   * @param response {@link HttpResponse}.
   * @param url the request URL.
   * @throws Dhis2ClientException in the case of error status codes.
   */
  private void handleErrors(HttpResponse response, String url) {
    final int code = response.getCode();

    if (ERROR_STATUS_CODES.contains(code)) {
      String message = String.format("%s (%d)", getErrorMessage(code), code);

      log(code, "Error URL: '{}'", url);

      throw new Dhis2ClientException(message, code);
    }
  }

  /**
   * Handles errors status code for HTTP GET requests.
   *
   * <ul>
   *   <li>400 Bad request
   *   <li>409 Conflict
   * </ul>
   *
   * @param response the {@link CloseableHttpResponse}.
   * @param url the request URL.
   * @throws Dhis2ClientException in the case of error status codes.
   */
  private void handleErrorsForGet(ClassicHttpResponse response, String url)
      throws IOException, ParseException {
    final int code = response.getCode();
    if (GET_ERROR_STATUS_CODES.contains(code)) {
      String responseBody = EntityUtils.toString(response.getEntity());

      log(code, "Response body: '{}'", responseBody);

      Response objResp = readValue(responseBody, Response.class);

      throw new Dhis2ClientException(objResp.getMessage(), code, objResp.getErrorCode());
    }
  }

  /**
   * Deserializes the given JSON content to an object of the given type.
   *
   * @param <T> type.
   * @param content the JSON content.
   * @param type the object type.
   * @return an object.
   * @throws IOException if reading failed.
   */
  protected <T> T readValue(String content, Class<T> type) throws IOException {
    return objectMapper.readValue(content, type);
  }

  /**
   * Return an error message for the given status code.
   *
   * @param code the status code.
   * @return an error message.
   */
  private String getErrorMessage(int code) {
    if (401 == code) {
      return "Authentication failed";
    }
    if (403 == code) {
      return "Access was denied";
    }
    if (404 == code) {
      return "Object not found";
    }
    if (409 == code) {
      return "Conflict";
    }
    if (code >= 400 && code < 500) {
      return "Client error";
    }
    if (code >= 500 && code < 600) {
      return "Server error";
    } else {
      return "Error";
    }
  }

  /**
   * Validates a request object.
   *
   * @param object the request object to validate.
   * @throws Dhis2ClientException if object is invalid.
   */
  private void validateRequestObject(Object object) {
    if (object == null) {
      throw new Dhis2ClientException("Request object is null", 400);
    }
  }

  /**
   * Write the given {@link CloseableHttpResponse} to the given {@link File}.
   *
   * @param response the {@link ClassicHttpResponse}.
   * @param file the file to write the response to.
   * @return the number of bytes copied.
   * @throws Dhis2ClientException if the write operation failed.
   */
  protected int writeToFile(ClassicHttpResponse response, File file) {
    try (FileOutputStream fileOut = FileUtils.openOutputStream(file);
        InputStream in = response.getEntity().getContent()) {
      return IOUtils.copy(in, fileOut);
    } catch (IOException ex) {
      throw new Dhis2ClientException("Failed to write to file", ex);
    }
  }

  /**
   * Write the given {@link ClassicHttpResponse} to the given {@link OutputStream}.
   *
   * @param response the {@link ClassicHttpResponse}.
   * @param out the output stream to write the response to.
   * @return the number of bytes copied.
   * @throws Dhis2ClientException if the write operation failed.
   */
  protected int writeToStream(ClassicHttpResponse response, OutputStream out) {
    try (InputStream in = response.getEntity().getContent()) {
      return IOUtils.copy(in, out);
    } catch (IOException ex) {
      throw new Dhis2ClientException("Failed to write to output stream", ex);
    }
  }

  /**
   * Adds authentication to the given request.
   *
   * @param request the {@link HttpUriRequestBase}.
   * @param <T> class.
   * @return the request.
   */
  protected <T extends HttpUriRequestBase> T withAuth(T request) {
    return HttpUtils.withAuth(request, config);
  }

  /**
   * Serializes the given object to a JSON string.
   *
   * @param object the object to serialize.
   * @return a JSON string representation of the object.
   * @throws Dhis2ClientException if the serialization failed.
   */
  protected String toJsonString(Object object) {
    try {
      String json = objectMapper.writeValueAsString(object);

      log("Object JSON: '{}'", json);

      return json;
    } catch (IOException ex) {
      throw new Dhis2ClientException("Failed to deserialize JSON", ex);
    }
  }

  /**
   * Returns a {@link Dhis2ClientException} based on the given exception.
   *
   * @param ex the exception.
   * @return a {@link Dhis2ClientException}.
   */
  protected Dhis2ClientException newDhis2ClientException(IOException ex) {
    int statusCode = -1;

    if (ex instanceof HttpResponseException) {
      statusCode = ((HttpResponseException) ex).getStatusCode();
    }

    return new Dhis2ClientException(ex.getMessage(), ex.getCause(), statusCode);
  }

  // -------------------------------------------------------------------------
  // Protected generic object methods
  // -------------------------------------------------------------------------

  /**
   * Saves an object using HTTP POST.
   *
   * @param path the URL path relative to the API end point.
   * @param object the object to save.
   * @param type the class type for the response entity.
   * @param <T> the type.
   * @return object holding information about the operation.
   * @throws Dhis2ClientException if unauthorized, access denied or resource not found.
   */
  protected <T extends BaseHttpResponse> T saveObject(String path, Object object, Class<T> type) {
    URI url = config.getResolvedUrl(path);

    return executeJsonPostPutRequest(new HttpPost(url), object, type);
  }

  /**
   * Saves an object using HTTP POST.
   *
   * @param uriBuilder the URI builder.
   * @param object the object to save.
   * @param type the class type for the response entity.
   * @param <T> the type.
   * @return object holding information about the operation.
   * @throws Dhis2ClientException if unauthorized, access denied or resource not found.
   */
  protected <T extends BaseHttpResponse> T saveObject(
      URIBuilder uriBuilder, Object object, Class<T> type) {
    URI url = HttpUtils.build(uriBuilder);

    return executeJsonPostPutRequest(new HttpPost(url), object, type);
  }

  /**
   * Updates an object using HTTP PUT.
   *
   * @param path the URL path relative to the API end point.
   * @param params the map of query parameter names and values to include in the URL.
   * @param object the object to save.
   * @param type the class type for the response entity.
   * @param <T> the type.
   * @return response object holding information about the operation.
   */
  protected <T extends BaseHttpResponse> T updateObject(
      String path, Map<String, String> params, Object object, Class<T> type) {
    try {
      URI url =
          config
              .getResolvedUriBuilder()
              .appendPath(path)
              .addParameters(toNameValuePairs(params))
              .build();

      return executeJsonPostPutRequest(new HttpPut(url), object, type);
    } catch (URISyntaxException ex) {
      throw new Dhis2ClientException("Invalid URI syntax", ex);
    }
  }

  /**
   * Removes an object using HTTP DELETE.
   *
   * @param path the URL path relative to the API end point.
   * @param type the class type for the response entity.
   * @param <T> type.
   * @return object holding information about the operation.
   */
  protected <T extends BaseHttpResponse> T removeObject(String path, Class<T> type) {
    URI url = config.getResolvedUrl(path);

    return executeRequest(new HttpDelete(url), type);
  }

  /**
   * Retrieves an object using HTTP GET.
   *
   * @param <T> the type.
   * @param path the URL path relative to the API end point.
   * @param type the class type of the object.
   * @return the object.
   */
  protected <T> T getObject(String path, Class<T> type) {
    return getObjectFromUrl(config.getResolvedUrl(path), type);
  }

  /**
   * Converts the given map of query parameters to a list of name value pairs.
   *
   * @param params the {@link Map} with query parameter names and values.
   * @return a list of {@link NameValuePair}.
   */
  protected List<NameValuePair> toNameValuePairs(Map<String, String> params) {
    return params.entrySet().stream()
        .map(p -> new BasicNameValuePair(p.getKey(), p.getValue()))
        .collect(Collectors.toList());
  }

  /**
   * Adds the item to the collection of the entity with the given identifier.
   *
   * @param path the object path in plural.
   * @param id the object identifier.
   * @param collection the collection path.
   * @param item the item identifier.
   * @return a {@link Response} holding information about the operation.
   */
  protected Response addToCollection(String path, String id, String collection, String item) {
    URI url =
        HttpUtils.build(
            config
                .getResolvedUriBuilder()
                .appendPath(path)
                .appendPath(id)
                .appendPath(collection)
                .appendPath(item));

    Response response = executeRequest(new HttpPost(url));

    Status status =
        response != null
                && response.getHttpStatus() != null
                && response.getHttpStatus().is2xxSuccessful()
            ? Status.OK
            : Status.ERROR;

    return new Response(status, response.getHttpStatusCode(), response.getMessage());
  }

  /**
   * Logs the message. If the given status code indicates an error, the message is logged at <code>
   * WARN</code> level. Otherwise logging is delegated to <code>log(String, Object...)</code>.
   *
   * @param statusCode the HTTP status code.
   * @param format the message format.
   * @param arguments the message arguments.
   */
  private void log(int statusCode, String format, Object arguments) {
    if (statusCode >= 400 && statusCode < 600) {
      log.warn(format, arguments);
    } else {
      log(format, arguments);
    }
  }

  /**
   * Logs the message.
   *
   * <p>To log messages at info level during tests:
   *
   * <pre>
   * mvn test -Dlog.level.dhis2=info
   * </pre>
   *
   * @param format the message format.
   * @param arguments the message arguments.
   */
  private void log(String format, Object... arguments) {
    String logLevel = getLogLevel();

    if (LOG_LEVEL_INFO.equalsIgnoreCase(logLevel)) {
      log.info(format, arguments);
    } else if (LOG_LEVEL_WARN.equalsIgnoreCase(logLevel)) {
      log.warn(format, arguments);
    } else {
      log.debug(format, arguments);
    }
  }

  /**
   * Returns the log level based on the system property {@link BaseDhis2#LOG_LEVEL_SYSTEM_PROPERTY},
   * for example {@link BaseDhis2#LOG_LEVEL_INFO}.
   *
   * @return the log level.
   */
  private String getLogLevel() {
    if (LOG_LEVEL.isPresent()) {
      return LOG_LEVEL.get();
    }

    return System.getProperty(LOG_LEVEL_SYSTEM_PROPERTY);
  }
}
