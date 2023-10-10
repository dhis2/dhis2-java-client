package org.hisp.dhis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.hc.core5.net.URIBuilder;
import org.junit.jupiter.api.Test;

class UriBuilderTest
{
    @Test
    void testConstructor()
        throws Exception
    {
        String url = new URIBuilder( "https://play.dhis2.org" )
            .build().toString();
        assertEquals( "https://play.dhis2.org", url );

        url = new URIBuilder( "https://play.dhis2.org/dev" )
            .build().toString();
        assertEquals( "https://play.dhis2.org/dev", url );

        url = new URIBuilder( "https://play.dhis2.org/dev/" )
            .build().toString();
        assertEquals( "https://play.dhis2.org/dev/", url );

        url = new URIBuilder( "https://play.dhis2.org/dev/api" )
            .build().toString();
        assertEquals( "https://play.dhis2.org/dev/api", url );
    }

    @Test
    void testAppendPathA()
        throws Exception
    {
        String url = new URIBuilder( "https://play.dhis2.org" )
            .appendPath( "dev" )
            .build().toString();
        assertEquals( "https://play.dhis2.org/dev", url );

        url = new URIBuilder( "https://play.dhis2.org" )
            .appendPath( "dev" )
            .appendPath( "api" )
            .build().toString();
        assertEquals( "https://play.dhis2.org/dev/api", url );
    }

    @Test
    void testAppendPathB()
        throws Exception
    {
        String url = new URIBuilder( "https://play.dhis2.org/site" )
            .appendPath( "dhis" )
            .build().toString();
        assertEquals( "https://play.dhis2.org/site/dhis", url );

        url = new URIBuilder( "https://play.dhis2.org/site/dhis" )
            .appendPath( "api" )
            .build().toString();
        assertEquals( "https://play.dhis2.org/site/dhis/api", url );
    }

    @Test
    void testAppendPathC()
        throws Exception
    {
        String url = new URIBuilder( "https://play.dhis2.org/dev/api" )
            .appendPath( "system/info" )
            .build().toString();
        assertEquals( "https://play.dhis2.org/dev/api/system/info", url );

        url = new URIBuilder( "https://play.dhis2.org/api" )
            .appendPath( "analytics" )
            .appendPath( "events" )
            .appendPath( "query" )
            .build().toString();
        assertEquals( "https://play.dhis2.org/api/analytics/events/query", url );
    }
}
