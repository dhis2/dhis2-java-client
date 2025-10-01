/*
 * Copyright (c) 2004-2025, University of Oslo
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

import static org.hisp.dhis.support.Assertions.assertNotBlank;
import static org.hisp.dhis.support.Assertions.assertSuccessResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;
import org.hisp.dhis.model.programfilter.DateFilterPeriod;
import org.hisp.dhis.model.programfilter.DatePeriodType;
import org.hisp.dhis.model.programfilter.EventDataFilter;
import org.hisp.dhis.model.programfilter.EventFilter;
import org.hisp.dhis.model.programfilter.EventQueryCriteria;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.response.HttpStatus;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.support.TestTags;
import org.hisp.dhis.util.UidUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class EventFilterApiTest {
  @Test
  void testGetEventFilter() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    EventFilter eventFilter = dhis2.getEventFilter("BEX7IU9j8t5");
    assertNotNull(eventFilter);
    assertEquals("BEX7IU9j8t5", eventFilter.getId());
    validateEventFilter(eventFilter);
  }

  @Test
  void testGetEventFilters() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    List<EventFilter> eventFilters = dhis2.getEventFilters(Query.instance());

    assertNotNull(eventFilters);
    assertFalse(eventFilters.isEmpty());

    EventFilter eventFilter = eventFilters.get(0);
    validateEventFilter(eventFilter);
  }

  @Test
  void testIsGetEventFilter() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);
    assertTrue(dhis2.isEventFilter("BEX7IU9j8t5"));
  }

  @Test
  void testCreateAndDeleteEventFilter() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);
    String uidA = UidUtils.generateUid();
    DateFilterPeriod dateFilterPeriod = new DateFilterPeriod();
    dateFilterPeriod.setStartDate(new Date());
    dateFilterPeriod.setEndDate(new Date());
    dateFilterPeriod.setType(DatePeriodType.RELATIVE);
    dateFilterPeriod.setEndBuffer(1);
    dateFilterPeriod.setStartBuffer(1);

    EventDataFilter dataFilter = new EventDataFilter();
    dataFilter.setGt(uidA);
    dataFilter.setGe(uidA);
    dataFilter.setLt(uidA);
    dataFilter.setLe(uidA);
    dataFilter.setEq(uidA);
    dataFilter.setDataItem(uidA);
    dataFilter.setDateFilter(dateFilterPeriod);

    EventQueryCriteria criteria = new EventQueryCriteria();
    criteria.setDisplayColumnOrder(List.of("displayColumn"));
    criteria.setDataFilters(List.of(dataFilter));
    criteria.setLastUpdatedDate(dateFilterPeriod);
    criteria.setOrder("orderValue");

    EventFilter eventFilter = new EventFilter();
    eventFilter.setId(uidA);
    eventFilter.setName(uidA);
    eventFilter.setDescription(uidA);
    eventFilter.setProgram("IpHINAT79UW");
    eventFilter.setEventQueryCriteria(criteria);

    // Create
    ObjectResponse createResp = dhis2.saveMetadataObject(eventFilter);
    assertEquals(201, createResp.getHttpStatusCode().intValue(), createResp.toString());
    assertEquals(HttpStatus.CREATED, createResp.getHttpStatus(), createResp.toString());
    assertEquals(Status.OK, createResp.getStatus(), createResp.toString());
    assertNotNull(createResp.getResponse());
    assertNotNull(createResp.getResponse().getUid());

    // Get saved event filter
    EventFilter savedEventFilter = dhis2.getEventFilter(uidA);
    validateEventFilter(savedEventFilter, eventFilter);

    EventQueryCriteria savedCriteria = savedEventFilter.getEventQueryCriteria();
    validateEventQueryCriteria(savedCriteria, criteria);

    assertNotNull(savedCriteria.getDataFilters());
    assertFalse(savedCriteria.getDataFilters().isEmpty());
    EventDataFilter savedDataFilter = savedCriteria.getDataFilters().get(0);
    validateEventDataFilter(savedDataFilter, dataFilter);

    DateFilterPeriod savedFilteredPeriod = savedCriteria.getLastUpdatedDate();
    validateSavedFilteredPeriod(savedFilteredPeriod, dateFilterPeriod);

    // Remove
    ObjectResponse removeRespA = dhis2.removeEventFilter(uidA);

    assertSuccessResponse(removeRespA, HttpStatus.OK, 200);
  }

  private static void validateEventFilter(EventFilter savedEventFilter, EventFilter eventFilter) {
    assertNotNull(savedEventFilter);
    assertEquals(eventFilter.getId(), savedEventFilter.getId());
    assertEquals(eventFilter.getProgram(), savedEventFilter.getProgram());
    assertEquals(eventFilter.getName(), savedEventFilter.getName());
    assertEquals(eventFilter.getDescription(), savedEventFilter.getDescription());
  }

  private void validateEventQueryCriteria(
      EventQueryCriteria savedCriteria, EventQueryCriteria criteria) {
    assertNotNull(savedCriteria);
    assertEquals(criteria.getDisplayColumnOrder(), savedCriteria.getDisplayColumnOrder());
    assertEquals(criteria.getOrder(), savedCriteria.getOrder());
  }

  private void validateEventDataFilter(
      EventDataFilter savedDataFilter, EventDataFilter dataFilter) {
    assertNotNull(savedDataFilter);
    assertEquals(dataFilter.getDataItem(), savedDataFilter.getDataItem());
    validateSavedFilteredPeriod(dataFilter.getDateFilter(), savedDataFilter.getDateFilter());
    assertEquals(dataFilter.getGt(), savedDataFilter.getGt());
    assertEquals(dataFilter.getGe(), savedDataFilter.getGe());
    assertEquals(dataFilter.getLt(), savedDataFilter.getLt());
    assertEquals(dataFilter.getLe(), savedDataFilter.getLe());
    assertEquals(dataFilter.getEq(), savedDataFilter.getEq());
  }

  private void validateSavedFilteredPeriod(
      DateFilterPeriod savedFilteredPeriod, DateFilterPeriod dateFilterPeriod) {
    assertNotNull(savedFilteredPeriod);
    assertNotNull(savedFilteredPeriod.getStartDate());
    assertNotNull(savedFilteredPeriod.getEndDate());
    assertEquals(dateFilterPeriod.getType(), savedFilteredPeriod.getType());
    assertEquals(dateFilterPeriod.getEndBuffer(), savedFilteredPeriod.getEndBuffer());
    assertEquals(dateFilterPeriod.getStartBuffer(), savedFilteredPeriod.getStartBuffer());
    assertEquals(dateFilterPeriod.getType(), savedFilteredPeriod.getType());
  }

  private void validateEventFilter(EventFilter eventFilter) {
    assertNotNull(eventFilter);
    assertNotBlank(eventFilter.getId());
    assertNotBlank(eventFilter.getName());
    assertNotNull(eventFilter.getSharing());
    assertNotNull(eventFilter.getAccess());
    assertNotNull(eventFilter.getCreated());
    assertNotNull(eventFilter.getLastUpdated());
    assertNotNull(eventFilter.getProgram());
    assertNotNull(eventFilter.getEventQueryCriteria());

    EventQueryCriteria eventQueryCriteria = eventFilter.getEventQueryCriteria();
    assertNotNull(eventQueryCriteria);
    assertNotNull(eventQueryCriteria.getOrder());
    assertNotNull(eventQueryCriteria.getDisplayColumnOrder());
    assertFalse(eventQueryCriteria.getDisplayColumnOrder().isEmpty());
  }
}
