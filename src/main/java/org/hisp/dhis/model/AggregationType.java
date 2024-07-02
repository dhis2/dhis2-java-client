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
  SUM,
  AVERAGE,
  AVERAGE_SUM_ORG_UNIT,
  LAST,
  LAST_AVERAGE_ORG_UNIT,
  LAST_LAST_ORG_UNIT,
  LAST_IN_PERIOD,
  LAST_IN_PERIOD_AVERAGE_ORG_UNIT,
  FIRST,
  FIRST_AVERAGE_ORG_UNIT,
  FIRST_FIRST_ORG_UNIT,
  COUNT,
  STDDEV,
  VARIANCE,
  MIN,
  MAX,
  MIN_SUM_ORG_UNIT,
  MAX_SUM_ORG_UNIT,
  NONE,
  CUSTOM,
  DEFAULT;

  public static final Set<AggregationType> AVERAGE_TYPES =
      Set.of(AVERAGE, AVERAGE_SUM_ORG_UNIT, LAST_AVERAGE_ORG_UNIT);

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
