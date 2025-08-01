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
package org.hisp.dhis.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.regex.Pattern;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.UNIT)
class RegexUtilsTest {
  public static final Pattern PATTERN_ACCESS_TOKEN = Pattern.compile("^ApiToken\\s+(.+)$");

  @Test
  void testGetMatch() {
    assertEquals(
        "d2p_Hng6afka01kf83kpb8jd5FeD3",
        RegexUtils.getMatch(PATTERN_ACCESS_TOKEN, "ApiToken d2p_Hng6afka01kf83kpb8jd5FeD3", 1));
    assertNull(RegexUtils.getMatch(PATTERN_ACCESS_TOKEN, "Bearer Jhbg6fgR4RfK", 1));
    assertNull(RegexUtils.getMatch(PATTERN_ACCESS_TOKEN, null, 1));
  }

  @Test
  void testGetFirstMatch() {
    assertEquals(
        "d2p_Hng6afka01kf83kpb8jd5FeD3",
        RegexUtils.getFirstMatch(PATTERN_ACCESS_TOKEN, "ApiToken d2p_Hng6afka01kf83kpb8jd5FeD3"));
  }
}
