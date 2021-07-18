package org.hisp.dhis;

import static org.junit.Assert.assertNotNull;

import org.hisp.dhis.auth.Authentication;
import org.hisp.dhis.auth.BasicAuthentication;
import org.hisp.dhis.auth.CookieAuthentication;
import org.hisp.dhis.category.IntegrationTest;
import org.hisp.dhis.query.Query;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category( IntegrationTest.class )
public class Dhis2AuthTest
{
    @Test
    public void testBasicAuthConstructor()
    {
        Dhis2Config config = new Dhis2Config( TestFixture.DEV_URL, "admin", "district" );

        Dhis2 dhis2 = new Dhis2( config );

        assertNotNull( dhis2.getDataElements( Query.instance() ) );
    }

    @Test
    public void testBasicAuthentication()
    {
        Authentication authentication = new BasicAuthentication( "admin", "district" );

        Dhis2Config config = new Dhis2Config( TestFixture.DEV_URL, authentication );

        Dhis2 dhis2 = new Dhis2( config );

        assertNotNull( dhis2.getDataElements( Query.instance() ) );
    }

    @Test
    public void testCookieAuthentication()
    {
        Authentication authentication = new CookieAuthentication( "1F3A5672388950E3543224EFC30435B4" );

        Dhis2Config config = new Dhis2Config( TestFixture.DEV_URL, authentication );

        Dhis2 dhis2 = new Dhis2( config );

        assertNotNull( dhis2.getDataElements( Query.instance() ) );
    }
}
