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
import org.hisp.dhis.model.Program;
import org.hisp.dhis.model.programrule.ProgramRule;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.response.HttpStatus;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.support.TestTags;
import org.hisp.dhis.util.UidUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class ProgramRuleApiTest {
  @Test
  void testGetProgramRule() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    ProgramRule programRule = dhis2.getProgramRule("NAgjOfWMXg6");

    assertNotNull(programRule);
    assertEquals("NAgjOfWMXg6", programRule.getId());
    assertNotBlank(programRule.getName());
    assertNotNull(programRule.getSharing());
    assertNotNull(programRule.getAccess());
    assertNotNull(programRule.getCreated());
    assertNotNull(programRule.getLastUpdated());
    assertNotBlank(programRule.getDescription());
    assertNotBlank(programRule.getCondition());
    assertNotNull(programRule.getProgram());
    assertNotNull(programRule.getProgramRuleActions());
    assertFalse(programRule.getProgramRuleActions().isEmpty());
  }

  @Test
  void testIsProgramRule() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);
    assertTrue(dhis2.isProgramRule("NAgjOfWMXg6"));
  }

  @Test
  void testIsNotProgramRule() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);
    assertFalse(dhis2.isProgramRule("NOT_A_PROGRAM_ROLE_ID"));
  }

  @Test
  void testGetProgramRules() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    List<ProgramRule> programRules = dhis2.getProgramRules(Query.instance());

    assertNotNull(programRules);
    assertFalse(programRules.isEmpty());

    ProgramRule programRule = programRules.get(0);

    assertNotNull(programRule);
    assertNotBlank(programRule.getId());
    assertNotBlank(programRule.getName());
    assertNotNull(programRule.getSharing());
    assertNotNull(programRule.getAccess());
    assertNotNull(programRule.getCreated());
    assertNotNull(programRule.getLastUpdated());
    assertNotBlank(programRule.getDescription());
    assertNotBlank(programRule.getCondition());
    assertNotNull(programRule.getProgram());
    assertNotNull(programRule.getProgramRuleActions());
    assertFalse(programRule.getProgramRuleActions().isEmpty());
  }

  @Test
  void testCreateAndDeleteProgramRule() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);
    String uidA = UidUtils.generateUid();
    Program program = new Program();
    program.setId("IpHINAT79UW");

    ProgramRule programRule = new ProgramRule();
    programRule.setId(uidA);
    programRule.setName(uidA);
    programRule.setCode(uidA);
    programRule.setDescription(" Program Rule Description");
    programRule.setProgram(program);
    programRule.setCondition("Program Rule Condition");
    programRule.setPriority(5);

    // Create
    ObjectResponse createResp = dhis2.saveMetadataObject(programRule);
    assertEquals(201, createResp.getHttpStatusCode().intValue(), createResp.toString());
    assertEquals(HttpStatus.CREATED, createResp.getHttpStatus(), createResp.toString());
    assertEquals(Status.OK, createResp.getStatus(), createResp.toString());
    assertNotNull(createResp.getResponse());
    assertNotNull(createResp.getResponse().getUid());

    ProgramRule savedProgramRule = dhis2.getProgramRule(programRule.getId());
    assertNotNull(savedProgramRule);
    assertEquals(programRule.getId(), savedProgramRule.getId());
    assertEquals(programRule.getName(), savedProgramRule.getName());
    assertEquals(programRule.getCode(), savedProgramRule.getCode());
    assertEquals(programRule.getDescription(), savedProgramRule.getDescription());
    assertEquals(programRule.getPriority(), savedProgramRule.getPriority());
    assertEquals(programRule.getDescription(), savedProgramRule.getDescription());
    assertEquals(programRule.getCondition(), savedProgramRule.getCondition());
    assertEquals(programRule.getProgram(), savedProgramRule.getProgram());
    assertEquals(programRule.getProgramRuleActions(), savedProgramRule.getProgramRuleActions());

    // Remove
    ObjectResponse removeRespA = dhis2.removeProgramRule(uidA);

    assertSuccessResponse(removeRespA, HttpStatus.OK, 200);
  }
}
