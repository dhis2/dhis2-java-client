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

import java.util.Objects;
import java.util.regex.Pattern;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.helpers.MessageFormatter;

public class TextUtils {
  private static final Pattern PATTTERN_VARIABLE = Pattern.compile("^\\$\\{(.*?)\\}$");

  private static final String PATTERN_LEAD_TICKS = "^```[a-zA-Z0-9]*\\s*\\n?";
  private static final String PATTERN_TRAIL_TICKS = "\\n?\\s*```$";
  private static final String EMPTY = "";

  /**
   * Returns a formatted message string. The argument pattern is "{}".
   *
   * @param format the format string.
   * @param arguments the format arguments.
   * @return a formatted message string.
   */
  public static String format(String format, Object... arguments) {
    return MessageFormatter.arrayFormat(format, arguments).getMessage();
  }

  /**
   * Returns "false" if the given boolean is null or false, otherwise "true".
   *
   * @param bool the boolean.
   * @return "false" or "true".
   */
  public static String toTrueFalse(Boolean bool) {
    return bool != null && bool ? "true" : "false";
  }

  /**
   * Returns an empty string if the input is null, otherwise returns the input.
   *
   * @param input the input string.
   * @return an empty string if input is null, otherwise the input string.
   */
  public static String emptyIfNull(String input) {
    return ObjectUtils.firstNonNull(input, StringUtils.EMPTY);
  }

  /**
   * Indicates whether the input string is on variable format, for example: <code>${my_var_key}
   * </code>.
   *
   * @param input the input string.
   * @return true if variable, false if not.
   */
  public static boolean isVariable(String input) {
    if (StringUtils.isEmpty(input)) {
      return false;
    }

    return PATTTERN_VARIABLE.matcher(input).matches();
  }

  /**
   * Replaces the last instance of the given character with the given replacement character.
   *
   * @param builder the {@link StringBuilder}.
   * @param text the text to replace.
   * @param replacement the replacement text.
   */
  public static void replaceLast(StringBuilder builder, String text, String replacement) {
    int lastIndex = builder.lastIndexOf(text);
    if (lastIndex != -1) {
      builder.replace(lastIndex, lastIndex + text.length(), replacement);
    }
  }

  /**
   * Returns a string based on the predicate. If the predicate is true, it returns the true string,
   * otherwise, it returns the false string.
   *
   * @param predicate the boolean predicate.
   * @param trueString the true string.
   * @param falseString the false string.
   * @return a string.
   */
  public static String getString(boolean predicate, String trueString, String falseString) {
    return predicate ? trueString : falseString;
  }

  /**
   * Removes a substring if it is at the end of the input string, otherwise returns the input.
   *
   * @param input the input string.
   * @param remove the string to remove.
   * @return
   */
  public static String removeEnd(String input, String remove) {
    if (StringUtils.isEmpty(input) || StringUtils.isEmpty(remove)) {
      return input;
    }

    if (input.endsWith(remove)) {
      return input.substring(0, input.length() - remove.length());
    }

    return input;
  }

  /**
   * Strips code block fences from a string. Handles leading ``` with optional format (sql, json)
   * and optional newline, and trailing ``` optionally preceded by a newline.
   *
   * @param input the input string with code fences.
   * @return the unwrapped content, or input string if not wrapped.
   */
  public static String stripCodeFences(String input) {
    if (StringUtils.isEmpty(input)) {
      return null;
    }

    String trimmed = input.trim();
    trimmed = trimmed.replaceFirst(PATTERN_LEAD_TICKS, EMPTY);
    trimmed = trimmed.replaceFirst(PATTERN_TRAIL_TICKS, EMPTY);

    return trimmed.strip();
  }

  /**
   * Wraps the input string in code fences with the specified language.
   *
   * @param input the input string.
   * @param language the language, e.g. "json", "java".
   * @return the input string wrapped in code fences with the specified language.
   */
  public static String wrapInCodeFences(String input, String language) {
    Objects.requireNonNull(language);
    if (isNull(input)) {
      return input;
    }

    return String.format(
        """
        ```%s
        %s
        ```
        """,
        language, input);
  }

  /**
   * Truncates the input string to the specified maximum length, appending the specified ellipsis.
   *
   * @param input the input string.
   * @param maxLength the max length.
   * @param ellipsis the ellipsis string to be appended if input length is greater than max length.
   * @return the possibly truncated input string.
   */
  public static String truncate(String input, int maxLength, String ellipsis) {
    if (StringUtils.isEmpty(input) || maxLength <= 0 || ellipsis == null) {
      return input;
    }

    if (input.length() > maxLength) {
      int length = maxLength - ellipsis.length();
      input = input.substring(0, length) + ellipsis;
    }

    return input;
  }

  /**
   * Wraps the input string in code fences with {@code json} as language.
   *
   * @param input the input string.
   * @return the input string wrapped in code fences with {@code json} as language.
   */
  public static String wrapInJsonCodeFences(String input) {
    return wrapInCodeFences(input, "json");
  }

  /**
   * Indicates whether the input string is null.
   *
   * @param input the input string.
   * @return true if the input is null, false otherwise.
   */
  public static boolean isNull(String input) {
    return input == null;
  }

  /**
   * Creates a new {@link ToStringBuilder} with the given object and a short prefix style.
   *
   * @param object the object to build the string representation for.
   * @return a {@link ToStringBuilder}.
   */
  public static ToStringBuilder newToStringBuilder(Object object) {
    return new ToStringBuilder(object, ToStringStyle.SHORT_PREFIX_STYLE);
  }

  /**
   * Creates a new {@link ToStringBuilder} with the given object and a short prefix style.
   *
   * @param object the object to build the string representation for.
   * @param parentToString the result of calling <code>toString</code> for the parent object.
   * @return a {@link ToStringBuilder}.
   */
  public static ToStringBuilder newToStringBuilder(Object object, String parentToString) {
    return newToStringBuilder(object).appendSuper(parentToString);
  }
}
