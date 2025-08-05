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
import org.hisp.dhis.model.user.UserRole;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.response.HttpStatus;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.support.TestTags;
import org.hisp.dhis.util.UidUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class UserRoleApiTest {

  @Test
  void testGetUserRole() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    UserRole userRole = dhis2.getUserRole("Ufph3mGRmMo");

    assertNotNull(userRole);
    assertEquals("Ufph3mGRmMo", userRole.getId());
    assertEquals("Superuser", userRole.getName());
    assertNotNull(userRole.getCreated());
    assertNotNull(userRole.getLastUpdated());
    assertFalse(userRole.getUsers().isEmpty());
  }

  @Test
  void testGetUserRoles() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    List<UserRole> userRoles = dhis2.getUserRoles(Query.instance());

    assertNotNull(userRoles);
    assertFalse(userRoles.isEmpty());
  }

  @Test
  void testCreateUpdateAndDeleteUserRoles() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);
    String uidA = UidUtils.generateUid();
    String uidB = UidUtils.generateUid();

    UserRole userRole = new UserRole();
    userRole.setName(uidA);
    userRole.setCode(uidA);
    userRole.setDescription(uidA);

    // Create
    ObjectResponse createRespA = dhis2.saveUserRole(userRole);

    validateResponse(createRespA, HttpStatus.CREATED, 201);

    String userRoleUid = createRespA.getResponse().getUid();

    // Get
    userRole = dhis2.getUserRole(userRoleUid);

    assertNotNull(userRole);
    assertEquals(userRoleUid, userRole.getId());
    assertEquals(uidA, userRole.getName());
    assertEquals(uidA, userRole.getDescription());

    userRole.setName(uidB);

    // Update
    ObjectResponse updateRespA = dhis2.updateUserRole(userRole);

    validateResponse(updateRespA, HttpStatus.OK, 200);
    assertEquals(userRoleUid, updateRespA.getResponse().getUid(), updateRespA.toString());

    // Get Updated
    userRole = dhis2.getUserRole(userRoleUid);
    assertNotNull(userRole);
    assertEquals(userRoleUid, userRole.getId());
    assertEquals(uidB, userRole.getName());

    // Remove
    ObjectResponse removeRespA = dhis2.removeUserRole(userRoleUid);

    validateResponse(removeRespA, HttpStatus.OK, 200);
  }

  private void validateResponse(
      ObjectResponse objectResponse, HttpStatus httpStatus, int expectedStatusCode) {
    assertEquals(
        expectedStatusCode,
        objectResponse.getHttpStatusCode().intValue(),
        objectResponse.toString());
    assertEquals(httpStatus, objectResponse.getHttpStatus(), objectResponse.toString());
    assertEquals(Status.OK, objectResponse.getStatus(), objectResponse.toString());
    assertNotNull(objectResponse.getResponse());
    assertNotNull(objectResponse.getResponse().getUid());
  }
}
