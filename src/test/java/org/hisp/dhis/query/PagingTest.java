package org.hisp.dhis.query;

import org.hisp.dhis.query.Paging;
import org.junit.Test;

import static org.junit.Assert.*;

public class PagingTest
{
    @Test
    public void testGetOffset()
    {
        Paging paging = new Paging( 3, 10 );
        assertEquals( 20, paging.getOffset() );

        paging = new Paging( 1, 10 );
        assertEquals( 0, paging.getOffset() );

        paging = new Paging( null, 10 );
        assertEquals( 0, paging.getOffset() );

        paging = new Paging( 3, null );
        assertEquals( 100, paging.getOffset() );

        paging = new Paging( null, null );
        assertEquals( 0, paging.getOffset() );
    }
}
