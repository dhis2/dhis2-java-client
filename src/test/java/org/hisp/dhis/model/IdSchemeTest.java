package org.hisp.dhis.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class IdSchemeTest
{
    @Test
    public void testGetAttributeIdScheme()
    {
        IdScheme attribute = IdScheme.createAttributeIdScheme( "JYHR54Rfr41" );

        assertEquals( IdScheme.ObjectProperty.ATTRIBUTE, attribute.getObjectProperty() );
        assertEquals( "JYHR54Rfr41", attribute.getAttribute() );
    }

    @Test
    public void testGetIdSchemeFromString()
    {
        IdScheme uidScheme = IdScheme.createIdScheme( "UID" );
        assertEquals( IdScheme.ObjectProperty.UID, uidScheme.getObjectProperty() );

        IdScheme codeScheme = IdScheme.createIdScheme( "CODE" );
        assertEquals( IdScheme.ObjectProperty.CODE, codeScheme.getObjectProperty() );
    }

    @Test
    public void testGetIdSchemeFromConstructor()
    {
        IdScheme uidScheme = new IdScheme( "UID" );
        assertEquals( IdScheme.ObjectProperty.UID, uidScheme.getObjectProperty() );

        IdScheme codeScheme = new IdScheme( "CODE" );
        assertEquals( IdScheme.ObjectProperty.CODE, codeScheme.getObjectProperty() );
    }

    @Test
    public void testGetAttributeIdSchemeFromString()
    {
        IdScheme attributeAScheme = IdScheme.createIdScheme( "attribute:HGT65Gdgq2k" );
        IdScheme attributeBScheme = IdScheme.createIdScheme( "attribute:LUJJhf2jf21" );
        IdScheme attributeCScheme = IdScheme.createIdScheme( null );

        assertEquals( IdScheme.ObjectProperty.ATTRIBUTE, attributeAScheme.getObjectProperty() );
        assertEquals( "HGT65Gdgq2k", attributeAScheme.getAttribute() );
        assertEquals( IdScheme.ObjectProperty.ATTRIBUTE, attributeBScheme.getObjectProperty() );
        assertEquals( "LUJJhf2jf21", attributeBScheme.getAttribute() );
        assertNull( attributeCScheme );
    }

    @Test
    public void testGetName()
    {
        IdScheme idSchemeA = IdScheme.createIdScheme( "attribute:HGT65Gdgq2k" );
        IdScheme idSchemeB = IdScheme.createIdScheme( "CODE" );

        assertEquals( "attribute:HGT65Gdgq2k", idSchemeA.name() );
        assertEquals( "CODE", idSchemeB.name() );
    }

    @Test( expected = IllegalArgumentException.class )
    public void testGetInvalidAttributeIdSchemeA()
    {
        IdScheme.createIdScheme( "FORM_NAME" );
    }

    @Test( expected = IllegalArgumentException.class )
    public void testGetInvalidAttributeIdSchemeB()
    {
        IdScheme.createIdScheme( "attribute:invalid_uid" );
    }
}
