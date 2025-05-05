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

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PeriodTypes {
  public static final List<PeriodType> PERIOD_TYPES =
      List.of(
          new PeriodType("Daily", 1, "P1D", "yyyyMMdd"),
          new PeriodType("Weekly", 7, "P7D", "yyyyWn"),
          new PeriodType("WeeklyWednesday", 7, "P7D", "yyyyWedWn"),
          new PeriodType("WeeklyThursday", 7, "P7D", "yyyyThuWn"),
          new PeriodType("WeeklySaturday", 7, "P7D", "yyyySatWn"),
          new PeriodType("WeeklySunday", 7, "P7D", "yyyySunWn"),
          new PeriodType("BiWeekly", 14, "P14D", "yyyyBiWn"),
          new PeriodType("Monthly", 30, "P1M", "yyyyMM"),
          new PeriodType("BiMonthly", 61, "P2M", "yyyyMMB"),
          new PeriodType("Quarterly", 91, "P3M", "yyyyQn"),
          new PeriodType("QuarterlyNov", 91, "P3M", "yyyyNovQn"),
          new PeriodType("SixMonthly", 182, "P6M", "yyyySn"),
          new PeriodType("SixMonthlyApril", 182, "P6M", "yyyyAprilSn"),
          new PeriodType("SixMonthlyNov", 182, "P6M", "yyyyNovSn"),
          new PeriodType("Yearly", 365, "P1Y", "yyyy"),
          new PeriodType("FinancialApril", 365, "P1Y", "yyyyApril"),
          new PeriodType("FinancialJuly", 365, "P1Y", "yyyyJuly"),
          new PeriodType("FinancialOct", 365, "P1Y", "yyyyOct"),
          new PeriodType("FinancialNov", 365, "P1Y", "yyyyNov"));
}
