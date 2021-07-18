package org.hisp.dhis.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CookieAuthentication
    implements Authentication
{
    private final String value;
}
