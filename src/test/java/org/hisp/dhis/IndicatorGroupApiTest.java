package org.hisp.dhis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.hisp.dhis.model.IndicatorGroup;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag( TestTags.INTEGRATION )
class IndicatorGroupApiTest
{
    @Test
    void testGetIndicatorGroup()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        IndicatorGroup group = dhis2.getIndicatorGroup( "pKHOV0uwPJk" );

        assertNotNull( group );
        assertEquals( "pKHOV0uwPJk", group.getId() );
        assertFalse( group.getIndicators().isEmpty() );
    }

    @Test
    void testIndicatorGroups()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        List<IndicatorGroup> groups = dhis2.getIndicatorGroups( Query.instance() );

        assertNotNull( groups );
        assertFalse( groups.isEmpty() );
        assertNotNull( groups.get( 0 ).getId() );
    }
}
