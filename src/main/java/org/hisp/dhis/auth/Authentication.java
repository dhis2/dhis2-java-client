package org.hisp.dhis.auth;

public interface Authentication
{
    String getHttpHeaderAuthName();

    String getHttpHeaderAuthValue();
}
