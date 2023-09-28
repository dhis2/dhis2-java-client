package org.hisp.dhis.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class VersionUtilsTest
{
    @Test
    void testGetMajorVersion()
    {
        assertEquals( 37, VersionUtils.getMajorVersion( "2.37.8.1-SNAPSHOT" ) );
        assertEquals( 28, VersionUtils.getMajorVersion( "2.28" ) );
        assertEquals( 35, VersionUtils.getMajorVersion( "2.35.11" ) );
        assertEquals( 37, VersionUtils.getMajorVersion( "2.37.9" ) );
        assertEquals( 35, VersionUtils.getMajorVersion( "2.35.6.1-SNAPSHOT" ) );
        assertEquals( 38, VersionUtils.getMajorVersion( "2.38.1-EMBARGOED" ) );
        assertNull( VersionUtils.getMajorVersion( "FOO" ) );
        assertNull( VersionUtils.getMajorVersion( null ) );
    }

    @Test
    void testGetPatchVersion()
    {
        assertEquals( 8, VersionUtils.getPatchVersion( "2.37.8.1-SNAPSHOT" ) );
        assertNull( VersionUtils.getPatchVersion( "2.28" ) );
        assertEquals( 11, VersionUtils.getPatchVersion( "2.35.11" ) );
        assertEquals( 9, VersionUtils.getPatchVersion( "2.37.9" ) );
        assertEquals( 6, VersionUtils.getPatchVersion( "2.35.6.1-SNAPSHOT" ) );
        assertEquals( 1, VersionUtils.getPatchVersion( "2.38.1-EMBARGOED" ) );
        assertNull( VersionUtils.getPatchVersion( "FOO" ) );
        assertNull( VersionUtils.getMajorVersion( null ) );
    }
}
