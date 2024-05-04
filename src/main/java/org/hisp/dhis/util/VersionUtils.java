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

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VersionUtils {
  private static final Pattern PATTERN_MAJOR_VERSION = Pattern.compile("\\d\\.(\\d{2}).*$");

  private static final Pattern PATTERN_PATCH_VERSION = Pattern.compile("\\d\\.\\d{2}\\.(\\d+).*$");

  /**
   * Returns the major version as an integer based on the given version string.
   *
   * @param version the version string.
   * @return the major version.
   */
  public static Integer getMajorVersion(String version) {
    return asInt(getFirstGroup(PATTERN_MAJOR_VERSION, orEmpty(version)));
  }

  /**
   * Returns the patch version as an integer based on the given version string.
   *
   * @param version the version string.
   * @return the patch version.
   */
  public static Integer getPatchVersion(String version) {
    return asInt(getFirstGroup(PATTERN_PATCH_VERSION, orEmpty(version)));
  }

  private static Integer asInt(String string) {
    return string != null ? Integer.valueOf(string) : null;
  }

  private static String getFirstGroup(Pattern pattern, String string) {
    Matcher matcher = pattern.matcher(string);
    return matcher.matches() ? matcher.group(1) : null;
  }

  private static String orEmpty(String string) {
    return ObjectUtils.firstNonNull(string, StringUtils.EMPTY);
  }
}
