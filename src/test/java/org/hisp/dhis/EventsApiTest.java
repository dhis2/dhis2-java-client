package org.hisp.dhis;

import static org.hisp.dhis.util.CollectionUtils.newImmutableList;
import static org.hisp.dhis.util.DateTimeUtils.getDate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.hisp.dhis.category.IntegrationTest;
import org.hisp.dhis.model.event.Event;
import org.hisp.dhis.model.event.EventDataValue;
import org.hisp.dhis.model.event.Events;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.event.ErrorReport;
import org.hisp.dhis.response.event.EventResponse;
import org.hisp.dhis.util.UidUtils;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category( IntegrationTest.class )
public class EventsApiTest
{
    @Test
    public void testSaveGetEvents()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        String uidA = UidUtils.generateUid();
        String uidB = UidUtils.generateUid();

        List<EventDataValue> dvA = newImmutableList(
            new EventDataValue( "oZg33kd9taw", "Male" ),
            new EventDataValue( "qrur9Dvnyt5", "45" ),
            new EventDataValue( "GieVkTxp4HH", "143" ),
            new EventDataValue( "vV9UWAZohSf", "43" ),
            new EventDataValue( "eMyVanycQSC", "2021-07-02" ),
            new EventDataValue( "msodh3rEMJa", "2021-08-05" ),
            new EventDataValue( "K6uUAvq500H", "A010" ),
            new EventDataValue( "fWIAEtYVEGk", "MODDISCH" ) );

        Event evA = new Event( uidA, "Zj7UnCAulEk", "DiszpKrYNg8", getDate( 2021, 7, 12 ), dvA );

        List<EventDataValue> dvB = newImmutableList(
            new EventDataValue( "oZg33kd9taw", "Female" ),
            new EventDataValue( "qrur9Dvnyt5", "41" ),
            new EventDataValue( "GieVkTxp4HH", "157" ),
            new EventDataValue( "vV9UWAZohSf", "36" ),
            new EventDataValue( "eMyVanycQSC", "2021-05-06" ),
            new EventDataValue( "msodh3rEMJa", "2021-06-08" ),
            new EventDataValue( "K6uUAvq500H", "A011" ),
            new EventDataValue( "fWIAEtYVEGk", "MODDISCH" ) );

        Event evB = new Event( uidB, "Zj7UnCAulEk", "DiszpKrYNg8", getDate( 2021, 7, 14 ), dvB );

        Events events = new Events( newImmutableList( evA, evB ) );

        EventResponse response = dhis2.saveEvents( events );

        assertNotNull( response );
        assertEquals( Status.OK, response.getStatus() );
        assertEquals( 2, response.getStats().getCreated() );
        assertEquals( 0, response.getStats().getUpdated() );
        assertEquals( 0, response.getStats().getIgnored() );
        assertEquals( 0, response.getStats().getDeleted() );

        evA = dhis2.getEvent( uidA );

        assertNotNull( evA );
        assertEquals( "Zj7UnCAulEk", evA.getProgramStage() );
        assertEquals( "DiszpKrYNg8", evA.getOrgUnit() );

        evB = dhis2.getEvent( uidB );

        assertNotNull( evB );
        assertEquals( "Zj7UnCAulEk", evB.getProgramStage() );
        assertEquals( "DiszpKrYNg8", evB.getOrgUnit() );
    }

    @Test
    public void testSaveEventsMissingOccurredAt()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        List<EventDataValue> dvA = newImmutableList(
            new EventDataValue( "oZg33kd9taw", "Male" ),
            new EventDataValue( "qrur9Dvnyt5", "45" ),
            new EventDataValue( "GieVkTxp4HH", "143" ),
            new EventDataValue( "vV9UWAZohSf", "43" ),
            new EventDataValue( "eMyVanycQSC", "2021-07-02" ),
            new EventDataValue( "msodh3rEMJa", "2021-08-05" ),
            new EventDataValue( "K6uUAvq500H", "A010" ),
            new EventDataValue( "fWIAEtYVEGk", "MODDISCH" ) );

        Event evA = new Event( UidUtils.generateUid(), "Zj7UnCAulEk", "DiszpKrYNg8", null, dvA );

        Events events = new Events( newImmutableList( evA ) );

        EventResponse response = dhis2.saveEvents( events );

        assertNotNull( response );
        assertEquals( Status.ERROR, response.getStatus() );
        assertEquals( 1, response.getStats().getIgnored() );
        assertEquals( 1, response.getValidationReport().getErrorReports().size() );

        ErrorReport errorReport = response.getValidationReport().getErrorReports().get( 0 );
        assertEquals( "E1031", errorReport.getErrorCode() );
    }
}
