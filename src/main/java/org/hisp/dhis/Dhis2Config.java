package org.hisp.dhis;

import org.springframework.web.util.UriComponentsBuilder;

/**
 * Configuration information about a DHIS 2 instance.
 */
public class Dhis2Config
{
    private String url;
    
    private String username;
    
    private String password;
    
    /**
     * Main constructor.
     * 
     * @param url the URL to the DHIS 2 instance including the {@code /api} part.
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
     * Provides a fully qualified URL to the DHIS 2 instance API.
     * 
     * @param path the URL path (the URL part after {@code /api/}.
     * @return a fully qualified URL to the DHIS 2 instance API.
     */
    public String getResolvedUrl( String path )
    {
        return UriComponentsBuilder.fromHttpUrl( url )
            .pathSegment( "api" )
            .pathSegment( path )
            .build()
            .toString();
    }
    
    /**
     * Provides a {@link UriComponentsBuilder} which is resolved to
     * the DHIS 2 instance API.
     * 
     * @return a {@link UriComponentsBuilder}.
     */
    public UriComponentsBuilder getResolvedUriBuilder()
    {
        return UriComponentsBuilder.fromHttpUrl( url )
            .pathSegment( "api" );
    }
    
    public String getUrl()
    {
        return url;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public String getPassword()
    {
        return password;
    }
}
