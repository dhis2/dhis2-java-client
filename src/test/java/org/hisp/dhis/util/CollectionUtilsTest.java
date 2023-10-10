package org.hisp.dhis.util;

import static org.hisp.dhis.util.CollectionUtils.firstMatch;
import static org.hisp.dhis.util.CollectionUtils.list;
import static org.hisp.dhis.util.CollectionUtils.mutableList;
import static org.hisp.dhis.util.CollectionUtils.mutableSet;
import static org.hisp.dhis.util.CollectionUtils.set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

class CollectionUtilsTest
{
    @Test
    void testFirstMatch()
    {
        List<String> collection = list( "a", "b", "c" );

        assertEquals( "a", firstMatch( collection, ( v ) -> "a".equals( v ) ) );
        assertEquals( "b", firstMatch( collection, ( v ) -> "b".equals( v ) ) );
        assertNull( firstMatch( collection, ( v ) -> "x".equals( v ) ) );
    }

    @Test
    void testSetAcceptsNull()
    {
        Set<String> set = set( "a", null, "b" );

        assertEquals( 3, set.size() );
    }

    @Test
    void testMutableSetAcceptsNull()
    {
        Set<String> set = mutableSet( "a", null, "b" );

        assertEquals( 3, set.size() );
    }

    @Test
    void testListAcceptsNull()
    {
        List<String> list = list( "a", null, "b" );

        assertEquals( 3, list.size() );
    }

    @Test
    void testMutableListAcceptsNull()
    {
        List<String> list = mutableList( "a", null, "b" );

        assertEquals( 3, list.size() );
    }
}
