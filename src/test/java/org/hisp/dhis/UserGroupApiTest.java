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

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.hisp.dhis.model.AggregationType;
import org.hisp.dhis.model.ValueType;
import org.hisp.dhis.model.trackedentity.TrackedEntityAttribute;
import org.hisp.dhis.model.user.User;
import org.hisp.dhis.model.user.UserGroup;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.response.HttpStatus;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.object.ObjectResponse;
import org.junit.jupiter.api.Test;

public class UserGroupApiTest {
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

    User user = dhis2.getUser("xE7jOejl9FI");

    UserGroup userGroup = new UserGroup();
    userGroup.setName("Sample userGroup");
    userGroup.setCode("UG_SAMPLE_CODE");
    userGroup.setUsers(List.of(user));

    // Create
    ObjectResponse createRespA = dhis2.saveUserGroup(userGroup);

    assertEquals(201, createRespA.getHttpStatusCode().intValue(), createRespA.toString());
    assertEquals(HttpStatus.CREATED, createRespA.getHttpStatus(), createRespA.toString());
    assertEquals(Status.OK, createRespA.getStatus(), createRespA.toString());
    assertNotNull(createRespA.getResponse());
    assertNotNull(createRespA.getResponse().getUid());

    String userGroupUid = createRespA.getResponse().getUid();

    // Get
    userGroup = dhis2.getUserGroup(userGroupUid);

    assertNotNull(userGroup);
    assertEquals(userGroupUid, userGroup.getId());
    assertFalse(userGroup.getUsers().isEmpty());

    String updatedName = "Sample userGroup updated";
    userGroup.setName(updatedName);

    // Update
    ObjectResponse updateRespA = dhis2.updateUserGroup(userGroup);

    assertEquals(200, updateRespA.getHttpStatusCode().intValue(), updateRespA.toString());
    assertEquals(HttpStatus.OK, updateRespA.getHttpStatus(), updateRespA.toString());
    assertEquals(Status.OK, updateRespA.getStatus(), updateRespA.toString());
    assertEquals(userGroupUid, updateRespA.getResponse().getUid(), updateRespA.toString());

    // Get Updated
    userGroup = dhis2.getUserGroup(userGroupUid);
    assertNotNull(userGroup);
    assertEquals(userGroupUid, userGroup.getId());
    assertEquals(updatedName, userGroup.getName());

    // Remove
    ObjectResponse removeRespA = dhis2.removeUserGroup(userGroupUid);

    assertEquals(200, removeRespA.getHttpStatusCode().intValue(), removeRespA.toString());
    assertEquals(HttpStatus.OK, removeRespA.getHttpStatus(), removeRespA.toString());
    assertEquals(Status.OK, removeRespA.getStatus(), removeRespA.toString());
    assertNotNull(removeRespA.getResponse());
    assertNotNull(removeRespA.getResponse().getUid());
  }
}
