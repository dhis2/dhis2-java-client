package org.hisp.dhis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.hisp.dhis.model.OrgUnit;
import org.hisp.dhis.model.OrgUnitGroup;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag( TestTags.INTEGRATION )
class OrgUnitGroupApiTest
{
    @Test
    void testGetOrgUnitGroup()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        OrgUnitGroup oug = dhis2.getOrgUnitGroup( "RpbiCJpIYEj" );

        assertEquals( "RpbiCJpIYEj", oug.getId() );
        assertEquals( "Country", oug.getName() );
        assertEquals( "Country", oug.getShortName() );
        assertEquals( "Country", oug.getCode() );
        assertNotNull( oug.getCreated() );
        assertNotNull( oug.getLastUpdated() );
        assertNull( oug.getDescription() );

        // Group members assertions

        List<OrgUnit> orgUnits = oug.getOrgUnits();

        assertNotNull( orgUnits );
        assertEquals( 1, orgUnits.size() );

        assertEquals( "ImspTQPwCqd", orgUnits.get( 0 ).getId() );
        assertEquals( "Sierra Leone", orgUnits.get( 0 ).getName() );
        assertEquals( "OU_525", orgUnits.get( 0 ).getCode() );
    }
}
