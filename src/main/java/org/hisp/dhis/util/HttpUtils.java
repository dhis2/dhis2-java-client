package org.hisp.dhis.util;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.core5.net.URIBuilder;
import org.hisp.dhis.Dhis2Config;
import org.hisp.dhis.auth.Authentication;

public class HttpUtils
{
    /**
     * Adds a HTTP header for authentication based on the {@link Authentication}
     * of the given {@link Dhis2Config}.
     *
     * @param request the {@link HttpUriRequestBase}.
     * @param config the {@link Dhis2Config}.
     * @param <T> the request class type.
     * @return the request object.
     */
    public static <T extends HttpUriRequestBase> T withAuth( T request, Dhis2Config config )
    {
        Authentication auth = config.getAuthentication();
        request.setHeader( auth.getHttpHeaderAuthName(), auth.getHttpHeaderAuthValue() );
        return request;
    }

    /**
     * Builds on URI without throwing checked exceptions.
     *
     * @param uriBuilder the {@link URIBuilder}.
     * @return a {@link URI}.
     */
    public static URI build( URIBuilder uriBuilder )
    {
        try
        {
            return uriBuilder.build();
        }
        catch ( URISyntaxException ex )
        {
            throw new RuntimeException( ex );
        }
    }

    /**
     * Returns the string representing the given URI. The URI is decoded.
     *
     * @param uri the {@link URI}.
     * @return a URI string.
     */
    public static String asString( URI uri )
    {
        try
        {
            return URLDecoder.decode( uri.toString(), StandardCharsets.UTF_8.toString() );
        }
        catch ( UnsupportedEncodingException ex )
        {
            throw new RuntimeException( ex );
        }
    }
}
