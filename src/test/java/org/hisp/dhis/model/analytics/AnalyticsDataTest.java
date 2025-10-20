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
            new AnalyticsHeader("C1", "C1", ValueType.TEXT),
            new AnalyticsHeader("C2", "C2", ValueType.TEXT),
            new AnalyticsHeader("C3", "C3", ValueType.TEXT)));
    data.addRow(List.of("1A", "1B", "1C"));
    data.addRow(List.of("3A", "3B", "3C"));
    data.addRow(List.of("2A", "2B", "2C"));
    data.addRow(List.of("5A", "5B", "5C"));
    data.addRow(List.of("4A", "4B", "4C"));
  }

  @Test
  void testGetWidthHeight() {
    assertEquals(3, data.getWidth());
    assertEquals(3, data.getHeaderWidth());
    assertEquals(5, data.getHeight());
  }

  @Test
  void testTruncateDataRows() {
    assertEquals(3, data.getWidth());
    assertEquals(3, data.getHeaderWidth());
    assertEquals(5, data.getHeight());
    assertFalse(data.isTruncated());

    data.truncate(2);

    assertEquals(3, data.getWidth());
    assertEquals(3, data.getHeaderWidth());
    assertEquals(2, data.getHeight());
    assertTrue(data.isTruncated());
  }

  @Test
  void testGetRow() {
    List<String> row1 = data.getRow(0);
    List<String> row3 = data.getRow(2);
    List<String> row5 = data.getRow(4);

    assertEquals("1A", row1.get(0));
    assertEquals("1C", row1.get(2));
    assertEquals("2A", row3.get(0));
    assertEquals("2C", row3.get(2));
    assertEquals("4A", row5.get(0));
    assertEquals("4C", row5.get(2));
  }

  @Test
  void testSortData() {
    List<String> row1 = data.getRow(0);
    List<String> row3 = data.getRow(2);
    List<String> row5 = data.getRow(4);

    assertEquals("1A", row1.get(0));
    assertEquals("1C", row1.get(2));
    assertEquals("2A", row3.get(0));
    assertEquals("2C", row3.get(2));
    assertEquals("4A", row5.get(0));
    assertEquals("4C", row5.get(2));

    data.sortRows();

    row1 = data.getRow(0);
    row3 = data.getRow(2);
    row5 = data.getRow(4);

    assertEquals("1A", row1.get(0));
    assertEquals("1C", row1.get(2));
    assertEquals("3A", row3.get(0));
    assertEquals("3C", row3.get(2));
    assertEquals("5A", row5.get(0));
    assertEquals("5C", row5.get(2));
  }
}
