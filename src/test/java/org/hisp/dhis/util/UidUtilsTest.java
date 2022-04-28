package org.hisp.dhis.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class UidUtilsTest
{
    @Test
    void testGetUid()
    {
        String uid = UidUtils.generateUid();

        assertNotNull( uid );
        assertEquals( 11, uid.length() );
    }

    @Test
    void testUidIsValid()
    {
        assertTrue( UidUtils.isValidUid( "mq4jAnN6fg3" ) );
        assertTrue( UidUtils.isValidUid( "QX4LpiTZmUH" ) );
        assertTrue( UidUtils.isValidUid( "rT1hdSWjfDC" ) );

        assertFalse( UidUtils.isValidUid( "1T1hdSWjfDC" ) );
        assertFalse( UidUtils.isValidUid( "QX4LpiTZmUHg" ) );
        assertFalse( UidUtils.isValidUid( "1T1hdS_WjfD" ) );
    }
}
