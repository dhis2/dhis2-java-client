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

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hisp.dhis.response.Dhis2ClientException;
import org.hisp.dhis.response.HttpStatus;

/** Utility class for verifying conditions and throwing exceptions if the conditions are not met. */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Verify {
  /**
   * Checks if the given object is not null, and throws a {@link Dhis2ClientException} with the
   * given message if it is.
   *
   * @param object the object to check.
   * @param format the message format.
   * @param arguments the arguments for the message format.
   * @return the object if it is not null.
   */
  public static <T> T notNull(T object, String format, Object... arguments) {
    test(object != null, format, arguments);
    return object;
  }

  /**
   * Checks if the given string is not empty, and throws a {@link Dhis2ClientException} with the
   * given message if it is.
   *
   * @param string the string to check.
   * @param format the message format.
   * @param arguments the arguments for the message format.
   * @return the string if it is not empty.
   */
  public static String notEmpty(String string, String format, Object... arguments) {
    test(StringUtils.isNotEmpty(string), format, arguments);
    return string;
  }

  /**
   * Checks if the given string is not blank, and throws a {@link Dhis2ClientException} with the
   * given message if it is.
   *
   * @param string the string to check.
   * @param format the message format.
   * @param arguments the arguments for the message format.
   * @return the string if it is not blank.
   */
  public static String notBlank(String string, String format, Object... arguments) {
    test(StringUtils.isNotBlank(string), format, arguments);
    return string;
  }

  /**
   * Checks if the given expression is false, and throws a {@link Dhis2ClientException} with the
   * given message if it is.
   *
   * @param expression the expression to check.
   * @param format the message format.
   * @param arguments the arguments for the message format.
   */
  private static void test(boolean expression, String format, Object... arguments) {
    if (!expression) {
      throwException(format, arguments);
    }
  }

  /**
   * Throws a {@link Dhis2ClientException} with the given message.
   *
   * @param format the message format.
   * @param arguments the arguments for the message format.
   */
  private static void throwException(String format, Object... arguments) {
    String message = TextUtils.format(format, arguments);
    throw new Dhis2ClientException(message, HttpStatus.BAD_REQUEST);
  }
}
