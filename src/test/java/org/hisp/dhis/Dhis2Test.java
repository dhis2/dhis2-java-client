package org.hisp.dhis;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class Dhis2Test
{
    @Test
    public void testDhis2()
    {
        Dhis2Config dhis2Config = new Dhis2Config(
            "https://play.dhis2.org/2.29/api",
            "admin",
            "district" );

        Dhis2 dhis2 = new Dhis2( dhis2Config );

        assertNotNull( dhis2 );
    }
}
