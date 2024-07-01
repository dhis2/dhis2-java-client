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

import static org.hisp.dhis.util.CollectionUtils.list;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import org.apache.hc.core5.net.URIBuilder;
import org.hisp.dhis.model.AggregationType;
import org.hisp.dhis.model.IdScheme;
import org.hisp.dhis.model.datavalueset.DataValueSetImportOptions;
import org.hisp.dhis.model.event.ProgramStatus;
import org.hisp.dhis.query.Filter;
import org.hisp.dhis.query.Order;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.query.analytics.AnalyticsQuery;
import org.hisp.dhis.query.datavalue.DataValueSetQuery;
import org.hisp.dhis.query.event.EventsQuery;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.UNIT)
class Dhis2Test {
  @Test
  void testGetDhis2() {
    Dhis2Config config = getDhis2Config();

    Dhis2 dhis2 = new Dhis2(config);

    assertEquals("https://dhis2.org", dhis2.getDhis2Url());
  }

  @Test
  void testGetObjectQueryA() {
    Dhis2Config config = getDhis2Config();

    Dhis2 dhis2 = new Dhis2(config);

    URIBuilder uriBuilder = config.getResolvedUriBuilder().appendPath("dataElements");

    Query query =
        Query.instance()
            .addFilter(Filter.like("name", "Immunization"))
            .addFilter(Filter.eq("valueType", "NUMBER"))
            .setOrder(Order.desc("code"))
            .setPaging(2, 100);

    URI uri = dhis2.getObjectQuery(uriBuilder, query);

    String expected =
        "https://dhis2.org/api/dataElements?"
            + "filter=name%3Alike%3AImmunization&filter=valueType%3Aeq%3ANUMBER&page=2&pageSize=100&order=code%3Adesc";

    assertEquals(expected, uri.toString());
  }

  @Test
  void testGetObjectQueryB() {
    Dhis2Config config = getDhis2Config();

    Dhis2 dhis2 = new Dhis2(config);

    URIBuilder uriBuilder = config.getResolvedUriBuilder().appendPath("indicators");

    Query query =
        Query.instance()
            .addFilter(Filter.like("name", "ANC"))
            .addOrder(Order.asc("name"))
            .addOrder(Order.desc("uid"))
            .setPaging(4, 50);

    URI uri = dhis2.getObjectQuery(uriBuilder, query);

    String expected =
        "https://dhis2.org/api/indicators?"
            + "filter=name%3Alike%3AANC&page=4&pageSize=50&order=name%3Aasc%2Cuid%3Adesc";

    assertEquals(expected, uri.toString());
  }

  @Test
  void testGetObjectQueryC() {
    Dhis2Config config = getDhis2Config();

    Dhis2 dhis2 = new Dhis2(config);

    URIBuilder uriBuilder = config.getResolvedUriBuilder().appendPath("dataSets");

    Query query =
        Query.instance()
            .addFilter(Filter.like("name", "ANC"))
            .addOrder(Order.asc("id"))
            .setPaging(1, 50);

    URI uri = dhis2.getObjectQuery(uriBuilder, query);

    String expected =
        "https://dhis2.org/api/dataSets?"
            + "filter=name%3Alike%3AANC&page=1&pageSize=50&order=id%3Aasc";

    assertEquals(expected, uri.toString());
  }

  @Test
  void testGetDataValueSetImportQuery() {
    Dhis2Config config = getDhis2Config();

    Dhis2 dhis2 = new Dhis2(config);

    URIBuilder uriBuilder = config.getResolvedUriBuilder().appendPath("dataValueSets");

    DataValueSetImportOptions options =
        DataValueSetImportOptions.instance()
            .setDataElementIdScheme(IdScheme.CODE)
            .setDryRun(true)
            .setPreheatCache(true)
            .setSkipAudit(true);

    URI uri = dhis2.getDataValueSetImportQuery(uriBuilder, options);

    String expected =
        "https://dhis2.org/api/dataValueSets?"
            + "async=true&dataElementIdScheme=code&dryRun=true&preheatCache=true&skipAudit=true";

    assertEquals(expected, uri.toString());
  }

  @Test
  void testGetAnalyticsQuery() {
    Dhis2Config config = getDhis2Config();

    Dhis2 dhis2 = new Dhis2(config);

    URIBuilder uriBuilder = config.getResolvedUriBuilder().appendPath("analytics");

    AnalyticsQuery query =
        AnalyticsQuery.instance()
            .setAggregationType(AggregationType.AVERAGE)
            .setIgnoreLimit(true)
            .setInputIdScheme(IdScheme.CODE)
            .setOutputIdScheme(IdScheme.UID);

    URI uri = dhis2.getAnalyticsQuery(uriBuilder, query);

    String expected =
        "https://dhis2.org/api/analytics?"
            + "aggregationType=AVERAGE&ignoreLimit=true&inputIdScheme=code&outputIdScheme=uid";

    assertEquals(expected, uri.toString());
  }

  @Test
  void testGetEventsQuery() {
    Dhis2Config config = getDhis2Config();

    Dhis2 dhis2 = new Dhis2(config);

    URIBuilder uriBuilder =
        config.getResolvedUriBuilder().appendPath("tracker").appendPath("events");

    EventsQuery query =
        EventsQuery.instance()
            .setProgram("hJhgt5cDs7j")
            .setProgramStatus(ProgramStatus.ACTIVE)
            .setFollowUp(true)
            .setIdScheme(IdScheme.CODE);

    URI uri = dhis2.getEventsQuery(uriBuilder, query);

    String expected =
        "https://dhis2.org/api/tracker/events?"
            + "program=hJhgt5cDs7j&programStatus=ACTIVE&followUp=true&idScheme=code";

    assertEquals(expected, uri.toString());
  }

  @Test
  void testGetDataValueSetQuery() {
    Dhis2Config config = getDhis2Config();

    Dhis2 dhis2 = new Dhis2(config);

    URIBuilder uriBuilder = config.getResolvedUriBuilder().appendPath("dataValueSets.json");

    DataValueSetQuery query =
        DataValueSetQuery.instance()
            .addDataElements(list("N9vniUuCcqY"))
            .addPeriods(list("202211"))
            .addOrgUnits(list("ImspTQPwCqd"))
            .setChildren(true);

    String expected =
        "https://dhis2.org/api/dataValueSets.json?"
            + "dataElement=N9vniUuCcqY&orgUnit=ImspTQPwCqd&period=202211&children=true";

    URI uri = dhis2.getDataValueSetQuery(uriBuilder, query);

    assertEquals(expected, uri.toString());
  }

  private Dhis2Config getDhis2Config() {
    return new Dhis2Config("https://dhis2.org", "admin", "district");
  }
}
