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

import static org.hisp.dhis.util.DateTimeUtils.getDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.hisp.dhis.model.trackedentity.TrackedEntitiesResult;
import org.hisp.dhis.model.trackedentity.TrackedEntity;
import org.hisp.dhis.query.Filter;
import org.hisp.dhis.query.Operator;
import org.hisp.dhis.query.event.OrgUnitSelectionMode;
import org.hisp.dhis.query.trackedentity.TrackedEntityQuery;
import org.hisp.dhis.response.Dhis2ClientException;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class TrackedEntitiesApiTest {
  private static final String OU_A = "DiszpKrYNg8";
  private static final String PR_A = "IpHINAT79UW";
  private static final String TET_A = "nEenWmSyUEp";

  @Test
  void testGetTrackedEntities() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    TrackedEntitiesResult result =
        dhis2.getTrackedEntities(
            TrackedEntityQuery.instance()
                .setOrgUnits(List.of(OU_A))
                .setOrgUnitMode(OrgUnitSelectionMode.SELECTED)
                .setUpdatedAfter(getDate(2014, 2, 1))
                .setProgram(PR_A));

    assertNotNull(result);

    List<TrackedEntity> trackedEntities = result.getTrackedEntities();

    assertNotNull(trackedEntities);
    assertFalse(trackedEntities.isEmpty());

    TrackedEntity trackedEntity = trackedEntities.get(0);

    assertNotNull(trackedEntity);
    assertNotNull(trackedEntity.getTrackedEntity());
    assertEquals(OU_A, trackedEntity.getOrgUnit());
  }

  @Test
  void testGetTrackedEntitiesWithFilter() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    TrackedEntityQuery query =
        TrackedEntityQuery.instance()
            .setFilters(List.of(new Filter("zDhUuAYrxNC", Operator.EQ, "Enemor HD")))
            .setOrgUnits(List.of(OU_A))
            .setOrgUnitMode(OrgUnitSelectionMode.SELECTED)
            .setProgram(PR_A);

    TrackedEntitiesResult result = dhis2.getTrackedEntities(query);

    assertNotNull(result);

    List<TrackedEntity> trackedEntities = result.getTrackedEntities();

    assertNotNull(trackedEntities);
  }

  @Test
  void testGetTrackedEntitiesWithTrackedEntityType() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    TrackedEntityQuery query =
        TrackedEntityQuery.instance()
            .setOrgUnits(List.of(OU_A))
            .setOrgUnitMode(OrgUnitSelectionMode.SELECTED)
            .setTrackedEntityType(TET_A);

    TrackedEntitiesResult result = dhis2.getTrackedEntities(query);

    assertNotNull(result);

    List<TrackedEntity> trackedEntities = result.getTrackedEntities();

    assertNotNull(trackedEntities);
    assertFalse(trackedEntities.isEmpty());
  }

  @Test
  void testGetTrackedEntityInvalidQuery() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    TrackedEntityQuery query =
        TrackedEntityQuery.instance()
            .setFilters(List.of(new Filter("zDhUuAYrxNC", Operator.EQ, "Enemor HD")))
            .setOrgUnits(List.of(OU_A))
            .setOrgUnitMode(OrgUnitSelectionMode.SELECTED);

    Dhis2ClientException ex =
        assertThrows(Dhis2ClientException.class, () -> dhis2.getTrackedEntities(query));

    assertEquals(400, ex.getStatusCode());
    assertEquals("E1003", ex.getErrorCode());
  }
}
