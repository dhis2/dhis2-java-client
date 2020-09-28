package org.hisp.dhis.response;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * Message providing information about a DHIS 2 web API response.
 *
 * @author Lars Helge Overland
 */
@Getter
@Setter
public class ResponseMessage
    implements HttpResponseMessage
{
    @JsonProperty
    private Status status;

    @JsonProperty
    private HttpStatus httpStatus;

    @JsonProperty
    private Integer httpStatusCode;

    @JsonProperty
    private Integer code;

    @JsonProperty
    private String message;

    @JsonProperty
    private String devMessage;

    @JsonIgnore
    private List<Header> headers = new ArrayList<>();

    public ResponseMessage()
    {
    }

    /**
     * Returns the value of the HTTP header with the given
     * name, or null if not found.
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
        return new StringBuilder( "[")
            .append( "status: " ).append( status ).append( ", " )
            .append( "code: " ).append( code ).append( ", " )
            .append( "httpStatus: " ).append( httpStatus ).append( ", " )
            .append( "httpStatusCode: " ).append( httpStatusCode ).append( ", " )
            .append( "devMessage: " ).append( devMessage ).append( "]" ).toString();
    }
}
