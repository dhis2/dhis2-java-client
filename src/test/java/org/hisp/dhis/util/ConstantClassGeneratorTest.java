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

import static org.hisp.dhis.support.TestObjects.setIdObject;
import static org.hisp.dhis.util.ConstantClassGenerator.toConstantClass;
import static org.hisp.dhis.util.ConstantClassGenerator.toJavaVariable;
import static org.hisp.dhis.util.ConstantClassGenerator.toNameValueEnum;
import static org.hisp.dhis.util.ConstantClassGenerator.toValueEnum;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.hisp.dhis.model.DataElement;
import org.hisp.dhis.model.Option;
import org.junit.jupiter.api.Test;

class ConstantClassGeneratorTest {
  @Test
  void testToConstantClass() {
    List<DataElement> objects =
        List.of(
            setIdObject(new DataElement(), "MdUnibraB1i", "TEMPERATURE", "Temperature"),
            setIdObject(new DataElement(), "BJuEIzwRi0o", "ELEVATION", "Elevation"),
            setIdObject(new DataElement(), "RMGGvQFLsF4", "PRECIPITATION", "Precipitation"));

    String actual = toConstantClass("DataElement", "org.hisp.dhis2.common", objects);

    String expected =
        """
        package org.hisp.dhis2.common;

        /** DataElement constants. */
        public class Dhis2DataElement {
          public static final String TEMPERATURE = "MdUnibraB1i";
          public static final String ELEVATION = "BJuEIzwRi0o";
          public static final String PRECIPITATION = "RMGGvQFLsF4";
        }
        """;

    assertEquals(expected, actual);
  }

  @Test
  void testToValueEnum() {
    List<Option> objects =
        List.of(
            setIdObject(new Option(), "frDDBbpwjgf", "OK", "200"),
            setIdObject(new Option(), "DEwVRyKpmog", "CREATED", "201"),
            setIdObject(new Option(), "YscMPNMuZ12", "MOVED_PERMANENTLY", "301"),
            setIdObject(new Option(), "hZpftMUf3GG", "FOUND", "302"),
            setIdObject(new Option(), "sBYU4b8XqFc", "TEMPORARY_REDIRECT", "307"),
            setIdObject(new Option(), "Pxu57c55bLE", "CONFLICT", "409"));

    String actual = toValueEnum("HttpStatus", "org.hisp.dhis2.common", objects);

    String expected =
        """
        package org.hisp.dhis2.common;

        /** HttpStatus enumeration. */
        public enum Dhis2HttpStatus {
          OK("200"),
          CREATED("201"),
          MOVED_PERMANENTLY("301"),
          FOUND("302"),
          TEMPORARY_REDIRECT("307"),
          CONFLICT("409");

          private final String value;

          Dhis2HttpStatus(String value) {
            this.value = value;
          }

          public String getValue() {
            return value;
          }
        }
        """;

    assertEquals(expected, actual);
  }

  @Test
  void testToNameValueEnum() {
    List<Option> objects =
        List.of(
            setIdObject(new Option(), "frDDBbpwjgf", "OK", "200"),
            setIdObject(new Option(), "DEwVRyKpmog", "CREATED", "201"),
            setIdObject(new Option(), "YscMPNMuZ12", "MOVED-PERMANENTLY", "301"),
            setIdObject(new Option(), "hZpftMUf3GG", "FOUND", "302"),
            setIdObject(new Option(), "sBYU4b8XqFc", "TEMPORARY-REDIRECT", "307"),
            setIdObject(new Option(), "Pxu57c55bLE", "CONFLICT", "409"));

    String actual = toNameValueEnum("HttpStatus", "org.hisp.dhis2.common", objects);

    String expected =
        """
        package org.hisp.dhis2.common;

        /** HttpStatus enumeration. */
        public enum Dhis2HttpStatus {
          OK("OK", "200"),
          CREATED("CREATED", "201"),
          MOVED_PERMANENTLY("MOVED-PERMANENTLY", "301"),
          FOUND("FOUND", "302"),
          TEMPORARY_REDIRECT("TEMPORARY-REDIRECT", "307"),
          CONFLICT("CONFLICT", "409");

          private final String name;

          private final String value;

          Dhis2HttpStatus(String name, String value) {
            this.name = name;
            this.value = value;
          }

          public String getName() {
            return name;
          }

          public String getValue() {
            return value;
          }
        }
        """;

    assertEquals(expected, actual);
  }

  @Test
  void testToJavaVariable() {
    assertEquals("MOVED_PERMANENTLY", toJavaVariable("MOVED PERMANENTLY"));
    assertEquals("MOVED_PERMANENTLY", toJavaVariable("MOVED-PERMANENTLY"));
    assertEquals("MOVED_PERMANENTLY", toJavaVariable("MO(V)ED-#PERM/ANENTLY"));
    assertEquals("MOVED_PERMANENTLY", toJavaVariable("MOV&ED #PERM/ANENTLY"));
  }
}
