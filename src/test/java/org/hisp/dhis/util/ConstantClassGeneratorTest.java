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
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.hisp.dhis.model.DataElement;
import org.junit.jupiter.api.Test;

class ConstantClassGeneratorTest {
  @Test
  void testToConstantClass() {
    List<DataElement> objects =
        List.of(
            setIdObject(new DataElement(), "MdUnibraB1i", "TEMPERATURE", "Temperature"),
            setIdObject(new DataElement(), "BJuEIzwRi0o", "ELEVATION", "Elevation"),
            setIdObject(new DataElement(), "RMGGvQFLsF4", "PRECIPITATION", "Precipitation"));

    String actual =
        ConstantClassGenerator.toConstantClass("DataElement", "org.hisp.dhis2.common", objects);

    String expected =
        """
        package org.hisp.dhis2.common;

        /**
         * DataElement constants.
         */
        public class Dhis2DataElement {
          public static final String TEMPERATURE = "MdUnibraB1i";
          public static final String ELEVATION = "BJuEIzwRi0o";
          public static final String PRECIPITATION = "RMGGvQFLsF4";
        }
        """;

    assertEquals(expected, actual);
  }
}
