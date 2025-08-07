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

import static org.apache.commons.lang3.StringUtils.trimToEmpty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/** Utilities for configuration. */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfigUtils {
  private static final char VALUE_SEPARATOR = ',';

  private static final String VALUE_ENABLED = "on";
  private static final String VALUE_DISABLED = "off";

  /**
   * Splits the given value on {@code ,} and returns the values as an immutable list. Trims each
   * value and filters out null and empty values.
   *
   * @param value the value.
   * @return the values as a {@link List}.
   */
  public static List<String> getAsList(String value) {
    List<String> values =
        new ArrayList<String>(
            Arrays.asList(StringUtils.split(trimToEmpty(value), VALUE_SEPARATOR)));

    return values.stream()
        .map(String::trim)
        .filter(StringUtils::isNotEmpty)
        .collect(Collectors.toList());
  }

  /**
   * Splits the given value on {@code ,} and returns the values as an immutable list of integers.
   * Trims each value, filters out empty values and values which cannot be converted to integer.
   *
   * @param value the value.
   * @return the values as a {@link List} of {@link Integer}.
   */
  public static List<Integer> getAsIntList(String value) {
    return getAsList(value).stream()
        .filter(NumberUtils::isInteger)
        .map(NumberUtils::toInteger)
        .toList();
  }

  /**
   * Splits the given value on {@code,} and returns the values as an immutable set. Trims each value
   * and filters out null and empty values.
   *
   * @param value the value.
   * @return the values as a {@link Set}.
   */
  public static Set<String> getAsSet(String value) {
    List<String> values =
        new ArrayList<>(Arrays.asList(StringUtils.split(trimToEmpty(value), VALUE_SEPARATOR)));

    return values.stream()
        .map(String::trim)
        .filter(StringUtils::isNotEmpty)
        .collect(Collectors.toUnmodifiableSet());
  }

  /**
   * Splits the given value on {@code ,} and returns the values as an array. Trims each value and
   * filters out null and empty values.
   *
   * @param value the value.
   * @return the values as an array.
   */
  public static String[] getAsArray(String value) {
    List<String> values = getAsList(value);

    return values.toArray(String[]::new);
  }

  /**
   * Checks if the given property value is enabled.
   *
   * @param value the property value to check.
   * @return true if the value is "on", false otherwise.
   */
  public static boolean isEnabled(String value) {
    return VALUE_ENABLED.equals(value);
  }

  /**
   * Checks if the given property value is not enabled, meaning it is either null, empty or not
   * equal to the enabled value.
   *
   * @param value the property value to check.
   * @return true if the value is not enabled, false otherwise.
   */
  public static boolean isNotEnabled(String value) {
    return !isEnabled(value);
  }

  /**
   * Checks if the given property value is disabled.
   *
   * @param value the property value to check.
   * @return true if the value is "off", false otherwise.
   */
  public static boolean isDisabled(String value) {
    return VALUE_DISABLED.equals(value);
  }
}
