package org.hisp.dhis.response;

import org.springframework.http.HttpHeaders;

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
    private HttpHeaders headers;

    public ResponseMessage()
    {
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
