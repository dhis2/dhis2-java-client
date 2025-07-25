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
package org.hisp.dhis.response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.UNIT)
class Dhis2ClientExceptionTest {
  @Test
  void testConstructor401() {
    Dhis2ClientException ex = new Dhis2ClientException("Unauthorized", 401);
    assertEquals("Unauthorized", ex.getMessage());
    assertEquals(401, ex.getStatusCode());
    assertEquals(HttpStatus.UNAUTHORIZED, ex.getHttpStatus());
  }

  @Test
  void testConstructor403() {
    Dhis2ClientException ex = new Dhis2ClientException("Forbidden", 403);
    assertEquals("Forbidden", ex.getMessage());
    assertEquals(403, ex.getStatusCode());
    assertEquals(HttpStatus.FORBIDDEN, ex.getHttpStatus());
  }

  @Test
  void testIsMethods() {
    assertTrue(new Dhis2ClientException("Unauthorized", 401).isUnauthorized());
    assertTrue(new Dhis2ClientException("Forbidden", 403).isForbidden());
    assertTrue(new Dhis2ClientException("Not Found", 404).isNotFound());

    assertFalse(new Dhis2ClientException("Bad Request", 400).isUnauthorized());
    assertFalse(new Dhis2ClientException("Method Not Allowed", 405).isForbidden());
    assertFalse(new Dhis2ClientException("Conflict", 409).isNotFound());
  }
}
