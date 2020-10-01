package org.hisp.dhis;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;

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
     * Appends the given path segment to the URI. The path string
     * may contain multiple segments.
     *
     * @param path the path segment.
     * @return a {@link UriBuilder}.
     */
    public UriBuilder pathSegment( String path )
    {
        List<String> segments = URLEncodedUtils.parsePathSegments( path );
        appendPathSegments( segments );
        return this;
    }

    /**
     * Appends the given path segments.
     *
     * @param segments the path segments.
     */
    private void appendPathSegments( List<String> segments )
    {
        List<String> paths = new ArrayList<>( getPathSegments() );
        paths.addAll( segments );
        setPathSegments( paths );
    }
}
