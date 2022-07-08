package org.hisp.dhis.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
}
