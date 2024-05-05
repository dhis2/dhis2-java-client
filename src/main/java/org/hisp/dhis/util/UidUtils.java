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

import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UidUtils {
  private static final String letters = "abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

  private static final String ALLOWED_CHARS = "0123456789" + letters;

  private static final int NUMBER_OF_CODEPOINTS = ALLOWED_CHARS.length();

  private static final Pattern UID_PATTERN = Pattern.compile("^[a-zA-Z]{1}[a-zA-Z0-9]{10}$");

  private static final int CODESIZE = 11;

  /**
   * Generates a UID according to the following rules:
   *
   * <ul>
   *   <li>Alphanumeric characters only.
   *   <li>Exactly 11 characters long.
   *   <li>First character is alphabetic.
   * </ul>
   *
   * @return a UID.
   */
  public static String generateUid() {
    return generateCode(CODESIZE);
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
   * @param codeSize the number of characters in the code.
   * @return the code.
   */
  public static String generateCode(int codeSize) {
    ThreadLocalRandom r = ThreadLocalRandom.current();

    char[] randomChars = new char[codeSize];

    // First char should be a letter

    randomChars[0] = letters.charAt(r.nextInt(letters.length()));

    for (int i = 1; i < codeSize; ++i) {
      randomChars[i] = ALLOWED_CHARS.charAt(r.nextInt(NUMBER_OF_CODEPOINTS));
    }

    return new String(randomChars);
  }
}
