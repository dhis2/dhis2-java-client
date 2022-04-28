package org.hisp.dhis;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.hisp.dhis.model.SystemInfo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag( "integration" )
public class SystemInfoApiTest
{
    @Test
    void testGetOrgUnit()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        SystemInfo info = dhis2.getSystemInfo();

        assertNotNull( info );
        assertNotNull( info.getVersion() );
        assertNotNull( info.getRevision() );
    }
}
