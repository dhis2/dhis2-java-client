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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import org.hisp.dhis.model.AggregationType;
import org.hisp.dhis.model.ValueType;
import org.hisp.dhis.model.trackedentity.TrackedEntityAttribute;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.response.HttpStatus;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class TrackedEntityAttributeApiTest {

  @Test
  void testGetTrackedEntityAttribute() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    TrackedEntityAttribute trackedEntityAttribute = dhis2.getTrackedEntityAttribute("VqEFza8wbwA");

    assertNotNull(trackedEntityAttribute);
    assertEquals("VqEFza8wbwA", trackedEntityAttribute.getId());
    assertEquals("Address", trackedEntityAttribute.getName());
  }

  @Test
  void testGetTrackedEntityAttributes() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    List<TrackedEntityAttribute> trackedEntityAttributes =
        dhis2.getTrackedEntityAttributes(Query.instance());

    assertNotNull(trackedEntityAttributes);
    assertFalse(trackedEntityAttributes.isEmpty());

    TrackedEntityAttribute trackedEntityAttribute = trackedEntityAttributes.get(0);
    assertEquals("VqEFza8wbwA", trackedEntityAttribute.getId());
    assertEquals("Address", trackedEntityAttribute.getName());
  }

  @Test
  void testCreateUpdateAndDeleteTrackedEntityAttributes() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    TrackedEntityAttribute tea = new TrackedEntityAttribute();
    tea.setName("Sample TEA");
    tea.setDescription("Sample TEA description");
    tea.setCode("TEA_SAMPLE_CODE");
    tea.setShortName("Sample shortName");
    tea.setValueType(ValueType.TEXT);
    tea.setAggregationType(AggregationType.COUNT);

    // Create

    ObjectResponse createRespA = dhis2.saveTrackedEntityAttribute(tea);

    assertEquals(201, createRespA.getHttpStatusCode().intValue(), createRespA.toString());
    assertEquals(HttpStatus.CREATED, createRespA.getHttpStatus(), createRespA.toString());
    assertEquals(Status.OK, createRespA.getStatus(), createRespA.toString());
    assertNotNull(createRespA.getResponse());
    assertNotNull(createRespA.getResponse().getUid());

    String teaUid = createRespA.getResponse().getUid();

    // Get

    tea = dhis2.getTrackedEntityAttribute(teaUid);

    assertNotNull(tea);
    assertEquals(teaUid, tea.getId());

    String updatedName = "Sample TEA updated";
    tea.setName(updatedName);

    // Update

    ObjectResponse updateRespA = dhis2.updateTrackedEntityAttribute(tea);

    assertEquals(200, updateRespA.getHttpStatusCode().intValue(), updateRespA.toString());
    assertEquals(HttpStatus.OK, updateRespA.getHttpStatus(), updateRespA.toString());
    assertEquals(Status.OK, updateRespA.getStatus(), updateRespA.toString());
    assertEquals(teaUid, updateRespA.getResponse().getUid(), updateRespA.toString());

    // Get Updated

    tea = dhis2.getTrackedEntityAttribute(teaUid);
    assertNotNull(tea);
    assertEquals(teaUid, tea.getId());
    assertEquals(updatedName, tea.getName());

    // Remove

    ObjectResponse removeRespA = dhis2.removeTrackedEntityAttribute(teaUid);

    assertEquals(200, removeRespA.getHttpStatusCode().intValue(), removeRespA.toString());
    assertEquals(HttpStatus.OK, removeRespA.getHttpStatus(), removeRespA.toString());
    assertEquals(Status.OK, removeRespA.getStatus(), removeRespA.toString());
    assertNotNull(removeRespA.getResponse());
    assertNotNull(removeRespA.getResponse().getUid());
  }
}
