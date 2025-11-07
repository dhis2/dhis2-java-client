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
import static org.junit.jupiter.api.Assertions.assertNull;
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

  @Test
  void testRemoveEnd() {
    assertEquals("/host", TextUtils.removeEnd("/host/", "/"));
    assertEquals("/", TextUtils.removeEnd("/host/", "host/"));
  }

  @Test
  void testRemoveEndNull() {
    assertEquals("", TextUtils.removeEnd("", "/"));
    assertEquals("", TextUtils.removeEnd("", ""));
    assertNull(TextUtils.removeEnd(null, null));
    assertNull(TextUtils.removeEnd(null, "/"));
  }

  @Test
  void testGetString() {
    assertEquals("exists", TextUtils.getString(true, "exists", "does not exist"));
    assertEquals("does not exist", TextUtils.getString(false, "exists", "does not exist"));
  }

  @Test
  void testStripCodeFencesSqlBlockWithNewlines() {
    String input =
        """
        ```sql
        select * from mytable;
        ```""";
    String expected = "select * from mytable;";
    assertEquals(expected, TextUtils.stripCodeFences(input));
  }

  @Test
  void testJsonBlock() {
    String input =
        """
        ```json
        {
          "key": "value"
        }
        ```""";
    String expected =
        """
        {
          "key": "value"
        }""";
    assertEquals(expected, TextUtils.stripCodeFences(input));
  }

  @Test
  void testStripCodeFencesInlineBlockWithoutNewlines() {
    String input = "```sql select * from mytable;```";
    String expected = "select * from mytable;";
    assertEquals(expected, TextUtils.stripCodeFences(input));
  }

  @Test
  void testStripCodeFencesXmlBlockWithoutLanguageTag() {
    String input =
        """
        ```
        <root>
          <child>value</child>
        </root>
        ```""";
    String expected =
        """
        <root>
          <child>value</child>
        </root>""";
    assertEquals(expected, TextUtils.stripCodeFences(input));
  }

  @Test
  void testStripCodeFencesEmptyBlock() {
    String input =
        """
        ```
        ```""";
    String expected = "";
    assertEquals(expected, TextUtils.stripCodeFences(input));
  }

  @Test
  void testStripCodeFencesOnlyLeadingBackticks() {
    String input =
        """
        ```sql
        select * from mytable;""";
    String expected = "select * from mytable;";
    assertEquals(expected, TextUtils.stripCodeFences(input));
  }

  @Test
  void testStripCodeFencesOnlyTrailingBackticks() {
    String input =
        """
        select * from mytable;
        ```""";
    String expected = "select * from mytable;";
    assertEquals(expected, TextUtils.stripCodeFences(input));
  }

  @Test
  void testStripCodeFencesLeadingAndTrailingWhitespace() {
    String input =
        """

        ```sql
        select * from mytable;
        ```

        """;
    String expected = "select * from mytable;";
    assertEquals(expected, TextUtils.stripCodeFences(input));
  }

  @Test
  void testStripCodeFencesNoBackticks() {
    String input = "select * from mytable;";
    String expected = "select * from mytable;";
    assertEquals(expected, TextUtils.stripCodeFences(input));
  }

  @Test
  void testStripCodeFencesNullInput() {
    assertNull(TextUtils.stripCodeFences(null));
  }

  @Test
  void testWrapInCodeFences() {
    String input =
        """
        public String isNull(String input) {
          return input == null;
        }\
        """;

    String expected =
        """
        ```java
        public String isNull(String input) {
          return input == null;
        }
        ```
        """;

    assertEquals(expected, TextUtils.wrapInCodeFences(input, "java"));
  }

  @Test
  void testWrapInJsonCodeFences() {
    String input =
        """
        {
          "code": "BLUE",
          "name": "Blue"
        }\
        """;

    String expected =
        """
        ```json
        {
          "code": "BLUE",
          "name": "Blue"
        }
        ```
        """;

    assertEquals(expected, TextUtils.wrapInJsonCodeFences(input));
  }

  @Test
  void testTruncate() {
    String inputA = "ANC 1st, ANC 2nd and ANC 3rd visit coverage for last 12 months";
    String inputB = "Bonthe, Kailahun, Kambia and Moyamba for last 4 quarters";
    String inputC = "Last 12 months";

    assertEquals("ANC 1st, ANC 2nd and ANC 3rd visit cov..", TextUtils.truncate(inputA, 40, ".."));
    assertEquals("Bonthe, Kailahun, Kambia and..", TextUtils.truncate(inputB, 30, ".."));
    assertEquals("Last 12 months", TextUtils.truncate(inputC, 40, ".."));

    assertNull(TextUtils.truncate(null, 0, ".."));
    assertEquals("Last 12 months", TextUtils.truncate(inputC, -10, ".."));
  }

  @Test
  void testIsNull() {
    assertTrue(TextUtils.isNull(null));
    assertFalse(TextUtils.isNull(""));
  }
}
