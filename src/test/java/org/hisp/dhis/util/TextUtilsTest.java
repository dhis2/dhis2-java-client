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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.UNIT)
class TextUtilsTest {
  @Test
  void testFormat() {
    String format = "File uploaded to: '{}' by user: '{}'";

    assertEquals(
        "File uploaded to: 'AWS' by user: 'usernameA'",
        TextUtils.format(format, "AWS", "usernameA"));
  }

  @Test
  void testToTrueFalse() {
    assertEquals("false", TextUtils.toTrueFalse(null));
    assertEquals("false", TextUtils.toTrueFalse(false));
    assertEquals("true", TextUtils.toTrueFalse(true));
  }

  @Test
  void testIsVariable() {
    assertTrue(TextUtils.isVariable("${dog}"));
    assertTrue(TextUtils.isVariable("${blue_color}"));
    assertTrue(TextUtils.isVariable("${tall-building}"));

    assertFalse(TextUtils.isVariable("$blue_color"));
    assertFalse(TextUtils.isVariable("{blue_color}"));
    assertFalse(TextUtils.isVariable("blue_color"));
    assertFalse(TextUtils.isVariable("blue${color}"));
  }

  @Test
  void testReplaceLastSuccess() {
    StringBuilder builder = new StringBuilder("OK,CREATED,FOUND,CONFLICT,");
    TextUtils.replaceLast(builder, ",", ";");
    String expected = "OK,CREATED,FOUND,CONFLICT;";

    assertEquals(expected, builder.toString());
  }

  @Test
  void testReplaceNotFound() {
    StringBuilder builder = new StringBuilder("OK,CREATED,FOUND,CONFLICT,");
    TextUtils.replaceLast(builder, "-", ";");
    String expected = "OK,CREATED,FOUND,CONFLICT,";

    assertEquals(expected, builder.toString());
  }
}
