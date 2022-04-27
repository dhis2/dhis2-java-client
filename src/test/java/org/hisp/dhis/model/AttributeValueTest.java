package org.hisp.dhis.model;

import static org.hisp.dhis.support.TestObjectUtils.set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AttributeValueTest
{
    @Test
    public void testAddGetAttributeValues()
    {
        Attribute atA = set( new Attribute(), 'A' );
        Attribute atB = set( new Attribute(), 'B' );
        Attribute atC = set( new Attribute(), 'B' );

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
        Attribute atC = set( new Attribute(), 'B' );

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
    }
}
