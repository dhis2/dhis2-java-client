package org.hisp.dhis.util;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.utils.URIBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HttpUtilsTest
{
    @Test
    public void testAsString()
        throws Exception
    {
        String expectedEncodedUri = "https://togo.dhis2.org/staging/api/analytics?dimension=dx%3AGF_CM-1a%3BGF_CM-1b%3BGF_CM-1c%3BGF_KP-1a%3BGF_KP-1b%3BGF_KP-3a&dimension=pe%3A202009%3B202010";
        String expectedDecodedUri = "https://togo.dhis2.org/staging/api/analytics?dimension=dx:GF_CM-1a;GF_CM-1b;GF_CM-1c;GF_KP-1a;GF_KP-1b;GF_KP-3a&dimension=pe:202009;202010";

        List<String> pathSegments = new ArrayList<>();
        pathSegments.add( "staging" );
        pathSegments.add( "api" );
        pathSegments.add( "analytics" );

        URI uri = new URIBuilder( "https://togo.dhis2.org" )
            .setPathSegments( pathSegments )
            .addParameter( "dimension", "dx:GF_CM-1a;GF_CM-1b;GF_CM-1c;GF_KP-1a;GF_KP-1b;GF_KP-3a" )
            .addParameter( "dimension", "pe:202009;202010" )
            .build();

        assertEquals( expectedEncodedUri, uri.toString() );
        assertEquals( expectedDecodedUri, HttpUtils.asString( uri ) );
    }
}
