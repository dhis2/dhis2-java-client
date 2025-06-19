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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.UNIT)
class DateTimeUtilsTest {

  // -----------------------------------------------------------------------------------------------
  // To date object methods
  // -----------------------------------------------------------------------------------------------

  @Test
  void testToLocalDate() {
    LocalDate date = DateTimeUtils.toLocalDate("2020-11-15");

    assertEquals(2020, date.getYear());
    assertEquals(11, date.getMonthValue());
    assertEquals(15, date.getDayOfMonth());
  }

  @Test
  void testToLocalDateTime() {
    LocalDateTime dateTime = DateTimeUtils.toLocalDateTime("2020-11-15T06:12:26.641");

    assertEquals(2020, dateTime.getYear());
    assertEquals(11, dateTime.getMonthValue());
    assertEquals(15, dateTime.getDayOfMonth());
    assertEquals(6, dateTime.getHour());
    assertEquals(12, dateTime.getMinute());
    assertEquals(26, dateTime.getSecond());

    dateTime = DateTimeUtils.toLocalDateTime("2018-10-02T00:00:00.000Z");

    assertEquals(2018, dateTime.getYear());
    assertEquals(10, dateTime.getMonthValue());
    assertEquals(2, dateTime.getDayOfMonth());
    assertEquals(0, dateTime.getHour());
    assertEquals(0, dateTime.getMinute());
    assertEquals(0, dateTime.getSecond());

    dateTime = DateTimeUtils.toLocalDateTime("2021-07-14T02:05:23.351Z");

    assertEquals(2021, dateTime.getYear());
    assertEquals(7, dateTime.getMonthValue());
    assertEquals(14, dateTime.getDayOfMonth());
    assertEquals(2, dateTime.getHour());
    assertEquals(5, dateTime.getMinute());
    assertEquals(23, dateTime.getSecond());
  }

  @Test
  void testToLocalDateTimeAsDate() {
    LocalDate date = DateTimeUtils.toLocalDateTimeAsDate("2020-11-15T06:12:26.641");

    assertEquals(2020, date.getYear());
    assertEquals(11, date.getMonthValue());
    assertEquals(15, date.getDayOfMonth());
  }

  @Test
  void testToLocalDateFromInstant() {
    LocalDate dateA = DateTimeUtils.toLocalDate(Instant.ofEpochSecond(1006511400L));
    LocalDate dateB = DateTimeUtils.toLocalDate(Instant.ofEpochSecond(1617532200L));

    assertEquals("2001-11-23", dateA.toString());
    assertEquals("2021-04-04", dateB.toString());
  }

  @Test
  void testToDuration() {
    Duration durationA = DateTimeUtils.toDuration("PT2H30M15S");
    Duration durationB = DateTimeUtils.toDuration("PT0.001S");

    assertEquals("PT2H30M15S", durationA.toString());
    assertEquals("PT0.001S", durationB.toString());
  }

  @Test
  void convertStringToDateTime() {
    String strA = "2022-01-15T14:30:00.500";
    String strB = "2023-11-20T08:05:23.078";
    String strC = "2024-06-11T09:14:59.999";

    Date dateA = DateTimeUtils.toDateTime(strA);
    Date dateB = DateTimeUtils.toDateTime(strB);
    Date dateC = DateTimeUtils.toDateTime(strC);

    assertEquals(2022, DateTimeUtils.toLocalDate(dateA).getYear());
    assertEquals(2023, DateTimeUtils.toLocalDate(dateB).getYear());
    assertEquals(2024, DateTimeUtils.toLocalDate(dateC).getYear());

    assertEquals(strA, DateTimeUtils.getDateTimeString(dateA));
    assertEquals(strB, DateTimeUtils.getDateTimeString(dateB));
    assertEquals(strC, DateTimeUtils.getDateTimeString(dateC));
  }

  @Test
  void convertStringToLocalDateTimeA() {
    LocalDateTime dateTime = DateTimeUtils.toLocalDateTime("2019-11-05T00:14:08.000Z");
    
    assertNotNull(dateTime);

    String string = DateTimeUtils.getLocalDateTimeString(dateTime);

    assertNotNull(string);

    dateTime = DateTimeUtils.toLocalDateTime(string);

    assertNotNull(dateTime);

    string = DateTimeUtils.getLocalDateTimeString(dateTime);

    assertNotNull(string);
  }

  @Test
  void convertStringToLocalDateTimeB() {
    LocalDateTime dateTime = DateTimeUtils.toLocalDateTime("2018-08-05T00:18:12.413");

    assertNotNull(dateTime);

    String string = DateTimeUtils.getLocalDateTimeString(dateTime);

    assertNotNull(string);

    dateTime = DateTimeUtils.toLocalDateTime(string);

    assertNotNull(dateTime);

    string = DateTimeUtils.getLocalDateTimeString(dateTime);

    assertNotNull(string);
  }

  @Test
  void convertStringToUtcDateTime() {
    LocalDateTime dateTime = DateTimeUtils.toLocalDateTime("2019-11-05T00:14:08.000Z");

    assertNotNull(dateTime);

    String string = DateTimeUtils.getUtcDateTimeString(dateTime);

    assertNotNull(string);

    dateTime = DateTimeUtils.toLocalDateTime(string);

    assertNotNull(dateTime);

    string = DateTimeUtils.getUtcDateTimeString(dateTime);

    assertNotNull(string);
  }
  
  // -----------------------------------------------------------------------------------------------
  // To date string methods
  // -----------------------------------------------------------------------------------------------

  @Test
  void testGetLocalDateString() {
    LocalDate date = LocalDate.of(2021, 8, 30);

    String str = DateTimeUtils.getLocalDateString(date);

    assertNotNull(str);
    assertEquals("2021-08-30", str);
  }

  @Test
  void testGetLocalDateTimeStringA() {
    LocalDateTime dateTime = LocalDateTime.of(2021, 8, 30, 14, 20, 5);

    String str = DateTimeUtils.getLocalDateTimeString(dateTime);

    assertNotNull(str);
    assertEquals("2021-08-30T14:20:05", str);
  }

  @Test
  void testGetLocalDateTimeStringB() {
    LocalDateTime dateTime = LocalDateTime.of(2021, 5, 20, 14, 20, 5, 372_000_000);

    String str = DateTimeUtils.getLocalDateTimeString(dateTime);

    assertNotNull(str);
    assertEquals("2021-05-20T14:20:05.372", str);
  }

  @Test
  void testGetDateTimeStringFromDate() {
    Date dateTime = getDate(2021, 5, 20, 14, 20, 5);

    String str = DateTimeUtils.getDateTimeString(dateTime);

    assertNotNull(str);
    assertEquals("2021-05-20T14:20:05.000", str);
  }

  @Test
  void testGetDateStringFromDate() {
    Date dateTime = getDate(2021, 5, 20, 0, 0, 0);

    String str = DateTimeUtils.getDateString(dateTime);

    assertNotNull(str);
    assertEquals("2021-05-20", str);
  }

  @Test
  void testGetUtcDateTimeStringA() {
    LocalDateTime dateTime = LocalDateTime.of(2021, 8, 30, 14, 20, 5);

    String str = DateTimeUtils.getUtcDateTimeString(dateTime);

    assertNotNull(str);
    assertEquals("2021-08-30T14:20:05Z", str);
  }

  @Test
  void testGetUtcDateTimeStringB() {
    LocalDateTime dateTime = LocalDateTime.of(2021, 5, 20, 14, 20, 5, 372_000_000);

    String str = DateTimeUtils.getUtcDateTimeString(dateTime);

    assertNotNull(str);
    assertEquals("2021-05-20T14:20:05.372Z", str);
  }

  @Test
  void testGetUtcDateTimeStringFromDate() {
    Date dateTime = getDate(2021, 8, 30, 14, 20, 5);

    String str = DateTimeUtils.getUtcDateTimeString(dateTime);

    assertNotNull(str);
    assertEquals("2021-08-30T14:20:05.000Z", str);
  }

  @Test
  void testGetDateTimeStringFromInstant() {
    Instant instant = Instant.ofEpochSecond(1646092800); // 2022-03-01T00:00:00Z

    String str = DateTimeUtils.getDateTimeString(instant);

    assertNotNull(str);
    assertEquals("2022-03-01T00:00:00Z", str);
  }

  // -----------------------------------------------------------------------------------------------
  // Validation methods
  // -----------------------------------------------------------------------------------------------

  @Test
  void testIsValidLocalDate() {
    assertFalse(DateTimeUtils.isValidLocalDate("20180122"));
    assertFalse(DateTimeUtils.isValidLocalDate("2018-10-2T00:00:00.000Z"));
    assertFalse(DateTimeUtils.isValidLocalDate(null));
    assertTrue(DateTimeUtils.isValidLocalDate("2018-01-22"));
  }

  @Test
  void testIsValidLocalDateTime() {
    assertFalse(DateTimeUtils.isValidLocalDateTime("20181002T00:0000000"));
    assertFalse(DateTimeUtils.isValidLocalDateTime("2018-10-2T00:00:00.000Z"));
    assertFalse(DateTimeUtils.isValidLocalDateTime("2019-10-02T0:00:00.000Z"));
    assertFalse(DateTimeUtils.isValidLocalDateTime("2020-13-02T00:00:00.000"));
    assertFalse(DateTimeUtils.isValidLocalDateTime(null));

    assertTrue(DateTimeUtils.isValidLocalDateTime("2020-10-02T00:00:00.000"));
    assertTrue(DateTimeUtils.isValidLocalDateTime("2017-08-26T14:10:45.541"));
    assertTrue(DateTimeUtils.isValidLocalDateTime("2017-08-06T00:00:00.000Z"));
    assertTrue(DateTimeUtils.isValidLocalDateTime("2018-10-02T14:52:00.000Z"));
    assertTrue(DateTimeUtils.isValidLocalDateTime("2018-10-02T16:45:56.000Z"));
    assertTrue(DateTimeUtils.isValidLocalDateTime("2018-02-02T18:12:35.000Z"));
    assertTrue(DateTimeUtils.isValidLocalDateTime("2018-12-02T18:12:35.798Z"));
  }

  // -----------------------------------------------------------------------------------------------
  // Support methods
  // -----------------------------------------------------------------------------------------------

  private Date getDate(int year, int month, int dayOfMonth, int hour, int minute, int second) {
    Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.set(year, (month - 1), dayOfMonth, hour, minute, second);
    return cal.getTime();
  }
}
