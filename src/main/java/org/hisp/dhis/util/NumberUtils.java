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

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/** Utilities for numbers. */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NumberUtils {
  /**
   * Scales (rounds) the given double value to the given scale.
   *
   * @param value the double value.
   * @param scale the scale or decimals.
   * @return the scaled value.
   */
  public static double round(double value, int scale) {
    return new BigDecimal(value).setScale(scale, RoundingMode.HALF_UP).doubleValue();
  }

  /**
   * Converts the given integer to a string.
   *
   * @param number the integer.
   * @return a string.
   */
  public static String toString(Integer number) {
    return number == null ? null : String.valueOf(number);
  }

  /**
   * Converts the given double to a string.
   *
   * @param number the integer.
   * @return a string.
   */
  public static String toString(Double number) {
    return number == null ? null : String.valueOf(number);
  }

  /**
   * Converts the given string to an integer.
   *
   * @param string the string.
   * @return an integer.
   */
  public static Integer toInteger(String string) {
    return org.apache.commons.lang3.math.NumberUtils.createInteger(string);
  }

  /**
   * Returns the int value of the given {@link Integer}, or 0 if null.
   *
   * @param integer the {@link Integer}.
   * @return the int value.
   */
  public static int toInt(Integer integer) {
    return integer != null ? integer.intValue() : 0;
  }

  /**
   * Converts the given string to an double.
   *
   * @param string the string.
   * @return an integer.
   */
  public static Double toDouble(String string) {
    return org.apache.commons.lang3.math.NumberUtils.createDouble(string);
  }
}
