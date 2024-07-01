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

import org.apache.hc.core5.net.URIBuilder;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.UNIT)
class UriBuilderTest {
  @Test
  void testConstructor() throws Exception {
    String url = new URIBuilder("https://play.dhis2.org").build().toString();
    assertEquals("https://play.dhis2.org", url);

    url = new URIBuilder("https://play.dhis2.org/dev").build().toString();
    assertEquals("https://play.dhis2.org/dev", url);

    url = new URIBuilder("https://play.dhis2.org/dev/").build().toString();
    assertEquals("https://play.dhis2.org/dev/", url);

    url = new URIBuilder("https://play.dhis2.org/dev/api").build().toString();
    assertEquals("https://play.dhis2.org/dev/api", url);
  }

  @Test
  void testAppendPathA() throws Exception {
    String url = new URIBuilder("https://play.dhis2.org").appendPath("dev").build().toString();
    assertEquals("https://play.dhis2.org/dev", url);

    url =
        new URIBuilder("https://play.dhis2.org")
            .appendPath("dev")
            .appendPath("api")
            .build()
            .toString();
    assertEquals("https://play.dhis2.org/dev/api", url);
  }

  @Test
  void testAppendPathB() throws Exception {
    String url =
        new URIBuilder("https://play.dhis2.org/site").appendPath("dhis").build().toString();
    assertEquals("https://play.dhis2.org/site/dhis", url);

    url = new URIBuilder("https://play.dhis2.org/site/dhis").appendPath("api").build().toString();
    assertEquals("https://play.dhis2.org/site/dhis/api", url);
  }

  @Test
  void testAppendPathC() throws Exception {
    String url =
        new URIBuilder("https://play.dhis2.org/dev/api")
            .appendPath("system/info")
            .build()
            .toString();
    assertEquals("https://play.dhis2.org/dev/api/system/info", url);

    url =
        new URIBuilder("https://play.dhis2.org/api")
            .appendPath("analytics")
            .appendPath("events")
            .appendPath("query")
            .build()
            .toString();
    assertEquals("https://play.dhis2.org/api/analytics/events/query", url);
  }
}
