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
package org.hisp.dhis.model;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import org.apache.maven.artifact.versioning.ComparableVersion;

@EqualsAndHashCode
public class SystemVersion implements Serializable {
  private final String version;

  /**
   * Constructor.
   *
   * @param version the version string.
   */
  public SystemVersion(String version) {
    this.version = version;
  }

  /**
   * Returns the version as a string.
   *
   * @return the version string.
   */
  public String version() {
    return version;
  }

  /**
   * Creates a {@link SystemVersion} from the given version string.
   *
   * @param version the version string.
   * @return a {@link SystemVersion}.
   */
  public static SystemVersion of(String version) {
    return new SystemVersion(version);
  }

  /**
   * Indicates whether this system version is higher than the given version.
   *
   * @param version the version to compare with.
   * @return true if higher, false if not.
   */
  public boolean isHigher(String version) {
    return compareThisTo(version) > 0;
  }

  /**
   * Indicates whether this system version is higher than or equal to the given version.
   *
   * @param version the version to compare with.
   * @return true if higher or equal, false if not.
   */
  public boolean isHigherOrEqual(String version) {
    return compareThisTo(version) >= 0;
  }

  /**
   * Indicates whether this system version is equal to the given version.
   *
   * @param version the version to compare with.
   * @return true if equal, false if not.
   */
  public boolean isEqual(String version) {
    return compareThisTo(version) == 0;
  }

  /**
   * Indicates whether this system version is lower than or equal to the given version.
   *
   * @param version the version to compare with.
   * @return true if lower or equal, false if not.
   */
  public boolean isLowerOrEqual(String version) {
    return compareThisTo(version) <= 0;
  }

  /**
   * Indicates whether this system version is lower than the given version.
   *
   * @param version the version to compare with.
   * @return true if lower, false if not.
   */
  public boolean isLower(String version) {
    return compareThisTo(version) < 0;
  }

  /**
   * Compares this version against the given version.
   *
   * @param version the version.
   * @return the outcome of <code>compareTo</code>.
   */
  private int compareThisTo(String version) {
    return new ComparableVersion(this.version).compareTo(new ComparableVersion(version));
  }
}
