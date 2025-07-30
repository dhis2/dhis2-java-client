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

import static org.hisp.dhis.support.Assertions.assertSuccessResponse;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;
import org.hisp.dhis.model.OrgUnit;
import org.hisp.dhis.model.user.User;
import org.hisp.dhis.model.user.UserRole;
import org.hisp.dhis.query.Filter;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.response.HttpStatus;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.support.TestTags;
import org.hisp.dhis.util.UidUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class UserApiTest {
  @Test
  void testGetUser() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    User user = dhis2.getUser("xE7jOejl9FI");

    assertNotNull(user);
    assertEquals("xE7jOejl9FI", user.getId());
    assertEquals("John Traore", user.getName());
    assertEquals("admin", user.getUsername());
    assertEquals("John", user.getFirstName());
    assertEquals("Traore", user.getSurname());
    assertEquals("dummy@dhis2.org", user.getEmail());
    assertFalse(user.getOrganisationUnits().isEmpty());

    OrgUnit orgUnit = user.getOrganisationUnits().get(0);

    assertNotNull(orgUnit);
    assertNotNull(orgUnit.getId());
    assertNotNull(orgUnit.getName());
  }

  @Test
  void testGetUserByUsername() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    List<User> users = dhis2.getUsers(Query.instance().addFilter(Filter.eq("username", "admin")));

    assertNotNull(users);
    assertEquals(1, users.size());

    User user = users.get(0);

    assertNotNull(user);
    assertEquals("xE7jOejl9FI", user.getId());
  }

  @Test
  void testGetUsers() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    List<User> users = dhis2.getUsers(Query.instance());

    assertNotNull(users);
    assertFalse(users.isEmpty());
  }

  @Test
  void testCreateUpdateAndDeleteUser() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);
    String uidA = UidUtils.generateUid();
    String updatedFirstName = "Lewis Updated";

    OrgUnit orgUnit = new OrgUnit();
    orgUnit.setId("YuQRtpLP10I");

    UserRole roleA = new UserRole();
    roleA.setId("Ufph3mGRmMo");

    User user = new User();
    user.setName(uidA);
    user.setCode(uidA);
    user.setUsername("lewis_john");
    user.setFirstName("Lewis");
    user.setSurname("John");
    user.setOrganisationUnits(List.of(orgUnit));
    user.setUserRoles(Set.of(roleA));

    // Create
    ObjectResponse createRespA = dhis2.saveUser(user);

    assertSuccessResponse(createRespA, HttpStatus.CREATED, 201);

    String userUid = createRespA.getResponse().getUid();

    // Get
    user = dhis2.getUser(userUid);

    assertNotNull(user);
    assertEquals(userUid, user.getId());
    assertTrue(user.hasOrganisationUnits());
    assertFalse(user.getOrganisationUnits().isEmpty());
    assertEquals(orgUnit.getId(), user.getFirstOrganisationUnit().getId());

    user.setFirstName(updatedFirstName);

    // Update
    ObjectResponse updateRespA = dhis2.updateUser(user);
    assertEquals(Status.OK, updateRespA.getStatus(), updateRespA.toString());

    // Get Updated
    user = dhis2.getUser(userUid);
    assertNotNull(user);
    assertEquals(userUid, user.getId());
    assertEquals(updatedFirstName, user.getFirstName());

    // Remove
    ObjectResponse removeRespA = dhis2.removeUser(userUid);

    assertSuccessResponse(removeRespA, HttpStatus.OK, 200);
  }
}
