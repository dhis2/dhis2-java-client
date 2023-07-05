package org.hisp.dhis.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ComparingTest
{
    @Test
    void testCompareTo()
    {
        assertTrue( Comparing.compareTo( 1, 2 ) < 0 );
        assertTrue( Comparing.compareTo( 2, 1 ) > 0 );
        assertEquals( 0, Comparing.compareTo( null, null ) );
        assertEquals( 0, Comparing.compareTo( 1, 1 ) );
        assertTrue( Comparing.compareTo( 1, null ) > 0 );
        assertTrue( Comparing.compareTo( null, 1 ) < 0 );
    }

    @Test
    void testCompareToNullLast()
    {
        assertTrue( Comparing.compareToNullLast( 1, 2 ) < 0 );
        assertTrue( Comparing.compareToNullLast( 2, 1 ) > 0 );
        assertEquals( 0, Comparing.compareToNullLast( null, null ) );
        assertEquals( 0, Comparing.compareToNullLast( 1, 1 ) );
        assertTrue( Comparing.compareToNullLast( 1, null ) < 0 );
        assertTrue( Comparing.compareToNullLast( null, 1 ) > 0 );
    }
}
