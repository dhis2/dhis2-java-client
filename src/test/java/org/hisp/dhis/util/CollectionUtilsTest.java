package org.hisp.dhis.util;

import static org.hisp.dhis.util.CollectionUtils.firstMatch;
import static org.hisp.dhis.util.CollectionUtils.list;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

public class CollectionUtilsTest
{
    @Test
    void testFirstMatch()
    {
        List<String> collection = list( "a", "b", "c" );

        assertEquals( "a", firstMatch( collection, ( v ) -> "a".equals( v ) ) );
        assertEquals( "b", firstMatch( collection, ( v ) -> "b".equals( v ) ) );
        assertNull( firstMatch( collection, ( v ) -> "x".equals( v ) ) );
    }
}
