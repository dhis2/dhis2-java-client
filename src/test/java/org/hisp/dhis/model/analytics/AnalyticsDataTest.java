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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.hisp.dhis.model.ValueType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnalyticsDataTest {
  private AnalyticsData data;

  @BeforeEach
  public void beforeEach() {
    data = new AnalyticsData();

    data.setHeaders(
        List.of(
            new AnalyticsHeader("dx", "Data", ValueType.TEXT, true),
            new AnalyticsHeader("pe", "Period", ValueType.TEXT, true),
            new AnalyticsHeader("ou", "OrgUnit", ValueType.TEXT, true),
            new AnalyticsHeader("value", "Value", ValueType.NUMBER, false)));
    data.setMetaData(new AnalyticsMetaData());
    data.addRow(List.of("A1", "B1", "C1", "2"));
    data.addRow(List.of("A2", "B2", "C2", "4"));
    data.addRow(List.of("A4", "B4", "C2", "3"));
    data.addRow(List.of("A3", "B3", "C1", "7"));
    data.addRow(List.of("A5", "B1", "C1", "8"));
    data.addRow(List.of("A7", "B3", "C1", "1"));
    data.addRow(List.of("A6", "B2", "C2", "6"));
    data.addRow(List.of("A8", "B4", "C2", "9"));
  }

  @Test
  void testGetWidthHeight() {
    assertEquals(4, data.getWidth());
    assertEquals(4, data.getHeaderWidth());
    assertEquals(8, data.getHeight());
  }

  @Test
  void testTruncateDataRows() {
    assertEquals(4, data.getWidth());
    assertEquals(4, data.getHeaderWidth());
    assertEquals(8, data.getHeight());
    assertFalse(data.isTruncated());

    data.truncate(4);

    assertEquals(4, data.getWidth());
    assertEquals(4, data.getHeaderWidth());
    assertEquals(4, data.getHeight());
    assertTrue(data.isTruncated());
  }

  @Test
  void testGetRow() {
    List<String> row1 = data.getRow(0);
    List<String> row2 = data.getRow(1);
    List<String> row3 = data.getRow(2);
    List<String> row5 = data.getRow(4);

    assertEquals("A1", row1.get(0));
    assertEquals("C1", row1.get(2));
    assertEquals("A2", row2.get(0));
    assertEquals("C2", row2.get(2));
    assertEquals("A4", row3.get(0));
    assertEquals("C2", row3.get(2));
    assertEquals("A5", row5.get(0));
    assertEquals("C1", row5.get(2));
  }

  @Test
  void testDataWithNames() {
    
  }

  @Test
  void testSortData() {
    List<String> row1 = data.getRow(0);
    List<String> row3 = data.getRow(2);
    List<String> row5 = data.getRow(4);

    assertEquals("A1", row1.get(0));
    assertEquals("C1", row1.get(2));
    assertEquals("A4", row3.get(0));
    assertEquals("C2", row3.get(2));
    assertEquals("A5", row5.get(0));
    assertEquals("C1", row5.get(2));
    
    data.sortRows();

    row1 = data.getRow(0);
    row3 = data.getRow(2);
    row5 = data.getRow(4);

    assertEquals("A1", row1.get(0));
    assertEquals("C1", row1.get(2));
    assertEquals("A3", row3.get(0));
    assertEquals("C1", row3.get(2));
    assertEquals("A5", row5.get(0));
    assertEquals("C1", row5.get(2));
  }
}
