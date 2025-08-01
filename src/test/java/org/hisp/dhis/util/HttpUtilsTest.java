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
package org.hisp.dhis.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.net.URIBuilder;
import org.hisp.dhis.Dhis2Config;
import org.hisp.dhis.auth.AccessTokenAuthentication;
import org.hisp.dhis.auth.BasicAuthentication;
import org.hisp.dhis.auth.CookieAuthentication;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.UNIT)
class HttpUtilsTest {
  private final String DEV_URL = "https://play.dhis2.org/dev";

  @Test
  void testAsString() throws Exception {
    String encodedUri =
        "https://atlantis.dhis2.org/staging/api/analytics?dimension=dx%3AGF_CM-1a%3BGF_CM-1b%3BGF_CM-1c%3BGF_KP-1a%3BGF_KP-1b%3BGF_KP-3a&dimension=pe%3A202009%3B202010";
    String decodedUri =
        "https://atlantis.dhis2.org/staging/api/analytics?dimension=dx:GF_CM-1a;GF_CM-1b;GF_CM-1c;GF_KP-1a;GF_KP-1b;GF_KP-3a&dimension=pe:202009;202010";

    List<String> pathSegments = new ArrayList<>();
    pathSegments.add("staging");
    pathSegments.add("api");
    pathSegments.add("analytics");

    URI uri =
        new URIBuilder("https://atlantis.dhis2.org")
            .setPathSegments(pathSegments)
            .addParameter("dimension", "dx:GF_CM-1a;GF_CM-1b;GF_CM-1c;GF_KP-1a;GF_KP-1b;GF_KP-3a")
            .addParameter("dimension", "pe:202009;202010")
            .build();

    assertEquals(encodedUri, uri.toString());
    assertEquals(decodedUri, HttpUtils.asString(uri));
  }

  @Test
  void testWithBasicAuth() throws Exception {
    HttpPost post = new HttpPost(DEV_URL);

    HttpUtils.withAuth(
        post, new Dhis2Config(DEV_URL, new BasicAuthentication("admin", "district")));

    assertEquals(
        "Basic YWRtaW46ZGlzdHJpY3Q=", post.getHeader(HttpHeaders.AUTHORIZATION).getValue());
  }

  @Test
  void testWithAccessTokenAuth() throws Exception {
    HttpPost post = new HttpPost(DEV_URL);

    HttpUtils.withAuth(
        post, new Dhis2Config(DEV_URL, new AccessTokenAuthentication("d2pat_kjytgr63jj837")));

    assertEquals(
        "ApiToken d2pat_kjytgr63jj837", post.getHeader(HttpHeaders.AUTHORIZATION).getValue());
  }

  @Test
  void testWithCookieAuth() throws Exception {
    HttpPost post = new HttpPost(DEV_URL);

    HttpUtils.withAuth(
        post, new Dhis2Config(DEV_URL, new CookieAuthentication("KJH8KJ24fRD3FK491")));

    assertEquals("JSESSIONID=KJH8KJ24fRD3FK491", post.getHeader(HttpHeaders.COOKIE).getValue());
  }

  @Test
  void testGetBasicAuthString() {
    assertEquals("Basic YWRtaW46ZGlzdHJpY3Q=", HttpUtils.getBasicAuthString("admin", "district"));
  }

  @Test
  void testGetApiTokenAuthString() {
    assertEquals(
        "ApiToken d2p_eyJzdWIiOiIxMjM0NTY3ODkwIn0",
        HttpUtils.getApiTokenAuthString("d2p_eyJzdWIiOiIxMjM0NTY3ODkwIn0"));
  }

  @Test
  void testGetApiToken() {
    assertEquals(
        "d2p_eyJzdWIiOiIxMjM0NTY3ODkwIn0",
        HttpUtils.getApiToken("ApiToken d2p_eyJzdWIiOiIxMjM0NTY3ODkwIn0"));
  }

  @Test
  void testGetBearerTokenAuthString() {
    assertEquals(
        "Bearer iOlsiYWRtaW4iLCJ1c2VyIl0sIm",
        HttpUtils.getBearerTokenAuthString("iOlsiYWRtaW4iLCJ1c2VyIl0sIm"));
  }

  @Test
  void testGetSessionIdString() {
    assertEquals(
        "JSESSIONID=hbWUiOiJFeGFtcGxlIFVzZXIiLC",
        HttpUtils.getSessionIdString("hbWUiOiJFeGFtcGxlIFVzZXIiLC"));
  }

  @Test
  void testGetBearerToken() {
    assertEquals(
        "iOlsiYWRtaW4iLCJ1c2VyIl0sIm",
        HttpUtils.getBearerToken("Bearer iOlsiYWRtaW4iLCJ1c2VyIl0sIm"));
  }
}
