package org.hisp.dhis.model;

import static org.hisp.dhis.support.TestObjects.set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AttributeValueTest
{
    @Test
    public void testAddGetAttributeValues()
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
    public void testGetAttributeValue()
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
    public void testUpdateAttributeValue()
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
    public void testRemoveAttributeValue()
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
}
