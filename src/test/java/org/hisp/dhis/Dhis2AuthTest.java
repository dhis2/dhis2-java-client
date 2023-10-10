package org.hisp.dhis;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.Header;
import org.hisp.dhis.auth.Authentication;
import org.hisp.dhis.auth.BasicAuthentication;
import org.hisp.dhis.auth.CookieAuthentication;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.support.TestTags;
import org.hisp.dhis.util.HttpUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag( TestTags.INTEGRATION )
class Dhis2AuthTest
{
    @Test
    void testBasicAuthConstructor()
    {
        Dhis2Config config = new Dhis2Config( TestFixture.DEFAULT_URL, "admin", "district" );

        Dhis2 dhis2 = new Dhis2( config );

        assertNotNull( dhis2.getDataElements( Query.instance() ) );
    }

    @Test
    void testBasicAuthInstance()
    {
        Dhis2 dhis2 = Dhis2.withBasicAuth( TestFixture.DEFAULT_URL, "admin", "district" );

        assertNotNull( dhis2.getDataElements( Query.instance() ) );
    }

    @Test
    void testBasicAuthentication()
    {
        Authentication authentication = new BasicAuthentication( "admin", "district" );

        Dhis2Config config = new Dhis2Config( TestFixture.DEFAULT_URL, authentication );

        Dhis2 dhis2 = new Dhis2( config );

        assertNotNull( dhis2.getDataElements( Query.instance() ) );
    }

    @Test
    void testCookieAuthentication()
        throws Exception
    {
        String sessionId = getSessionId( TestFixture.DEFAULT_CONFIG );

        Authentication authentication = new CookieAuthentication( sessionId );

        Dhis2Config config = new Dhis2Config( TestFixture.DEFAULT_URL, authentication );

        Dhis2 dhis2 = new Dhis2( config );

        assertNotNull( dhis2.getDataElements( Query.instance() ) );
    }

    private String getSessionId( Dhis2Config basicAuthConfig )
        throws Exception
    {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet request = new HttpGet( TestFixture.DEFAULT_URL + "/api/dataElements.json" );
        HttpUtils.withAuth( request, basicAuthConfig );

        try ( CloseableHttpResponse response = httpClient.execute( request ) )
        {
            Header cookie = response.getHeader( "Set-Cookie" );

            Pattern pattern = Pattern.compile( "JSESSIONID=(\\w+);.*" );
            Matcher matcher = pattern.matcher( cookie.getValue() );
            matcher.matches();

            return matcher.group( 1 );
        }
    }
}
