package org.hisp.dhis;

public class TestFixture
{
    public static final String DEFAULT_URL = "https://play.dhis2.org/2.36.3";

    public static final String DEV_URL = "https://play.dhis2.org/dev";

    public static final Dhis2Config DEFAULT_CONFIG = new Dhis2Config(
        DEFAULT_URL, "system", "System123" );

    public static final Dhis2Config DEV_CONFIG = new Dhis2Config(
        DEV_URL, "system", "System123" );
}
