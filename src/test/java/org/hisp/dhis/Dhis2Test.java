package org.hisp.dhis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Dhis2Test
{
    @Test
    public void testGetDhis2()
    {
        Dhis2Config config = new Dhis2Config( "https://play.dhis2.org/demo", "admin", "district" );

        Dhis2 dhis2 = new Dhis2( config );

        assertEquals( "https://play.dhis2.org/demo", dhis2.getDhis2Url() );
    }
}
