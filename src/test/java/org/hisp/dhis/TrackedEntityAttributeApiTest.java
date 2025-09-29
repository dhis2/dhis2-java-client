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

import static org.hisp.dhis.support.Assertions.assertNotEmpty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import org.hisp.dhis.model.AggregationType;
import org.hisp.dhis.model.ValueType;
import org.hisp.dhis.model.metadata.Metadata;
import org.hisp.dhis.model.trackedentity.TrackedEntityAttribute;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.response.HttpStatus;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.support.TestTags;
import org.hisp.dhis.util.UidUtils;
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
  void testCreateUpdateDeleteTrackedEntityAttributes() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    String uidA = UidUtils.generateUid();
    String uidB = UidUtils.generateUid();

    TrackedEntityAttribute tea = new TrackedEntityAttribute();
    tea.setName(uidA);
    tea.setDescription(uidA);
    tea.setCode(uidA);
    tea.setShortName(uidA);
    tea.setValueType(ValueType.TEXT);
    tea.setAggregationType(AggregationType.COUNT);

    // Create

    ObjectResponse createResp = dhis2.saveTrackedEntityAttribute(tea);

    assertEquals(201, createResp.getHttpStatusCode().intValue(), createResp.toString());
    assertEquals(HttpStatus.CREATED, createResp.getHttpStatus(), createResp.toString());
    assertEquals(Status.OK, createResp.getStatus(), createResp.toString());
    assertNotNull(createResp.getResponse());
    assertNotNull(createResp.getResponse().getUid());

    String teaId = createResp.getResponse().getUid();

    // Get

    tea = dhis2.getTrackedEntityAttribute(teaId);

    assertNotNull(tea);
    assertEquals(teaId, tea.getId());

    tea.setName(uidB);

    // Update

    ObjectResponse updateResp = dhis2.updateTrackedEntityAttribute(tea);

    assertEquals(200, updateResp.getHttpStatusCode().intValue(), updateResp.toString());
    assertEquals(HttpStatus.OK, updateResp.getHttpStatus(), updateResp.toString());
    assertEquals(Status.OK, updateResp.getStatus(), updateResp.toString());
    assertEquals(teaId, updateResp.getResponse().getUid(), updateResp.toString());

    // Get updated

    tea = dhis2.getTrackedEntityAttribute(teaId);
    assertNotNull(tea);
    assertEquals(teaId, tea.getId());
    assertEquals(uidB, tea.getName());
    assertNotNull(tea.getDescription());

    // Remove

    ObjectResponse removeResp = dhis2.removeTrackedEntityAttribute(teaId);

    assertEquals(200, removeResp.getHttpStatusCode().intValue(), removeResp.toString());
    assertEquals(HttpStatus.OK, removeResp.getHttpStatus(), removeResp.toString());
    assertEquals(Status.OK, removeResp.getStatus(), removeResp.toString());
    assertNotNull(removeResp.getResponse());
    assertNotNull(removeResp.getResponse().getUid());
  }

  @Test
  void testGetTrackedEntityAttributesPaged() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    Metadata<TrackedEntityAttribute> metadata =
        dhis2.getTrackedEntityAttributesPaged(Query.instance());

    assertNotNull(metadata);
    assertNotNull(metadata.getPager());
    assertEquals(1, metadata.getPager().getPage());
    assertNotEmpty(metadata.getObjects());
  }
}
