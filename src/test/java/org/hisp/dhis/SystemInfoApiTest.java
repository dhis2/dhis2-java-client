package org.hisp.dhis;

import static org.junit.Assert.assertNotNull;

import org.hisp.dhis.category.IntegrationTest;
import org.hisp.dhis.model.SystemInfo;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category( IntegrationTest.class )
public class SystemInfoApiTest
{
    @Test
    public void testGetOrgUnit()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        SystemInfo info = dhis2.getSystemInfo();

        assertNotNull( info );
        assertNotNull( info.getVersion() );
        assertNotNull( info.getRevision() );
    }
}
