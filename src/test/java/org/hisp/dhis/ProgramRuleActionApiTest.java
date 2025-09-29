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
import static org.hisp.dhis.support.Assertions.assertSuccessResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.hisp.dhis.model.programrule.ProgramRule;
import org.hisp.dhis.model.programrule.ProgramRuleAction;
import org.hisp.dhis.model.programrule.ProgramRuleActionType;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.response.HttpStatus;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.support.TestTags;
import org.hisp.dhis.util.UidUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class ProgramRuleActionApiTest {
  @Test
  void testGetProgramRuleAction() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    ProgramRuleAction programRuleAction = dhis2.getProgramRuleAction("actonrw1056");
    assertNotNull(programRuleAction);
    assertEquals("actonrw1056", programRuleAction.getId());
    assertNotNull(programRuleAction.getSharing());
    assertNotNull(programRuleAction.getAccess());
    assertNotNull(programRuleAction.getCreated());
    assertNotNull(programRuleAction.getLastUpdated());
    assertNotNull(programRuleAction.getProgramRule());
    assertNotNull(programRuleAction.getProgramRuleActionType());
    assertNotNull(programRuleAction.getDataElement());
  }

  @Test
  void testIsProgramRuleAction() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);
    assertTrue(dhis2.isProgramRuleAction("actonrw1056"));
  }

  @Test
  void testIsNotProgramRuleAction() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);
    assertFalse(dhis2.isProgramRuleAction("NOT_A_PROGRAM_ROLE_ID"));
  }

  @Test
  void testGetProgramRuleActions() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    List<ProgramRuleAction> programRuleActions = dhis2.getProgramRuleActions(Query.instance());

    assertNotNull(programRuleActions);
    assertFalse(programRuleActions.isEmpty());

    ProgramRuleAction programRuleAction = programRuleActions.get(0);

    assertNotNull(programRuleAction);
    assertNotBlank(programRuleAction.getId());
    assertNotNull(programRuleAction.getCreated());
    assertNotNull(programRuleAction.getLastUpdated());
    assertNotNull(programRuleAction.getProgramRule());
    assertNotNull(programRuleAction.getProgramRuleActionType());
    assertNotNull(programRuleAction.getDataElement());
  }

  @Test
  void testCreateAndDeleteProgramRuleAction() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);
    String uidA = UidUtils.generateUid();

    ProgramRule programRule = new ProgramRule();
    programRule.setId("NAgjOfWMXg6");

    ProgramRuleAction programRuleAction = new ProgramRuleAction();
    programRuleAction.setId(uidA);
    // programRuleAction.setName(uidA); //looks like name is not saved or retrieved.
    programRuleAction.setCode(uidA);
    programRuleAction.setContent("Content");
    programRuleAction.setData("Data");
    programRuleAction.setProgramRuleActionType(ProgramRuleActionType.WARNINGONCOMPLETE);
    programRuleAction.setProgramRule(programRule);

    // Create
    ObjectResponse createResp = dhis2.saveMetadataObject(programRuleAction);
    assertEquals(201, createResp.getHttpStatusCode().intValue(), createResp.toString());
    assertEquals(HttpStatus.CREATED, createResp.getHttpStatus(), createResp.toString());
    assertEquals(Status.OK, createResp.getStatus(), createResp.toString());
    assertNotNull(createResp.getResponse());
    assertNotNull(createResp.getResponse().getUid());

    ProgramRuleAction savedProgramRuleAction =
        dhis2.getProgramRuleAction(programRuleAction.getId());
    assertNotNull(savedProgramRuleAction);
    assertEquals(programRuleAction.getId(), savedProgramRuleAction.getId());
    assertEquals(programRuleAction.getCode(), savedProgramRuleAction.getCode());
    assertEquals(programRuleAction.getContent(), savedProgramRuleAction.getContent());
    assertEquals(programRuleAction.getDataElement(), savedProgramRuleAction.getDataElement());
    assertEquals(
        programRuleAction.getProgramRuleActionType(),
        savedProgramRuleAction.getProgramRuleActionType());
    assertEquals(programRuleAction.getData(), savedProgramRuleAction.getData());

    // Remove
    ObjectResponse removeRespA = dhis2.removeProgramRuleAction(uidA);

    assertSuccessResponse(removeRespA, HttpStatus.OK, 200);
  }
}
