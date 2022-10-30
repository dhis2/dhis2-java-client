package org.hisp.dhis;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class AuthTest
{
    @Test
    void testWithBasicAuth()
    {
        Dhis2 dhis2 = Dhis2.withBasicAuth(
            "https://play.dhis2.org/demo", "admin", "district" );
        assertNotNull( dhis2 );
    }

    @Test
    void testWithCookieAuth()
    {
        Dhis2 dhis2 = Dhis2.withCookieAuth(
            "https://play.dhis2.org/demo", "5EC557E60D7E5CE8D78EEC1389592D3E" );
        assertNotNull( dhis2 );
    }

    void testWithAccessTokenAuth()
    {
        Dhis2 dhis2 = Dhis2.withAccessTokenAuth(
            "https://play.dhis2.org/demo", "d2pat_2bBQecgNcxrS4EPhBJuRlQkwiLr2ATnC2557514242" );
        assertNotNull( dhis2 );
    }
}
