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

import static org.hisp.dhis.support.Assertions.assertNotBlank;
import static org.hisp.dhis.support.Assertions.assertNotEmpty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.hisp.dhis.model.ProgramStage;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class ProgramStageApiTest {
  @Test
  void getGetProgramStage() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    ProgramStage programStage = dhis2.getProgramStage("A03MvHHogjR");

    assertNotNull(programStage);
    assertEquals("A03MvHHogjR", programStage.getId());
    assertNotBlank(programStage.getName());
    assertNotNull(programStage.getCreated());
    assertNotNull(programStage.getLastUpdated());
    assertNotBlank(programStage.getDescription());
    assertNotEmpty(programStage.getProgramStageDataElements());
    assertNotBlank(programStage.getExecutionDateLabel());
    assertNotNull(programStage.getRepeatable());
    assertNotNull(programStage.getAutoGenerateEvent());
    assertNotNull(programStage.getDisplayGenerateEventBox());
    assertNotNull(programStage.getBlockEntryForm());
    assertNotNull(programStage.getPreGenerateUID());
    assertNotNull(programStage.getRemindCompleted());
    assertNotNull(programStage.getGeneratedByEnrollmentDate());
    assertNotNull(programStage.getAllowGenerateNextVisit());
    assertNotNull(programStage.getOpenAfterEnrollment());
    assertNotNull(programStage.getHideDueDate());
    assertNotNull(programStage.getEnableUserAssignment());
    assertNotNull(programStage.getReferral());
  }
}
