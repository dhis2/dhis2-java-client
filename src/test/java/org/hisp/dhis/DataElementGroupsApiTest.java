package org.hisp.dhis;

import static org.hisp.dhis.util.CollectionUtils.list;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.hisp.dhis.model.DataElementGroup;
import org.hisp.dhis.query.Filter;
import org.hisp.dhis.query.Order;
import org.hisp.dhis.query.Query;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag( "integration" )
public class DataElementGroupsApiTest
{
    @Test
    void testGetDataElementGroups()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        List<DataElementGroup> degs = dhis2.getDataElementGroups( Query.instance()
            .addFilter( Filter.in( "id", list( "SriP0jBXMr6", "GBHN1a1Jddh", "b3gDdvmrSFc" ) ) )
            .setOrder( Order.asc( "id" ) ) );

        assertNotNull( degs );
        assertEquals( 3, degs.size() );

        DataElementGroup deg1 = degs.get( 0 );
        assertEquals( "b3gDdvmrSFc", deg1.getId() );
        assertEquals( "IDSR", deg1.getName() );
        assertEquals( 0, deg1.getDataElements().size() );

        DataElementGroup deg2 = degs.get( 1 );
        assertEquals( "GBHN1a1Jddh", deg2.getId() );
        assertEquals( "All Others", deg2.getName() );
        assertEquals( 0, deg2.getDataElements().size() );

        DataElementGroup deg3 = degs.get( 2 );
        assertEquals( "SriP0jBXMr6", deg3.getId() );
        assertEquals( "Lassa Fever", deg3.getName() );
        assertEquals( 0, deg3.getDataElements().size() );
    }

    @Test
    void testGetDataElementGroupsWithExpandAssociations()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        List<DataElementGroup> degs = dhis2.getDataElementGroups( Query.instance().withExpandAssociations()
            .addFilter( Filter.in( "id", list( "SriP0jBXMr6", "GBHN1a1Jddh", "b3gDdvmrSFc" ) ) )
            .setOrder( Order.asc( "id" ) ) );

        assertNotNull( degs );
        assertEquals( 3, degs.size() );

        DataElementGroup deg1 = degs.get( 0 );
        assertEquals( "b3gDdvmrSFc", deg1.getId() );
        assertEquals( "IDSR", deg1.getName() );
        assertEquals( 5, deg1.getDataElements().size() );

        DataElementGroup deg2 = degs.get( 1 );
        assertEquals( "GBHN1a1Jddh", deg2.getId() );
        assertEquals( "All Others", deg2.getName() );
        assertEquals( 1, deg2.getDataElements().size() );
        assertEquals( "qrur9Dvnyt5", deg2.getDataElements().get( 0 ).getId() );

        DataElementGroup deg3 = degs.get( 2 );
        assertEquals( "SriP0jBXMr6", deg3.getId() );
        assertEquals( "Lassa Fever", deg3.getName() );
        assertEquals( 3, deg3.getDataElements().size() );
    }
}
