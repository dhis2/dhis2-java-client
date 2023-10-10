package org.hisp.dhis;

import static org.hisp.dhis.util.CollectionUtils.list;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import org.apache.hc.core5.net.URIBuilder;
import org.hisp.dhis.model.AggregationType;
import org.hisp.dhis.model.IdScheme;
import org.hisp.dhis.model.datavalueset.DataValueSetImportOptions;
import org.hisp.dhis.model.event.ProgramStatus;
import org.hisp.dhis.query.Filter;
import org.hisp.dhis.query.Order;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.query.analytics.AnalyticsQuery;
import org.hisp.dhis.query.datavalue.DataValueSetQuery;
import org.hisp.dhis.query.event.EventsQuery;
import org.junit.jupiter.api.Test;

public class Dhis2Test
{
    @Test
    void testGetDhis2()
    {
        Dhis2Config config = getDhis2Config();

        Dhis2 dhis2 = new Dhis2( config );

        assertEquals( "https://dhis2.org", dhis2.getDhis2Url() );
    }

    @Test
    void testGetObjectQuery()
    {
        Dhis2Config config = getDhis2Config();

        Dhis2 dhis2 = new Dhis2( config );

        URIBuilder uriBuilder = config.getResolvedUriBuilder().appendPath( "dataElements" );

        Query query = Query.instance()
            .addFilter( Filter.like( "name", "Immunization" ) )
            .addFilter( Filter.eq( "valueType", "NUMBER" ) )
            .setOrder( Order.desc( "code" ) )
            .setPaging( 2, 100 );

        URI uri = dhis2.getObjectQuery( uriBuilder, query );

        String expected = "https://dhis2.org/api/dataElements?" +
            "filter=name%3Alike%3AImmunization&filter=valueType%3Aeq%3ANUMBER&page=2&pageSize=100&order=code%3Adesc";

        assertEquals( expected, uri.toString() );
    }

    @Test
    void testGetDataValueSetImportQuery()
    {
        Dhis2Config config = getDhis2Config();

        Dhis2 dhis2 = new Dhis2( config );

        URIBuilder uriBuilder = config.getResolvedUriBuilder().appendPath( "dataValueSets" );

        DataValueSetImportOptions options = DataValueSetImportOptions.instance()
            .setDataElementIdScheme( IdScheme.CODE )
            .setDryRun( true )
            .setPreheatCache( true )
            .setSkipAudit( true );

        URI uri = dhis2.getDataValueSetImportQuery( uriBuilder, options );

        String expected = "https://dhis2.org/api/dataValueSets?" +
            "async=true&dataElementIdScheme=code&dryRun=true&preheatCache=true&skipAudit=true";

        assertEquals( expected, uri.toString() );
    }

    @Test
    void testGetAnalyticsQuery()
    {
        Dhis2Config config = getDhis2Config();

        Dhis2 dhis2 = new Dhis2( config );

        URIBuilder uriBuilder = config.getResolvedUriBuilder().appendPath( "analytics" );

        AnalyticsQuery query = AnalyticsQuery.instance()
            .setAggregationType( AggregationType.AVERAGE )
            .setIgnoreLimit( true )
            .setInputIdScheme( IdScheme.CODE )
            .setOutputIdScheme( IdScheme.UID );

        URI uri = dhis2.getAnalyticsQuery( uriBuilder, query );

        String expected = "https://dhis2.org/api/analytics?" +
            "aggregationType=AVERAGE&ignoreLimit=true&inputIdScheme=code&outputIdScheme=uid";

        assertEquals( expected, uri.toString() );
    }

    @Test
    void testGetEventsQuery()
    {
        Dhis2Config config = getDhis2Config();

        Dhis2 dhis2 = new Dhis2( config );

        URIBuilder uriBuilder = config.getResolvedUriBuilder().appendPath( "tracker" ).appendPath( "events" );

        EventsQuery query = EventsQuery.instance()
            .setProgram( "hJhgt5cDs7j" )
            .setProgramStatus( ProgramStatus.ACTIVE )
            .setFollowUp( true )
            .setIdScheme( IdScheme.CODE );

        URI uri = dhis2.getEventsQuery( uriBuilder, query );

        String expected = "https://dhis2.org/api/tracker/events?" +
            "program=hJhgt5cDs7j&programStatus=ACTIVE&followUp=true&idScheme=code";

        assertEquals( expected, uri.toString() );
    }

    @Test
    void testGetDataValueSetQuery()
    {
        Dhis2Config config = getDhis2Config();

        Dhis2 dhis2 = new Dhis2( config );

        URIBuilder uriBuilder = config.getResolvedUriBuilder().appendPath( "dataValueSets.json" );

        DataValueSetQuery query = DataValueSetQuery.instance()
            .addDataElements( list( "N9vniUuCcqY" ) )
            .addPeriods( list( "202211" ) )
            .addOrgUnits( list( "ImspTQPwCqd" ) )
            .setChildren( true );

        String expected = "https://dhis2.org/api/dataValueSets.json?" +
            "dataElement=N9vniUuCcqY&orgUnit=ImspTQPwCqd&period=202211&children=true";

        URI uri = dhis2.getDataValueSetQuery( uriBuilder, query );

        assertEquals( expected, uri.toString() );
    }

    private Dhis2Config getDhis2Config()
    {
        return new Dhis2Config( "https://dhis2.org", "admin", "district" );
    }
}
