package org.hisp.dhis.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;

import org.junit.Test;

public class DateTimeUtilsTest
{
    @Test
    public void testGetTimestampString()
    {
        LocalDateTime dateTime = LocalDateTime.of( 2021, 8, 30, 14, 20, 5 );

        String str = DateTimeUtils.getTimestampString( dateTime );

        // 2021-08-30T14:20:05
        System.out.println( str );
        assertNotNull( str );
        assertEquals( "2021-08-30T14:20:05", str );
        // assertEquals( "", str.substring( 0, 0 ) );
    }
}
