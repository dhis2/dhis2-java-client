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

import static org.hisp.dhis.ApiTestUtils.assertSuccessResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.hisp.dhis.model.Attribute;
import org.hisp.dhis.model.ValueType;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.response.HttpStatus;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.support.TestTags;
import org.hisp.dhis.util.UidUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class AttributeApiTest {
  @Test
  void testGetAttribute() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    Attribute attribute = dhis2.getAttribute("Z4X3J7jMLYV");

    assertNotNull(attribute);
    assertEquals("Z4X3J7jMLYV", attribute.getId());
    assertEquals("Classification", attribute.getName());
    assertEquals("CLASSIFICATION", attribute.getCode());
  }

  @Test
  void testIsAttribute() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    assertTrue(dhis2.isAttribute("Z4X3J7jMLYV"));
    assertFalse(dhis2.isAttribute("DRhlGBgFBXK"));
  }

  @Test
  void testGetAttributes() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    List<Attribute> attributes = dhis2.getAttributes(Query.instance());

    assertNotNull(attributes);
    assertFalse(attributes.isEmpty());
  }

  @Test
  void testCreateUpdateAndDeleteAttributes() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);
    String uidA = UidUtils.generateUid();
    String uidB = UidUtils.generateUid();

    Attribute attribute = new Attribute();
    attribute.setValueType(ValueType.TEXT);
    attribute.setName(uidA);
    attribute.setCode(uidA);
    attribute.setShortName(uidA);
    attribute.setDescription(uidA);
    attribute.setUnique(false);
    attribute.setProgramAttribute(true);
    attribute.setDataElementAttribute(true);
    attribute.setProgramStageAttribute(true);
    attribute.setMandatory(false);

    // Create
    ObjectResponse createRespA = dhis2.saveAttribute(attribute);

    assertSuccessResponse(createRespA, HttpStatus.CREATED, 201);

    String attributeUid = createRespA.getResponse().getUid();

    // Get
    attribute = dhis2.getAttribute(attributeUid);

    assertNotNull(attribute);
    assertEquals(attributeUid, attribute.getId());
    assertEquals(ValueType.TEXT, attribute.getValueType());
    assertEquals(uidA, attribute.getName());
    assertEquals(uidA, attribute.getCode());
    assertEquals(uidA, attribute.getShortName());
    assertEquals(uidA, attribute.getDescription());
    assertFalse(attribute.getUnique());
    assertFalse(attribute.getMandatory());

    attribute.setName(uidB);

    // Update
    ObjectResponse updateRespA = dhis2.updateAttribute(attribute);

    assertSuccessResponse(updateRespA, HttpStatus.OK, 200);
    assertEquals(attributeUid, updateRespA.getResponse().getUid(), updateRespA.toString());

    // Get Updated
    attribute = dhis2.getAttribute(attributeUid);
    assertNotNull(attribute);
    assertEquals(attributeUid, attribute.getId());
    assertEquals(uidB, attribute.getName());

    // Remove
    ObjectResponse removeRespA = dhis2.removeAttribute(attributeUid);

    assertSuccessResponse(removeRespA, HttpStatus.OK, 200);
  }
}
