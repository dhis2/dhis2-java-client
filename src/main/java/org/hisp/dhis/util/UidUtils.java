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
package org.hisp.dhis.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hisp.dhis.model.exception.IllegalArgumentFormatException;

/** Utilities for DHIS2 UID generation. */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UidUtils {
  private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final String DIGITS = "0123456789";
  private static final String ALLOWED_CHARS = ALPHABET + DIGITS;

  private static final int CHAR_LENGTH = ALLOWED_CHARS.length();
  private static final int UID_LENGTH = 11;

  private static final Pattern UID_PATTERN = Pattern.compile("^[a-zA-Z]{1}[a-zA-Z0-9]{10}$");

  /**
   * Generates a DHIS2 UID according to the following rules.
   *
   * <ul>
   *   <li>Alphanumeric characters only.
   *   <li>Exactly 11 characters long.
   *   <li>First character is alphabetic.
   * </ul>
   *
   * @return a DHIS2 UID string.
   */
  public static String generateUid() {
    return generateCode(UID_LENGTH);
  }

  /**
   * Generates the given number of DHIS2 UIDs.
   *
   * @param n the number of UIDs to generate.
   * @return a list of DHIS2 UID strings.
   */
  public static List<String> generateUids(int n) {
    return IntStream.range(0, n).mapToObj(i -> generateUid()).toList();
  }

  /**
   * Tests whether the given code is a valid UID.
   *
   * @param code the code to validate.
   * @return true if the code is valid.
   */
  public static boolean isValidUid(String code) {
    return code != null && UID_PATTERN.matcher(code).matches();
  }

  /**
   * Generates a pseudo random string with alphanumeric characters.
   *
   * @param length the number of characters in the code.
   * @return the code.
   */
  public static String generateCode(int length) {
    ThreadLocalRandom rand = ThreadLocalRandom.current();

    char[] randomChars = new char[length];

    // First char must be a letter
    randomChars[0] = ALPHABET.charAt(rand.nextInt(ALPHABET.length()));

    for (int i = 1; i < length; ++i) {
      randomChars[i] = ALLOWED_CHARS.charAt(rand.nextInt(CHAR_LENGTH));
    }

    return new String(randomChars);
  }

  /**
   * Generates a DHIS2 UID from an input string. The algorithm is deterministic and minimizes risk
   * of collisions. The input must be between 2 and 1024 characters long.
   *
   * @param input the input string.
   * @return a DHIS2 UID. Returns null if the input is invalid, empty string if input is blank.
   * @throws IllegalArgumentException if the input string is invalid.
   */
  public static String toUid(String input) {
    if (input == null) {
      return null;
    }
    if (input.isBlank()) {
      return StringUtils.EMPTY;
    }

    if (input.length() < 2 || input.length() > 1024) {
      throw new IllegalArgumentFormatException(
          "Input string must be between 3 and 1024 characters long: {}", input.length());
    }

    try {
      // Hash input string using SHA-256
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

      // Convert hash to a BigInteger
      BigInteger bigInteger = new BigInteger(1, hashBytes);

      // Convert BigInteger to Base62
      String base62 = fromBigInteger(bigInteger, ALPHABET, UID_LENGTH);

      // Ensure the UID starts with a letter
      if (Character.isDigit(base62.charAt(0))) {
        // If first character is a digit, shift Base62 string by one character by moving first char
        // to the end and append 'A'
        base62 = base62.substring(1) + ALPHABET.charAt(0);
      }
      return base62;

    } catch (NoSuchAlgorithmException ex) {
      throw new IllegalArgumentFormatException("SHA-256 algorithm not found", ex);
    }
  }

  /**
   * Converts a BigInteger to a Base62 string of a specified length.
   *
   * @param value the BigInteger to convert.
   * @param alphabet the Base62 alphabet.
   * @param length the desired length of the Base62 string.
   * @return a Base62 string of the specified length.
   */
  private static String fromBigInteger(BigInteger value, String alphabet, int length) {
    StringBuilder sb = new StringBuilder();
    BigInteger base = BigInteger.valueOf(alphabet.length());

    for (int i = 0; i < length; i++) {
      BigInteger[] qr = value.divideAndRemainder(base);
      value = qr[0];
      BigInteger remainder = qr[1];
      sb.insert(0, alphabet.charAt(remainder.intValue()));
    }

    return sb.toString();
  }
}
