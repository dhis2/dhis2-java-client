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
package org.hisp.dhis.analytics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.Map;
import org.hisp.dhis.Dhis2;
import org.hisp.dhis.TestFixture;
import org.hisp.dhis.model.ValueType;
import org.hisp.dhis.model.analytics.AnalyticsData;
import org.hisp.dhis.model.analytics.AnalyticsHeader;
import org.hisp.dhis.model.analytics.AnalyticsMetaData;
import org.hisp.dhis.model.analytics.MetaDataItem;
import org.hisp.dhis.query.analytics.AnalyticsQuery;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(TestTags.INTEGRATION)
class AnalyticsDataApiTest {
  @Test
  @Disabled("Using local environment")
  void testGetAnalyticsData() {
    Dhis2 dhis2 = new Dhis2(TestFixture.LOCAL_CONFIG);

    AnalyticsQuery query =
        AnalyticsQuery.instance()
            .addDataDimension(List.of("fbfJHSPpUQD", "cYeuwXTCPkU", "Jtf34kNZhzP"))
            .addPeriodDimension(List.of("202501", "202502", "202503"))
            .addOrgUnitFilter(List.of("ImspTQPwCqd"))
            .setSkipData(false)
            .setSkipMeta(false)
            .setIncludeMetadataDetails(true);

    AnalyticsData data = dhis2.getAnalyticsData(query);

    log.info(data.toString());

    assertNotNull(data);
    assertEquals(3, data.getWidth());
    assertEquals(3, data.getHeaderWidth());
    assertTrue(data.getHeight() > 0, String.valueOf(data.getHeight()));

    List<AnalyticsHeader> headers = data.getHeaders();

    assertNotNull(headers);
    assertEquals(3, headers.size());

    AnalyticsHeader firstHeader = headers.get(0);

    assertNotNull(firstHeader);
    assertEquals("dx", firstHeader.getName());
    assertEquals("Data", firstHeader.getColumn());
    assertEquals(ValueType.TEXT, firstHeader.getValueType());

    AnalyticsMetaData metaData = data.getMetaData();

    Map<String, MetaDataItem> items = metaData.getItems();

    assertNotNull(items);
    assertFalse(items.keySet().isEmpty());

    MetaDataItem dxItem = items.get("dx");

    assertNotNull(dxItem);
    assertEquals("dx", dxItem.getUid());
    assertEquals("Data", dxItem.getName());

    Map<String, List<String>> dimensions = metaData.getDimensions();

    assertNotNull(dimensions);
    assertTrue(dimensions.containsKey("dx"));
    assertTrue(dimensions.containsKey("pe"));
    assertTrue(dimensions.containsKey("ou"));

    assertNotNull(metaData);

    List<String> firstRow = data.getRows().get(0);

    assertNotNull(firstRow);
    assertEquals(3, firstRow.size());
  }
}
