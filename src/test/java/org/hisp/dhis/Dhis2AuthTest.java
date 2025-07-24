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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.regex.Matcher;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.Header;
import org.hisp.dhis.auth.Authentication;
import org.hisp.dhis.auth.BasicAuthentication;
import org.hisp.dhis.auth.CookieAuthentication;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.support.TestTags;
import org.hisp.dhis.util.HttpUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class Dhis2AuthTest {
  @Test
  void testBasicAuthConstructor() {
    Dhis2Config config = new Dhis2Config(TestFixture.DEFAULT_URL, "admin", "district");

    Dhis2 dhis2 = new Dhis2(config);

    assertNotNull(dhis2.getDataElements(Query.instance()));
  }

  @Test
  void testBasicAuthInstance() {
    Dhis2 dhis2 = Dhis2.withBasicAuth(TestFixture.DEFAULT_URL, "admin", "district");

    assertNotNull(dhis2.getDataElements(Query.instance()));
  }

  @Test
  void testBasicAuthentication() {
    Authentication authentication = new BasicAuthentication("admin", "district");

    Dhis2Config config = new Dhis2Config(TestFixture.DEFAULT_URL, authentication);

    Dhis2 dhis2 = new Dhis2(config);

    assertNotNull(dhis2.getDataElements(Query.instance()));
  }

  @Test
  void testCookieAuthentication() throws Exception {
    String sessionId = getSessionId(TestFixture.DEFAULT_CONFIG);

    Authentication authentication = new CookieAuthentication(sessionId);

    Dhis2Config config = new Dhis2Config(TestFixture.DEFAULT_URL, authentication);

    Dhis2 dhis2 = new Dhis2(config);

    assertNotNull(dhis2.getDataElements(Query.instance()));
  }

  @Test
  void testGetUserAuthorization() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    List<String> authorization = dhis2.getUserAuthorization();

    assertNotNull(authorization);
    assertFalse(authorization.isEmpty());
  }

  @Test
  void testIsValidAuthSuccess() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    assertTrue(dhis2.isValidAuth());
  }

  @Test
  void testIsValidAuthFailure() {
    Dhis2Config config = new Dhis2Config(TestFixture.DEFAULT_URL, "admin", "NJvPYP^uOB8mL7");

    Dhis2 dhis2 = new Dhis2(config);

    assertFalse(dhis2.isValidAuth());
  }

  private String getSessionId(Dhis2Config basicAuthConfig) throws Exception {
    CloseableHttpClient httpClient = HttpClients.createDefault();

    HttpGet request = new HttpGet(TestFixture.DEFAULT_URL + "/api/dataElements.json");
    HttpUtils.withAuth(request, basicAuthConfig);

    return httpClient.execute(
        request,
        response -> {
          Header cookie = response.getHeader("Set-Cookie");
          Matcher matcher = HttpUtils.PATTERN_SESSION_ID.matcher(cookie.getValue());
          matcher.matches();
          return matcher.group(1);
        });
  }
}
