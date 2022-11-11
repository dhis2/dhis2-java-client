package org.hisp.dhis.auth;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.apache.hc.core5.http.HttpHeaders;

@Getter
@RequiredArgsConstructor
public class AccessTokenAuthentication
    implements Authentication
{
    @NonNull
    private final String accessToken;

    @Override
    public String getHttpHeaderAuthName()
    {
        return HttpHeaders.AUTHORIZATION;
    }

    @Override
    public String getHttpHeaderAuthValue()
    {
        return String.format( "ApiToken %s", accessToken );
    }
}
