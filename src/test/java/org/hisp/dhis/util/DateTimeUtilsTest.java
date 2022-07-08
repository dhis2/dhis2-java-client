package org.hisp.dhis.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    void testGetTimestampSecondsString()
    {
        LocalDateTime dateTime = LocalDateTime.of( 2021, 8, 30, 14, 20, 5 );

        String strA = DateTimeUtils.getTimestampSecondsString( dateTime );

        assertNotNull( strA );
        assertEquals( "2021-08-30T14:20:05", strA );

        String strB = DateTimeUtils.getTimestampSecondsString( LocalDateTime.now() );

        assertNotNull( strB );
        assertEquals( 19, strB.length() );
    }

    @Test
    void testGetTimestampMillisString()
    {
        LocalDateTime dateTime = LocalDateTime.of( 2021, 5, 20, 14, 20, 5, 372_000_000 );

        String strA = DateTimeUtils.getTimestampMillisString( dateTime );

        assertNotNull( strA );
        assertEquals( "2021-05-20T14:20:05.372", strA );

        String strB = DateTimeUtils.getTimestampMillisString( LocalDateTime.now() );

        assertNotNull( strB );
        assertEquals( 23, strB.length() );
    }
}
