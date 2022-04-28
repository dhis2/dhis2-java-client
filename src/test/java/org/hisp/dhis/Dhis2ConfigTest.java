package org.hisp.dhis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

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
    public void testGetResolvedUrl()
    {
        Dhis2Config config = new Dhis2Config( "https://play.dhis2.org/dev", "admin", "district" );

        assertEquals( "https://play.dhis2.org/dev/api/dataElements",
            config.getResolvedUrl( "dataElements" ).toString() );

        assertEquals( "https://play.dhis2.org/dev/api/system/info",
            config.getResolvedUrl( "system/info" ).toString() );

        assertEquals( "https://play.dhis2.org/dev/api/analytics/events/query",
            config.getResolvedUrl( "analytics/events/query" ).toString() );
    }
}
