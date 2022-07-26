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
        LocalDateTime dateTime = DateTimeUtils.getLocalDateTime( "2018-10-02T00:00:00.000Z" );

        assertEquals( 2018, dateTime.getYear() );
        assertEquals( 10, dateTime.getMonthValue() );
        assertEquals( 2, dateTime.getDayOfMonth() );

        dateTime = DateTimeUtils.getLocalDateTime( "2021-07-14T02:05:03.351Z" );

        assertEquals( 2021, dateTime.getYear() );
        assertEquals( 7, dateTime.getMonthValue() );
        assertEquals( 14, dateTime.getDayOfMonth() );
    }

    @Test
    void testIsValidLocalDateTime()
    {
        assertFalse( DateTimeUtils.isValidLocalDateTime( "20181002T00:0000000" ) );
        assertFalse( DateTimeUtils.isValidLocalDateTime( "2018-10-2T00:00:00.000Z" ) );
        assertFalse( DateTimeUtils.isValidLocalDateTime( "2019-10-02T0:00:00.000Z" ) );
        assertFalse( DateTimeUtils.isValidLocalDateTime( "2020-10-02T00:00:00.000" ) );
        assertFalse( DateTimeUtils.isValidLocalDateTime( "2020-13-02T00:00:00.000" ) );

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

        String strB = DateTimeUtils.getLocalDateTimeString( LocalDateTime.now() );

        assertNotNull( strB );
    }

    @Test
    void testGetLocalDateTimeStringB()
    {
        LocalDateTime dateTime = LocalDateTime.of( 2021, 5, 20, 14, 20, 5, 372_000_000 );

        String strA = DateTimeUtils.getLocalDateTimeString( dateTime );

        assertNotNull( strA );
        assertEquals( "2021-05-20T14:20:05.372", strA );

        String strB = DateTimeUtils.getLocalDateTimeString( LocalDateTime.now() );

        assertNotNull( strB );
    }
}
