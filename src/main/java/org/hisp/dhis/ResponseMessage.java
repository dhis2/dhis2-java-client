package org.hisp.dhis;

import org.springframework.http.HttpHeaders;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Message providing information about a DHIS 2 web 
 * API response.
 */
public class ResponseMessage
{
    @JsonProperty
    private Status status;

    @JsonProperty
    private String httpStatus;
    
    @JsonProperty
    private Integer httpStatusCode;

    @JsonProperty
    private Integer code;

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

    public String getHttpStatus()
    {
        return httpStatus;
    }

    public void setHttpStatus( String httpStatus )
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
