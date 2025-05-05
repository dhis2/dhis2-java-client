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
package org.hisp.dhis.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SystemVersionTest {
  private final SystemVersion v_2_39 = SystemVersion.of("2.39");
  private final SystemVersion v_2_39_2 = SystemVersion.of("2.39.2");
  private final SystemVersion v_2_39_2_1 = SystemVersion.of("2.39.2.1");
  private final SystemVersion v_2_40 = SystemVersion.of("2.40");
  private final SystemVersion v_2_40_0 = SystemVersion.of("2.40.0");
  private final SystemVersion v_2_40_1 = SystemVersion.of("2.40.1");

  @Test
  void testIsHigher() {
    assertTrue(v_2_39_2.isHigher(v_2_39.version()));
    assertTrue(v_2_40.isHigher(v_2_39_2_1.version()));
    assertFalse(v_2_39_2.isHigher(v_2_39_2.version()));
    assertFalse(v_2_39_2_1.isHigher(v_2_40.version()));
  }

  @Test
  void testIsHigherOrEqual() {
    assertTrue(v_2_40.isHigherOrEqual(v_2_40.version()));
    assertTrue(v_2_40_0.isHigherOrEqual(v_2_39_2_1.version()));
    assertFalse(v_2_39_2_1.isHigherOrEqual(v_2_40.version()));
    assertFalse(v_2_39_2.isHigherOrEqual(v_2_39_2_1.version()));
  }

  @Test
  void testEquals() {
    assertEquals(v_2_39_2, SystemVersion.of("2.39.2"));
    assertEquals(v_2_39_2_1, SystemVersion.of("2.39.2.1"));
    assertNotEquals(v_2_40_1, SystemVersion.of("2.40.0"));
    assertNotEquals(v_2_39_2_1, SystemVersion.of("2.39.2"));
  }
}
