package org.hisp.dhis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.client.utils.URLEncodedUtils;
import org.junit.Test;

public class Dhis2ConfigTest
{
    @Test
    public void testGetResolvedUriBuilder()
        throws Exception
    {
        Dhis2Config config = new Dhis2Config( "https://company.com", "admin", "district" );
        String url = config.getResolvedUriBuilder()
            .build().toString();
        assertEquals( "https://company.com/api", url );

        config = new Dhis2Config( "https://company.com/", "admin", "district" );
        url = config.getResolvedUriBuilder()
            .build().toString();
        assertEquals( "https://company.com/api", url );

        config = new Dhis2Config( "https://company.com/site", "admin", "district" );
        url = config.getResolvedUriBuilder()
            .build().toString();
        assertEquals( "https://company.com/site/api", url );

        config = new Dhis2Config( "https://company.com/site/", "admin", "district" );
        url = config.getResolvedUriBuilder()
            .build().toString();
        assertEquals( "https://company.com/site/api", url );

        config = new Dhis2Config( "https://company.com/site/dhis", "admin", "district" );
        url = config.getResolvedUriBuilder()
            .build().toString();
        assertEquals( "https://company.com/site/dhis/api", url );

        config = new Dhis2Config( "https://company.com/site/dhis/", "admin", "district" );
        url = config.getResolvedUriBuilder()
            .build().toString();
        assertEquals( "https://company.com/site/dhis/api", url );
    }

    @Test
    public void testParsePathSegments()
    {
        List<String> segments = URLEncodedUtils.parsePathSegments( "dataElements", Consts.UTF_8 );

        assertEquals( 1, segments.size() );
        assertEquals( "dataElements", segments.get( 0 ) );

        segments = URLEncodedUtils.parsePathSegments( "system/info", Consts.UTF_8 );

        assertEquals( 2, segments.size() );
        assertEquals( "system", segments.get( 0 ) );
        assertEquals( "info", segments.get( 1 ) );

        segments = URLEncodedUtils.parsePathSegments( "analytics/events/query", Consts.UTF_8 );

        assertEquals( 3, segments.size() );
        assertEquals( "analytics", segments.get( 0 ) );
        assertEquals( "events", segments.get( 1 ) );
        assertEquals( "query", segments.get( 2 ) );
    }

    @Test
    public void testGetResolvedUrl()
    {
        Dhis2Config config = new Dhis2Config( "https://play.dhis2.org/dev", "admin", "district" );

        assertEquals( "https://play.dhis2.org/dev/api/dataElements",
            config.getResolvedUrl( "dataElements" ).toString() );

        assertEquals( "https://play.dhis2.org/dev/api/system/info",
            config.getResolvedUrl( "system/info" ).toString() );

    }
}
