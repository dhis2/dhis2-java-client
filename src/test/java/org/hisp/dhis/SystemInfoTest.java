package org.hisp.dhis;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.hisp.dhis.model.SystemInfo;
import org.hisp.dhis.model.SystemVersion;
import org.junit.Test;

public class SystemInfoTest
{
    @Test
    public void testVersionComparison()
    {
        SystemInfo info = new SystemInfo();
        info.setVersion( "2.35.6" );
        info.setRevision( "b8d4ef2" );

        SystemVersion version = info.getSystemVersion();

        assertTrue( version.isHigher( "2.35.4" ) );
        assertTrue( version.isHigher( "2.32.3" ) );
        assertTrue( version.isHigher( "2.32-SNAPSHOT" ) );
        assertTrue( version.isHigherOrEqual( "2.34.3" ) );
        assertTrue( version.isHigherOrEqual( "2.35.6" ) );
        assertTrue( version.isEqual( "2.35.6" ) );
        assertTrue( version.isLowerOrEqual( "2.35.6" ) );
        assertTrue( version.isLowerOrEqual( "2.36.2" ) );
        assertTrue( version.isLowerOrEqual( "2.36-SNAPSHOT" ) );
        assertTrue( version.isLower( "2.37.1" ) );

        assertFalse( version.isHigher( "2.37.2" ) );
        assertFalse( version.isHigherOrEqual( "2.35.7" ) );
        assertFalse( version.isHigherOrEqual( "2.36-SNAPSHOT" ) );
        assertFalse( version.isEqual( "2.35.2" ) );
        assertFalse( version.isLowerOrEqual( "2.34.2" ) );
        assertFalse( version.isLowerOrEqual( "2.34.2-SNAPSHOT" ) );
        assertFalse( version.isLower( "2.34.5" ) );
    }
}
