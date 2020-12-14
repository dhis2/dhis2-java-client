package org.hisp.dhis.response;

import lombok.Getter;

/**
 * Exception caused by client side errors.
 *
 * @author Lars Helge Overland
 */
@Getter
public class Dhis2ClientException
    extends RuntimeException
{
    private int statusCode;

    public Dhis2ClientException( String message, int statusCode )
    {
        super( message );
        this.statusCode = statusCode;
    }

    public Dhis2ClientException( String message, Throwable cause, int statusCode )
    {
        super( message, cause );
        this.statusCode = statusCode;
    }
}
