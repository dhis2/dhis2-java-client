package org.hisp.dhis;

public class TestFixture
{
    public static final Dhis2Config DEFAULT_CONFIG = new Dhis2Config(
        "https://play.dhis2.org/2.35.6", "system", "System123" );

    public static final Dhis2Config DEV_CONFIG = new Dhis2Config(
        "https://play.dhis2.org/dev", "system", "System123" );
}
