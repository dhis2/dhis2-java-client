package org.hisp.dhis.auth;

import lombok.Getter;

import org.apache.commons.lang3.Validate;

@Getter
public class CookieAuthentication
    implements Authentication
{
    private final String value;

    public CookieAuthentication( String value )
    {
        Validate.notNull( value );
        this.value = value;
    }
}
