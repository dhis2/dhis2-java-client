package org.hisp.dhis;

import static org.hisp.dhis.util.CollectionUtils.list;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.hisp.dhis.model.Indicator;
import org.hisp.dhis.query.Filter;
import org.hisp.dhis.query.Order;
import org.hisp.dhis.query.Query;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag( "integration" )
public class IndicatorsApiTest
{
    @Test
    void testGetIndicators()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        Query query = Query.instance().withExpandAssociations()
            .addFilter( Filter.in( "id", list( "gNAXtpqAqW2", "W7MxOZjZ1VC" ) ) )
            .setOrder( Order.asc( "id" ) );

        List<Indicator> indicators = dhis2.getIndicators( query );

        assertNotNull( indicators );
        assertEquals( 2, indicators.size() );

        Indicator indicator1 = indicators.get( 0 );
        assertEquals( "gNAXtpqAqW2", indicator1.getId() );
        assertEquals( "Births total", indicator1.getName() );

        Indicator indicator2 = indicators.get( 1 );
        assertEquals( "W7MxOZjZ1VC", indicator2.getId() );
        assertEquals( "IPT doses total", indicator2.getName() );
    }
}
