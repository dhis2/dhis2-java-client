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

import org.junit.jupiter.api.Test;

class NumberUtilsTest {
  @Test
  void testRound() {
    assertEquals(124.0, NumberUtils.round(123.988, 1));
    assertEquals(123.4, NumberUtils.round(123.411, 1));
    assertEquals(123.0, NumberUtils.round(123.011, 1));
  }

  @Test
  void testToInteger() {
    assertEquals(643, NumberUtils.toInteger("643"));
    assertEquals(4, NumberUtils.toInteger("4"));
    assertNull(NumberUtils.toInteger(null));
  }

  @Test
  void testToString() {
    assertEquals("643", NumberUtils.toString(643));
    assertEquals("4", NumberUtils.toString(4));
    assertNull(NumberUtils.toString((Integer) null));
  }

  @Test
  void testToDouble() {
    assertEquals(643.2, NumberUtils.toDouble("643.2"));
    assertEquals(4.55, NumberUtils.toDouble("4.55"));
    assertEquals(42, NumberUtils.toDouble("42"));
    assertNull(NumberUtils.toDouble(null));
  }

  @Test
  void testDoubleToString() {
    assertEquals("643.88", NumberUtils.toString(643.88));
    assertEquals("4.1", NumberUtils.toString(4.1));
    assertEquals("10", NumberUtils.toString(10));
    assertNull(NumberUtils.toString((Double) null));
  }
}
