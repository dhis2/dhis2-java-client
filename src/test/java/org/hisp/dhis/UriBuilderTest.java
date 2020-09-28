package org.hisp.dhis;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UriBuilderTest
{
    @Test
    public void testConstructor()
        throws Exception
    {
        String url = new UriBuilder( "https://play.dhis2.org" )
            .build().toString();
        assertEquals( "https://play.dhis2.org", url );

        url = new UriBuilder( "https://play.dhis2.org/dev" )
            .build().toString();
        assertEquals( "https://play.dhis2.org/dev", url );

        url = new UriBuilder( "https://play.dhis2.org/dev/" )
            .build().toString();
        assertEquals( "https://play.dhis2.org/dev/", url );

        url = new UriBuilder( "https://play.dhis2.org/dev/api" )
            .build().toString();
        assertEquals( "https://play.dhis2.org/dev/api", url );
    }

    @Test
    public void testPathSegmentA()
        throws Exception
    {
        String url = new UriBuilder( "https://play.dhis2.org" )
            .pathSegment( "dev" )
            .build().toString();
        assertEquals( "https://play.dhis2.org/dev", url );

        url = new UriBuilder( "https://play.dhis2.org" )
            .pathSegment( "dev" )
            .pathSegment( "api" )
            .build().toString();
        assertEquals( "https://play.dhis2.org/dev/api", url );
    }

    @Test
    public void testPathSegmentB()
        throws Exception
    {
        String url = new UriBuilder( "https://play.dhis2.org/site" )
            .pathSegment( "dhis" )
            .build().toString();
        assertEquals( "https://play.dhis2.org/site/dhis", url );

        url = new UriBuilder( "https://play.dhis2.org/site/dhis" )
            .pathSegment( "api" )
            .build().toString();
        assertEquals( "https://play.dhis2.org/site/dhis/api", url );
    }
}
