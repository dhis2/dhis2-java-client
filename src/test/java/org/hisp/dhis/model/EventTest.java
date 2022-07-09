package org.hisp.dhis.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.hisp.dhis.model.event.Event;
import org.hisp.dhis.model.event.EventDataValue;
import org.junit.jupiter.api.Test;

public class EventTest
{
    @Test
    void testGetDataValue()
    {
        Event event = new Event( "q4Oloz9P8zj" );
        event.getDataValues().add( new EventDataValue( "i7LbGuGEana", "1" ) );
        event.getDataValues().add( new EventDataValue( "hK06Hg9jDSB", "2" ) );
        event.getDataValues().add( new EventDataValue( "KvIzgr6osJQ", "3" ) );
        event.getDataValues().add( new EventDataValue( "b5VnIJbjioI", "4" ) );
        event.getDataValues().add( new EventDataValue( "tbqtM7AQpnl", "5" ) );

        assertEquals( "3", event.getDataValue( "KvIzgr6osJQ" ).getValue() );
        assertEquals( "5", event.getDataValue( "tbqtM7AQpnl" ).getValue() );
        assertNull( event.getDataValue( "w4wYmYSS8ng" ) );
    }

    @Test
    void testGetValueAsBoolean()
    {
        EventDataValue value = new EventDataValue( "rNwYgGgYzPA", "true" );

        assertTrue( value.isBoolean() );
        assertTrue( value.getBooleanValue() );

        value = new EventDataValue( "rNwYgGgYzPA", "TRUE" );

        assertTrue( value.isBoolean() );
        assertTrue( value.getBooleanValue() );

        value = new EventDataValue( "rNwYgGgYzPA", "false" );

        assertTrue( value.isBoolean() );
        assertFalse( value.getBooleanValue() );

        value = new EventDataValue( "rNwYgGgYzPA", "FALSE" );

        assertTrue( value.isBoolean() );
        assertFalse( value.getBooleanValue() );

        value = new EventDataValue( "rNwYgGgYzPA", "1" );

        assertFalse( value.isBoolean() );
        assertNull( value.getBooleanValue() );
    }

    @Test
    void testGetValueAsInteger()
    {
        EventDataValue value = new EventDataValue( "rNwYgGgYzPA", "142" );

        assertTrue( value.isInteger() );
        assertEquals( 142, value.getIntegerValue() );

        value = new EventDataValue( "rNwYgGgYzPA", "14.73" );

        assertFalse( value.isInteger() );
        assertNull( value.getIntegerValue() );
    }

    @Test
    void testGetValueAsDouble()
    {
        EventDataValue value = new EventDataValue( "rNwYgGgYzPA", "18.52" );

        assertTrue( value.isDouble() );
        assertEquals( 18.52, value.getDoubleValue() );

        value = new EventDataValue( "rNwYgGgYzPA", "Yes" );

        assertFalse( value.isDouble() );
        assertNull( value.getDoubleValue() );
    }
}
