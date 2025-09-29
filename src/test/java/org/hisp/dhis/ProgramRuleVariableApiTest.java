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

import static org.hisp.dhis.model.programrule.ProgramRuleVariableSourceType.DATAELEMENT_NEWEST_EVENT_PROGRAM;
import static org.hisp.dhis.support.Assertions.assertNotBlank;
import static org.hisp.dhis.support.Assertions.assertSuccessResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.hisp.dhis.model.Program;
import org.hisp.dhis.model.ValueType;
import org.hisp.dhis.model.programrule.ProgramRuleVariable;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.response.HttpStatus;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.support.TestTags;
import org.hisp.dhis.util.UidUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class ProgramRuleVariableApiTest {
  @Test
  void testGetProgramRuleVariable() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    ProgramRuleVariable variable = dhis2.getProgramRuleVariable("aKpfPKSRQnv");

    assertNotNull(variable);
    assertEquals("aKpfPKSRQnv", variable.getId());
    assertNotBlank(variable.getName());
    assertNotNull(variable.getSharing());
    assertNotNull(variable.getAccess());
    assertNotNull(variable.getCreated());
    assertNotNull(variable.getLastUpdated());
    assertNotNull(variable.getProgram());
    assertNotNull(variable.getProgramRuleVariableSourceType());
    assertNotNull(variable.getDataElement());
    assertFalse(variable.isUseCodeForOptionSet());
    assertNotNull(variable.getValueType());
  }

  @Test
  void testIsProgramRuleVariable() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);
    assertTrue(dhis2.isProgramRuleVariable("aKpfPKSRQnv"));
  }

  @Test
  void testGetProgramRuleVariables() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    List<ProgramRuleVariable> variables = dhis2.getProgramRuleVariables(Query.instance());

    assertNotNull(variables);
    assertFalse(variables.isEmpty());

    ProgramRuleVariable variable = variables.get(0);

    assertNotNull(variable);
    assertNotNull(variable.getId());
    assertNotBlank(variable.getName());
    assertNotNull(variable.getSharing());
    assertNotNull(variable.getAccess());
    assertNotNull(variable.getCreated());
    assertNotNull(variable.getLastUpdated());
    assertNotNull(variable.getProgram());
    assertNotNull(variable.getProgramRuleVariableSourceType());
    assertNotNull(variable.getDataElement());
    assertTrue(variable.isUseCodeForOptionSet());
    assertNotNull(variable.getValueType());
  }

  @Test
  void testCreateAndDeleteProgramRuleVariable() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);
    String uidA = UidUtils.generateUid();
    Program program = new Program();
    program.setId("IpHINAT79UW"); // Child Programme

    ProgramRuleVariable programRuleVariable = new ProgramRuleVariable();
    programRuleVariable.setId(uidA);
    programRuleVariable.setName(uidA);
    programRuleVariable.setCode(uidA);
    programRuleVariable.setProgramRuleVariableSourceType(DATAELEMENT_NEWEST_EVENT_PROGRAM);
    programRuleVariable.setValueType(ValueType.TEXT);
    programRuleVariable.setProgram(program);

    // Create
    ObjectResponse createResp = dhis2.saveMetadataObject(programRuleVariable);
    assertEquals(201, createResp.getHttpStatusCode().intValue(), createResp.toString());
    assertEquals(HttpStatus.CREATED, createResp.getHttpStatus(), createResp.toString());
    assertEquals(Status.OK, createResp.getStatus(), createResp.toString());
    assertNotNull(createResp.getResponse());
    assertNotNull(createResp.getResponse().getUid());

    // Remove
    ObjectResponse removeRespA = dhis2.removeProgramRuleVariable(uidA);

    assertSuccessResponse(removeRespA, HttpStatus.OK, 200);
  }
}
