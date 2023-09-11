package org.hisp.dhis;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import lombok.Getter;

import org.apache.commons.lang3.StringUtils;
import org.apache.hc.core5.net.URIBuilder;
import org.hisp.dhis.auth.AccessTokenAuthentication;
import org.hisp.dhis.auth.Authentication;
import org.hisp.dhis.auth.BasicAuthentication;
import org.hisp.dhis.auth.CookieAuthentication;
import org.hisp.dhis.response.Dhis2ClientException;

/**
 * Configuration information about a DHIS 2 instance.
 *
 * @author Lars Helge Overland
 */
@Getter
public class Dhis2Config
{
    private final String url;

    private final Authentication authentication;

    /**
     * Constructor. Uses basic authentication.
     *
     * @param url the URL to the DHIS 2 instance, do not include the
     *        {@code /api} part or a trailing {@code /}.
     * @param username the DHIS 2 account username.
     * @param password the DHIS 2 account password.
     */
    public Dhis2Config( String url, String username, String password )
    {
        Objects.requireNonNull( url );
        Objects.requireNonNull( username );
        Objects.requireNonNull( password );
        this.url = normalizeUrl( url );
        this.authentication = new BasicAuthentication( username, password );
    }

    /**
     * Constructor. Uses basic authentication.
     *
     * @param url the URL to the DHIS 2 instance, do not include the
     *        {@code /api} part or a trailing {@code /}.
     * @param authentication the {@link Authentication}, can be
     *        {@link BasicAuthentication}, {@link AccessTokenAuthentication} or
     *        {@link CookieAuthentication}.
     */
    public Dhis2Config( String url, Authentication authentication )
    {
        Objects.requireNonNull( url );
        Objects.requireNonNull( authentication );
        this.url = normalizeUrl( url );
        this.authentication = authentication;
    }

    /**
     * Normalizes the given URL.
     *
     * @param url the URL string.
     * @return a URL string.
     */
    private String normalizeUrl( String url )
    {
        return StringUtils.removeEnd( url, "/" );
    }

    /**
     * Provides a fully qualified {@link URI} to the DHIS 2 instance API.
     *
     * @param path the URL path (the URL part after {@code /api/}.
     * @return a fully qualified {@link URI} to the DHIS 2 instance API.
     */
    public URI getResolvedUrl( String path )
    {
        Objects.requireNonNull( path );

        try
        {
            return new URIBuilder( url )
                .appendPath( "api" )
                .appendPath( path )
                .build();
        }
        catch ( URISyntaxException ex )
        {
            throw new Dhis2ClientException( String.format( "Invalid URI syntax: '%s'", url ), ex );
        }
    }

    /**
     * Provides a {@link URIBuilder} which is resolved to the DHIS 2 instance
     * API.
     *
     * @return a {@link URIBuilder}.
     */
    public URIBuilder getResolvedUriBuilder()
    {
        try
        {
            return new URIBuilder( url )
                .appendPath( "api" );
        }
        catch ( URISyntaxException ex )
        {
            throw new Dhis2ClientException( String.format( "Invalid URI syntax: '%s'", url ), ex );
        }
    }
}
