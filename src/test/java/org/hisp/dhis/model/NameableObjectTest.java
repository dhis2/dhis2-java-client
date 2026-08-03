/*
 * Copyright (c) 2004-2026, University of Oslo
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
package org.hisp.dhis.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.UNIT)
class NameableObjectTest {
  @Test
  void testGetReferenceValid() {
    DataElement dataElement = new DataElement();
    dataElement.setName("Test result type [KNOWN_VALUES:CONFIRMED,UNCONFIRMED]");

    assertEquals("CONFIRMED,UNCONFIRMED", dataElement.getReference("KNOWN_VALUES"));

    dataElement = new DataElement();
    dataElement.setCode("Test result type [KNOWN_VALUES:MALE,FEMALE]");

    assertEquals("MALE,FEMALE", dataElement.getReference("KNOWN_VALUES"));

    dataElement = new DataElement();
    dataElement.setDescription("Test result type [KNOWN_VALUES:MANUAL,AUTOMATIC]");

    assertEquals("MANUAL,AUTOMATIC", dataElement.getReference("KNOWN_VALUES"));
  }

  @Test
  void testGetReferenceInvalid() {
    DataElement dataElement = new DataElement();

    assertNull(dataElement.getReference("KNOWN_VALUES"));

    dataElement = new DataElement();
    dataElement.setShortName("Test result type [KNOWN_VALUES:CONFIRMED,UNCONFIRMED]");

    assertNull(dataElement.getReference("KNOWN_VALUES"));

    dataElement.setName("Test result type [KNOWN_VALUES:CONFIRMED,UNCONFIRMED]");

    dataElement = new DataElement();
    dataElement.setName("Test result type [VALUES:CONFIRMED,UNCONFIRMED]");

    assertNull(dataElement.getReference("KNOWN_VALUES"));

    dataElement = new DataElement();
    dataElement.setName("Test result type KNOWN_VALUES:CONFIRMED,UNCONFIRMED");

    assertNull(dataElement.getReference("KNOWN_VALUES"));
  }
}
