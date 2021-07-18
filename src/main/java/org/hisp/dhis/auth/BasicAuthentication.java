package org.hisp.dhis.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BasicAuthentication
    implements Authentication
{
    private final String username;

    private final String password;
}
