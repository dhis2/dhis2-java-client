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

import static org.hisp.dhis.util.CollectionUtils.list;
import static org.hisp.dhis.util.DateTimeUtils.toDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.hisp.dhis.model.enrollment.Enrollment;
import org.hisp.dhis.model.event.Event;
import org.hisp.dhis.model.event.EventDataValue;
import org.hisp.dhis.model.metadata.ImportStrategy;
import org.hisp.dhis.model.relationship.Relationship;
import org.hisp.dhis.model.relationship.RelationshipItem;
import org.hisp.dhis.model.relationship.RelationshipsResult;
import org.hisp.dhis.model.trackedentity.TrackedEntity;
import org.hisp.dhis.model.tracker.TrackedEntityObjects;
import org.hisp.dhis.query.relationship.RelationshipQuery;
import org.hisp.dhis.query.tracker.TrackedEntityImportParams;
import org.hisp.dhis.response.Dhis2ClientException;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.event.ErrorReport;
import org.hisp.dhis.response.trackedentity.TrackedEntityResponse;
import org.hisp.dhis.support.TestTags;
import org.hisp.dhis.util.UidUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class TrackedObjectsImportApiTest {

  @Test
  void testSaveAndRemoveEvents() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    final String uidA = UidUtils.generateUid();
    final String uidB = UidUtils.generateUid();

    List<EventDataValue> dvA =
        list(
            new EventDataValue("oZg33kd9taw", "Male"),
            new EventDataValue("qrur9Dvnyt5", "45"),
            new EventDataValue("GieVkTxp4HH", "143"),
            new EventDataValue("vV9UWAZohSf", "43"),
            new EventDataValue("eMyVanycQSC", "2021-07-02"),
            new EventDataValue("msodh3rEMJa", "2021-08-05"),
            new EventDataValue("K6uUAvq500H", "A010"),
            new EventDataValue("fWIAEtYVEGk", "MODDISCH"));

    Event evA = new Event(uidA);
    evA.setProgram("eBAyeGv0exc");
    evA.setProgramStage("Zj7UnCAulEk");
    evA.setOrgUnit("DiszpKrYNg8");
    evA.setOccurredAt(toDate(2021, 7, 12));
    evA.setPointGeometry(18.066, 59.333);
    evA.setDataValues(dvA);

    List<EventDataValue> dvB =
        list(
            new EventDataValue("oZg33kd9taw", "Female"),
            new EventDataValue("qrur9Dvnyt5", "41"),
            new EventDataValue("GieVkTxp4HH", "157"),
            new EventDataValue("vV9UWAZohSf", "36"),
            new EventDataValue("eMyVanycQSC", "2021-05-06"),
            new EventDataValue("msodh3rEMJa", "2021-06-08"),
            new EventDataValue("K6uUAvq500H", "A011"),
            new EventDataValue("fWIAEtYVEGk", "MODDISCH"));

    Event evB = new Event(uidB);
    evB.setProgram("eBAyeGv0exc");
    evB.setProgramStage("Zj7UnCAulEk");
    evB.setOrgUnit("DiszpKrYNg8");
    evB.setOccurredAt(toDate(2021, 7, 14));
    evB.setPointGeometry(24.946, 60.192);
    evB.setDataValues(dvB);

    TrackedEntityObjects trackedEntityObjects = new TrackedEntityObjects();
    trackedEntityObjects.setEvents(list(evA, evB));

    TrackedEntityImportParams trackerQuery =
        TrackedEntityImportParams.instance().setImportStrategy(ImportStrategy.CREATE);

    TrackedEntityResponse response =
        dhis2.saveTrackedEntityObjects(trackedEntityObjects, trackerQuery);

    assertNotNull(response);
    assertEquals(Status.OK, response.getStatus(), response.toString());
    assertEquals(2, response.getStats().getCreated());
    assertEquals(0, response.getStats().getUpdated());
    assertEquals(0, response.getStats().getIgnored());
    assertEquals(0, response.getStats().getDeleted());

    evA = dhis2.getEvent(uidA);

    assertNotNull(evA);
    assertEquals("eBAyeGv0exc", evA.getProgram());
    assertEquals("DiszpKrYNg8", evA.getOrgUnit());
    assertNotNull(evA.getGeometry());

    evB = dhis2.getEvent(uidB);

    assertNotNull(evB);
    assertEquals("eBAyeGv0exc", evB.getProgram());
    assertEquals("DiszpKrYNg8", evB.getOrgUnit());
    assertNotNull(evB.getGeometry());

    trackerQuery.setImportStrategy(ImportStrategy.DELETE);
    response = dhis2.saveTrackedEntityObjects(trackedEntityObjects, trackerQuery);

    assertNotNull(response);
    assertEquals(Status.OK, response.getStatus(), response.toString());
    assertEquals(0, response.getStats().getCreated());
    assertEquals(0, response.getStats().getUpdated());
    assertEquals(0, response.getStats().getIgnored());
    assertEquals(2, response.getStats().getDeleted());

    assertEquals(
        404, assertThrows(Dhis2ClientException.class, () -> dhis2.getEvent(uidA)).getStatusCode());
    assertEquals(
        404, assertThrows(Dhis2ClientException.class, () -> dhis2.getEvent(uidB)).getStatusCode());
  }

  @Test
  void testSaveAndRemoveTrackedEntities() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    final String uidA = UidUtils.generateUid();
    final String uidB = UidUtils.generateUid();

    TrackedEntity teA = new TrackedEntity();
    teA.setTrackedEntity(uidA);
    teA.setOrgUnit("DiszpKrYNg8");
    teA.setTrackedEntityType("nEenWmSyUEp");

    TrackedEntity teB = new TrackedEntity();
    teB.setTrackedEntity(uidB);
    teB.setOrgUnit("DiszpKrYNg8");
    teB.setTrackedEntityType("nEenWmSyUEp");

    TrackedEntityObjects trackedEntityObjects = new TrackedEntityObjects();
    trackedEntityObjects.setTrackedEntities(list(teA, teB));

    TrackedEntityImportParams trackerQuery =
        TrackedEntityImportParams.instance().setImportStrategy(ImportStrategy.CREATE);

    TrackedEntityResponse response =
        dhis2.saveTrackedEntityObjects(trackedEntityObjects, trackerQuery);

    assertNotNull(response);
    assertEquals(Status.OK, response.getStatus(), response.toString());
    assertEquals(2, response.getStats().getCreated());
    assertEquals(0, response.getStats().getUpdated());
    assertEquals(0, response.getStats().getIgnored());
    assertEquals(0, response.getStats().getDeleted());

    teA = dhis2.getTrackedEntity(uidA);

    assertNotNull(teA);
    assertEquals("nEenWmSyUEp", teA.getTrackedEntityType());
    assertEquals("DiszpKrYNg8", teA.getOrgUnit());

    teB = dhis2.getTrackedEntity(uidB);

    assertNotNull(teB);
    assertEquals("nEenWmSyUEp", teB.getTrackedEntityType());
    assertEquals("DiszpKrYNg8", teB.getOrgUnit());

    trackerQuery.setImportStrategy(ImportStrategy.DELETE);
    response = dhis2.saveTrackedEntityObjects(trackedEntityObjects, trackerQuery);

    assertNotNull(response);
    assertEquals(Status.OK, response.getStatus(), response.toString());
    assertEquals(0, response.getStats().getCreated());
    assertEquals(0, response.getStats().getUpdated());
    assertEquals(0, response.getStats().getIgnored());
    assertEquals(2, response.getStats().getDeleted());

    assertEquals(
        404,
        assertThrows(Dhis2ClientException.class, () -> dhis2.getTrackedEntity(uidA))
            .getStatusCode());
    assertEquals(
        404,
        assertThrows(Dhis2ClientException.class, () -> dhis2.getTrackedEntity(uidB))
            .getStatusCode());
  }

  @Test
  void testSaveAndRemoveRelationship() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    final String relationshipUidA = UidUtils.generateUid();

    Relationship reA = new Relationship();
    reA.setRelationship(relationshipUidA);
    reA.setRelationshipName("Test relationship");
    reA.setRelationshipType("tBeOL0DL026");
    reA.setFrom(getRelationshipItem("bLpfA4CjeMl"));
    reA.setTo(getRelationshipItem("gGDBG5aGlIk"));

    TrackedEntityObjects trackedEntityObjects = new TrackedEntityObjects();
    trackedEntityObjects.setRelationships(list(reA));

    TrackedEntityImportParams trackerQuery =
        TrackedEntityImportParams.instance().setImportStrategy(ImportStrategy.CREATE);

    TrackedEntityResponse response =
        dhis2.saveTrackedEntityObjects(trackedEntityObjects, trackerQuery);
    assertNotNull(response);
    assertEquals(Status.OK, response.getStatus(), response.toString());
    assertEquals(1, response.getStats().getCreated());
    assertEquals(0, response.getStats().getUpdated());
    assertEquals(0, response.getStats().getIgnored());
    assertEquals(0, response.getStats().getDeleted());

    RelationshipQuery relationshipQuery =
        RelationshipQuery.instance().setTrackedEntity("bLpfA4CjeMl");
    RelationshipsResult relationshipResult = dhis2.getRelationships(relationshipQuery);
    assertNotNull(relationshipResult);
    assertNotNull(relationshipResult.getRelationships());
    assertEquals(1, relationshipResult.getRelationships().size());
    assertEquals(relationshipUidA, relationshipResult.getRelationships().get(0).getRelationship());

    TrackedEntityObjects trackedEntityRelationship = new TrackedEntityObjects();
    trackedEntityRelationship.setRelationships(list(reA));
    trackerQuery.setImportStrategy(ImportStrategy.DELETE);
    response = dhis2.saveTrackedEntityObjects(trackedEntityObjects, trackerQuery);

    assertNotNull(response);
    assertEquals(Status.OK, response.getStatus(), response.toString());
    assertEquals(0, response.getStats().getCreated());
    assertEquals(0, response.getStats().getUpdated());
    assertEquals(0, response.getStats().getIgnored());
    assertEquals(1, response.getStats().getDeleted());

    relationshipResult = dhis2.getRelationships(relationshipQuery);
    assertNotNull(relationshipResult);
    assertTrue(relationshipResult.getRelationships().isEmpty());
  }

  @Test
  void testSaveEnrollmentWithError() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    String enrollmentUId = UidUtils.generateUid();
    Enrollment enA = new Enrollment();
    enA.setEnrollment(enrollmentUId);
    enA.setOrgUnit("DiszpKrYNg8");
    enA.setTrackedEntity("bLpfA4CjeMl");

    TrackedEntityObjects trackedEntityObjects = new TrackedEntityObjects();
    trackedEntityObjects.setEnrollments(list(enA));

    TrackedEntityImportParams trackerQuery =
        TrackedEntityImportParams.instance().setImportStrategy(ImportStrategy.CREATE);

    TrackedEntityResponse response =
        dhis2.saveTrackedEntityObjects(trackedEntityObjects, trackerQuery);

    assertNotNull(response);
    assertEquals(Status.ERROR, response.getStatus(), response.toString());
    assertEquals(0, response.getStats().getCreated());
    assertEquals(0, response.getStats().getUpdated());
    assertEquals(1, response.getStats().getIgnored());
    assertEquals(0, response.getStats().getDeleted());

    assertNotNull(response.getValidationReport());
    assertEquals(1, response.getValidationReport().getErrorReports().size());

    ErrorReport errorReport = response.getValidationReport().getErrorReports().get(0);
    assertNotNull(errorReport);
    assertNotNull(errorReport.getUid());
    assertEquals("E1122", errorReport.getErrorCode());
    assertEquals(enrollmentUId, errorReport.getUid());
    assertEquals("Missing required enrollment property: `program`.", errorReport.getMessage());
    assertEquals("ENROLLMENT", errorReport.getTrackerType());
  }

  @Test
  void testSaveAndRemoveTrackedEntitiesAndRelationships() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    final String uidA = UidUtils.generateUid();
    final String uidB = UidUtils.generateUid();

    TrackedEntity teA = new TrackedEntity();
    teA.setTrackedEntity(uidA);
    teA.setOrgUnit("DiszpKrYNg8");
    teA.setTrackedEntityType("nEenWmSyUEp");

    TrackedEntity teB = new TrackedEntity();
    teB.setTrackedEntity(uidB);
    teB.setOrgUnit("DiszpKrYNg8");
    teB.setTrackedEntityType("nEenWmSyUEp");

    final String relationshipUidA = UidUtils.generateUid();

    Relationship reA = new Relationship();
    reA.setRelationship(relationshipUidA);
    reA.setRelationshipName("Test relationship");
    reA.setRelationshipType("tBeOL0DL026");
    reA.setFrom(getRelationshipItem(teA.getTrackedEntity()));
    reA.setTo(getRelationshipItem(teB.getTrackedEntity()));

    TrackedEntityObjects trackedEntityObjects = new TrackedEntityObjects();
    trackedEntityObjects.setTrackedEntities(list(teA, teB));
    trackedEntityObjects.setRelationships(list(reA));

    TrackedEntityImportParams trackerQuery =
        TrackedEntityImportParams.instance().setImportStrategy(ImportStrategy.CREATE_AND_UPDATE);

    TrackedEntityResponse response =
        dhis2.saveTrackedEntityObjects(trackedEntityObjects, trackerQuery);
    assertNotNull(response);
    assertEquals(Status.OK, response.getStatus(), response.toString());
    assertEquals(3, response.getStats().getCreated());
    assertEquals(0, response.getStats().getUpdated());
    assertEquals(0, response.getStats().getIgnored());
    assertEquals(0, response.getStats().getDeleted());

    trackerQuery.setImportStrategy(ImportStrategy.DELETE);
    response = dhis2.saveTrackedEntityObjects(trackedEntityObjects, trackerQuery);

    assertNotNull(response);
    assertEquals(Status.OK, response.getStatus(), response.toString());
    assertEquals(0, response.getStats().getCreated());
    assertEquals(0, response.getStats().getUpdated());
    assertEquals(0, response.getStats().getIgnored());
    assertEquals(3, response.getStats().getDeleted());
  }

  /**
   * Creates a {@link RelationshipItem} with a {@link TrackedEntity}.
   *
   * @param trackedEntityUid the UID of the tracked entity.
   * @return a {@link RelationshipItem} with the specified tracked entity.
   */
  private RelationshipItem getRelationshipItem(String trackedEntityUid) {
    TrackedEntity trackedEntity = new TrackedEntity();
    trackedEntity.setTrackedEntity(trackedEntityUid);

    RelationshipItem relationshipItem = new RelationshipItem();
    relationshipItem.setTrackedEntity(trackedEntity);

    return relationshipItem;
  }
}
