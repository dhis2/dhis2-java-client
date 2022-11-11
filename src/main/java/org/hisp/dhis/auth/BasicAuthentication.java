package org.hisp.dhis.auth;

import java.util.Base64;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.apache.hc.core5.http.HttpHeaders;

@Getter
@RequiredArgsConstructor
public class BasicAuthentication
    implements Authentication
{
    @NonNull
    private final String username;

    @NonNull
    private final String password;

    @Override
    public String getHttpHeaderAuthName()
    {
        return HttpHeaders.AUTHORIZATION;
    }

    @Override
    public String getHttpHeaderAuthValue()
    {
        String value = String.format( "%s:%s", username, password );

        return "Basic " + Base64.getEncoder().encodeToString( value.getBytes() );
    }
}
