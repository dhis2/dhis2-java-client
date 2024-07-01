/*
 * Copyright (c) 2004-2024, University of Oslo
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
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.UNIT)
class IdSchemeTest {
  @Test
  void testGetAttributeIdScheme() {
    IdScheme attribute = IdScheme.createAttributeIdScheme("JYHR54Rfr41");

    assertEquals(IdScheme.ObjectProperty.ATTRIBUTE, attribute.getObjectProperty());
    assertEquals("JYHR54Rfr41", attribute.getAttribute());
  }

  @Test
  void testGetIdSchemeFromString() {
    IdScheme uidScheme = IdScheme.createIdScheme("uid");
    assertEquals(IdScheme.ObjectProperty.UID, uidScheme.getObjectProperty());

    IdScheme codeScheme = IdScheme.createIdScheme("code");
    assertEquals(IdScheme.ObjectProperty.CODE, codeScheme.getObjectProperty());

    IdScheme codeUpperCaseScheme = IdScheme.createIdScheme("CODE");
    assertEquals(IdScheme.ObjectProperty.CODE, codeUpperCaseScheme.getObjectProperty());
  }

  @Test
  void testGetIdSchemeFromConstructor() {
    IdScheme uidScheme = new IdScheme("uid");
    assertEquals(IdScheme.ObjectProperty.UID, uidScheme.getObjectProperty());

    IdScheme codeScheme = new IdScheme("code");
    assertEquals(IdScheme.ObjectProperty.CODE, codeScheme.getObjectProperty());

    IdScheme codeUpperCaseScheme = new IdScheme("CODE");
    assertEquals(IdScheme.ObjectProperty.CODE, codeUpperCaseScheme.getObjectProperty());
  }

  @Test
  void testGetAttributeIdSchemeFromString() {
    IdScheme attributeAScheme = IdScheme.createIdScheme("attribute:HGT65Gdgq2k");
    IdScheme attributeBScheme = IdScheme.createIdScheme("attribute:LUJJhf2jf21");
    IdScheme attributeCScheme = IdScheme.createIdScheme(null);

    assertEquals(IdScheme.ObjectProperty.ATTRIBUTE, attributeAScheme.getObjectProperty());
    assertEquals("HGT65Gdgq2k", attributeAScheme.getAttribute());
    assertEquals(IdScheme.ObjectProperty.ATTRIBUTE, attributeBScheme.getObjectProperty());
    assertEquals("LUJJhf2jf21", attributeBScheme.getAttribute());
    assertNull(attributeCScheme);
  }

  @Test
  void testGetName() {
    IdScheme idSchemeA = IdScheme.createIdScheme("attribute:HGT65Gdgq2k");
    IdScheme idSchemeB = IdScheme.createIdScheme("code");
    IdScheme idSchemeC = IdScheme.createIdScheme("CODE");

    assertEquals("attribute:HGT65Gdgq2k", idSchemeA.name());
    assertEquals("code", idSchemeB.name());
    assertEquals("code", idSchemeC.name());
  }

  @Test
  void testToString() {
    IdScheme idSchemeA = IdScheme.createIdScheme("attribute:HGT65Gdgq2k");
    IdScheme idSchemeB = IdScheme.createIdScheme("code");
    IdScheme idSchemeC = IdScheme.createIdScheme("CODE");

    assertEquals("attribute:HGT65Gdgq2k", idSchemeA.toString());
    assertEquals("code", idSchemeB.toString());
    assertEquals("code", idSchemeC.toString());
  }

  @Test
  void testGetNullIdScheme() {
    assertNull(IdScheme.createIdScheme(null));
  }

  @Test
  void testGetInvalidAttributeIdSchemeA() {
    assertThrows(IllegalArgumentException.class, () -> IdScheme.createIdScheme("FORM_NAME"));
  }

  @Test
  void testGetInvalidAttributeIdSchemeB() {
    assertThrows(
        IllegalArgumentException.class, () -> IdScheme.createIdScheme("attribute:invalid_uid"));
  }

  @Test
  void testNullConstructor() {
    assertThrows(NullPointerException.class, () -> new IdScheme(null));
  }
}
