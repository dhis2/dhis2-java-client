package org.hisp.dhis.response;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

/**
 * Exception caused by client side errors.
 *
 * @author Lars Helge Overland
 */
@Getter
@Setter
public class Dhis2ClientException
    extends RuntimeException
{
    private HttpStatus statusCode;

    private HttpHeaders responseHeaders;

    private String reponseBodyString;

    public Dhis2ClientException( String message, Throwable cause, HttpStatus statusCode, HttpHeaders responseHeaders, String responseBodyString )
    {
        super( message, cause );
        this.statusCode = statusCode;
        this.responseHeaders = responseHeaders;
        this.reponseBodyString = responseBodyString;
    }

    public int getRawStatusCode()
    {
        return statusCode != null ? statusCode.value() : -1;
    }

    public String getStatusText()
    {
        return statusCode != null ? statusCode.name() : null;
    }
}
