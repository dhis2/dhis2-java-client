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
package org.hisp.dhis.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RelativePeriod {
  TODAY("Today", 1),
  YESTERDAY("Yesterday", 1),
  LAST_3_DAYS("Last 3 days", 3),
  LAST_7_DAYS("Last 7 days", 7),
  LAST_14_DAYS("Last 14 days", 14),
  LAST_30_DAYS("Last 30 days", 30),
  LAST_60_DAYS("Last 60 days", 60),
  LAST_90_DAYS("Last 90 days", 90),
  LAST_180_DAYS("Last 180 days", 180),
  THIS_MONTH("This month", 1),
  THIS_BIMONTH("This bi-month", 1),
  LAST_BIMONTH("Last bi-month", 1),
  THIS_QUARTER("This quarter", 1),
  LAST_QUARTER("Last quarter", 1),
  THIS_SIX_MONTH("This six-month", 1),
  LAST_SIX_MONTH("Last six-month", 1),
  WEEKS_THIS_YEAR("Weeks this year", 52),
  MONTHS_THIS_YEAR("Months this year", 12),
  BIMONTHS_THIS_YEAR("Bi-months this year", 6),
  QUARTERS_THIS_YEAR("Quarters this year", 4),
  THIS_YEAR("This year", 1),
  MONTHS_LAST_YEAR("Months last year", 12),
  QUARTERS_LAST_YEAR("Quarters last year", 4),
  LAST_YEAR("Last year", 1),
  LAST_5_YEARS("Last 5 years", 5),
  LAST_10_YEARS("Last 10 years", 10),
  LAST_12_MONTHS("Last 12 months", 12),
  LAST_6_MONTHS("Last 6 months", 6),
  LAST_3_MONTHS("Last 3 months", 3),
  LAST_6_BIMONTHS("Last 6 bi-months", 6),
  LAST_4_QUARTERS("Last 4 quarters", 4),
  LAST_2_SIXMONTHS("Last 2 six-months", 2),
  THIS_FINANCIAL_YEAR("This financial year", 1),
  LAST_FINANCIAL_YEAR("Last financial year", 1),
  LAST_5_FINANCIAL_YEARS("Last 5 financial years", 5),
  LAST_10_FINANCIAL_YEARS("Last 10 financial years", 10),
  THIS_WEEK("This week", 1),
  LAST_WEEK("Last week", 1),
  THIS_BIWEEK("This bi-week", 1),
  LAST_BIWEEK("Last bi-week", 1),
  LAST_4_WEEKS("Last 4 weeks", 4),
  LAST_4_BIWEEKS("Last 4 bi-weeks", 4),
  LAST_12_WEEKS("Last 12 weeks", 12),
  LAST_52_WEEKS("Last 52 weeks", 52);

  private final String name;

  private final int periods;
}
