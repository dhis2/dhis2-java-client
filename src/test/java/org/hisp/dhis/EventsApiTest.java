package org.hisp.dhis;

import static org.hisp.dhis.util.CollectionUtils.list;
import static org.hisp.dhis.util.DateTimeUtils.getDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.hisp.dhis.model.event.Event;
import org.hisp.dhis.model.event.EventDataValue;
import org.hisp.dhis.model.event.Events;
import org.hisp.dhis.model.event.EventsResult;
import org.hisp.dhis.query.event.EventsQuery;
import org.hisp.dhis.response.Dhis2ClientException;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.event.ErrorReport;
import org.hisp.dhis.response.event.EventResponse;
import org.hisp.dhis.support.TestTags;
import org.hisp.dhis.util.UidUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag( TestTags.INTEGRATION )
class EventsApiTest
{
    @Test
    void testSaveGetRemoveEvents()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        String uidA = UidUtils.generateUid();
        String uidB = UidUtils.generateUid();

        List<EventDataValue> dvA = list(
            new EventDataValue( "oZg33kd9taw", "Male" ),
            new EventDataValue( "qrur9Dvnyt5", "45" ),
            new EventDataValue( "GieVkTxp4HH", "143" ),
            new EventDataValue( "vV9UWAZohSf", "43" ),
            new EventDataValue( "eMyVanycQSC", "2021-07-02" ),
            new EventDataValue( "msodh3rEMJa", "2021-08-05" ),
            new EventDataValue( "K6uUAvq500H", "A010" ),
            new EventDataValue( "fWIAEtYVEGk", "MODDISCH" ) );

        Event evA = new Event( uidA );
        evA.setProgram( "eBAyeGv0exc" );
        evA.setProgramStage( "Zj7UnCAulEk" );
        evA.setOrgUnit( "DiszpKrYNg8" );
        evA.setOccurredAt( getDate( 2021, 7, 12 ) );
        evA.setDataValues( dvA );

        List<EventDataValue> dvB = list(
            new EventDataValue( "oZg33kd9taw", "Female" ),
            new EventDataValue( "qrur9Dvnyt5", "41" ),
            new EventDataValue( "GieVkTxp4HH", "157" ),
            new EventDataValue( "vV9UWAZohSf", "36" ),
            new EventDataValue( "eMyVanycQSC", "2021-05-06" ),
            new EventDataValue( "msodh3rEMJa", "2021-06-08" ),
            new EventDataValue( "K6uUAvq500H", "A011" ),
            new EventDataValue( "fWIAEtYVEGk", "MODDISCH" ) );

        Event evB = new Event( uidB );
        evB.setProgram( "eBAyeGv0exc" );
        evB.setProgramStage( "Zj7UnCAulEk" );
        evB.setOrgUnit( "DiszpKrYNg8" );
        evB.setOccurredAt( getDate( 2021, 7, 14 ) );
        evB.setDataValues( dvB );

        Events events = new Events( list( evA, evB ) );

        EventResponse response = dhis2.saveEvents( events );

        assertNotNull( response );
        assertEquals( Status.OK, response.getStatus(), response.toString() );
        assertEquals( 2, response.getStats().getCreated() );
        assertEquals( 0, response.getStats().getUpdated() );
        assertEquals( 0, response.getStats().getIgnored() );
        assertEquals( 0, response.getStats().getDeleted() );

        evA = dhis2.getEvent( uidA );

        assertNotNull( evA );
        assertEquals( "eBAyeGv0exc", evA.getProgram() );
        assertEquals( "DiszpKrYNg8", evA.getOrgUnit() );

        evB = dhis2.getEvent( uidB );

        assertNotNull( evB );
        assertEquals( "eBAyeGv0exc", evB.getProgram() );
        assertEquals( "DiszpKrYNg8", evB.getOrgUnit() );

        response = dhis2.removeEvent( evA );

        assertNotNull( response );
        assertEquals( Status.OK, response.getStatus(), response.toString() );
        assertEquals( 0, response.getStats().getCreated() );
        assertEquals( 0, response.getStats().getUpdated() );
        assertEquals( 0, response.getStats().getIgnored() );
        assertEquals( 1, response.getStats().getDeleted() );

        response = dhis2.removeEvent( evB );

        assertNotNull( response );
        assertEquals( Status.OK, response.getStatus(), response.toString() );
        assertEquals( 0, response.getStats().getCreated() );
        assertEquals( 0, response.getStats().getUpdated() );
        assertEquals( 0, response.getStats().getIgnored() );
        assertEquals( 1, response.getStats().getDeleted() );
    }

    @Test
    void testRemoveEvents()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        String uidA = UidUtils.generateUid();
        String uidB = UidUtils.generateUid();

        List<EventDataValue> dvA = list(
            new EventDataValue( "oZg33kd9taw", "Male" ),
            new EventDataValue( "qrur9Dvnyt5", "45" ),
            new EventDataValue( "GieVkTxp4HH", "143" ),
            new EventDataValue( "vV9UWAZohSf", "43" ),
            new EventDataValue( "eMyVanycQSC", "2021-07-02" ),
            new EventDataValue( "msodh3rEMJa", "2021-08-05" ),
            new EventDataValue( "K6uUAvq500H", "A010" ),
            new EventDataValue( "fWIAEtYVEGk", "MODDISCH" ) );

        Event evA = new Event( uidA );
        evA.setProgram( "eBAyeGv0exc" );
        evA.setProgramStage( "Zj7UnCAulEk" );
        evA.setOrgUnit( "DiszpKrYNg8" );
        evA.setOccurredAt( getDate( 2021, 7, 12 ) );
        evA.setDataValues( dvA );

        List<EventDataValue> dvB = list(
            new EventDataValue( "oZg33kd9taw", "Female" ),
            new EventDataValue( "qrur9Dvnyt5", "41" ),
            new EventDataValue( "GieVkTxp4HH", "157" ),
            new EventDataValue( "vV9UWAZohSf", "36" ),
            new EventDataValue( "eMyVanycQSC", "2021-05-06" ),
            new EventDataValue( "msodh3rEMJa", "2021-06-08" ),
            new EventDataValue( "K6uUAvq500H", "A011" ),
            new EventDataValue( "fWIAEtYVEGk", "MODDISCH" ) );

        Event evB = new Event( uidB );
        evB.setProgram( "eBAyeGv0exc" );
        evB.setProgramStage( "Zj7UnCAulEk" );
        evB.setOrgUnit( "DiszpKrYNg8" );
        evB.setOccurredAt( getDate( 2021, 7, 14 ) );
        evB.setDataValues( dvB );

        Events events = new Events( list( evA, evB ) );

        EventResponse response = dhis2.saveEvents( events );

        assertNotNull( response );
        assertEquals( Status.OK, response.getStatus(), response.toString() );
        assertEquals( 2, response.getStats().getCreated() );

        assertNotNull( dhis2.getEvent( evA.getId() ) );
        assertNotNull( dhis2.getEvent( evB.getId() ) );

        response = dhis2.removeEvents( events );

        assertNotNull( response );
        assertEquals( Status.OK, response.getStatus(), response.toString() );
        assertEquals( 0, response.getStats().getCreated() );
        assertEquals( 0, response.getStats().getUpdated() );
        assertEquals( 0, response.getStats().getIgnored() );
        assertEquals( 2, response.getStats().getDeleted() );

        assertEquals( 404,
            assertThrows( Dhis2ClientException.class, () -> dhis2.getEvent( evA.getId() ) ).getStatusCode() );
        assertEquals( 404,
            assertThrows( Dhis2ClientException.class, () -> dhis2.getEvent( evB.getId() ) ).getStatusCode() );
    }

    @Test
    void testSaveEventsMissingOccurredAt()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        List<EventDataValue> dvA = list(
            new EventDataValue( "oZg33kd9taw", "Male" ),
            new EventDataValue( "qrur9Dvnyt5", "45" ),
            new EventDataValue( "GieVkTxp4HH", "143" ),
            new EventDataValue( "vV9UWAZohSf", "43" ),
            new EventDataValue( "eMyVanycQSC", "2021-07-02" ),
            new EventDataValue( "msodh3rEMJa", "2021-08-05" ),
            new EventDataValue( "K6uUAvq500H", "A010" ),
            new EventDataValue( "fWIAEtYVEGk", "MODDISCH" ) );

        Event evA = new Event( UidUtils.generateUid() );
        evA.setProgram( "eBAyeGv0exc" );
        evA.setProgramStage( "Zj7UnCAulEk" );
        evA.setOrgUnit( "DiszpKrYNg8" );
        evA.setOccurredAt( null );
        evA.setDataValues( dvA );

        Events events = new Events( list( evA ) );

        EventResponse response = dhis2.saveEvents( events );

        assertNotNull( response );
        assertEquals( Status.ERROR, response.getStatus(), response.toString() );
        assertEquals( 1, response.getStats().getIgnored() );
        assertEquals( 1, response.getValidationReport().getErrorReports().size() );

        ErrorReport errorReport = response.getValidationReport().getErrorReports().get( 0 );
        assertEquals( "E1031", errorReport.getErrorCode() );
    }

    @Test
    void testGetEvents()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        EventsQuery query = EventsQuery.instance()
            .setProgram( "eBAyeGv0exc" )
            .setProgramStage( "Zj7UnCAulEk" );

        EventsResult events = dhis2.getEvents( query );

        assertNotNull( events );
        assertNotNull( events.getInstances() );
        assertEquals( 50, events.getInstances().size() );

        Event event = events.getInstances().get( 0 );

        assertNotNull( event.getId() );
        assertNotNull( event.getProgram() );
        assertNotNull( event.getProgramStage() );
        assertNotNull( event.getOrgUnit() );
        assertNotNull( event.getStatus() );
    }
}
