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

import static org.hisp.dhis.support.Assertions.assertNotEmpty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import org.hisp.dhis.model.Container;
import org.hisp.dhis.model.Product;
import org.junit.jupiter.api.Test;

class JacksonYamUtilsTest {
  @Test
  void testToYamlObject() {
    String expected =
        """
        id: "YDb6ff4R3a8"
        name: "ThinkPadT14s"
        """;

    Product object = new Product();
    object.setId("YDb6ff4R3a8");
    object.setName("ThinkPadT14s");

    String actual = JacksonYamUtils.toYamlString(object);

    assertEquals(expected, actual);
  }

  @Test
  void testToYamlObjects() {
    String expected =
        """
        products:
        - id: "YDb6ff4R3a8"
          name: "ThinkPad T14s"
        - id: "p84TSR7yXnc"
          name: "Dell XPS 13"
        """;

    Product pA = new Product();
    pA.setId("YDb6ff4R3a8");
    pA.setName("ThinkPad T14s");

    Product pB = new Product();
    pB.setId("p84TSR7yXnc");
    pB.setName("Dell XPS 13");

    Container products = new Container(List.of(pA, pB));

    String actual = JacksonYamUtils.toYamlString(products);

    assertEquals(expected, actual);
  }

  @Test
  void testFromYaml() {
    String yaml =
        """
        products:
        - id: "YDb6ff4R3a8"
          name: "ThinkPad T14s"
        - id: "p84TSR7yXnc"
          name: "Dell XPS 13"
        """;

    Container container = JacksonYamUtils.fromYaml(yaml, Container.class);

    assertNotNull(container);
    assertNotEmpty(container.getProducts());
  }
}
