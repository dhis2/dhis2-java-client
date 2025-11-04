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
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.hisp.dhis.model.ValueType;
import org.hisp.dhis.support.TestTags;
import org.hisp.dhis.util.MapBuilder;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.UNIT)
class AnalyticsDataIndexTest {
  private AnalyticsData getDataA() {
    List<AnalyticsHeader> headers =
        List.of(
            new AnalyticsHeader(DATA_X, "Data", ValueType.TEXT, true),
            new AnalyticsHeader(PERIOD, "Period", ValueType.TEXT, true),
            new AnalyticsHeader(ORG_UNIT, "OrgUnit", ValueType.TEXT, true),
            new AnalyticsHeader("value", "Value", ValueType.NUMBER, false));

    AnalyticsMetaData metadata = new AnalyticsMetaData();
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
            .put("dx", List.of("A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8"))
            .put("pe", List.of("B1", "B2", "B3", "B4"))
            .put("ou", List.of("C1", "C2"))
            .build());

    AnalyticsData data = new AnalyticsData();

    data.setHeaders(headers);
    data.setMetaData(metadata);

    data.addRow(List.of("A1", "B1", "C1", "2"));
    data.addRow(List.of("A2", "B2", "C2", "4"));
    data.addRow(List.of("A3", "B3", "C1", "7"));
    data.addRow(List.of("A4", "B4", "C2", "3"));
    data.addRow(List.of("A5", "B1", "C1", "8"));
    data.addRow(List.of("A6", "B2", "C2", "6"));
    data.addRow(List.of("A7", "B3", "C1", "1"));
    data.addRow(List.of("A8", "B4", "C2", "9"));

    return data;
  }

  @Test
  void testToKey() {
    String keyA = AnalyticsDataIndex.toKey(List.of("A1", "B1", "C1", "2"), List.of(0, 1, 2));
    String keyB = AnalyticsDataIndex.toKey(List.of("A4", "B2", "2"), List.of(0, 1));

    assertEquals("A1::B1::C1", keyA);
    assertEquals("A4::B2", keyB);
  }

  @Test
  void testGetValue() {
    AnalyticsData data = getDataA();

    AnalyticsDataIndex index = data.getIndex(3);

    assertEquals(8, index.keySet().size());

    assertEquals("2", index.getValue("A1", "B1", "C1"));
    assertEquals("4", index.getValue("A2", "B2", "C2"));
    assertEquals("7", index.getValue("A3", "B3", "C1"));
    assertEquals("3", index.getValue("A4", "B4", "C2"));
    assertEquals("8", index.getValue("A5", "B1", "C1"));
    assertEquals("6", index.getValue("A6", "B2", "C2"));
    assertEquals("1", index.getValue("A7", "B3", "C1"));
    assertEquals("9", index.getValue("A8", "B4", "C2"));

    assertNull(index.getValue("X1", "Y1", "Z1"));
  }

  @Test
  void testGetDoubleValue() {
    AnalyticsData data = getDataA();

    AnalyticsDataIndex index = data.getIndex(3);

    assertEquals(8, index.keySet().size());

    assertEquals(2d, index.getDoubleValue("A1", "B1", "C1"));
    assertEquals(4d, index.getDoubleValue("A2", "B2", "C2"));
    assertEquals(7d, index.getDoubleValue("A3", "B3", "C1"));

    assertNull(index.getDoubleValue("X1", "Y1", "Z1"));
  }

  @Test
  void testGetValueException() {
    AnalyticsData data = getDataA();

    AnalyticsDataIndex index = data.getIndex(3);

    assertNull(index.getValue("A1", "B9", "C9"));

    assertThrows(IllegalArgumentException.class, () -> index.getValue("A1", "B1"));
  }
}
