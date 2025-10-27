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

import java.util.List;
import org.hisp.dhis.model.ValueType;
import org.hisp.dhis.support.TestTags;
import org.hisp.dhis.util.MapBuilder;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.UNIT)
class AnalyticsDataSortTest {
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
    data.addRow(List.of("A4", "B4", "C2", "3"));
    data.addRow(List.of("A3", "B3", "C1", "7"));
    data.addRow(List.of("A5", "B1", "C1", "8"));
    data.addRow(List.of("A7", "B3", "C1", "1"));
    data.addRow(List.of("A6", "B2", "C2", "6"));
    data.addRow(List.of("A8", "B4", "C2", "9"));

    return data;
  }

  @Test
  void testSortDataA() {
    AnalyticsData data = getDataA();

    List<String> row1 = data.getRow(0);
    List<String> row3 = data.getRow(2);
    List<String> row5 = data.getRow(4);
    List<String> row7 = data.getRow(6);

    assertEquals("A1", row1.get(0));
    assertEquals("C1", row1.get(2));
    assertEquals("A4", row3.get(0));
    assertEquals("C2", row3.get(2));
    assertEquals("A5", row5.get(0));
    assertEquals("C1", row5.get(2));
    assertEquals("A6", row7.get(0));
    assertEquals("C2", row7.get(2));

    data.sortRows();

    row1 = data.getRow(0);
    row3 = data.getRow(2);
    row5 = data.getRow(4);
    row7 = data.getRow(6);

    assertEquals("A1", row1.get(0));
    assertEquals("C1", row1.get(2));
    assertEquals("A3", row3.get(0));
    assertEquals("C1", row3.get(2));
    assertEquals("A5", row5.get(0));
    assertEquals("C1", row5.get(2));
    assertEquals("A7", row7.get(0));
    assertEquals("C1", row7.get(2));
  }

  private AnalyticsData getDataB() {
    List<AnalyticsHeader> headers =
        List.of(
            new AnalyticsHeader(DATA_X, "Data", ValueType.TEXT, true),
            new AnalyticsHeader(PERIOD, "Period", ValueType.TEXT, true),
            new AnalyticsHeader(ORG_UNIT, "OrgUnit", ValueType.TEXT, true),
            new AnalyticsHeader("value", "Value", ValueType.NUMBER, false));

    AnalyticsMetaData metadata = new AnalyticsMetaData();
    metadata.setItems(
        new MapBuilder<String, MetaDataItem>()
            .put("A1", new MetaDataItem("ANC 1"))
            .put("A2", new MetaDataItem("ANC 2"))
            .put("012025", new MetaDataItem("January 2024"))
            .put("022025", new MetaDataItem("February 2024"))
            .put("032025", new MetaDataItem("March 2024"))
            .put("042025", new MetaDataItem("April 2024"))
            .put("052025", new MetaDataItem("May 2024"))
            .put("062025", new MetaDataItem("June 2024"))
            .put("C1", new MetaDataItem("Ngelehun CHC"))
            .put("C2", new MetaDataItem("Benduma MCHP"))
            .build());
    metadata.setDimensions(
        new MapBuilder<String, List<String>>()
            .put("dx", List.of("A1", "A2"))
            .put("pe", List.of("012025", "022025", "032025", "042025", "052025", "062025"))
            .put("ou", List.of("C1", "C2"))
            .build());

    AnalyticsData data = new AnalyticsData();

    data.setHeaders(headers);
    data.setMetaData(metadata);

    data.addRow(List.of("A1", "May 2024", "C1", "1"));
    data.addRow(List.of("A1", "January 2024", "C1", "2"));
    data.addRow(List.of("A1", "March 2024", "C1", "4"));
    data.addRow(List.of("A1", "June 2024", "C2", "3"));
    data.addRow(List.of("A1", "February 2024", "C2", "7"));
    data.addRow(List.of("A1", "April 2024", "C2", "6"));
    data.addRow(List.of("A2", "June 2024", "C2", "5"));
    data.addRow(List.of("A2", "January 2024", "C1", "2"));
    data.addRow(List.of("A2", "March 2024", "C1", "2"));
    data.addRow(List.of("A2", "April 2024", "C2", "9"));
    data.addRow(List.of("A2", "February 2024", "C2", "5"));
    data.addRow(List.of("A2", "May 2024", "C1", "3"));

    return data;
  }

  @Test
  void testSortDataB() {
    AnalyticsData data = getDataB();

    data.sortRows();

    List<String> row1 = data.getRow(0);
    List<String> row2 = data.getRow(1);
    List<String> row3 = data.getRow(2);
    List<String> row4 = data.getRow(3);
    List<String> row6 = data.getRow(5);
    List<String> row7 = data.getRow(6);
    List<String> row8 = data.getRow(7);

    assertEquals("A1", row1.get(0));
    assertEquals("January 2024", row1.get(1));
    assertEquals("A1", row2.get(0));
    assertEquals("February 2024", row2.get(1));
    assertEquals("A1", row3.get(0));
    assertEquals("March 2024", row3.get(1));
    assertEquals("A1", row4.get(0));
    assertEquals("April 2024", row4.get(1));
    assertEquals("A1", row6.get(0));
    assertEquals("June 2024", row6.get(1));
    assertEquals("A2", row7.get(0));
    assertEquals("January 2024", row7.get(1));
    assertEquals("A2", row8.get(0));
    assertEquals("February 2024", row8.get(1));
  }
}
