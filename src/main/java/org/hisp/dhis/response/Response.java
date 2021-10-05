package org.hisp.dhis.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.hc.core5.http.Header;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Message providing information about a DHIS 2 web API response.
 *
 * @author Lars Helge Overland
 */
@Getter
@Setter
@NoArgsConstructor
public class Response
    extends BaseHttpResponse
{
    @JsonProperty
    protected Status status;

    @JsonProperty
    protected Integer code;

    @JsonProperty
    protected String message;

    @JsonProperty
    protected String errorCode;

    @JsonProperty
    protected String devMessage;

    public Response( Status status, Integer httpStatusCode, String message )
    {
        this.status = status;
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }

    public Response( Status status, Integer httpStatusCode, String message, String errorCode )
    {
        this( status, httpStatusCode, message );
        this.errorCode = errorCode;
    }

    public HttpStatus getHttpStatus()
    {
        return HttpStatus.valueOf( httpStatusCode );
    }

    /**
     * Returns the value of the HTTP header with the given name, or null if not
     * found.
     *
     * @param name the HTTP header name.
     * @return the HTTP header value.
     */
    public String getHeader( String name )
    {
        if ( headers != null )
        {
            for ( Header header : headers )
            {
                if ( name.equals( header.getName() ) )
                {
                    return header.getValue();
                }
            }
        }

        return null;
    }

    @Override
    public String toString()
    {
        return new StringBuilder( "[" )
            .append( "status: " ).append( status ).append( ", " )
            .append( "code: " ).append( code ).append( ", " )
            .append( "httpStatusCode: " ).append( httpStatusCode ).append( ", " )
            .append( "devMessage: " ).append( devMessage ).append( "]" ).toString();
    }
}
