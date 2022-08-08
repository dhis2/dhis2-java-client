package org.hisp.dhis.auth;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BasicAuthentication
    implements Authentication
{
    @NonNull
    private final String username;

    @NonNull
    private final String password;
}
