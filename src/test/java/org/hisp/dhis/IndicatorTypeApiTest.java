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
import static org.hisp.dhis.support.Assertions.assertNotEmpty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.hisp.dhis.model.IndicatorType;
import org.hisp.dhis.query.Filter;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.response.HttpStatus;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.support.TestTags;
import org.hisp.dhis.util.UidUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class IndicatorTypeApiTest {
  @Test
  void testGetIndicatorType() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    IndicatorType indicatorType = dhis2.getIndicatorType("JkWynlWMjJR");
    assertNotNull(indicatorType);
    assertEquals("JkWynlWMjJR", indicatorType.getId());
    assertEquals("Number (Factor 1)", indicatorType.getName());
    assertEquals(1, indicatorType.getFactor());
    assertTrue(indicatorType.getNumber());
    assertNotNull(indicatorType.getCreated());
    assertNotNull(indicatorType.getLastUpdated());
  }

  @Test
  void testGetIndicatorTypesWithInFilter() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    Query query =
        Query.instance().addFilter(Filter.in("id", List.of("bWuNrMHEoZ0", "vQ0dGV9EDrw")));

    List<IndicatorType> indicatorTypes = dhis2.getIndicatorTypes(query);

    assertNotEmpty(indicatorTypes);
    assertEquals(2, indicatorTypes.size());
  }

  @Test
  void testCreateUpdateAndDeleteIndicatorType() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);
    String uidA = UidUtils.generateUid();
    String uidB = UidUtils.generateUid();

    IndicatorType indicatorType = new IndicatorType();
    indicatorType.setName(uidA);
    indicatorType.setCode(uidA);
    indicatorType.setNumber(true);
    indicatorType.setFactor(2);

    // Create
    ObjectResponse createRespA = dhis2.saveIndicatorType(indicatorType);

    assertSuccessResponse(createRespA, HttpStatus.CREATED, 201);

    String indicatorTypeUid = createRespA.getResponse().getUid();

    // Get
    indicatorType = dhis2.getIndicatorType(indicatorTypeUid);

    assertNotNull(indicatorType);
    assertEquals(indicatorTypeUid, indicatorType.getId());
    assertEquals(uidA, indicatorType.getName());
    assertEquals(uidA, indicatorType.getCode());
    assertEquals(2, indicatorType.getFactor());
    assertTrue(indicatorType.getNumber());
    assertNotNull(indicatorType.getCreated());
    assertNotNull(indicatorType.getLastUpdated());

    String updatedName = uidB;
    indicatorType.setName(updatedName);

    // Update
    ObjectResponse updateRespA = dhis2.updateIndicatorType(indicatorType);

    assertSuccessResponse(updateRespA, HttpStatus.OK, 200);
    assertEquals(indicatorTypeUid, updateRespA.getResponse().getUid(), updateRespA.toString());

    // Get Updated
    indicatorType = dhis2.getIndicatorType(indicatorTypeUid);

    assertNotNull(indicatorType);
    assertEquals(indicatorTypeUid, indicatorType.getId());
    assertEquals(updatedName, indicatorType.getName());

    // Remove
    ObjectResponse removeRespA = dhis2.removeIndicatorType(indicatorTypeUid);

    assertSuccessResponse(removeRespA, HttpStatus.OK, 200);
  }
}
