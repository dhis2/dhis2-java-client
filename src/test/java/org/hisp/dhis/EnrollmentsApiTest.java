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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import org.hisp.dhis.model.enrollment.Enrollment;
import org.hisp.dhis.model.enrollment.EnrollmentsResult;
import org.hisp.dhis.query.enrollment.EnrollmentQuery;
import org.hisp.dhis.support.TestTags;
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
}
