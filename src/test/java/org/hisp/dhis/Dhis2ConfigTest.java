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

import org.hisp.dhis.response.Dhis2ClientException;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.UNIT)
class Dhis2ConfigTest {
  @Test
  void testGetResolvedUriBuilder() throws Exception {
    Dhis2Config config = new Dhis2Config("https://company.com", "admin", "district");
    String url = config.getResolvedUriBuilder().build().toString();
    assertEquals("https://company.com/api", url);

    config = new Dhis2Config("https://company.com/", "admin", "district");
    url = config.getResolvedUriBuilder().build().toString();
    assertEquals("https://company.com/api", url);

    config = new Dhis2Config("https://company.com/site", "admin", "district");
    url = config.getResolvedUriBuilder().build().toString();
    assertEquals("https://company.com/site/api", url);

    config = new Dhis2Config("https://company.com/site/", "admin", "district");
    url = config.getResolvedUriBuilder().build().toString();
    assertEquals("https://company.com/site/api", url);

    config = new Dhis2Config("https://company.com/site/dhis", "admin", "district");
    url = config.getResolvedUriBuilder().build().toString();
    assertEquals("https://company.com/site/dhis/api", url);

    config = new Dhis2Config("https://company.com/site/dhis/", "admin", "district");
    url = config.getResolvedUriBuilder().build().toString();
    assertEquals("https://company.com/site/dhis/api", url);
  }

  @Test
  void testGetResolvedUriBuilderInvalidUri() {
    Dhis2Config config = new Dhis2Config("http://company com", "admin", "district");

    assertThrows(Dhis2ClientException.class, () -> config.getResolvedUriBuilder().build());
  }

  @Test
  void testGetResolvedUrl() {
    Dhis2Config config = new Dhis2Config("https://play.dhis2.org/dev", "admin", "district");

    assertEquals(
        "https://play.dhis2.org/dev/api/dataElements",
        config.getResolvedUrl("dataElements").toString());

    assertEquals(
        "https://play.dhis2.org/dev/api/system/info",
        config.getResolvedUrl("system/info").toString());

    assertEquals(
        "https://play.dhis2.org/dev/api/analytics/events/query",
        config.getResolvedUrl("analytics/events/query").toString());
  }

  @Test
  void testGetResolvedUrlInvalidUri() {
    Dhis2Config config = new Dhis2Config("http:/company|com", "admin", "district");

    assertThrows(Dhis2ClientException.class, () -> config.getResolvedUrl("indicators"));
  }

  @Test
  void testNormalizeUrl() {
    Dhis2Config config = new Dhis2Config("https://play.dhis2.org/dev", "admin", "district");

    assertEquals("https://dhis2.org/dev", config.normalizeUrl("https://dhis2.org/dev/"));
    assertEquals("https://dhis2.org/dev", config.normalizeUrl("https://dhis2.org/dev"));
  }
}
