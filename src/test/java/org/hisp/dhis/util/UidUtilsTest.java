package org.hisp.dhis.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UidUtilsTest
{
    @Test
    public void testGetUid()
    {
        String uid = UidUtils.generateUid();

        assertNotNull( uid );
        assertEquals( 11, uid.length() );
    }

    @Test
    public void testUidIsValid()
    {
        assertTrue( UidUtils.isValidUid( "mq4jAnN6fg3" ) );
        assertTrue( UidUtils.isValidUid( "QX4LpiTZmUH" ) );
        assertTrue( UidUtils.isValidUid( "rT1hdSWjfDC" ) );

        assertFalse( UidUtils.isValidUid( "1T1hdSWjfDC" ) );
        assertFalse( UidUtils.isValidUid( "QX4LpiTZmUHg" ) );
        assertFalse( UidUtils.isValidUid( "1T1hdS_WjfD" ) );
    }
}
