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
package org.hisp.dhis.util;

import static org.hisp.dhis.support.Assertions.assertSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;
import org.hisp.dhis.model.AggregationType;
import org.hisp.dhis.model.DataDomain;
import org.hisp.dhis.model.DataElement;
import org.hisp.dhis.model.Option;
import org.hisp.dhis.model.Product;
import org.hisp.dhis.model.ValueType;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.UNIT)
class JacksonUtilsTest {
  @Test
  void testToJsonString() {
    String expected =
        """
        {"id":"YDb6ff4R3a8","code":"NEG","name":"Negative","attributeValues":[],"translations":[]}""";

    Option option = new Option();
    option.setId("YDb6ff4R3a8");
    option.setCode("NEG");
    option.setName("Negative");

    String actual = JacksonUtils.toJsonString(option);

    assertEquals(expected, actual);
  }

  @Test
  void testToFormattedJsonString() {
    String expected =
        """
        {
          "id" : "YDb6ff4R3a8",
          "code" : "NEG",
          "name" : "Negative",
          "attributeValues" : [ ],
          "translations" : [ ]
        }""";

    Option option = new Option();
    option.setId("YDb6ff4R3a8");
    option.setCode("NEG");
    option.setName("Negative");

    String actual = JacksonUtils.toFormattedJsonString(option);

    assertEquals(expected, actual);
  }

  @Test
  void testDataElementToJsonString() {
    DataElement dataElement = getDataElementA();

    String actual = JacksonUtils.toJsonString(dataElement);

    String expected =
        """
        {\
        "id":"fbfJHSPpUQD",\
        "code":"DE_359596",\
        "name":"ANC 1st visit",\
        "created":"2024-01-15T14:32:12.732",\
        "lastUpdated":"2024-01-15T14:32:12.732",\
        "attributeValues":[],\
        "translations":[],\
        "shortName":"ANC",\
        "aggregationType":"SUM",\
        "valueType":"NUMBER",\
        "domainType":"AGGREGATE",\
        "legendSets":[],\
        "dataElementGroups":[],\
        "dataSetElements":[]}""";

    assertEquals(expected, actual);
  }

  @Test
  void testDataElementFromJsonHms() throws IOException {
    String content =
        """
        {
          "id": "cYeuwXTCPkU",
          "code": "DE_359597",
          "name": "ANC 2nd visit",
          "shortName": "ANC 2",
          "created": "2024-01-15T14:32:12",
          "lastUpdated": "2024-01-15T14:32:12",
          "aggregationType": "SUM",
          "valueType": "NUMBER",
          "domainType": "AGGREGATE"
        }
        """;

    DataElement object = JacksonUtils.fromJson(content, DataElement.class);

    assertEquals("cYeuwXTCPkU", object.getId());
    assertEquals("ANC 2nd visit", object.getName());
    assertNotNull(object.getCreated());
    assertNotNull(object.getLastUpdated());
  }

  @Test
  void testProductFromJsonTimestampMillis() {
    String content =
        """
        {
          "id": "Nwc5JM9ma9X",
          "created": "2024-01-15T14:32:12.759"
        }
        """;

    Product product = JacksonUtils.fromJson(content, Product.class);

    assertEquals("Nwc5JM9ma9X", product.getId());
    assertNotNull(product.getCreated());

    LocalDate created = DateTimeUtils.toLocalDate(product.getCreated());
    assertEquals(2024, created.getYear());
    assertEquals(Month.JANUARY, created.getMonth());
    assertEquals(15, created.getDayOfMonth());
  }

  @Test
  void testProductFromJsonTimestamp() {
    String content =
        """
        {
          "id": "HwTY8RtDPQh",
          "created": "2022-10-03T12:35:42"
        }
        """;

    Product product = JacksonUtils.fromJson(content, Product.class);

    assertEquals("HwTY8RtDPQh", product.getId());
    assertNotNull(product.getCreated());

    LocalDate created = DateTimeUtils.toLocalDate(product.getCreated());
    assertEquals(2022, created.getYear());
    assertEquals(Month.OCTOBER, created.getMonth());
    assertEquals(3, created.getDayOfMonth());
  }

  @Test
  void testProductFromJsonDate() {
    String content =
        """
        {
          "id": "LnDRgi5hnkF",
          "created": "2025-03-20"
        }
        """;

    Product product = JacksonUtils.fromJson(content, Product.class);

    assertEquals("LnDRgi5hnkF", product.getId());
    assertNotNull(product.getCreated());

    LocalDate created = DateTimeUtils.toLocalDate(product.getCreated());
    assertEquals(2025, created.getYear());
    assertEquals(Month.MARCH, created.getMonth());
    assertEquals(20, created.getDayOfMonth());
  }

  @Test
  void testFromJsonToStringList() {
    String content =
        """
        ["s46m5MS0hxu", "YtbsuPPo010", "l6byfWFUGaP"]
        """;

    List<String> list = JacksonUtils.fromJsonToList(content);

    List<String> expected = List.of("s46m5MS0hxu", "YtbsuPPo010", "l6byfWFUGaP");

    assertSize(3, list);
    assertEquals(expected, list);
  }

  @Test
  void testFromJsonToIntegerList() {
    String content =
        """
        [1, 2, 3, 4, 5]
        """;

    List<Integer> list = JacksonUtils.fromJsonToList(content);

    List<Integer> expected = List.of(1, 2, 3, 4, 5);

    assertSize(5, list);
    assertEquals(expected, list);
  }

  /**
   * Returns a {@link DataElement}.
   *
   * @return a {@link DataElement}.
   */
  private DataElement getDataElementA() {
    Date date = DateTimeUtils.toDateTime("2024-01-15T14:32:12.732");
    DataElement dataElement = new DataElement();
    dataElement.setId("fbfJHSPpUQD");
    dataElement.setCode("DE_359596");
    dataElement.setName("ANC 1st visit");
    dataElement.setShortName("ANC");
    dataElement.setCreated(date);
    dataElement.setLastUpdated(date);
    dataElement.setAggregationType(AggregationType.SUM);
    dataElement.setValueType(ValueType.NUMBER);
    dataElement.setDomainType(DataDomain.AGGREGATE);
    return dataElement;
  }
}
