package org.hisp.dhis;

import org.hisp.dhis.category.IntegrationTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class OrgUnitApiTest
{
    @Test
    public void testOrgUnitMerge()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.CONFIG );
    }
}
