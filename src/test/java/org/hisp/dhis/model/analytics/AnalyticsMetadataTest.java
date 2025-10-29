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
package org.hisp.dhis.model.analytics;

import static org.hisp.dhis.model.analytics.AnalyticsDimension.DATA_X;
import static org.hisp.dhis.model.analytics.AnalyticsDimension.ORG_UNIT;
import static org.hisp.dhis.model.analytics.AnalyticsDimension.PERIOD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import org.hisp.dhis.support.TestTags;
import org.hisp.dhis.util.MapBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.UNIT)
class AnalyticsMetadataTest {
  private AnalyticsMetaData metadata;

  @BeforeEach
  void beforeEach() {
    metadata = new AnalyticsMetaData();
    metadata.setItems(
        new MapBuilder<String, MetaDataItem>()
            .put("A1", new MetaDataItem("Indicator 1"))
            .put("A2", new MetaDataItem("Indicator 2"))
            .put("A3", new MetaDataItem("Indicator 3"))
            .put("A4", new MetaDataItem("Indicator 4"))
            .put("A5", new MetaDataItem("Indicator 5"))
            .put("A6", new MetaDataItem("Indicator 6"))
            .put("A7", new MetaDataItem("Indicator 7"))
            .put("A8", new MetaDataItem("Indicator 8"))
            .put("B1", new MetaDataItem("Month 1"))
            .put("B2", new MetaDataItem("Month 2"))
            .put("B3", new MetaDataItem("Month 3"))
            .put("B4", new MetaDataItem("Month 4"))
            .put("C1", new MetaDataItem("Facility 1"))
            .put("C2", new MetaDataItem("Facility 2"))
            .build());
    metadata.setDimensions(
        new MapBuilder<String, List<String>>()
            .put(DATA_X, List.of("A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8"))
            .put(PERIOD, List.of("B1", "B2", "B3", "B4"))
            .put(ORG_UNIT, List.of("C1", "C2"))
            .build());
  }

  @Test
  void testGetItemName() {
    assertEquals("Indicator 2", metadata.getItemName("A2"));
    assertEquals("Month 3", metadata.getItemName("B3"));
    assertEquals("Facility 1", metadata.getItemName("C1"));
    assertNull(metadata.getItemName("X1"));
  }

  @Test
  void testGetMetadataItem() {
    assertEquals("A1", metadata.getMetadataItem("A1").getUid());
    assertEquals("Indicator 1", metadata.getMetadataItem("A1").getName());

    assertEquals("B1", metadata.getMetadataItem("B1").getUid());
    assertEquals("Month 1", metadata.getMetadataItem("B1").getName());

    assertNull(metadata.getMetadataItem("X1"));
  }

  @Test
  void testGetMetadataItems() {
    List<MetaDataItem> dataItems = metadata.getMetadataItems(DATA_X);

    assertEquals(8, dataItems.size());
    assertEquals("A1", dataItems.get(0).getUid());
    assertEquals("Indicator 1", dataItems.get(0).getName());

    List<MetaDataItem> periodItems = metadata.getMetadataItems(PERIOD);

    assertEquals(4, periodItems.size());
    assertEquals("B1", periodItems.get(0).getUid());
    assertEquals("Month 1", periodItems.get(0).getName());

    List<MetaDataItem> orgUnitItems = metadata.getMetadataItems(ORG_UNIT);

    assertEquals(2, orgUnitItems.size());
    assertEquals("C1", orgUnitItems.get(0).getUid());
    assertEquals("Facility 1", orgUnitItems.get(0).getName());
  }

  @Test
  void testGetMetadataItemsEmpty() {
    assertTrue(metadata.getMetadataItems("foo").isEmpty());
  }

  @Test
  void testGetDimensionItemNameIdMap() {
    Map<String, String> map = metadata.getDimensionItemNameIdMap(AnalyticsDimension.PERIOD);

    assertEquals(4, map.keySet().size());
    assertEquals("B1", map.get("Month 1"));
    assertEquals("B2", map.get("Month 2"));
    assertEquals("B4", map.get("Month 4"));
  }

  @Test
  void testGetDimensionItemNameIdMapEmpty() {
    AnalyticsMetaData metadataA = new AnalyticsMetaData();
    metadataA.setItems(Map.of());
    metadataA.setDimensions(Map.of());

    assertTrue(metadataA.getDimensionItemNameIdMap(AnalyticsDimension.PERIOD).isEmpty());
  }
}
