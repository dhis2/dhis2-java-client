package org.hisp.dhis.auth;

import lombok.Getter;

import org.apache.commons.lang3.Validate;

@Getter
public class BasicAuthentication
    implements Authentication
{
    private final String username;

    private final String password;

    public BasicAuthentication( String username, String password )
    {
        Validate.notNull( username );
        Validate.notNull( password );
        this.username = username;
        this.password = password;
    }
}
