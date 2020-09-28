package org.hisp.dhis;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;

import lombok.Getter;

/**
 * Configuration information about a DHIS 2 instance.
 *
 * @author Lars Helge Overland
 */
@Getter
public class Dhis2Config
{
    private String url;

    private String username;

    private String password;

    /**
     * Main constructor.
     *
     * @param url the URL to the DHIS 2 instance, do not include the {@code /api} part.
     * @param username the DHIS 2 account username.
     * @param password the DHIS 2 account password.
     */
    public Dhis2Config( String url, String username, String password )
    {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Provides a fully qualified {@link URI} to the DHIS 2 instance API.
     *
     * @param path the URL path (the URL part after {@code /api/}.
     * @return a fully qualified {@link URI} to the DHIS 2 instance API.
     */
    public URI getResolvedUrl( String path )
    {
        try
        {
            return new URIBuilder( url )
                .setPathSegments( "api", path )
                .build();
        }
        catch ( URISyntaxException ex )
        {
            throw new RuntimeException( String.format( "Invalid URI syntax: '%s'", url ), ex );
        }
    }

    /**
     * Provides a {@link URIBuilder} which is resolved to the DHIS 2
     * instance API.
     *
     * @return a {@link URIBuilder}.
     */
    public URIBuilder getResolvedUriBuilder()
    {
        try
        {
            return new URIBuilder( url )
                .setPathSegments( "api" );
        }
        catch ( URISyntaxException ex )
        {
            throw new RuntimeException( String.format( "Invalid URI syntax: '%s'", url ), ex );
        }
    }
}
