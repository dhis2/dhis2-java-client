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

import java.util.Set;

/** Enumeration for aggregation type. */
public enum AggregationType {
  /** Sum of values. */
  SUM,
  /** Average of values. */
  AVERAGE,
  /** Average of sums across organizational units. */
  AVERAGE_SUM_ORG_UNIT,
  /** Last value in the set. */
  LAST,
  /** Last average calculated for each organizational unit. */
  LAST_AVERAGE_ORG_UNIT,
  /** Last value for each organizational unit. */
  LAST_LAST_ORG_UNIT,
  /** Last value within a specified period. */
  LAST_IN_PERIOD,
  /** Average of the last values within each organizational unit in a specified period. */
  LAST_IN_PERIOD_AVERAGE_ORG_UNIT,
  /** First value in the set. */
  FIRST,
  /** First average calculated for each organizational unit. */
  FIRST_AVERAGE_ORG_UNIT,
  /** First value for each organizational unit. */
  FIRST_FIRST_ORG_UNIT,
  /** Count of values. */
  COUNT,
  /** Standard deviation of values. */
  STDDEV,
  /** Variance of values. */
  VARIANCE,
  /** Minimum value in the set. */
  MIN,
  /** Maximum value in the set. */
  MAX,
  /** Minimum of sums across organizational units. */
  MIN_SUM_ORG_UNIT,
  /** Maximum of sums across organizational units. */
  MAX_SUM_ORG_UNIT,
  /** No aggregation. */
  NONE,
  /** Custom aggregation, defined elsewhere. */
  CUSTOM,
  /** Default aggregation, specifics depend on context. */
  DEFAULT;

  /** Average aggregation types. */
  public static final Set<AggregationType> AVERAGE_TYPES =
      Set.of(AVERAGE, AVERAGE_SUM_ORG_UNIT, LAST_AVERAGE_ORG_UNIT);

  /** Last aggregation types. */
  public static final Set<AggregationType> LAST_TYPES = Set.of(LAST, LAST_AVERAGE_ORG_UNIT);

  /**
   * Indicates whether the aggregation type is average.
   *
   * @return true if average.
   */
  public boolean isAverage() {
    return AVERAGE_TYPES.contains(this);
  }
}
