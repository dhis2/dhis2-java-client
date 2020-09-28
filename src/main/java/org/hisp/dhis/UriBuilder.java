package org.hisp.dhis;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.utils.URIBuilder;

public class UriBuilder
    extends URIBuilder
{
    public UriBuilder( URI uri )
    {
        super( uri );
    }

    public UriBuilder( String uri )
        throws URISyntaxException
    {
        super( uri );
    }

    /**
     * Appends the given path segment to the URI.
     *
     * @param path the path segment.
     * @return a {@link UriBuilder}.
     */
    public UriBuilder pathSegment( String path )
    {
        List<String> paths = new ArrayList<>( getPathSegments() );
        paths.add( path );
        setPathSegments( paths );
        return this;
    }
}
