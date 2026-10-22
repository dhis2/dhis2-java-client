/*
 * Copyright (c) 2004-2026, University of Oslo
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
package org.hisp.dhis.model.period;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RelativePeriods {
  @JsonProperty private boolean thisDay;

  @JsonProperty private boolean yesterday;

  @JsonProperty private boolean last3Days;

  @JsonProperty private boolean last7Days;

  @JsonProperty private boolean last14Days;

  @JsonProperty private boolean last30Days;

  @JsonProperty private boolean last60Days;

  @JsonProperty private boolean last90Days;

  @JsonProperty private boolean last180Days;

  @JsonProperty private boolean thisMonth;

  @JsonProperty private boolean lastMonth;

  @JsonProperty private boolean thisBimonth;

  @JsonProperty private boolean lastBimonth;

  @JsonProperty private boolean thisQuarter;

  @JsonProperty private boolean lastQuarter;

  @JsonProperty private boolean thisSixMonth;

  @JsonProperty private boolean lastSixMonth;

  @JsonProperty private boolean weeksThisYear;

  @JsonProperty private boolean monthsThisYear;

  @JsonProperty private boolean biMonthsThisYear;

  @JsonProperty private boolean quartersThisYear;

  @JsonProperty private boolean thisYear;

  @JsonProperty private boolean monthsLastYear;

  @JsonProperty private boolean quartersLastYear;

  @JsonProperty private boolean lastYear;

  @JsonProperty private boolean last5Years;

  @JsonProperty private boolean last10Years;

  @JsonProperty private boolean last12Months;

  @JsonProperty private boolean last6Months;

  @JsonProperty private boolean last3Months;

  @JsonProperty private boolean last6BiMonths;

  @JsonProperty private boolean last4Quarters;

  @JsonProperty private boolean last2SixMonths;

  @JsonProperty private boolean thisFinancialYear;

  @JsonProperty private boolean lastFinancialYear;

  @JsonProperty private boolean last5FinancialYears;

  @JsonProperty private boolean last10FinancialYears;

  @JsonProperty private boolean thisWeek;

  @JsonProperty private boolean lastWeek;

  @JsonProperty private boolean thisBiWeek;

  @JsonProperty private boolean lastBiWeek;

  @JsonProperty private boolean last4Weeks;

  @JsonProperty private boolean last4BiWeeks;

  @JsonProperty private boolean last12Weeks;

  @JsonProperty private boolean last52Weeks;
}
