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

import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateTimeUtils {
  private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

  private static final String DATE_FORMAT = "yyyy-MM-dd";

  /**
   * Returns a date.
   *
   * @param year the year.
   * @param month the month, from 1.
   * @param dayOfMonth, from 1 to 31.
   * @return a {@link Date}.
   */
  public static Date getDate(int year, int month, int dayOfMonth) {
    LocalDate localDate = LocalDate.of(year, month, dayOfMonth);

    return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
  }

  /**
   * Returns a date string on the format: <code>yyyy-MM-dd</code>.
   *
   * @param date the {@link Date}.
   * @return a date string.
   */
  public static String getDateString(Date date) {
    return new SimpleDateFormat(DATE_FORMAT).format(date);
  }

  /**
   * Returns a {@link LocalDate} based on the given string. The string must be on the ISO local date
   * format, e.g. <code>2007-12-03</code>.
   *
   * @param string the date string.
   * @return a {@link LocalDate}.
   */
  public static LocalDate getLocalDate(String string) {
    return LocalDate.parse(string);
  }

  /**
   * Returns a date string on the format: <code>yyyy-MM-dd</code>.
   *
   * @param date the {@link LocalDate}.
   * @return a date time string.
   */
  public static String getLocalDateString(LocalDate date) {
    return DateTimeFormatter.ISO_LOCAL_DATE.format(date);
  }

  /**
   * Indicates whether the given string can be parsed to a local date, i.e. is in a valid ISO date
   * format, e.g. <code>2007-12-03</code>.
   *
   * @param string the date string.
   * @return true if the given string can be parsed to a local date, false otherwise.
   */
  public static boolean isValidLocalDate(String string) {
    try {
      return string != null && getLocalDate(string) != null;
    } catch (DateTimeException ex) {
      return false;
    }
  }

  /**
   * Returns a {@link LocalDateTime} based on the given string using the UTC time zone. The string
   * must be on the ISO local date time format, e.g. <code>2007-12-03T10:15:30.00</code> or <code>
   * 2007-12-03T10:15:30.00Z</code>. A trailing 'Z' indicating Zulu/UTC time will be ignored.
   *
   * @param string the instant string.
   * @return a {@link LocalDateTime}.
   */
  public static LocalDateTime getLocalDateTime(String string) {
    string = StringUtils.removeEndIgnoreCase(string, "z");

    return LocalDateTime.parse(string);
  }

  /**
   * Returns a {@link LocalDate} based on the given string using the UTC time zone. The string must
   * be on the ISO local date time format, e.g. <code>2007-12-03T10:15:30.00</code> or <code>
   * 2007-12-03T10:15:30.00Z</code>. A trailing 'Z' indicating Zulu/UTC time will be ignored.
   *
   * @param string the instant string.
   * @return a {@link LocalDateTime}.
   */
  public static LocalDate getLocalDateTimeAsDate(String string) {
    return getLocalDateTime(string).toLocalDate();
  }

  /**
   * Indicates whether the given string can be parsed to a local date time, i.e. is in a valid ISO
   * date time format, e.g. <code>2007-12-03T10:15:30.00Z</code>.
   *
   * @param string the instant string.
   * @return true if the given string can be parsed to a local date time, false otherwise.
   */
  public static boolean isValidLocalDateTime(String string) {
    try {
      return string != null && getLocalDateTime(string) != null;
    } catch (DateTimeException ex) {
      return false;
    }
  }

  /**
   * Returns a date time string on the format: <code>yyyy-MM-dd'T'HH:mm:ss.SSS</code>.
   *
   * @param dateTime the {@link LocalDateTime}.
   * @return a date time string.
   */
  public static String getLocalDateTimeString(LocalDateTime dateTime) {
    return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(dateTime);
  }

  /**
   * Returns a date time string on the format: <code>yyyy-MM-dd'T'HH:mm:ss.SSS</code>.
   *
   * @param dateTime the {@link Date}.
   * @return a date time string.
   */
  public static String getDateTimeString(Date dateTime) {
    return new SimpleDateFormat(DATE_TIME_FORMAT).format(dateTime);
  }

  /**
   * Returns a date time string on the format: <code>yyyy-MM-dd'T'HH:mm:ss.SSSZ</code>.
   *
   * @param dateTime the {@link LocalDateTime}.
   * @return a date time string.
   */
  public static String getUtcDateTimeString(LocalDateTime dateTime) {
    return String.format("%sZ", DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(dateTime));
  }

  /**
   * Returns a date time string on the format: <code>yyyy-MM-dd'T'HH:mm:ss.SSSZ</code>.
   *
   * @param dateTime the {@link Date}.
   * @return a date time string.
   */
  public static String getUtcDateTimeString(Date dateTime) {
    return String.format("%sZ", new SimpleDateFormat(DATE_TIME_FORMAT).format(dateTime));
  }
}
