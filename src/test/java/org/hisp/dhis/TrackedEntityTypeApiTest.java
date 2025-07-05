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
package org.hisp.dhis;

import static org.hisp.dhis.support.Assertions.assertNotBlank;
import static org.hisp.dhis.support.Assertions.assertNotEmpty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.hisp.dhis.model.trackedentity.TrackedEntityAttribute;
import org.hisp.dhis.model.trackedentity.TrackedEntityType;
import org.hisp.dhis.model.trackedentity.TrackedEntityTypeAttribute;
import org.hisp.dhis.query.Filter;
import org.hisp.dhis.query.Order;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.response.HttpStatus;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.support.TestTags;
import org.hisp.dhis.util.UidUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
public class TrackedEntityTypeApiTest {
  @Test
  void testGetTrackedEntityType() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    TrackedEntityType tet = dhis2.getTrackedEntityType("nEenWmSyUEp");

    assertNotNull(tet);
    assertEquals("nEenWmSyUEp", tet.getId());
    assertNotBlank(tet.getName());
    assertNotNull(tet.getCreated());
    assertNotNull(tet.getLastUpdated());
    assertNotEmpty(tet.getTrackedEntityTypeAttributes());

    List<TrackedEntityTypeAttribute> tetas = tet.getTrackedEntityTypeAttributes();

    TrackedEntityTypeAttribute teta = tetas.get(0);

    assertNotNull(teta);
    assertNotNull(teta.getTrackedEntityAttribute());

    TrackedEntityAttribute tea = teta.getTrackedEntityAttribute();

    assertNotNull(tea.getId());
    assertNotBlank(tea.getName());
    assertNotNull(tea.getValueType());
  }

  @Test
  void testIsTrackedEntityType() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    assertTrue(dhis2.isTrackedEntityType("nEenWmSyUEp"));
    assertFalse(dhis2.isTrackedEntityType("sqXtGfOo3ko"));
  }

  @Test
  void testGetTrackedEntityTypes() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    List<TrackedEntityType> types =
        dhis2.getTrackedEntityTypes(
            Query.instance()
                .addFilter(Filter.in("id", List.of("hyKZglfYp0C", "nEenWmSyUEp", "kIeke8tAQnd")))
                .setOrder(Order.asc("id")));

    assertEquals(3, types.size());
    assertEquals("hyKZglfYp0C", types.get(0).getId());
    assertEquals("kIeke8tAQnd", types.get(1).getId());
    assertEquals("nEenWmSyUEp", types.get(2).getId());
  }

  @Test
  void testSaveRemoveTrackedEntityType() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    String id = UidUtils.generateUid();
    String code = "Code" + id;
    String name = "Name" + id;

    TrackedEntityType tet = new TrackedEntityType();
    tet.setId(id);
    tet.setCode(code);
    tet.setName(name);

    ObjectResponse saveResponse = dhis2.saveTrackedEntityType(tet);

    assertNotNull(saveResponse);
    assertEquals(Status.OK, saveResponse.getStatus());
    assertEquals(HttpStatus.CREATED, saveResponse.getHttpStatus());

    ObjectResponse removeResponse = dhis2.removeTrackedEntityType(id);

    assertNotNull(removeResponse);
    assertEquals(Status.OK, removeResponse.getStatus());
    assertEquals(HttpStatus.OK, removeResponse.getHttpStatus());
  }
}
