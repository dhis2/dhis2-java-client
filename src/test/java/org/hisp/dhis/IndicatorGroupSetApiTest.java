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

import static org.hisp.dhis.ApiTestUtils.assertSuccessResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import org.hisp.dhis.model.IndicatorGroupSet;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.response.HttpStatus;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class IndicatorGroupSetApiTest {
  @Test
  void getIndicatorGroupSet() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    IndicatorGroupSet groupSet = dhis2.getIndicatorGroupSet("kO23KcpBwro");

    assertNotNull(groupSet);
    assertEquals("kO23KcpBwro", groupSet.getId());
    assertFalse(groupSet.getIndicatorGroups().isEmpty());
  }

  @Test
  void testGetIndicatorGroupSets() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    List<IndicatorGroupSet> groupSets = dhis2.getIndicatorGroupSets(Query.instance());

    assertNotNull(groupSets);
    assertFalse(groupSets.isEmpty());
    assertNotNull(groupSets.get(0).getId());
  }

  @Test
  void testCreateUpdateAndDeleteIndicator() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);
    IndicatorGroupSet indicatorGroupSet = new IndicatorGroupSet();
    indicatorGroupSet.setName("Sample indicator group set");
    indicatorGroupSet.setCode("IGS_SAMPLE_CODE");
    indicatorGroupSet.setDescription("Sample description");
    indicatorGroupSet.setShortName("Sample short name");
    indicatorGroupSet.setCompulsory(true);
    indicatorGroupSet.setIndicatorGroups(List.of(dhis2.getIndicatorGroup("pKHOV0uwPJk")));

    // Create
    ObjectResponse createRespA = dhis2.saveIndicatorGroupSet(indicatorGroupSet);

    assertSuccessResponse(createRespA, HttpStatus.CREATED, 201);

    String indicatorGroupSetUid = createRespA.getResponse().getUid();

    // Get
    indicatorGroupSet = dhis2.getIndicatorGroupSet(indicatorGroupSetUid);

    assertNotNull(indicatorGroupSet);
    assertEquals(indicatorGroupSetUid, indicatorGroupSet.getId());
    assertEquals("Sample indicator group set", indicatorGroupSet.getName());
    assertEquals("IGS_SAMPLE_CODE", indicatorGroupSet.getCode());
    assertEquals("Sample description", indicatorGroupSet.getDescription());
    assertEquals("Sample short name", indicatorGroupSet.getShortName());
    assertNotNull(indicatorGroupSet.getCreated());
    assertNotNull(indicatorGroupSet.getLastUpdated());
    assertNotNull(indicatorGroupSet.getIndicatorGroups());
    assertFalse(indicatorGroupSet.getIndicatorGroups().isEmpty());

    String updatedName = "Sample indicator group set updated";
    indicatorGroupSet.setName(updatedName);

    // Update
    ObjectResponse updateRespA = dhis2.updateIndicatorGroupSet(indicatorGroupSet);

    assertSuccessResponse(updateRespA, HttpStatus.OK, 200);
    assertEquals(indicatorGroupSetUid, updateRespA.getResponse().getUid(), updateRespA.toString());

    // Get Updated
    indicatorGroupSet = dhis2.getIndicatorGroupSet(indicatorGroupSetUid);

    assertNotNull(indicatorGroupSet);
    assertEquals(indicatorGroupSetUid, indicatorGroupSet.getId());
    assertEquals(updatedName, indicatorGroupSet.getName());

    // Remove
    ObjectResponse removeRespA = dhis2.removeIndicatorGroupSet(indicatorGroupSetUid);

    assertSuccessResponse(removeRespA, HttpStatus.OK, 200);
  }
}
