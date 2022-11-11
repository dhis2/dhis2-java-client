package org.hisp.dhis.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.net.URIBuilder;
import org.hisp.dhis.Dhis2Config;
import org.hisp.dhis.auth.AccessTokenAuthentication;
import org.hisp.dhis.auth.BasicAuthentication;
import org.hisp.dhis.auth.CookieAuthentication;
import org.junit.jupiter.api.Test;

public class HttpUtilsTest
{
    private final String DEV_URL = "https://play.dhis2.org/dev";

    @Test
    void testAsString()
        throws Exception
    {
        String encodedUri = "https://atlantis.dhis2.org/staging/api/analytics?dimension=dx%3AGF_CM-1a%3BGF_CM-1b%3BGF_CM-1c%3BGF_KP-1a%3BGF_KP-1b%3BGF_KP-3a&dimension=pe%3A202009%3B202010";
        String decodedUri = "https://atlantis.dhis2.org/staging/api/analytics?dimension=dx:GF_CM-1a;GF_CM-1b;GF_CM-1c;GF_KP-1a;GF_KP-1b;GF_KP-3a&dimension=pe:202009;202010";

        List<String> pathSegments = new ArrayList<>();
        pathSegments.add( "staging" );
        pathSegments.add( "api" );
        pathSegments.add( "analytics" );

        URI uri = new URIBuilder( "https://atlantis.dhis2.org" )
            .setPathSegments( pathSegments )
            .addParameter( "dimension", "dx:GF_CM-1a;GF_CM-1b;GF_CM-1c;GF_KP-1a;GF_KP-1b;GF_KP-3a" )
            .addParameter( "dimension", "pe:202009;202010" )
            .build();

        assertEquals( encodedUri, uri.toString() );
        assertEquals( decodedUri, HttpUtils.asString( uri ) );
    }

    @Test
    void testWithBasicAuth()
        throws Exception
    {
        HttpPost post = new HttpPost( DEV_URL );

        HttpUtils.withAuth( post, new Dhis2Config( DEV_URL, new BasicAuthentication( "admin", "district" ) ) );

        assertEquals( "Basic YWRtaW46ZGlzdHJpY3Q=", post.getHeader( HttpHeaders.AUTHORIZATION ).getValue() );
    }

    @Test
    void testWithAccessTokenAuth()
        throws Exception
    {
        HttpPost post = new HttpPost( DEV_URL );

        HttpUtils.withAuth( post, new Dhis2Config( DEV_URL, new AccessTokenAuthentication( "d2pat_kjytgr63jj837" ) ) );

        assertEquals( "ApiToken d2pat_kjytgr63jj837", post.getHeader( HttpHeaders.AUTHORIZATION ).getValue() );
    }

    @Test
    void testWithCookieAuth()
        throws Exception
    {
        HttpPost post = new HttpPost( DEV_URL );

        HttpUtils.withAuth( post, new Dhis2Config( DEV_URL, new CookieAuthentication( "KJH8KJ24fRD3FK491" ) ) );

        assertEquals( "JSESSIONID=KJH8KJ24fRD3FK491", post.getHeader( HttpHeaders.COOKIE ).getValue() );
    }
}
