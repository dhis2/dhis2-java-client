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

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.hisp.dhis.model.Container;
import org.hisp.dhis.model.Product;
import org.junit.jupiter.api.Test;

class JacksonXmlUtilsTest {
  @Test
  void testToXmlStringObject() {
    String expected =
        """
        <product><id>YDb6ff4R3a8</id><name>ThinkPadT14s</name></product>\
        """;

    Product product = new Product();
    product.setId("YDb6ff4R3a8");
    product.setName("ThinkPadT14s");

    String actual = JacksonXmlUtils.toXmlString(product);

    assertEquals(expected, actual);
  }

  @Test
  void testToXmlStringObjectList() {
    String expected =
        """
        <container>\
        <products>\
        <product>\
        <id>YDb6ff4R3a8</id><name>ThinkPad T14s</name>\
        </product>\
        <product>\
        <id>p84TSR7yXnc</id><name>Dell XPS 13</name>\
        </product>\
        </products>\
        </container>\
        """;

    Product pA = new Product();
    pA.setId("YDb6ff4R3a8");
    pA.setName("ThinkPad T14s");

    Product pB = new Product();
    pB.setId("p84TSR7yXnc");
    pB.setName("Dell XPS 13");

    Container products = new Container(List.of(pA, pB));

    String actual = JacksonXmlUtils.toXmlString(products);

    assertEquals(expected, actual);
  }

  @Test
  void testGetRootElementNameSimple() {
    String stringA =
        """
        <product>
          <id>YDb6ff4R3a8</id>
          <name>ThinkPad T14s</name>
        </product>""";
    String stringB =
        """
        <DataElements>
          <DataElement>
            <Id>HftXwWArGVX</Id>
          </DataElement>
        </DataElements>""";
    String stringC =
        """
        <Metadata>
          <DataElements>
            <DataElement>
              <Id>HftXwWArGVX</Id>
            </DataElement>
          </DataElements>
        </Metadata>""";
    String stringD =
        """
        <EVS.R01>
          <HDR>
            <HDR.control_id V='1101'/>
            <HDR.version_id V='POCT1'/>
          </HDR>
        </EVS.R01>""";
    String stringE =
        """
        <OBS.R01>
          <HDR>
            <HDR.control_id V='1138' />
            <HDR.version_id V='POCT1' />
          </HDR>
        </OBS.R01>""";

    assertEquals("product", JacksonXmlUtils.getRootElementName(stringA));
    assertEquals("DataElements", JacksonXmlUtils.getRootElementName(stringB));
    assertEquals("Metadata", JacksonXmlUtils.getRootElementName(stringC));
    assertEquals("EVS.R01", JacksonXmlUtils.getRootElementName(stringD));
    assertEquals("OBS.R01", JacksonXmlUtils.getRootElementName(stringE));
  }

  @Test
  void testGetRootElementNameWithXmlDeclaration() {
    String string =
        """
        <?xml version='1.0' encoding='utf-8'?>
        <OBS.R01>
          <HDR>
            <HDR.control_id V='1138' />
          </HDR>
        </OBS.R01>""";

    assertEquals("OBS.R01", JacksonXmlUtils.getRootElementName(string));
  }

  @Test
  void testGetRootElementNameWithXmlAndDocumentTypeDeclaration() {
    String string =
        """
        <?xml version='1.0' encoding='utf-8'?>
        <!DOCTYPE EVS.R01 SYSTEM 'EVS.R01.dtd'>
        <EVS.R01>
          <HDR>
            <HDR.control_id V='1101'/>
            <HDR.version_id V='POCT1'/>
          </HDR>
        </EVS.R01>""";

    assertEquals("EVS.R01", JacksonXmlUtils.getRootElementName(string));
  }
}
