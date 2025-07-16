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
import org.hisp.dhis.model.user.User;
import org.hisp.dhis.model.user.UserGroup;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.response.HttpStatus;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.support.TestTags;
import org.hisp.dhis.util.UidUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class UserGroupApiTest {
  @Test
  void testGetUserGroup() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    UserGroup userGroup = dhis2.getUserGroup("ZrsVF7IJ93y");

    assertNotNull(userGroup);
    assertEquals("ZrsVF7IJ93y", userGroup.getId());
    assertEquals("Family Health Partner", userGroup.getName());
    assertFalse(userGroup.getUsers().isEmpty());
  }

  @Test
  void testGetUserGroups() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    List<UserGroup> userGroups = dhis2.getUserGroups(Query.instance());

    assertNotNull(userGroups);
    assertFalse(userGroups.isEmpty());
  }

  @Test
  void testCreateUpdateAndDeleteUserGroups() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);
    String uidA = UidUtils.generateUid();
    String uidB = UidUtils.generateUid();

    User user = dhis2.getUser("xE7jOejl9FI");

    UserGroup userGroup = new UserGroup();
    userGroup.setName(uidA);
    userGroup.setCode(uidA);
    userGroup.setUsers(List.of(user));

    // Create
    ObjectResponse createRespA = dhis2.saveUserGroup(userGroup);

    validateResponse(createRespA, HttpStatus.CREATED, 201);

    String userGroupUid = createRespA.getResponse().getUid();

    // Get
    userGroup = dhis2.getUserGroup(userGroupUid);

    assertNotNull(userGroup);
    assertEquals(userGroupUid, userGroup.getId());
    assertFalse(userGroup.getUsers().isEmpty());

    userGroup.setName(uidB);

    // Update
    ObjectResponse updateRespA = dhis2.updateUserGroup(userGroup);

    validateResponse(updateRespA, HttpStatus.OK, 200);
    assertEquals(userGroupUid, updateRespA.getResponse().getUid(), updateRespA.toString());

    // Get Updated
    userGroup = dhis2.getUserGroup(userGroupUid);
    assertNotNull(userGroup);
    assertEquals(userGroupUid, userGroup.getId());
    assertEquals(uidB, userGroup.getName());

    // Remove
    ObjectResponse removeRespA = dhis2.removeUserGroup(userGroupUid);

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
