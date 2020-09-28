package org.hisp.dhis;

import static org.junit.Assert.assertEquals;

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
}
