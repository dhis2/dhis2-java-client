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
    void testAddEventDataValue()
    {
        Event event = new Event( "q4Oloz9P8zj" );
        event.addDataValue( new EventDataValue( "i7LbGuGEana", "1" ) );
        event.addDataValue( new EventDataValue( "hK06Hg9jDSB", "2" ) );
        event.addDataValue( new EventDataValue( "i7LbGuGEana", "3" ) ); // Exists
        event.addDataValue( new EventDataValue( "b5VnIJbjioI", "4" ) );
        event.addDataValue( new EventDataValue( "hK06Hg9jDSB", "5" ) ); // Exists

        assertEquals( 3, event.getDataValues().size() );
        assertEquals( "3", event.getEventDataValue( "i7LbGuGEana" ).getValue() );
        assertEquals( "5", event.getEventDataValue( "hK06Hg9jDSB" ).getValue() );
        assertEquals( "4", event.getEventDataValue( "b5VnIJbjioI" ).getValue() );
    }

    @Test
    void testGetEventDataValue()
    {
        Event event = new Event( "q4Oloz9P8zj" );
        event.addDataValue( new EventDataValue( "i7LbGuGEana", "1" ) );
        event.addDataValue( new EventDataValue( "hK06Hg9jDSB", "2" ) );
        event.addDataValue( new EventDataValue( "KvIzgr6osJQ", "3" ) );
        event.addDataValue( new EventDataValue( "b5VnIJbjioI", "4" ) );
        event.addDataValue( new EventDataValue( "tbqtM7AQpnl", "5" ) );

        assertEquals( "3", event.getEventDataValue( "KvIzgr6osJQ" ).getValue() );
        assertEquals( "5", event.getEventDataValue( "tbqtM7AQpnl" ).getValue() );
        assertNull( event.getDataValue( "w4wYmYSS8ng" ) );
    }

    @Test
    void testGetDataValue()
    {
        Event event = new Event( "q4Oloz9P8zj" );
        event.addDataValue( new EventDataValue( "i7LbGuGEana", "1" ) );
        event.addDataValue( new EventDataValue( "hK06Hg9jDSB", "2" ) );
        event.addDataValue( new EventDataValue( "KvIzgr6osJQ", "3" ) );
        event.addDataValue( new EventDataValue( "b5VnIJbjioI", "4" ) );
        event.addDataValue( new EventDataValue( "tbqtM7AQpnl", "5" ) );

        assertEquals( "3", event.getDataValue( "KvIzgr6osJQ" ) );
        assertEquals( "5", event.getDataValue( "tbqtM7AQpnl" ) );
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

    @Test
    void testGetValueAsLocalDateTime()
    {
        EventDataValue value = new EventDataValue( "rNwYgGgYzPA", "2018-10-02T14:52:00.000Z" );

        assertTrue( value.isLocalDateTime() );
        assertEquals( 2018, value.getLocalDateTimeValue().getYear() );
        assertEquals( 10, value.getLocalDateTimeValue().getMonthValue() );
        assertEquals( 2, value.getLocalDateTimeValue().getDayOfMonth() );
    }
}
