package org.hisp.dhis;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class Dhis2Test
{
    private static final String BASE_URL = "http://localhost/dhis";

    private Dhis2Config dhis2Config;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void before()
    {
        dhis2Config = new Dhis2Config( BASE_URL, "admin", "district" );
    }

    @Test
    public void testDhis2()
    {
        Dhis2 dhis2 = new Dhis2( dhis2Config );

        assertNotNull( dhis2 );
    }
}
