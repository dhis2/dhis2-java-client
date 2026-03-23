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

import static org.hisp.dhis.util.DateTimeUtils.toDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.hisp.dhis.model.Note;
import org.hisp.dhis.model.enrollment.Enrollment;
import org.hisp.dhis.model.enrollment.EnrollmentsResult;
import org.hisp.dhis.model.metadata.ImportStrategy;
import org.hisp.dhis.model.tracker.TrackedEntityObjects;
import org.hisp.dhis.query.enrollment.EnrollmentQuery;
import org.hisp.dhis.query.tracker.TrackedEntityImportParams;
import org.hisp.dhis.response.Dhis2ClientException;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.trackedentity.TrackedEntityResponse;
import org.hisp.dhis.support.TestTags;
import org.hisp.dhis.util.UidUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class EnrollmentsApiTest {

  private static final String OU_A = "DiszpKrYNg8";
  private static final String PR_A = "IpHINAT79UW";

  @Test
  void testGetEnrollments() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    EnrollmentQuery query = EnrollmentQuery.instance().setOrgUnits(List.of(OU_A)).setProgram(PR_A);
    EnrollmentsResult result = dhis2.getEnrollments(query);

    assertNotNull(result);

    List<Enrollment> enrollments = result.getEnrollments();

    assertNotNull(enrollments);
    assertFalse(enrollments.isEmpty());

    Enrollment enrollment = enrollments.get(0);

    assertNotNull(enrollment);
    assertEquals(OU_A, enrollment.getOrgUnit());
    assertEquals(PR_A, enrollment.getProgram());
  }

  @Test
  void testSaveGetRemoveEnrollments() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    final String enrollmentId = UidUtils.generateUid();
    final String noteId = UidUtils.generateUid();
    final String programId = "ur1Edk5Oe2n";
    final String trackedEntity = "GWKZthWooDQ";
    final String orgUnit = "RQgXBKxgvHf";

    Enrollment enrollment = new Enrollment(enrollmentId);
    enrollment.setProgram(programId);
    enrollment.setOrgUnit(orgUnit);
    enrollment.setTrackedEntity(trackedEntity);
    enrollment.setEnrolledAt(new Date());
    enrollment.setOccurredAt(toDate(2021, 7, 12));
    Note note = new Note();
    note.setNote(noteId);
    note.setStoredBy("admin");
    note.setValue(StringUtils.repeat('a', 5000));
    enrollment.setNotes(List.of(note));

    TrackedEntityImportParams trackerQuery =
        TrackedEntityImportParams.instance().setImportStrategy(ImportStrategy.CREATE);

    TrackedEntityObjects trackedEntityObjects = new TrackedEntityObjects();
    trackedEntityObjects.addEnrollment(enrollment);
    TrackedEntityResponse response =
        dhis2.saveTrackedEntityObjects(trackedEntityObjects, trackerQuery);

    assertNotNull(response);
    assertTrue(response.isStatusOk());
    assertFalse(response.hasErrorReports());
    assertEquals(Status.OK, response.getStatus(), response.toString());
    assertEquals(1, response.getStats().getCreated());
    assertEquals(0, response.getStats().getUpdated());
    assertEquals(0, response.getStats().getIgnored());
    assertEquals(0, response.getStats().getDeleted());

    enrollment = dhis2.getEnrollment(enrollmentId);

    assertNotNull(enrollment);
    assertEquals(programId, enrollment.getProgram());
    assertEquals(orgUnit, enrollment.getOrgUnit());
    assertEquals(trackedEntity, enrollment.getTrackedEntity());

    assertNotNull(enrollment.getNotes());
    assertEquals(1, enrollment.getNotes().size());
    note = enrollment.getNotes().get(0);
    assertEquals(noteId, note.getNote());
    assertEquals(5000, note.getValue().length());
    assertEquals("admin", note.getStoredBy());

    trackedEntityObjects = new TrackedEntityObjects();
    trackedEntityObjects.addEnrollment(enrollment);
    trackerQuery = TrackedEntityImportParams.instance().setImportStrategy(ImportStrategy.DELETE);
    response = dhis2.saveTrackedEntityObjects(trackedEntityObjects, trackerQuery);

    assertNotNull(response);
    assertEquals(Status.OK, response.getStatus(), response.toString());
    assertEquals(0, response.getStats().getCreated());
    assertEquals(0, response.getStats().getUpdated());
    assertEquals(0, response.getStats().getIgnored());
    assertEquals(1, response.getStats().getDeleted());

    Dhis2ClientException exception =
        assertThrows(Dhis2ClientException.class, () -> dhis2.getEnrollment(enrollmentId));
    assertNotNull(exception);
    assertEquals(404, exception.getStatusCode());
  }
}
