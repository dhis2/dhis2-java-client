package org.hisp.dhis.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.hisp.dhis.response.Dhis2ClientException;
import org.junit.jupiter.api.Test;

class VerifyTest {
  @Test
  void testNotEmptyFail() {
    Dhis2ClientException ex = assertThrows(Dhis2ClientException.class, () -> Verify.notEmpty("", "{} must be not empty", "Session ID"));
    assertEquals("Session ID must be not empty", ex.getMessage());
  }
  
  @Test
  void testNotEmptyValid() {
    assertEquals("SessionID", Verify.notEmpty("SessionID", "{} must be not empty", "Session ID"));
  }
  
  @Test
  void testNotBlankFail() {
    Dhis2ClientException ex = assertThrows(Dhis2ClientException.class, () -> Verify.notBlank(" ", "{} must be not blank", "Session ID"));
    assertEquals("Session ID must be not blank", ex.getMessage());
  }
  
  @Test
  void testNotBlankValid() {
    assertEquals("SessionID", Verify.notBlank("SessionID", "{} must be not blank", "Session ID"));
  }

  @Test
  void testNotNullFail() {
    Dhis2ClientException ex = assertThrows(Dhis2ClientException.class, () -> Verify.notNull(null, "{} must be not null", "Session ID"));
    assertEquals("Session ID must be not null", ex.getMessage());
  }

  @Test
  void testNotNullValid() {
    assertEquals("SessionID", Verify.notNull("SessionID", "{} must be not null", "Session ID"));
  }
}
