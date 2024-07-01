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

import static org.apache.hc.core5.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.hc.core5.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.hc.core5.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hisp.dhis.util.CollectionUtils.asList;
import static org.hisp.dhis.util.CollectionUtils.set;
import static org.hisp.dhis.util.HttpUtils.getUriAsString;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.apache.hc.client5.http.HttpResponseException;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpHead;
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
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.hisp.dhis.model.IdentifiableObject;
import org.hisp.dhis.model.Objects;
import org.hisp.dhis.model.datavalueset.DataValueSetImportOptions;
import org.hisp.dhis.model.event.Events;
import org.hisp.dhis.model.event.EventsResult;
import org.hisp.dhis.query.Filter;
import org.hisp.dhis.query.Operator;
import org.hisp.dhis.query.Order;
import org.hisp.dhis.query.Paging;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.query.RootJunction;
import org.hisp.dhis.query.analytics.AnalyticsQuery;
import org.hisp.dhis.query.analytics.Dimension;
import org.hisp.dhis.query.datavalue.DataValueSetQuery;
import org.hisp.dhis.query.event.EventsQuery;
import org.hisp.dhis.response.BaseHttpResponse;
import org.hisp.dhis.response.Dhis2ClientException;
import org.hisp.dhis.response.HttpStatus;
import org.hisp.dhis.response.Response;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.response.objects.ObjectsResponse;
import org.hisp.dhis.util.HttpUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Lars Helge Overland
 */
@Slf4j
public class BaseDhis2 {
  protected static final String FIELDS_PARAM = "fields";

  protected static final String SKIP_SHARING_PARAM = "skipSharing";

  protected static final String ID_FIELDS = "id,code,name,created,lastUpdated,attributeValues";

  protected static final String NAME_FIELDS = String.format("%s,shortName,description", ID_FIELDS);

  protected static final String OPTION_SET_FIELDS =
      String.format("%s,valueType,version", ID_FIELDS);

  protected static final String DATA_ELEMENT_FIELDS =
      String.format(
          "%1$s,aggregationType,valueType,domainType,url,legendSets[%1$s],optionSet[%2$s]",
          NAME_FIELDS, OPTION_SET_FIELDS);

  protected static final String DATA_ELEMENT_GROUP_SET_FIELDS =
      String.format(
          "%1$s,compulsory,dataDimension,dimensionType,dataElementGroups[%1$s]", NAME_FIELDS);

  protected static final String CATEGORY_OPTION_FIELDS =
      String.format(
          "%1$s,shortName,startDate,endDate,formName,categories[%1$s],organisationUnits[%1$s]",
          ID_FIELDS);

  protected static final String CATEGORY_OPTION_COMBO_FIELDS =
      String.format("%1$s,ignoreApproval,dimensionItem,categoryOptions[%1$s]", ID_FIELDS);

  protected static final String CATEGORY_OPTION_GROUP_FIELDS =
      String.format(
          "%1$s,dataDimensionType,dimensionItemType,categoryOptions[%2$s],groupSets[%2$s]",
          NAME_FIELDS, ID_FIELDS);

  protected static final String CATEGORY_OPTION_GROUP_SET_FIELDS =
      String.format(
          "%1$s,dataDimension,dataDimensionType,categoryOptionGroups[%2$s]",
          NAME_FIELDS, ID_FIELDS);

  protected static final String CATEGORY_COMBO_FIELDS =
      String.format(
          "%1$s,dataDimensionType,skipTotal,categories[%1$s],categoryOptionCombos[%2$s]",
          ID_FIELDS, CATEGORY_OPTION_COMBO_FIELDS);

  protected static final String CATEGORY_FIELDS =
      String.format(
          "%1$s,dataDimensionType,dataDimension,categoryOptions[%2$s]", NAME_FIELDS, ID_FIELDS);

  protected static final String INDICATOR_TYPE_FIELDS =
      String.format("%s,factor,number", NAME_FIELDS);

  protected static final String INDICATOR_FIELDS =
      String.format(
          "%1$s,annualized,numerator,numeratorDescription,denominator,denominatorDescription,url,"
              + "indicatorType[%2$s]",
          NAME_FIELDS, INDICATOR_TYPE_FIELDS);

  protected static final String INDICATOR_GROUP_SET_FIELDS =
      String.format("%1$s,compulsory,indicatorGroups[%1$s]", NAME_FIELDS);

  protected static final String DATA_SET_FIELDS =
      String.format(
          "%1$s,formName,displayFormName,categoryCombo[%1$s],"
              + "dataSetElements[dataSet[%1$s],dataElement[%1$s],categoryCombo[%1$s]],dimensionItem,openFuturePeriods,"
              + "expiryDays,timelyDays,url,formType,periodType,version,dimensionItemType,aggregationType,favorite,"
              + "compulsoryFieldsCompleteOnly,skipOffline,validCompleteOnly,dataElementDecoration,"
              + "openPeriodsAfterCoEndDate,notifyCompletingUser,noValueRequiresComment,fieldCombinationRequired,mobile,"
              + "dataEntryForm[%2$s]",
          NAME_FIELDS, ID_FIELDS);

  protected static final String ORG_UNIT_FIELDS =
      String.format(
          "%s,path,level,parent[%s],openingDate,closedDate,comment,"
              + "url,contactPerson,address,email,phoneNumber",
          NAME_FIELDS, NAME_FIELDS);

  protected static final String ORG_UNIT_GROUP_SET_FIELDS =
      String.format(
          "%1$s,dataDimension,compulsory,organisationUnitGroups[%2$s]", NAME_FIELDS, ID_FIELDS);

  protected static final String TE_ATTRIBUTE_FIELDS =
      String.format("%s,valueType,confidential,unique", NAME_FIELDS);

  protected static final String PROGRAM_STAGE_DATA_ELEMENT_FIELDS =
      String.format(
          "%s,dataElement[%s],compulsory,displayInReports,skipSynchronization,skipAnalytics",
          NAME_FIELDS, DATA_ELEMENT_FIELDS);

  protected static final String ME_FIELDS =
      String.format(
          "%1$s,username,surname,firstName,email,settings,programs,dataSets,authorities,organisationUnits[%2$s]",
          ID_FIELDS, ORG_UNIT_FIELDS);

  protected static final String RESOURCE_SYSTEM_INFO = "system/info";

  protected static final String DATE_FORMAT = "yyyy-MM-dd";

  private static final Set<Integer> ERROR_STATUS_CODES =
      set(SC_UNAUTHORIZED, SC_FORBIDDEN, SC_NOT_FOUND);

  protected final Dhis2Config config;

  protected final ObjectMapper objectMapper;

  protected final CloseableHttpClient httpClient;

  public BaseDhis2(Dhis2Config config) {
    Validate.notNull(config, "Config must be specified");

    this.config = config;

    this.objectMapper = new ObjectMapper();
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    objectMapper.setSerializationInclusion(Include.NON_NULL);
    objectMapper.setDateFormat(new SimpleDateFormat(DATE_FORMAT));

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
  protected <T> T getObject(URIBuilder uriBuilder, Query query, Class<T> type) {
    URI url = getObjectQuery(uriBuilder, query);

    return getObjectFromUrl(url, type);
  }

  /**
   * Indicates whether an object exists using HTTP HEAD.
   *
   * @param uriBuilder the URI builder.
   * @return true if the object exists.
   */
  protected boolean objectExists(URIBuilder uriBuilder) {
    HttpHead request = withAuth(new HttpHead(HttpUtils.build(uriBuilder)));

    try (CloseableHttpResponse response = httpClient.execute(request)) {
      return HttpStatus.OK.value() == response.getCode();
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
      String filterValue =
          String.format(
              "%s:%s:%s",
              filter.getProperty(), filter.getOperator().value(), getQueryValue(filter));

      uriBuilder.addParameter("filter", filterValue);
    }

    if (!query.getFilters().isEmpty() && query.getRootJunction() == RootJunction.OR) {
      uriBuilder.addParameter("rootJunction", "OR");
    }

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

    List<Order> orders = query.getOrder();

    if (!orders.isEmpty()) {
      String orderValue =
          query.getOrder().stream().map(Order::toValue).collect(Collectors.joining(","));

      uriBuilder.addParameter("order", orderValue);
    }

    return HttpUtils.build(uriBuilder);
  }

  /**
   * Retrieves a dataValueSet object using HTTP GET.
   *
   * @param uriBuilder the URI builder.
   * @param query the {@link DataValueSetQuery} filters to apply.
   * @param type the class type of the object.
   * @param <T> type.
   * @return the object.
   */
  protected <T> T getDataValueSetResponse(
      URIBuilder uriBuilder, DataValueSetQuery query, Class<T> type) {
    URI url = getDataValueSetQuery(uriBuilder, query);

    return getObjectFromUrl(url, type);
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
  protected <T> T getAnalyticsResponse(URIBuilder uriBuilder, AnalyticsQuery query, Class<T> type) {
    URI url = getAnalyticsQuery(uriBuilder, query);

    return getObjectFromUrl(url, type);
  }

  /**
   * Returns a {@link URI} based on the given analytics query.
   *
   * @param uriBuilder the URI builder.
   * @param query the {@link AnalyticsQuery} filters to apply.
   * @return a URI.
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
    addParameter(uriBuilder, "inputIdScheme", query.getInputIdScheme());
    addParameter(uriBuilder, "outputIdScheme", query.getOutputIdScheme());

    return HttpUtils.build(uriBuilder);
  }

  /**
   * Returns a {@link URI} based on the given data value set import options.
   *
   * @param uriBuilder the URI builder.
   * @param options the {@link DataValueSetImportOptions} to apply.
   * @return a URI.
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
    addParameter(uriBuilder, "preheatCache", options.getPreheatCache());
    addParameter(uriBuilder, "skipAudit", options.getSkipAudit());

    return HttpUtils.build(uriBuilder);
  }

  /**
   * Retrieves events using HTTP GET.
   *
   * @param uriBuilder the URI builder.
   * @param query the {@link EventsQuery}.
   * @return the {@link Events} object.
   */
  protected EventsResult getEventsResponse(URIBuilder uriBuilder, EventsQuery query) {
    URI url = getEventsQuery(uriBuilder, query);

    return getObjectFromUrl(url, EventsResult.class);
  }

  /**
   * Returns a {@link URI} based on the given events query.
   *
   * @param uriBuilder the URI builder.
   * @param query the {@link EventsQuery}.
   * @return a URI.
   */
  protected URI getEventsQuery(URIBuilder uriBuilder, EventsQuery query) {
    addParameter(uriBuilder, "program", query.getProgram());
    addParameter(uriBuilder, "programStage", query.getProgramStage());
    addParameter(uriBuilder, "programStatus", query.getProgramStatus());
    addParameter(uriBuilder, "followUp", query.getFollowUp());
    addParameter(uriBuilder, "trackedEntityInstance", query.getTrackedEntityInstance());
    addParameter(uriBuilder, "orgUnit", query.getOrgUnit());
    addParameter(uriBuilder, "ouMode", query.getOuMode());
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

    return HttpUtils.build(uriBuilder);
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
   * Converts the given filter to a query value.
   *
   * @param filter the {@link Filter}.
   * @return a query value.
   */
  private Object getQueryValue(Filter filter) {
    if (Operator.IN == filter.getOperator()) {
      return "[" + filter.getValue() + "]";
    } else {
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
   * @throws Dhis2ClientException if access was denied or resource was not found.
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
   * Executes the given {@link HttpUriRequestBase} request, which may be a POST or PUT request.
   *
   * @param request the request.
   * @param object the object to pass as JSON in the request body.
   * @param type the class type for the response entity.
   * @param <T> class.
   * @return a {@link Response}.
   * @throws Dhis2ClientException if access denied or resource not found.
   */
  protected <T extends BaseHttpResponse> T executeJsonPostPutRequest(
      HttpUriRequestBase request, Object object, Class<T> type) {
    validateRequestObject(object);

    String requestBody = toJsonString(object);

    log("Request URI: '{}', body: '{}'", getUriAsString(request), requestBody);

    HttpEntity entity = new StringEntity(requestBody, StandardCharsets.UTF_8);

    request.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
    request.setEntity(entity);

    return executeRequest(request, type);
  }

  /**
   * Executes the given request, parses the response body and returns a response message.
   *
   * @param request the request.
   * @param type the class type for the response entity.
   * @return a response message.
   * @throws Dhis2ClientException if access denied or resource not found.
   */
  private <T extends BaseHttpResponse> T executeRequest(HttpUriRequestBase request, Class<T> type) {
    withAuth(request);

    try (CloseableHttpResponse response = httpClient.execute(request)) {
      handleErrors(response, request.getRequestUri());

      String responseBody = EntityUtils.toString(response.getEntity());

      log.debug("Response body: '{}'", responseBody);

      T responseMessage = objectMapper.readValue(responseBody, type);

      responseMessage.setHeaders(asList(response.getHeaders()));
      responseMessage.setHttpStatusCode(response.getCode());

      return responseMessage;
    } catch (IOException ex) {
      throw newDhis2ClientException(ex);
    } catch (ParseException ex) {
      throw new Dhis2ClientException("HTTP response could not be parsed", ex);
    }
  }

  /**
   * Executes the given request without attempting to parse a response body and returns a response
   * message.
   *
   * @param request the request.
   * @return a response message.
   * @throws Dhis2ClientException if access denied or resource not found.
   */
  protected Response executeRequest(HttpUriRequestBase request) {
    withAuth(request);

    try (CloseableHttpResponse response = httpClient.execute(request)) {
      handleErrors(response, request.getRequestUri());

      HttpStatus httpStatus = HttpStatus.valueOf(response.getCode());
      Status status = httpStatus != null && httpStatus.is2xxSuccessful() ? Status.OK : Status.ERROR;

      Response resp = new Response();

      resp.setHeaders(asList(response.getHeaders()));
      resp.setStatus(status);
      resp.setHttpStatusCode(response.getCode());

      return resp;
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
    request.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
    request.setEntity(entity);
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
  protected <T> T getObjectFromUrl(URI url, Class<T> type) {
    log("Get object from URL: '{}'", url);

    try (CloseableHttpResponse response = getJsonHttpResponse(url)) {
      handleErrors(response, url.toString());

      String responseBody = EntityUtils.toString(response.getEntity());

      log("Response body: '{}'", responseBody);

      return objectMapper.readValue(responseBody, type);
    } catch (IOException ex) {
      throw new Dhis2ClientException("Failed to fetch object", ex);
    } catch (ParseException ex) {
      throw new Dhis2ClientException("HTTP response could not be parsed", ex);
    }
  }

  /**
   * Gets a {@link CloseableHttpResponse} for the given URL.
   *
   * @param url the URL.
   * @return a {@link CloseableHttpResponse}.
   */
  protected CloseableHttpResponse getJsonHttpResponse(URI url) {
    HttpGet request = withAuth(new HttpGet(url));
    request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

    log("Get request URL: '{}'", HttpUtils.asString(url));

    try {
      return httpClient.execute(request);
    } catch (IOException ex) {
      throw new Dhis2ClientException("Request failed", ex);
    }
  }

  /**
   * Handles error status codes, currently <code>401/403</code> and <code>404</code>.
   *
   * @param response {@link HttpResponse}.
   * @param url the request URL.
   * @throws Dhis2ClientException
   */
  private void handleErrors(HttpResponse response, String url) {
    final int code = response.getCode();

    if (ERROR_STATUS_CODES.contains(code)) {
      String message = String.format("%s (%d)", getErrorMessage(code), code);

      log("Error URL: '{}'", url);

      throw new Dhis2ClientException(message, code);
    }
  }

  /**
   * Return an error message for the given status code.
   *
   * @param code the status code.
   * @return an error message.
   */
  private String getErrorMessage(int code) {
    if (401 == code) return "Authentication failed";
    if (403 == code) return "Access was denied";
    if (404 == code) return "Object not found";
    if (409 == code) return "Conflict";
    if (code >= 400 && code < 500) return "Client error";
    if (code >= 500 && code < 600) return "Server error";
    else return "Error";
  }

  /**
   * Validates a request object.
   *
   * @param object the request object to validate.
   * @throws Dhis2ClientException
   */
  private void validateRequestObject(Object object) {
    if (object == null) {
      throw new Dhis2ClientException("Request object is null", 400);
    }
  }

  /**
   * Write the given {@link HttpResponse} to the given {@link File}.
   *
   * @param response the response.
   * @param file the file to write the response to.
   * @throws Dhis2ClientException if the write operation failed.
   */
  protected void writeToFile(CloseableHttpResponse response, File file) {
    try (FileOutputStream fileOut = FileUtils.openOutputStream(file);
        InputStream in = response.getEntity().getContent()) {
      IOUtils.copy(in, fileOut);
    } catch (IOException ex) {
      throw new Dhis2ClientException("Failed to write to file", ex);
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
   * Saves a metadata object using HTTP POST.
   *
   * @param path the URL path relative to the API end point.
   * @param object the object to save.
   * @return {@link ObjectResponse} holding information about the operation.
   * @throws Dhis2ClientException if the save operation failed due to client side error.
   */
  protected ObjectResponse saveMetadataObject(String path, IdentifiableObject object) {
    return saveObject(path, object, ObjectResponse.class);
  }

  /**
   * Saves an object using HTTP POST.
   *
   * @param path the URL path relative to the API end point.
   * @param object the object to save.
   * @param type the class type for the response entity.
   * @param <T> class.
   * @return object holding information about the operation.
   * @throws Dhis2ClientException if the save operation failed due to client side error.
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
   * @param <T> class.
   * @return object holding information about the operation.
   * @throws Dhis2ClientException if the save operation failed due to client side error.
   */
  protected <T extends BaseHttpResponse> T saveObject(
      URIBuilder uriBuilder, Object object, Class<T> type) {
    URI url = HttpUtils.build(uriBuilder);

    return executeJsonPostPutRequest(new HttpPost(url), object, type);
  }

  /**
   * Saves or updates metadata objects.
   *
   * @param objects the {@link Objects}.
   * @return {@link ObjectsResponse} holding information about the operation.
   */
  protected ObjectsResponse saveMetadataObjects(Objects objects) {
    URI url = config.getResolvedUrl("metadata");

    return executeJsonPostPutRequest(new HttpPost(url), objects, ObjectsResponse.class);
  }

  /**
   * Updates an object using HTTP PUT.
   *
   * @param path the URL path relative to the API end point.
   * @param object the object to save.
   * @return {@link ObjectResponse} holding information about the operation.
   */
  protected ObjectResponse updateMetadataObject(String path, IdentifiableObject object) {
    Map<String, String> params = Map.of(SKIP_SHARING_PARAM, "true");

    return updateObject(path, params, object, ObjectResponse.class);
  }

  /**
   * Updates an object using HTTP PUT.
   *
   * @param path the URL path relative to the API end point.
   * @param params the map of query parameter names and values to include in the URL.
   * @param object the object to save.
   * @param type the class type for the response entity.
   * @param <T> class.
   * @return object holding information about the operation.
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
   * @return {@link ObjectResponse} holding information about the operation.
   */
  protected ObjectResponse removeMetadataObject(String path) {
    return removeObject(path, ObjectResponse.class);
  }

  /**
   * Removes an object using HTTP DELETE.
   *
   * @param path the URL path relative to the API end point.
   * @param type the class type for the response entity.
   * @param <T> class.
   * @return object holding information about the operation.
   */
  protected <T extends BaseHttpResponse> T removeObject(String path, Class<T> type) {
    URI url = config.getResolvedUrl(path);

    return executeRequest(new HttpDelete(url), type);
  }

  /**
   * Retrieves an object using HTTP GET.
   *
   * @param path the URL path relative to the API end point.
   * @param type the class type of the object.
   * @param <T> type.
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
   * Logs the message at debug level.
   *
   * @param format the message format.
   * @param arguments the message arguments.
   */
  protected void log(String format, Object... arguments) {
    if ("info".equalsIgnoreCase(System.getProperty("log.level.dhis2"))) {
      log.info(format, arguments);
    }
    else {
      log.debug(format, arguments);
    }
  }
}
