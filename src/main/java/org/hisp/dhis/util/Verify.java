package org.hisp.dhis.util;

import org.apache.commons.lang3.StringUtils;
import org.hisp.dhis.response.Dhis2ClientException;
import org.hisp.dhis.response.HttpStatus;

/**
 * Utility class for verifying conditions and throwing exceptions if the conditions are not met.
 */
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
