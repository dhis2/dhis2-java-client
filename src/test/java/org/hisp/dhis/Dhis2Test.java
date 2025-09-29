/*
 * Copyright (c) 2004-2024, University of Oslo
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.Set;
import org.apache.hc.core5.net.URIBuilder;
import org.hisp.dhis.query.Filter;
import org.hisp.dhis.query.InternalQuery;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.response.Dhis2ClientException;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class Dhis2Test {

  @Test
  void testGetInvalidUrl() {
    Dhis2 dhis2 = new Dhis2(new Dhis2Config("https://not_a_domain.abc", "username", "pw"));

    assertThrows(Dhis2ClientException.class, () -> dhis2.getOrgUnitGroups(Query.instance()));
  }

  @Test
  void testGetQueryValueFromList() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    assertEquals("Frank", dhis2.getQueryValue(Filter.eq("w75KJ2mc4zz", "Frank")));
    assertEquals(
        "[Frank,Maria,Elizabeth]",
        dhis2.getQueryValue(Filter.in("w75KJ2mc4zz", List.of("Frank", "Maria", "Elizabeth"))));
  }

  @Test
  void testGetQueryValueFromSet() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);
    String value =
        (String)
            dhis2.getQueryValue(Filter.in("w75KJ2mc4zz", Set.of("Frank", "Maria", "Elizabeth")));

    assertTrue(value.contains("Frank"));
    assertTrue(value.contains("Maria"));
    assertTrue(value.contains("Elizabeth"));
  }

  @Test
  void testAddPaging() throws Exception {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    Query query = Query.instance().setPaging(2, 50);

    URIBuilder builder = new URIBuilder("https://myserver.org/api");

    dhis2.addPaging(builder, query, InternalQuery.instance());

    assertEquals("https://myserver.org/api?page=2&pageSize=50", builder.toString());
  }

  @Test
  void testAddPagingWithDefaultPaging() throws Exception {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    Query query = Query.instance();

    URIBuilder builder = new URIBuilder("https://myserver.org/api");

    dhis2.addPaging(builder, query, InternalQuery.instance().withDefaultPaging());

    assertEquals("https://myserver.org/api", builder.toString());
  }

  @Test
  void testGetAccessDenied() {
    Dhis2 dhis2 =
        new Dhis2(new Dhis2Config("https://play.dhis2.org/demo", "notauser", "invalidpw"));

    Dhis2ClientException ex =
        assertThrows(Dhis2ClientException.class, () -> dhis2.getOrgUnitGroups(Query.instance()));

    assertEquals(401, ex.getStatusCode());
    assertEquals("Authentication failed (401)", ex.getMessage());
  }

  @Test
  void testGetNotFound() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    Dhis2ClientException ex =
        assertThrows(Dhis2ClientException.class, () -> dhis2.getOrgUnitGroup("NonExisting"));

    assertEquals(404, ex.getStatusCode());
    assertEquals("Object not found (404)", ex.getMessage());
  }

  @Test
  void testSaveNullObject() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    Dhis2ClientException ex =
        assertThrows(Dhis2ClientException.class, () -> dhis2.saveOrgUnitGroup(null));

    assertEquals(400, ex.getStatusCode());
  }

  @Test
  void testWithBasicAuthAuthFail() {
    Dhis2ClientException ex =
        assertThrows(Dhis2ClientException.class, () -> Dhis2.withBasicAuth("", "", ""));
    assertEquals("URL must be provided", ex.getMessage());
  }

  @Test
  void testWithAccessTokenAuthFail() {
    Dhis2ClientException ex =
        assertThrows(Dhis2ClientException.class, () -> Dhis2.withAccessTokenAuth("", ""));
    assertEquals("URL must be provided", ex.getMessage());
  }

  @Test
  void testWithCookieAuthAuthFail() {
    Dhis2ClientException ex =
        assertThrows(Dhis2ClientException.class, () -> Dhis2.withCookieAuth("", ""));
    assertEquals("URL must be provided", ex.getMessage());
  }
}
