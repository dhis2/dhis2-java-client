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
import static org.hisp.dhis.support.Assertions.assertSuccessResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import org.hisp.dhis.model.IndicatorGroup;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.response.HttpStatus;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.support.TestTags;
import org.hisp.dhis.util.UidUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class IndicatorGroupApiTest {
  @Test
  void testGetIndicatorGroup() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    IndicatorGroup group = dhis2.getIndicatorGroup("pKHOV0uwPJk");

    assertNotNull(group);
    assertEquals("pKHOV0uwPJk", group.getId());
    assertNotBlank(group.getName());
    assertNotNull(group.getCreated());
    assertNotNull(group.getLastUpdated());
    assertNotNull(group.getSharing());
    assertNotNull(group.getAccess());
    assertNotEmpty(group.getIndicators());
  }

  @Test
  void testIndicatorGroups() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    List<IndicatorGroup> groups = dhis2.getIndicatorGroups(Query.instance());

    assertNotNull(groups);
    assertNotEmpty(groups);
    assertNotNull(groups.get(0).getId());
  }

  @Test
  void testCreateUpdateAndDeleteIndicatorGroup() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);
    String uidA = UidUtils.generateUid();
    String uidB = UidUtils.generateUid();

    IndicatorGroup indicatorGroup = new IndicatorGroup();
    indicatorGroup.setName(uidA);
    indicatorGroup.setCode(uidA);
    indicatorGroup.setDescription(uidA);

    // Create
    ObjectResponse createRespA = dhis2.saveIndicatorGroup(indicatorGroup);

    assertSuccessResponse(createRespA, HttpStatus.CREATED, 201);

    String indicatorGroupUid = createRespA.getResponse().getUid();

    // Get
    indicatorGroup = dhis2.getIndicatorGroup(indicatorGroupUid);

    assertNotNull(indicatorGroup);
    assertEquals(indicatorGroupUid, indicatorGroup.getId());
    assertEquals(uidA, indicatorGroup.getName());
    assertEquals(uidA, indicatorGroup.getCode());
    assertEquals(uidA, indicatorGroup.getDescription());
    assertNotNull(indicatorGroup.getCreated());
    assertNotNull(indicatorGroup.getLastUpdated());

    indicatorGroup.setName(uidB);

    // Update
    ObjectResponse updateRespA = dhis2.updateIndicatorGroup(indicatorGroup);

    assertSuccessResponse(updateRespA, HttpStatus.OK, 200);
    assertEquals(indicatorGroupUid, updateRespA.getResponse().getUid(), updateRespA.toString());

    // Get Updated
    indicatorGroup = dhis2.getIndicatorGroup(indicatorGroupUid);

    assertNotNull(indicatorGroup);
    assertEquals(indicatorGroupUid, indicatorGroup.getId());
    assertEquals(uidB, indicatorGroup.getName());

    // Remove
    ObjectResponse removeRespA = dhis2.removeIndicatorGroup(indicatorGroupUid);

    assertSuccessResponse(removeRespA, HttpStatus.OK, 200);
  }
}
