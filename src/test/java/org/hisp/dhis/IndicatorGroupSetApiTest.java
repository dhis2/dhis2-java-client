package org.hisp.dhis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.hisp.dhis.model.IndicatorGroupSet;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag( TestTags.INTEGRATION )
class IndicatorGroupSetApiTest
{
    @Test
    void getIndicatorGroupSet()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        IndicatorGroupSet groupSet = dhis2.getIndicatorGroupSet( "kO23KcpBwro" );

        assertNotNull( groupSet );
        assertEquals( "kO23KcpBwro", groupSet.getId() );
        assertFalse( groupSet.getIndicatorGroups().isEmpty() );
    }

    @Test
    void testGetIndicatorGroupSets()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        List<IndicatorGroupSet> groupSets = dhis2.getIndicatorGroupSets( Query.instance() );

        assertNotNull( groupSets );
        assertFalse( groupSets.isEmpty() );
        assertNotNull( groupSets.get( 0 ).getId() );
    }
}
