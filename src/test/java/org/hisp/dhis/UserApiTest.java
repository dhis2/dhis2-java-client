package org.hisp.dhis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.List;
import org.hisp.dhis.model.OrgUnit;
import org.hisp.dhis.model.user.User;
import org.hisp.dhis.query.Filter;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.support.TestTags;
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
}
