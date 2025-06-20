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
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import org.hisp.dhis.model.trackedentity.TrackedEntitiesResult;
import org.hisp.dhis.model.trackedentity.TrackedEntity;
import org.hisp.dhis.query.Filter;
import org.hisp.dhis.query.Paging;
import org.hisp.dhis.query.trackedentity.TrackedEntityQuery;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class TrackedEntityApiTest {
  @Test
  void testGetTrackedEntity() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    TrackedEntity trackedEntity = dhis2.getTrackedEntity("kfwLSxq7mXk");

    assertNotNull(trackedEntity);
    assertNotEmpty(trackedEntity.getAttributes());
    assertEquals("kfwLSxq7mXk", trackedEntity.getTrackedEntity());
    assertNotNull(trackedEntity.getCreatedAt());
    assertNotNull(trackedEntity.getUpdatedAt());
    assertNotNull(trackedEntity.getOrgUnit());
  }

  @Test
  void testGetTrackedEntities() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    TrackedEntityQuery query =
        TrackedEntityQuery.instance().setProgram("IpHINAT79UW").setOrgUnit("DiszpKrYNg8");

    TrackedEntitiesResult trackedEntities = dhis2.getTrackedEntities(query);

    assertNotNull(trackedEntities);
    assertNotEmpty(trackedEntities.getTrackedEntities());
  }

  @Test
  void testGetTrackedEntitiesWithLikeFilter() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    TrackedEntityQuery query =
        TrackedEntityQuery.instance()
            .setProgram("IpHINAT79UW")
            .setOrgUnit("DiszpKrYNg8")
            .addFilter(Filter.like("w75KJ2mc4zz", "Frank"));

    TrackedEntitiesResult trackedEntities = dhis2.getTrackedEntities(query);

    assertNotNull(trackedEntities);
    assertNotEmpty(trackedEntities.getTrackedEntities());
  }

  @Test
  void testGetTrackedEntitiesWithInFilter() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    TrackedEntityQuery query =
        TrackedEntityQuery.instance()
            .setProgram("IpHINAT79UW")
            .setOrgUnit("DiszpKrYNg8")
            .addFilter(Filter.in("w75KJ2mc4zz", List.of("Frank", "Maria", "Elizabeth")));

    TrackedEntitiesResult trackedEntities = dhis2.getTrackedEntities(query);

    assertNotNull(trackedEntities);
    assertNotEmpty(trackedEntities.getTrackedEntities());
  }

  @Test
  void testGetTrackedEntitiesWithPaging() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    TrackedEntityQuery query =
        TrackedEntityQuery.instance()
            .setProgram("IpHINAT79UW")
            .setOrgUnit("DiszpKrYNg8")
            .setPaging(new Paging(1, 2));

    TrackedEntitiesResult trackedEntities = dhis2.getTrackedEntities(query);

    assertNotNull(trackedEntities);
    assertNotEmpty(trackedEntities.getTrackedEntities());
    assertEquals(2, trackedEntities.getTrackedEntities().size());
  }
}
