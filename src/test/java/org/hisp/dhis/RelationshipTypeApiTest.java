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
import static org.hisp.dhis.model.relationship.RelationshipEntity.PROGRAM_STAGE_INSTANCE;
import static org.hisp.dhis.model.relationship.RelationshipEntity.TRACKED_ENTITY_INSTANCE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;
import org.hisp.dhis.model.relationship.RelationshipConstraint;
import org.hisp.dhis.model.relationship.RelationshipType;
import org.hisp.dhis.model.relationship.TrackerDataView;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.response.HttpStatus;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.support.TestTags;
import org.hisp.dhis.util.UidUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class RelationshipTypeApiTest {
  @Test
  void testGetRelationshipType() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    RelationshipType relationshipType = dhis2.getRelationshipType("wJ1UZTcmY20");

    assertNotNull(relationshipType);
    assertEquals("wJ1UZTcmY20", relationshipType.getId());
    assertEquals("Birth to Baby postnatal", relationshipType.getName());
    assertEquals("Birth to Baby postnatal", relationshipType.getFromToName());
    assertEquals("Baby postnatal to Birth", relationshipType.getToFromName());
    assertNotNull(relationshipType.getCreated());
    assertNotNull(relationshipType.getLastUpdated());

    RelationshipConstraint constraint = relationshipType.getFromConstraint();
    assertNotNull(constraint);
    assertEquals(PROGRAM_STAGE_INSTANCE, constraint.getRelationshipEntity());
    assertNotNull(constraint.getProgramStage());
    assertEquals("A03MvHHogjR", constraint.getProgramStage().getId());
  }

  @Test
  void testIsRelationshipType() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    assertTrue(dhis2.isRelationshipType("wJ1UZTcmY20"));
    assertFalse(dhis2.isRelationshipType("DRhlGBgFBXK"));
  }

  @Test
  void testGetRelationshipTypes() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    List<RelationshipType> relationshipTypes = dhis2.getRelationshipTypes(Query.instance());

    assertNotNull(relationshipTypes);
    assertFalse(relationshipTypes.isEmpty());
  }

  @Test
  void testCreateUpdateAndDeleteRelationshipTypes() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);
    String uidA = UidUtils.generateUid();
    String uidB = UidUtils.generateUid();

    RelationshipType relationshipType = new RelationshipType();
    relationshipType.setName(uidA);
    relationshipType.setCode(uidA);
    relationshipType.setDescription(uidA);
    relationshipType.setFromToName("Sample relationship type from to");
    relationshipType.setToFromName("Sample relationship type to from");
    relationshipType.setBidirectional(true);
    relationshipType.setReferral(true);

    TrackerDataView trackerDataViewFrom = new TrackerDataView();
    trackerDataViewFrom.setDataElements(Set.of("a3kGcGDCuk6"));

    RelationshipConstraint fromConstraint = new RelationshipConstraint();
    fromConstraint.setRelationshipEntity(PROGRAM_STAGE_INSTANCE);
    fromConstraint.setProgramStage(dhis2.getProgramStage("A03MvHHogjR"));
    fromConstraint.setTrackerDataView(trackerDataViewFrom);
    relationshipType.setFromConstraint(fromConstraint);

    TrackerDataView trackerDataViewTo = new TrackerDataView();
    trackerDataViewTo.setAttributes(Set.of("lZGmxYbs97q"));

    RelationshipConstraint toConstraint = new RelationshipConstraint();
    toConstraint.setRelationshipEntity(TRACKED_ENTITY_INSTANCE);
    toConstraint.setTrackedEntityType(dhis2.getTrackedEntityType("nEenWmSyUEp"));
    toConstraint.setTrackerDataView(trackerDataViewTo);
    relationshipType.setToConstraint(toConstraint);

    // Create
    ObjectResponse createRespA = dhis2.saveRelationshipType(relationshipType);

    assertSuccessResponse(createRespA, HttpStatus.CREATED, 201);

    String relationshipTypeUid = createRespA.getResponse().getUid();

    // Get
    RelationshipType savedRelationshipType = dhis2.getRelationshipType(relationshipTypeUid);

    assertNotNull(relationshipType);
    assertEquals(relationshipTypeUid, savedRelationshipType.getId());
    validateSavedRelationshipType(relationshipType, savedRelationshipType);

    // Update
    savedRelationshipType.setName(uidB);

    ObjectResponse updateRespA = dhis2.updateRelationshipType(savedRelationshipType);

    assertSuccessResponse(updateRespA, HttpStatus.OK, 200);
    assertEquals(relationshipTypeUid, updateRespA.getResponse().getUid(), updateRespA.toString());

    // Get Updated
    relationshipType = dhis2.getRelationshipType(relationshipTypeUid);
    assertNotNull(relationshipType);
    assertEquals(relationshipTypeUid, relationshipType.getId());
    assertEquals(uidB, relationshipType.getName());

    // Remove
    ObjectResponse removeRespA = dhis2.removeRelationshipType(relationshipTypeUid);

    assertSuccessResponse(removeRespA, HttpStatus.OK, 200);
  }

  private void validateSavedRelationshipType(
      RelationshipType relationshipType, RelationshipType savedRelationshipType) {
    assertEquals(relationshipType.getName(), savedRelationshipType.getName());
    assertEquals(relationshipType.getCode(), savedRelationshipType.getCode());
    assertEquals(relationshipType.getDescription(), savedRelationshipType.getDescription());
    assertEquals(relationshipType.getFromToName(), savedRelationshipType.getFromToName());
    assertEquals(relationshipType.getToFromName(), savedRelationshipType.getToFromName());
    assertTrue(relationshipType.getBidirectional());
    assertTrue(relationshipType.getReferral());
    assertNotNull(savedRelationshipType.getCreated());
    assertNotNull(savedRelationshipType.getLastUpdated());

    // Validate from constraint
    RelationshipConstraint savedFromConstraint = savedRelationshipType.getFromConstraint();
    assertNotNull(savedFromConstraint);
    assertEquals(PROGRAM_STAGE_INSTANCE, savedFromConstraint.getRelationshipEntity());
    assertEquals("A03MvHHogjR", savedFromConstraint.getProgramStage().getId());

    Set<String> savedDataElements = savedFromConstraint.getTrackerDataView().getDataElements();
    TrackerDataView trackerDataViewFrom = relationshipType.getFromConstraint().getTrackerDataView();
    assertEquals(trackerDataViewFrom.getDataElements(), savedDataElements);

    // Validate to constraint
    RelationshipConstraint savedToConstraint = savedRelationshipType.getToConstraint();
    assertNotNull(savedToConstraint);
    assertEquals(TRACKED_ENTITY_INSTANCE, savedToConstraint.getRelationshipEntity());
    assertEquals("nEenWmSyUEp", savedToConstraint.getTrackedEntityType().getId());

    Set<String> savedAttributes = savedToConstraint.getTrackerDataView().getAttributes();
    TrackerDataView trackerDataViewTo = relationshipType.getToConstraint().getTrackerDataView();
    assertEquals(trackerDataViewTo.getAttributes(), savedAttributes);
  }
}
