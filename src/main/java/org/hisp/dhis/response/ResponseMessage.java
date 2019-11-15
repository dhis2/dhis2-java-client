package org.hisp.dhis.response;

import org.springframework.http.HttpHeaders;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Message providing information about a DHIS 2 web API response.
 *
 * @author Lars Helge Overland
 */
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

    public Status getStatus()
    {
        return status;
    }

    public void setStatus( Status status )
    {
        this.status = status;
    }

    public HttpStatus getHttpStatus()
    {
        return httpStatus;
    }

    public void setHttpStatus( HttpStatus httpStatus )
    {
        this.httpStatus = httpStatus;
    }

    public Integer getHttpStatusCode()
    {
        return httpStatusCode;
    }

    public void setHttpStatusCode( Integer httpStatusCode )
    {
        this.httpStatusCode = httpStatusCode;
    }

    public Integer getCode()
    {
        return code;
    }

    public void setCode( Integer code )
    {
        this.code = code;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage( String message )
    {
        this.message = message;
    }

    public String getDevMessage()
    {
        return devMessage;
    }

    public void setDevMessage( String devMessage )
    {
        this.devMessage = devMessage;
    }

    public HttpHeaders getHeaders()
    {
        return headers;
    }

    public void setHeaders( HttpHeaders headers )
    {
        this.headers = headers;
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
