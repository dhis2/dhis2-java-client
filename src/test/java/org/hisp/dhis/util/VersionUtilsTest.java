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
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class VersionUtilsTest {
  @Test
  void testGetMajorVersion() {
    assertEquals(37, VersionUtils.getMajorVersion("2.37.8.1-SNAPSHOT"));
    assertEquals(28, VersionUtils.getMajorVersion("2.28"));
    assertEquals(35, VersionUtils.getMajorVersion("2.35.11"));
    assertEquals(37, VersionUtils.getMajorVersion("2.37.9"));
    assertEquals(35, VersionUtils.getMajorVersion("2.35.6.1-SNAPSHOT"));
    assertEquals(38, VersionUtils.getMajorVersion("2.38.1-EMBARGOED"));
    assertNull(VersionUtils.getMajorVersion("FOO"));
    assertNull(VersionUtils.getMajorVersion(null));
  }

  @Test
  void testGetPatchVersion() {
    assertEquals(8, VersionUtils.getPatchVersion("2.37.8.1-SNAPSHOT"));
    assertNull(VersionUtils.getPatchVersion("2.28"));
    assertEquals(11, VersionUtils.getPatchVersion("2.35.11"));
    assertEquals(9, VersionUtils.getPatchVersion("2.37.9"));
    assertEquals(6, VersionUtils.getPatchVersion("2.35.6.1-SNAPSHOT"));
    assertEquals(1, VersionUtils.getPatchVersion("2.38.1-EMBARGOED"));
    assertNull(VersionUtils.getPatchVersion("FOO"));
    assertNull(VersionUtils.getMajorVersion(null));
  }
}
