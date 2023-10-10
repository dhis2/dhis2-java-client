package org.hisp.dhis;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor( access = AccessLevel.PRIVATE )
public final class TestFixture
{
    public static final String DEFAULT_URL = "https://play.dhis2.org/2.37.10";

    public static final String DEV_URL = "https://play.dhis2.org/dev";

    public static final String LOCAL_URL = "https://localhost/dhis";

    public static final Dhis2Config DEFAULT_CONFIG = new Dhis2Config(
        DEFAULT_URL, "system", "System123" );

    public static final Dhis2Config DEV_CONFIG = new Dhis2Config(
        DEV_URL, "system", "System123" );

    public static final Dhis2Config LOCAL_CONFIG = new Dhis2Config(
        LOCAL_URL, "system", "System123" );
}
