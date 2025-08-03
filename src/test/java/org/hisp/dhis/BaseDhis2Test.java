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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.URI;
import org.apache.hc.core5.net.URIBuilder;
import org.hisp.dhis.model.AggregationType;
import org.hisp.dhis.model.DataDomain;
import org.hisp.dhis.model.DataElement;
import org.hisp.dhis.model.ValueType;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.UNIT)
class BaseDhis2Test {
  /**
   * Test deserialize to string. Note that strings are considered valid JSON primitives when quoted.
   */
  @Test
  void testDeserializeString() throws IOException {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    String content =
        """
        "HkSu7IWlvrM"
        """;

    String string = dhis2.readValue(content, String.class);

    assertEquals("HkSu7IWlvrM", string);
  }

  @Test
  void testDeserializeDataElement() throws IOException {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    String content =
        """
        {
          "id": "fbfJHSPpUQD",
          "code": "DE_359596",
          "name": "ANC 1st visit",
          "shortName": "ANC 1",
          "created": "2010-02-05T10:58:43.646",
          "lastUpdated": "2022-03-22T08:59:44.053",
          "aggregationType": "SUM",
          "valueType": "NUMBER",
          "domainType": "AGGREGATE"
        }
        """;

    DataElement object = dhis2.readValue(content, DataElement.class);

    assertEquals("fbfJHSPpUQD", object.getId());
    assertEquals("DE_359596", object.getCode());
    assertEquals("ANC 1st visit", object.getName());
    assertEquals("ANC 1", object.getShortName());
    assertNotNull(object.getCreated());
    assertNotNull(object.getLastUpdated());
    assertEquals(AggregationType.SUM, object.getAggregationType());
    assertEquals(ValueType.NUMBER, object.getValueType());
    assertEquals(DataDomain.AGGREGATE, object.getDomainType());
  }

  @Test
  void testWithMetadataImportQueryParams() throws Exception {
    Dhis2Config config = new Dhis2Config("https://server.org", "admin", "distrct");
    Dhis2 dhis2 = new Dhis2(new Dhis2Config("https://server.org", "admin", "distrct"));
    URIBuilder uriBuilder = config.getResolvedUriBuilder().appendPath("metadata");

    URI expected =
        new URI(
            """
        https://server.org/api/metadata\
        ?importStrategy=CREATE_AND_UPDATE\
        &atomicMode=ALL\
        &skipSharing=false\
        &async=false""");

    assertEquals(expected, dhis2.withMetadataImportQueryParams(uriBuilder));
  }
}
