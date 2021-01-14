package org.hisp.dhis;

import org.hisp.dhis.category.IntegrationTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class Dhis2CrudApiTest
{
    private static final Dhis2Config config = new Dhis2Config(
        "https://play.dhis2.org/2.35.1", "admin", "district" );

    @Test
    public void testCategoryOption()
    {
        Dhis2 dhis2 = new Dhis2( config );

    }

}
