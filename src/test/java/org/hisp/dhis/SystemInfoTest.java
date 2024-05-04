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
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.hisp.dhis.model.SystemInfo;
import org.hisp.dhis.model.SystemVersion;
import org.junit.jupiter.api.Test;

class SystemInfoTest {
  @Test
  void testVersionComparison() {
    SystemInfo info = new SystemInfo();
    info.setVersion("2.35.6");
    info.setRevision("b8d4ef2");

    SystemVersion version = info.getSystemVersion();

    assertTrue(version.isHigher("2.35.4"));
    assertTrue(version.isHigher("2.32.3"));
    assertTrue(version.isHigher("2.35"));
    assertTrue(version.isHigher("2.34.0"));
    assertTrue(version.isHigher("2.32-SNAPSHOT"));
    assertTrue(version.isHigherOrEqual("2.34.3"));
    assertTrue(version.isHigherOrEqual("2.35.6"));
    assertTrue(version.isEqual("2.35.6"));
    assertTrue(version.isLowerOrEqual("2.35.6"));
    assertTrue(version.isLowerOrEqual("2.36.2"));
    assertTrue(version.isLowerOrEqual("2.36-SNAPSHOT"));
    assertTrue(version.isLower("2.37.1"));

    assertFalse(version.isHigher("2.37.2"));
    assertFalse(version.isHigherOrEqual("2.35.7"));
    assertFalse(version.isHigherOrEqual("2.36-SNAPSHOT"));
    assertFalse(version.isEqual("2.35.2"));
    assertFalse(version.isLowerOrEqual("2.34.2"));
    assertFalse(version.isLowerOrEqual("2.34.2-SNAPSHOT"));
    assertFalse(version.isLower("2.34.5"));
  }
}
