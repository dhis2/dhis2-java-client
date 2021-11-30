package org.hisp.dhis;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.hisp.dhis.model.SystemInfo;
import org.junit.Test;

public class SystemInfoTest
{
    @Test
    public void testVersionComparison()
    {
        SystemInfo info = new SystemInfo();
        info.setVersion( "2.35.6" );
        info.setRevision( "b8d4ef2" );

        assertTrue( info.isHigher( "2.35.4" ) );
        assertTrue( info.isHigher( "2.32.3" ) );
        assertTrue( info.isHigher( "2.32-SNAPSHOT" ) );
        assertTrue( info.isHigherOrEqual( "2.34.3" ) );
        assertTrue( info.isHigherOrEqual( "2.35.6" ) );
        assertTrue( info.isEqual( "2.35.6" ) );
        assertTrue( info.isLowerOrEqual( "2.35.6" ) );
        assertTrue( info.isLowerOrEqual( "2.36.2" ) );
        assertTrue( info.isLowerOrEqual( "2.36-SNAPSHOT" ) );
        assertTrue( info.isLower( "2.37.1" ) );

        assertFalse( info.isHigher( "2.37.2" ) );
        assertFalse( info.isHigherOrEqual( "2.35.7" ) );
        assertFalse( info.isHigherOrEqual( "2.36-SNAPSHOT" ) );
        assertFalse( info.isEqual( "2.35.2" ) );
        assertFalse( info.isLowerOrEqual( "2.34.2" ) );
        assertFalse( info.isLowerOrEqual( "2.34.2-SNAPSHOT" ) );
        assertFalse( info.isLower( "2.34.5" ) );
    }
}
