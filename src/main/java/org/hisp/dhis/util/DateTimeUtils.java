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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/** Utilities for date and time. */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateTimeUtils {
  public static final String DATE_FORMAT = "yyyy-MM-dd";

  public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

  public static final TimeZone TZ_UTC = TimeZone.getTimeZone("UTC");

  public static final String JSON_DATE_FORMAT = DATE_FORMAT;

  public static final String JSON_DATE_TIME_FORMAT = "yyyy-MM-dd'T'hh:mm:ss";

  // -----------------------------------------------------------------------------------------------
  // To date object methods
  // -----------------------------------------------------------------------------------------------

  /**
   * Returns a {@link Date}.
   *
   * @param year the year.
   * @param month the month, from 1.
   * @param dayOfMonth, from 1 to 31.
   * @return a {@link Date}.
   */
  public static Date toDate(int year, int month, int dayOfMonth) {
    LocalDate localDate = LocalDate.of(year, month, dayOfMonth);

    return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
  }

  /**
   * Returns a {@link java.util.Date} from a {@link java.time.LocalDate}.
   *
   * @param date the {@link java.time.LocalDate}.
   * @return the {@link java.util.Date}.
   */
  public static Date toDate(LocalDate date) {
    return Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
  }

  /**
   * Returns a {@link java.util.Date} from a {@link java.time.LocalDateTime}.
   *
   * @param dateTime the {@link java.time.LocalDateTime}.
   * @return the {@link java.util.Date}.
   */
  public static Date toDate(LocalDateTime dateTime) {
    return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
  }

  /**
   * Returns a {@link java.util.Date} based on the given string. The string must be on the format
   * <code>yyyy-MM-dd'T'HH:mm:ss.SSS</code>.
   *
   * @param string the date time string.
   * @return a {@link Date}.
   */
  public static Date toDateTime(String string) {
    return parse(DATE_TIME_FORMAT, string);
  }

  /**
   * Returns a {@link LocalDate} based on the given string. The string must be on the ISO local date
   * format, e.g. <code>2007-12-03</code>.
   *
   * @param string the date string.
   * @return a {@link LocalDate}.
   */
  public static LocalDate toLocalDate(String string) {
    return LocalDate.parse(string);
  }

  /**
   * Returns a {@link LocalDateTime} based on the given string using the UTC time zone. The string
   * must be on the ISO local date time format, e.g. <code>2007-12-03T10:15:30.00</code> or <code>
   * 2007-12-03T10:15:30.00Z</code>. A trailing 'Z' indicating Zulu/UTC time will be ignored.
   *
   * @param string the instant string.
   * @return a {@link LocalDateTime}.
   */
  public static LocalDateTime toLocalDateTime(String string) {
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
  public static LocalDate toLocalDateTimeAsDate(String string) {
    return toLocalDateTime(string).toLocalDate();
  }

  /**
   * Returns the {@link Instant} of received date param. if data is null, it also returns null
   * Instant.
   *
   * @param date the {@link Date}.
   * @return the {@link Instant} of received date param.
   */
  public static Instant toInstant(Date date) {
    return date != null ? date.toInstant() : null;
  }

  /**
   * Returns a {@link Duration} based on the given string. The string must be on the ISO 8601
   * duration format, e.g. <code>PT2H30M15S</code>.
   *
   * @param string the duration string.
   * @return a {@link Duration}.
   */
  public static Duration toDuration(String string) {
    return Duration.parse(string);
  }

  /**
   * Returns the {@link LocalDate} from a given {@link Instant}.
   *
   * @param instant the {@link Instant}.
   * @return the {@link LocalDate}.
   */
  public static LocalDate toLocalDate(Instant instant) {
    return instant.atZone(ZoneId.systemDefault()).toLocalDate();
  }

  /**
   * Returns the {@link LocalDateTime} from a given {@link Instant}.
   *
   * @param instant the {@link Instant}.
   * @return the {@link LocalDateTime}.
   */
  public static LocalDateTime toLocalDateTime(Instant instant) {
    return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
  }

  /**
   * Returns the {@link LocalDateTime} based on the UTC time zone from a given {@link Instant}.
   *
   * @param instant the {@link Instant}.
   * @return the {@link LocalDateTime}.
   */
  public static LocalDateTime toLocalDateTimeUtc(Instant instant) {
    return instant.atZone(ZoneOffset.UTC).toLocalDateTime();
  }

  /**
   * Converts the given {@link Date} to a {@link LocalDate}.
   *
   * @param date the {@link Date}.
   * @return a {@link LocalDate}.
   */
  public static LocalDate toLocalDate(Date date) {
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
  }

  /**
   * Converts the given {@link Date} to a {@link LocalDateTime}.
   *
   * @param date the {@link Date}.
   * @return a {@link LocalDateTime}.
   */
  public static LocalDateTime toLocalDateTime(Date date) {
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
  }

  // -----------------------------------------------------------------------------------------------
  // To date string methods
  // -----------------------------------------------------------------------------------------------

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
   * Returns a date time string on the format: <code>yyyy-MM-dd'T'HH:mm:ss.SSS</code>.
   *
   * @param dateTime the {@link Date}.
   * @return a date time string.
   */
  public static String getDateTimeString(Date dateTime) {
    return new SimpleDateFormat(DATE_TIME_FORMAT).format(dateTime);
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
   * Returns a date time string on the format: <code>yyyy-MM-dd'T'HH:mm:ss.SSS</code>.
   *
   * @param dateTime the {@link LocalDateTime}.
   * @return a date time string.
   */
  public static String getLocalDateTimeString(LocalDateTime dateTime) {
    return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(dateTime);
  }

  /**
   * Returns a date time string on the format: <code>yyyy-MM-dd'T'HH:mm:ss.SSSZ</code>.
   *
   * @param instant the {@link Instant}.
   * @return a date time string.
   */
  public static String getDateTimeString(Instant instant) {
    return DateTimeFormatter.ISO_INSTANT.format(instant);
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

  // -----------------------------------------------------------------------------------------------
  // Validation methods
  // -----------------------------------------------------------------------------------------------

  /**
   * Indicates whether the given string can be parsed to a local date, i.e. is in a valid ISO date
   * format, e.g. <code>2007-12-03</code>.
   *
   * @param string the date string.
   * @return true if the given string can be parsed to a local date, false otherwise.
   */
  public static boolean isValidLocalDate(String string) {
    try {
      return string != null && toLocalDate(string) != null;
    } catch (DateTimeException ex) {
      return false;
    }
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
      return string != null && toLocalDateTime(string) != null;
    } catch (DateTimeException ex) {
      return false;
    }
  }

  /**
   * Indicates whether the given string can be parsed to a duration., i.e. is in a valid duration
   * format, e.g. <code>PT2H30M15S</code>.
   *
   * @param string the date time string.
   * @return true if the given string can be parsed to a date time, false otherwise.
   */
  public static boolean isValidDuration(String string) {
    try {
      return string != null && toDuration(string) != null;
    } catch (DateTimeException ex) {
      return false;
    }
  }

  // -----------------------------------------------------------------------------------------------
  // Support methods
  // -----------------------------------------------------------------------------------------------

  /**
   * Parses a date string using the specified format, returning null if parsing failed.
   *
   * @param format the date format.
   * @param string the date string.
   * @return a {@link Date} if the string is valid, null otherwise.
   */
  private static Date parse(String format, String string) {
    try {
      return new SimpleDateFormat(format).parse(string);
    } catch (ParseException ex) {
      return null;
    }
  }
}
