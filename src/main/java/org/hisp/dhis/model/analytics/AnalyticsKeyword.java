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
package org.hisp.dhis.model.analytics;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AnalyticsKeyword {
  /** User org unit. */
  public static final String USER_ORGUNIT = "USER_ORGUNIT";

  /** Children of user org unit. */
  public static final String USER_ORGUNIT_CHILDREN = "USER_ORGUNIT_CHILDREN";

  /** Grand children of user org unit. */
  public static final String USER_ORGUNIT_GRANDCHILDREN = "USER_ORGUNIT_GRANDCHILDREN";

  /** Org unit level prefix. */
  public static final String ORG_UNIT_LEVEL_PREFIX = "LEVEL-";

  /** Org unit group prefix. */
  public static final String ORG_UNIT_GROUP_PREFIX = "OU_GROUP-";

  /** Data element group prefix. */
  public static final String DATA_ELEMENT_GROUP_PREFIX = "DE_GROUP-";

  /**
   * Returns the org unit level keyword for the given level.
   *
   * @param level the level.
   * @return the org unit level keyword.
   */
  public static final String getOrgUnitLevel(int level) {
    return ORG_UNIT_LEVEL_PREFIX + level;
  }

  /**
   * Returns the org unit level keyword for the given identifier string.
   *
   * @param id the org unit level identifier.
   * @return the org unit level keyword.
   */
  public static final String getOrgUnitLevel(String id) {
    return ORG_UNIT_LEVEL_PREFIX + id;
  }

  /**
   * Returns the org unit group keyword for the given id.
   *
   * @param id the org unit group id.
   * @return the org unit group keyword.
   */
  public static final String getOrgUnitGroup(String id) {
    return ORG_UNIT_GROUP_PREFIX + id;
  }

  /**
   * Returns the data element group keyword for the given id.
   *
   * @param id the data element group id.
   * @return the data element group keyword.
   */
  public static final String getDataElementGroup(String id) {
    return DATA_ELEMENT_GROUP_PREFIX + id;
  }
}
