package org.hisp.dhis.model;

import static org.hisp.dhis.support.TestObjects.set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class AttributeValueTest
{
    @Test
    void testAddGetAttributeValues()
    {
        Attribute atA = set( new Attribute(), 'A' );
        Attribute atB = set( new Attribute(), 'B' );
        Attribute atC = set( new Attribute(), 'C' );

        AttributeValue avA = new AttributeValue( atA, "ValA" );
        AttributeValue avB = new AttributeValue( atB, "ValB" );
        AttributeValue avC = new AttributeValue( atC, "ValC" );

        OrgUnit orgUnit = set( new OrgUnit(), 'A' );
        orgUnit.addAttributeValue( avA );
        orgUnit.addAttributeValue( avB );
        orgUnit.addAttributeValue( avC );

        assertEquals( 3, orgUnit.getAttributeValues().size() );
        assertTrue( orgUnit.getAttributeValues().contains( avA ) );
        assertTrue( orgUnit.getAttributeValues().contains( avB ) );
        assertTrue( orgUnit.getAttributeValues().contains( avC ) );
    }

    @Test
    void testGetAttributeValue()
    {
        Attribute atA = set( new Attribute(), 'A' );
        Attribute atB = set( new Attribute(), 'B' );
        Attribute atC = set( new Attribute(), 'C' );

        AttributeValue avA = new AttributeValue( atA, "ValA" );
        AttributeValue avB = new AttributeValue( atB, "ValB" );
        AttributeValue avC = new AttributeValue( atC, "ValC" );

        OrgUnit orgUnit = set( new OrgUnit(), 'A' );
        orgUnit.addAttributeValue( avA );
        orgUnit.addAttributeValue( avB );
        orgUnit.addAttributeValue( avC );

        assertEquals( avA, orgUnit.getAttributeValue( atA.getId() ) );
        assertEquals( avB, orgUnit.getAttributeValue( atB.getId() ) );
        assertEquals( avC, orgUnit.getAttributeValue( atC.getId() ) );

        assertEquals( "ValA", orgUnit.getAttributeValue( atA.getId() ).getValue() );
        assertEquals( "ValB", orgUnit.getAttributeValue( atB.getId() ).getValue() );
        assertEquals( "ValC", orgUnit.getAttributeValue( atC.getId() ).getValue() );
    }

    @Test
    void testHasAttributeValue()
    {
        Attribute atA = set( new Attribute(), 'A' );
        Attribute atB = set( new Attribute(), 'B' );
        Attribute atC = set( new Attribute(), 'C' );

        AttributeValue avA = new AttributeValue( atA, "ValA" );
        AttributeValue avB = new AttributeValue( atB, "ValB" );

        OrgUnit orgUnit = set( new OrgUnit(), 'A' );
        orgUnit.addAttributeValue( avA );
        orgUnit.addAttributeValue( avB );

        assertTrue( orgUnit.hasAttributeValue( atA.getId() ) );
        assertTrue( orgUnit.hasAttributeValue( atB.getId() ) );
        assertFalse( orgUnit.hasAttributeValue( atC.getId() ) );
    }

    @Test
    void testUpdateAttributeValue()
    {
        Attribute atA = set( new Attribute(), 'A' );
        Attribute atB = set( new Attribute(), 'B' );

        AttributeValue avA = new AttributeValue( atA, "ValA" );
        AttributeValue avB = new AttributeValue( atB, "ValB" );

        OrgUnit orgUnit = set( new OrgUnit(), 'A' );
        orgUnit.addAttributeValue( avA );
        orgUnit.addAttributeValue( avB );

        assertEquals( "ValA", orgUnit.getAttributeValue( atA.getId() ).getValue() );
        assertEquals( "ValB", orgUnit.getAttributeValue( atB.getId() ).getValue() );

        avA.setValue( "ValX" );

        orgUnit.updateAttributeValue( avA );

        assertEquals( "ValX", orgUnit.getAttributeValue( atA.getId() ).getValue() );
    }

    @Test
    void testRemoveAttributeValue()
    {
        Attribute atA = set( new Attribute(), 'A' );
        Attribute atB = set( new Attribute(), 'B' );
        Attribute atC = set( new Attribute(), 'C' );

        AttributeValue avA = new AttributeValue( atA, "ValA" );
        AttributeValue avB = new AttributeValue( atB, "ValB" );
        AttributeValue avC = new AttributeValue( atC, "ValC" );

        OrgUnit orgUnit = set( new OrgUnit(), 'A' );
        orgUnit.addAttributeValue( avA );
        orgUnit.addAttributeValue( avB );
        orgUnit.addAttributeValue( avC );

        assertEquals( 3, orgUnit.getAttributeValues().size() );
        assertTrue( orgUnit.getAttributeValues().contains( avA ) );
        assertTrue( orgUnit.getAttributeValues().contains( avB ) );
        assertTrue( orgUnit.getAttributeValues().contains( avC ) );

        orgUnit.removeAttributeValue( atA.getId() );

        assertEquals( 2, orgUnit.getAttributeValues().size() );
        assertFalse( orgUnit.getAttributeValues().contains( avA ) );
        assertTrue( orgUnit.getAttributeValues().contains( avB ) );
        assertTrue( orgUnit.getAttributeValues().contains( avC ) );

        orgUnit.removeAttributeValue( atB.getId() );

        assertEquals( 1, orgUnit.getAttributeValues().size() );
        assertFalse( orgUnit.getAttributeValues().contains( avA ) );
        assertFalse( orgUnit.getAttributeValues().contains( avB ) );
        assertTrue( orgUnit.getAttributeValues().contains( avC ) );
    }

    @Test
    void testGetValueAsInteger()
    {
        Attribute atA = set( new Attribute(), 'A' );
        AttributeValue avA = new AttributeValue( atA, "142" );

        assertTrue( avA.isInteger() );
        assertEquals( 142, avA.getIntegerValue() );

        avA = new AttributeValue( atA, "14.73" );

        assertFalse( avA.isInteger() );
        assertNull( avA.getIntegerValue() );

        avA = new AttributeValue( atA, null );

        assertFalse( avA.isInteger() );
        assertNull( avA.getIntegerValue() );
    }

    @Test
    void testGetValueAsDouble()
    {
        Attribute atA = set( new Attribute(), 'A' );
        AttributeValue avA = new AttributeValue( atA, "18.52" );

        assertTrue( avA.isDouble() );
        assertEquals( 18.52, avA.getDoubleValue() );

        avA = new AttributeValue( atA, "Yes" );

        assertFalse( avA.isDouble() );
        assertNull( avA.getDoubleValue() );

        avA = new AttributeValue( atA, null );

        assertFalse( avA.isDouble() );
        assertNull( avA.getDoubleValue() );
    }
}
