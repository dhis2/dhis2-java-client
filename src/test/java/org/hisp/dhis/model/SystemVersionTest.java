package org.hisp.dhis.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SystemVersionTest
{
    private final SystemVersion v_2_39 = SystemVersion.of( "2.39" );
    private final SystemVersion v_2_39_2 = SystemVersion.of( "2.39.2" );
    private final SystemVersion v_2_39_2_1 = SystemVersion.of( "2.39.2.1" );
    private final SystemVersion v_2_40 = SystemVersion.of( "2.40" );
    private final SystemVersion v_2_40_0 = SystemVersion.of( "2.40.0" );
    private final SystemVersion v_2_40_1 = SystemVersion.of( "2.40.1" );
    
    @Test
    void testIsHigher()
    {
        assertTrue( v_2_39_2.isHigher( v_2_39.getVersion() ) );
        assertTrue( v_2_40.isHigher( v_2_39_2_1.getVersion() ) );
        assertFalse( v_2_39_2.isHigher( v_2_39_2.getVersion() ) );
        assertFalse( v_2_39_2_1.isHigher( v_2_40.getVersion() ) );
    }
    
    @Test
    void testIsHigherOrEqual()
    {
        assertTrue( v_2_40.isHigherOrEqual( v_2_40.getVersion() ) );
        assertTrue( v_2_40_0.isHigherOrEqual( v_2_39_2_1.getVersion() ) );
        assertFalse( v_2_39_2_1.isHigherOrEqual( v_2_40.getVersion() ) );
        assertFalse( v_2_39_2.isHigherOrEqual( v_2_39_2_1.getVersion() ) );
    }

    @Test
    void testEquals()
    {
        assertEquals( v_2_39_2, SystemVersion.of( "2.39.2" ) );
        assertEquals( v_2_39_2_1, SystemVersion.of( "2.39.2.1" ) );
        assertNotEquals( v_2_40_1, SystemVersion.of( "2.40.0" ) );
        assertNotEquals( v_2_39_2_1, SystemVersion.of( "2.39.2" ) );
    }
}
