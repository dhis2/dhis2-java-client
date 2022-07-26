package org.hisp.dhis.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class DateTimeUtilsTest
{
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

        String strA = DateTimeUtils.getLocalDateTimeString( dateTime );

        assertNotNull( strA );
        assertEquals( "2021-08-30T14:20:05", strA );
    }

    @Test
    void testGetLocalDateTimeStringB()
    {
        LocalDateTime dateTime = LocalDateTime.of( 2021, 5, 20, 14, 20, 5, 372_000_000 );

        String strA = DateTimeUtils.getLocalDateTimeString( dateTime );

        assertNotNull( strA );
        assertEquals( "2021-05-20T14:20:05.372", strA );
    }

    @Test
    void testGetUtcDateTimeStringA()
    {
        LocalDateTime dateTime = LocalDateTime.of( 2021, 8, 30, 14, 20, 5 );

        String strA = DateTimeUtils.getUtcDateTimeString( dateTime );

        assertNotNull( strA );
        assertEquals( "2021-08-30T14:20:05Z", strA );
    }

    @Test
    void testGetUtcDateTimeStringB()
    {
        LocalDateTime dateTime = LocalDateTime.of( 2021, 5, 20, 14, 20, 5, 372_000_000 );

        String strA = DateTimeUtils.getUtcDateTimeString( dateTime );

        assertNotNull( strA );
        assertEquals( "2021-05-20T14:20:05.372Z", strA );
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

}
