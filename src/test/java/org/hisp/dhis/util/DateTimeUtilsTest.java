package org.hisp.dhis.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;

public class DateTimeUtilsTest
{
    @Test
    void testGetLocalDate()
    {
        LocalDate date = DateTimeUtils.getLocalDate( "2020-11-15" );

        assertEquals( 2020, date.getYear() );
        assertEquals( 11, date.getMonthValue() );
        assertEquals( 15, date.getDayOfMonth() );
    }

    @Test
    void testIsValidLocalDate()
    {
        assertFalse( DateTimeUtils.isValidLocalDate( "20180122" ) );
        assertFalse( DateTimeUtils.isValidLocalDate( "2018-10-2T00:00:00.000Z" ) );
        assertFalse( DateTimeUtils.isValidLocalDate( null ) );
        assertTrue( DateTimeUtils.isValidLocalDate( "2018-01-22" ) );
    }

    void testGetLocalDateString()
    {
        LocalDate date = LocalDate.of( 2021, 8, 30 );

        String str = DateTimeUtils.getLocalDateString( date );

        assertNotNull( str );
        assertEquals( "2021-08-30", str );
    }

    @Test
    void testGetLocalDateTime()
    {
        LocalDateTime dateTime = DateTimeUtils.getLocalDateTime( "2020-11-15T06:12:26.641" );

        assertEquals( 2020, dateTime.getYear() );
        assertEquals( 11, dateTime.getMonthValue() );
        assertEquals( 15, dateTime.getDayOfMonth() );
        assertEquals( 6, dateTime.getHour() );
        assertEquals( 12, dateTime.getMinute() );
        assertEquals( 26, dateTime.getSecond() );

        dateTime = DateTimeUtils.getLocalDateTime( "2018-10-02T00:00:00.000Z" );

        assertEquals( 2018, dateTime.getYear() );
        assertEquals( 10, dateTime.getMonthValue() );
        assertEquals( 2, dateTime.getDayOfMonth() );
        assertEquals( 0, dateTime.getHour() );
        assertEquals( 0, dateTime.getMinute() );
        assertEquals( 0, dateTime.getSecond() );

        dateTime = DateTimeUtils.getLocalDateTime( "2021-07-14T02:05:23.351Z" );

        assertEquals( 2021, dateTime.getYear() );
        assertEquals( 7, dateTime.getMonthValue() );
        assertEquals( 14, dateTime.getDayOfMonth() );
        assertEquals( 2, dateTime.getHour() );
        assertEquals( 5, dateTime.getMinute() );
        assertEquals( 23, dateTime.getSecond() );
    }

    @Test
    void testIsValidLocalDateTime()
    {
        assertFalse( DateTimeUtils.isValidLocalDateTime( "20181002T00:0000000" ) );
        assertFalse( DateTimeUtils.isValidLocalDateTime( "2018-10-2T00:00:00.000Z" ) );
        assertFalse( DateTimeUtils.isValidLocalDateTime( "2019-10-02T0:00:00.000Z" ) );
        assertFalse( DateTimeUtils.isValidLocalDateTime( "2020-13-02T00:00:00.000" ) );
        assertFalse( DateTimeUtils.isValidLocalDateTime( null ) );

        assertTrue( DateTimeUtils.isValidLocalDateTime( "2020-10-02T00:00:00.000" ) );
        assertTrue( DateTimeUtils.isValidLocalDateTime( "2017-08-26T14:10:45.541" ) );
        assertTrue( DateTimeUtils.isValidLocalDateTime( "2017-08-06T00:00:00.000Z" ) );
        assertTrue( DateTimeUtils.isValidLocalDateTime( "2018-10-02T14:52:00.000Z" ) );
        assertTrue( DateTimeUtils.isValidLocalDateTime( "2018-10-02T16:45:56.000Z" ) );
        assertTrue( DateTimeUtils.isValidLocalDateTime( "2018-02-02T18:12:35.000Z" ) );
        assertTrue( DateTimeUtils.isValidLocalDateTime( "2018-12-02T18:12:35.798Z" ) );
    }

    @Test
    void testGetLocalDateTimeStringA()
    {
        LocalDateTime dateTime = LocalDateTime.of( 2021, 8, 30, 14, 20, 5 );

        String str = DateTimeUtils.getLocalDateTimeString( dateTime );

        assertNotNull( str );
        assertEquals( "2021-08-30T14:20:05", str );
    }

    @Test
    void testGetLocalDateTimeStringB()
    {
        LocalDateTime dateTime = LocalDateTime.of( 2021, 5, 20, 14, 20, 5, 372_000_000 );

        String str = DateTimeUtils.getLocalDateTimeString( dateTime );

        assertNotNull( str );
        assertEquals( "2021-05-20T14:20:05.372", str );
    }

    @Test
    void testGetDateTimeStringFromDate()
    {
        Date dateTime = getDate( 2021, 5, 20, 14, 20, 5 );

        String str = DateTimeUtils.getDateTimeString( dateTime );

        assertNotNull( str );
        assertEquals( "2021-05-20T14:20:05.000", str );
    }

    @Test
    void testGetDateStringFromDate()
    {
        Date dateTime = getDate( 2021, 5, 20, 0, 0, 0 );

        String str = DateTimeUtils.getDateString( dateTime );

        assertNotNull( str );
        assertEquals( "2021-05-20", str );
    }

    @Test
    void testGetUtcDateTimeStringA()
    {
        LocalDateTime dateTime = LocalDateTime.of( 2021, 8, 30, 14, 20, 5 );

        String str = DateTimeUtils.getUtcDateTimeString( dateTime );

        assertNotNull( str );
        assertEquals( "2021-08-30T14:20:05Z", str );
    }

    @Test
    void testGetUtcDateTimeStringB()
    {
        LocalDateTime dateTime = LocalDateTime.of( 2021, 5, 20, 14, 20, 5, 372_000_000 );

        String str = DateTimeUtils.getUtcDateTimeString( dateTime );

        assertNotNull( str );
        assertEquals( "2021-05-20T14:20:05.372Z", str );
    }

    @Test
    void testGetUtcDateTimeStringFromDate()
    {
        Date dateTime = getDate( 2021, 8, 30, 14, 20, 5 );

        String str = DateTimeUtils.getUtcDateTimeString( dateTime );

        assertNotNull( str );
        assertEquals( "2021-08-30T14:20:05.000Z", str );
    }

    @Test
    void convertStringToLocalDateTimeA()
    {
        LocalDateTime dateTime = DateTimeUtils.getLocalDateTime( "2019-11-05T00:14:08.000Z" );

        assertNotNull( dateTime );

        String string = DateTimeUtils.getLocalDateTimeString( dateTime );

        assertNotNull( string );

        dateTime = DateTimeUtils.getLocalDateTime( string );

        assertNotNull( dateTime );

        string = DateTimeUtils.getLocalDateTimeString( dateTime );

        assertNotNull( string );
    }

    @Test
    void convertStringToLocalDateTimeB()
    {
        LocalDateTime dateTime = DateTimeUtils.getLocalDateTime( "2018-08-05T00:18:12.413" );

        assertNotNull( dateTime );

        String string = DateTimeUtils.getLocalDateTimeString( dateTime );

        assertNotNull( string );

        dateTime = DateTimeUtils.getLocalDateTime( string );

        assertNotNull( dateTime );

        string = DateTimeUtils.getLocalDateTimeString( dateTime );

        assertNotNull( string );
    }

    @Test
    void convertStringToUtcDateTime()
    {
        LocalDateTime dateTime = DateTimeUtils.getLocalDateTime( "2019-11-05T00:14:08.000Z" );

        assertNotNull( dateTime );

        String string = DateTimeUtils.getUtcDateTimeString( dateTime );

        assertNotNull( string );

        dateTime = DateTimeUtils.getLocalDateTime( string );

        assertNotNull( dateTime );

        string = DateTimeUtils.getUtcDateTimeString( dateTime );

        assertNotNull( string );
    }

    private Date getDate( int year, int month, int dayOfMonth, int hour, int minute, int second )
    {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set( year, (month - 1), dayOfMonth, hour, minute, second );
        return cal.getTime();
    }
}
