package org.hisp.dhis;

import static org.hisp.dhis.util.CollectionUtils.list;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.hisp.dhis.model.OrgUnitGroup;
import org.hisp.dhis.query.Filter;
import org.hisp.dhis.query.Order;
import org.hisp.dhis.query.Query;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag( "integration" )
public class OrgUnitGroupsApiTest
{
    @Test
    void testGetOrgUnitGroups()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        List<OrgUnitGroup> ougs = dhis2.getOrgUnitGroups( Query.instance()
            .addFilter( Filter.in( "id", list( "nlX2VoouN63", "RpbiCJpIYEj", "J40PpdN4Wkk" ) ) )
            .setOrder( Order.asc( "id" ) ) );

        assertNotNull( ougs );
        assertEquals( 3, ougs.size() );

        OrgUnitGroup oug1 = ougs.get( 0 );
        assertEquals( "J40PpdN4Wkk", oug1.getId() );
        assertEquals( "Northern Area", oug1.getName() );
        assertEquals( 0, oug1.getOrgUnits().size() );

        OrgUnitGroup oug2 = ougs.get( 1 );
        assertEquals( "nlX2VoouN63", oug2.getId() );
        assertEquals( "Eastern Area", oug2.getName() );
        assertEquals( 0, oug2.getOrgUnits().size() );

        OrgUnitGroup oug3 = ougs.get( 2 );
        assertEquals( "RpbiCJpIYEj", oug3.getId() );
        assertEquals( "Country", oug3.getName() );
        assertEquals( 0, oug3.getOrgUnits().size() );
    }

    @Test
    void testGetOrgUnitGroupsWithExpandAssociations()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        List<OrgUnitGroup> ougs = dhis2.getOrgUnitGroups( Query.instance().withExpandAssociations()
            .addFilter( Filter.in( "id", list( "nlX2VoouN63", "RpbiCJpIYEj", "J40PpdN4Wkk" ) ) )
            .setOrder( Order.asc( "id" ) ) );

        assertNotNull( ougs );
        assertEquals( 3, ougs.size() );

        OrgUnitGroup oug1 = ougs.get( 0 );
        assertEquals( "J40PpdN4Wkk", oug1.getId() );
        assertEquals( "Northern Area", oug1.getName() );
        assertEquals( 4, oug1.getOrgUnits().size() );

        OrgUnitGroup oug2 = ougs.get( 1 );
        assertEquals( "nlX2VoouN63", oug2.getId() );
        assertEquals( "Eastern Area", oug2.getName() );
        assertEquals( 3, oug2.getOrgUnits().size() );

        OrgUnitGroup oug3 = ougs.get( 2 );
        assertEquals( "RpbiCJpIYEj", oug3.getId() );
        assertEquals( "Country", oug3.getName() );
        assertEquals( 1, oug3.getOrgUnits().size() );
    }
}
