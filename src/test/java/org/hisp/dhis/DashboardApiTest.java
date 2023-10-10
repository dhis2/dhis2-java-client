package org.hisp.dhis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.hisp.dhis.model.Dashboard;
import org.hisp.dhis.query.Filter;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag( TestTags.INTEGRATION )
class DashboardApiTest
{
    @Test
    void testGetDashboard()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        Dashboard dashboard = dhis2.getDashboard( "L1BtjXgpUpd" );

        assertNotNull( dashboard );
        assertEquals( "L1BtjXgpUpd", dashboard.getId() );
        assertNotNull( dashboard.getName() );
    }

    @Test
    void testGetDashboards()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        List<Dashboard> dashboards = dhis2.getDashboards( Query.instance()
            .addFilter( Filter.like( "name", "Immunization" ) ) );

        assertFalse( dashboards.isEmpty() );
        assertNotNull( dashboards.get( 0 ).getId() );
    }
}
