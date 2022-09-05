package org.hisp.dhis.auth;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccessTokenAuthentication
    implements Authentication
{
    @NonNull
    private final String accessToken;

    @Override
    public String getHttpHeaderAuthValue()
    {
        return String.format( "ApiToken %s", accessToken );
    }
}
