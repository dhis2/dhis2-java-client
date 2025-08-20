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
  TODAY("Today"),
  YESTERDAY("Yesterday"),
  LAST_3_DAYS("Last 3 days"),
  LAST_7_DAYS("Last 7 days"),
  LAST_14_DAYS("Last 14 days"),
  LAST_30_DAYS("Last 30 days"),
  LAST_60_DAYS("Last 60 days"),
  LAST_90_DAYS("Last 90 days"),
  LAST_180_DAYS("Last 180 days"),
  THIS_MONTH("This month"),
  THIS_BIMONTH("This bi-month"),
  LAST_BIMONTH("Last bi-month"),
  THIS_QUARTER("This quarter"),
  LAST_QUARTER("Last quarter"),
  THIS_SIX_MONTH("This six-month"),
  LAST_SIX_MONTH("Last six-month"),
  WEEKS_THIS_YEAR("Weeks this year"),
  MONTHS_THIS_YEAR("Months this year"),
  BIMONTHS_THIS_YEAR("Bi-months this year"),
  QUARTERS_THIS_YEAR("Quarters this year"),
  THIS_YEAR("This year"),
  MONTHS_LAST_YEAR("Months last year"),
  QUARTERS_LAST_YEAR("Quarters last year"),
  LAST_YEAR("Last year"),
  LAST_5_YEARS("Last 5 years"),
  LAST_10_YEARS("Last 10 years"),
  LAST_12_MONTHS("Last 12 months"),
  LAST_6_MONTHS("Last 6 months"),
  LAST_3_MONTHS("Last 3 months"),
  LAST_6_BIMONTHS("Last 6 bi-months"),
  LAST_4_QUARTERS("Last 4 quarters"),
  LAST_2_SIXMONTHS("Last 2 six-months"),
  THIS_FINANCIAL_YEAR("This financial year"),
  LAST_FINANCIAL_YEAR("Last financial year"),
  LAST_5_FINANCIAL_YEARS("Last 5 financial years"),
  LAST_10_FINANCIAL_YEARS("Last 10 financial years"),
  THIS_WEEK("This week"),
  LAST_WEEK("Last week"),
  THIS_BIWEEK("This bi-week"),
  LAST_BIWEEK("Last bi-week"),
  LAST_4_WEEKS("Last 4 weeks"),
  LAST_4_BIWEEKS("Last 4 bi-weeks"),
  LAST_12_WEEKS("Last 12 weeks"),
  LAST_52_WEEKS("Last 52 weeks");

  private final String name;
}
