package org.hisp.dhis.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class IdSchemeTest
{
    @Test
    void testGetAttributeIdScheme()
    {
        IdScheme attribute = IdScheme.createAttributeIdScheme( "JYHR54Rfr41" );

        assertEquals( IdScheme.ObjectProperty.ATTRIBUTE, attribute.getObjectProperty() );
        assertEquals( "JYHR54Rfr41", attribute.getAttribute() );
    }

    @Test
    void testGetIdSchemeFromString()
    {
        IdScheme uidScheme = IdScheme.createIdScheme( "uid" );
        assertEquals( IdScheme.ObjectProperty.UID, uidScheme.getObjectProperty() );

        IdScheme codeScheme = IdScheme.createIdScheme( "code" );
        assertEquals( IdScheme.ObjectProperty.CODE, codeScheme.getObjectProperty() );

        IdScheme codeUpperCaseScheme = IdScheme.createIdScheme( "CODE" );
        assertEquals( IdScheme.ObjectProperty.CODE, codeUpperCaseScheme.getObjectProperty() );
    }

    @Test
    void testGetIdSchemeFromConstructor()
    {
        IdScheme uidScheme = new IdScheme( "uid" );
        assertEquals( IdScheme.ObjectProperty.UID, uidScheme.getObjectProperty() );

        IdScheme codeScheme = new IdScheme( "code" );
        assertEquals( IdScheme.ObjectProperty.CODE, codeScheme.getObjectProperty() );

        IdScheme codeUpperCaseScheme = new IdScheme( "CODE" );
        assertEquals( IdScheme.ObjectProperty.CODE, codeUpperCaseScheme.getObjectProperty() );
    }

    @Test
    void testGetAttributeIdSchemeFromString()
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
    void testGetName()
    {
        IdScheme idSchemeA = IdScheme.createIdScheme( "attribute:HGT65Gdgq2k" );
        IdScheme idSchemeB = IdScheme.createIdScheme( "code" );
        IdScheme idSchemeC = IdScheme.createIdScheme( "CODE" );

        assertEquals( "attribute:HGT65Gdgq2k", idSchemeA.name() );
        assertEquals( "code", idSchemeB.name() );
        assertEquals( "code", idSchemeC.name() );
    }

    @Test
    void testToString()
    {
        IdScheme idSchemeA = IdScheme.createIdScheme( "attribute:HGT65Gdgq2k" );
        IdScheme idSchemeB = IdScheme.createIdScheme( "code" );
        IdScheme idSchemeC = IdScheme.createIdScheme( "CODE" );

        assertEquals( "attribute:HGT65Gdgq2k", idSchemeA.toString() );
        assertEquals( "code", idSchemeB.toString() );
        assertEquals( "code", idSchemeC.toString() );
    }

    @Test
    void testGetNullIdScheme()
    {
        assertNull( IdScheme.createIdScheme( null ) );
    }

    @Test
    void testGetInvalidAttributeIdSchemeA()
    {
        assertThrows( IllegalArgumentException.class, () -> IdScheme.createIdScheme( "FORM_NAME" ) );
    }

    @Test
    void testGetInvalidAttributeIdSchemeB()
    {
        assertThrows( IllegalArgumentException.class, () -> IdScheme.createIdScheme( "attribute:invalid_uid" ) );
    }

    @Test
    void testNullConstructor()
    {
        assertThrows( NullPointerException.class, () -> new IdScheme( null ) );
    }
}
