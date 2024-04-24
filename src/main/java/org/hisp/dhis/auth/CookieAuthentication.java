package org.hisp.dhis.auth;

import org.apache.hc.core5.http.HttpHeaders;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CookieAuthentication
    implements Authentication
{
    @NonNull
    private final String sessionId;

    @Override
    public String getHttpHeaderAuthName()
    {
        return HttpHeaders.COOKIE;
    }

    @Override
    public String getHttpHeaderAuthValue()
    {
        return String.format( "JSESSIONID=%s", sessionId );
    }
}
