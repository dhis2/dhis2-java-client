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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
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
  void testGetStringTrueFalse() {
    assertEquals("exists", TextUtils.getString(true, "exists", "does not exist"));
    assertEquals("does not exist", TextUtils.getString(false, "exists", "does not exist"));
  }

  @Test
  void testGetStringTrue() {
    assertEquals("exists", TextUtils.getString(true, "exists"));
    assertNull(TextUtils.getString(false, "exists"));
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

  @Test
  void testJoin() {
    assertEquals("one::two::three", TextUtils.join("::", "one", "two", "three"));
    assertEquals("one::three", TextUtils.join("::", "one", "", "three"));
    assertEquals("one::three", TextUtils.join("::", "one", null, "three"));
    assertEquals("", TextUtils.join("::", ""));
  }

  @Test
  void testGetReferenceValid() {
    assertEquals(
        "ONE,TWO,THREE",
        TextUtils.getReference("Data element [KNOWN_VALUES:ONE,TWO,THREE]", "KNOWN_VALUES"));
    assertEquals(
        "ACTIVE", TextUtils.getReference("Status is [KNOWN_VALUES:ACTIVE]", "KNOWN_VALUES"));
    assertEquals(
        "VALUE ONE, VALUE TWO",
        TextUtils.getReference("Check [KNOWN_VALUES: VALUE ONE, VALUE TWO ]", "KNOWN_VALUES"));
    assertEquals("123", TextUtils.getReference("Prefix [KNOWN_VALUES:123] Suffix", "KNOWN_VALUES"));
    String multiText = "First [OTHER_KEY:VAL_A] and target [KNOWN_VALUES:TARGET_VAL]";
    assertEquals("TARGET_VAL", TextUtils.getReference(multiText, "KNOWN_VALUES"));
  }

  @Test
  void testGetReferenceInvalid() {
    // Space between opening bracket and key
    assertNull(TextUtils.getReference("[ KNOWN_VALUES:ONE,TWO]", "KNOWN_VALUES"));
    // Space between key and colon
    assertNull(TextUtils.getReference("[KNOWN_VALUES :ONE,TWO]", "KNOWN_VALUES"));
    // Space before key and around colon
    assertNull(TextUtils.getReference("[ KNOWN_VALUES : ONE,TWO]", "KNOWN_VALUES"));
    // Missing closing bracket
    assertNull(TextUtils.getReference("Data element [KNOWN_VALUES:ONE,TWO", "KNOWN_VALUES"));
    // Missing opening bracket
    assertNull(TextUtils.getReference("Data element KNOWN_VALUES:ONE,TWO]", "KNOWN_VALUES"));
    // Missing colon
    assertNull(TextUtils.getReference("[KNOWN_VALUES ONE,TWO]", "KNOWN_VALUES"));
    // Empty value after colon
    assertNull(TextUtils.getReference("[KNOWN_VALUES:]", "KNOWN_VALUES"));
    // Key mismatch
    assertNull(TextUtils.getReference("[OTHER_KEY:ONE,TWO]", "KNOWN_VALUES"));
    // Null and blank arguments
    assertNull(TextUtils.getReference(null, "KNOWN_VALUES"));
    assertNull(TextUtils.getReference("", "KNOWN_VALUES"));
    assertNull(TextUtils.getReference("   ", "KNOWN_VALUES"));
    assertNull(TextUtils.getReference("[KNOWN_VALUES:ONE]", null));
    assertNull(TextUtils.getReference("[KNOWN_VALUES:ONE]", ""));
    assertNull(TextUtils.getReference("[KNOWN_VALUES:ONE]", "   "));
  }

  @Test
  void testToList() {
    assertEquals(List.of("a", "b", "c"), TextUtils.toList("a,b,c", ","));
    assertEquals(List.of("a", "b", "c"), TextUtils.toList(" a , b , c ", ","));
    assertEquals(List.of("a", "b"), TextUtils.toList("a, ,b", ","));
    assertEquals(List.of("a", "b", "c"), TextUtils.toList(",a,b,c,", ","));
    assertEquals(List.of("a", "b", "c"), TextUtils.toList("a;b,c", ";,"));
    assertEquals(List.of("abc"), TextUtils.toList("abc", ","));
    assertEquals(List.of(), TextUtils.toList("", ","));
    assertEquals(List.of(), TextUtils.toList("   ", ","));
    assertThrows(NullPointerException.class, () -> TextUtils.toList(null, ","));
  }
}
