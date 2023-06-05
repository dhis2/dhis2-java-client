package org.hisp.dhis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.hisp.dhis.model.Indicator;
import org.hisp.dhis.model.IndicatorType;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag( "integration" )
public class IndicatorApiTest
{
    @Test
    void testGetIndicator()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        Indicator indicator = dhis2.getIndicator( "dwEq7wi6nXV" );
        assertNotNull( indicator );
        assertEquals( "dwEq7wi6nXV", indicator.getId() );
        assertEquals( "IN_52496", indicator.getCode() );
        assertEquals( "ANC IPT 1 Coverage", indicator.getName() );
        assertEquals( "ANC IPT 1 Coverage", indicator.getShortName() );
        assertNotNull( indicator.getCreated() );
        assertNotNull( indicator.getLastUpdated() );
        assertFalse( indicator.isAnnualized() );
        assertNotNull( indicator.getUrl() );

        IndicatorType indicatorType = indicator.getIndicatorType();
        assertNotNull( indicatorType.getId() );
        assertNotNull( indicatorType.getName() );

        assertNotNull( indicator.getNumerator() );
        assertEquals( "IPT 1st dose total given", indicator.getNumeratorDescription() );
        assertNotNull( indicator.getDenominator() );
        assertEquals( "ANC 1st visit total", indicator.getDenominatorDescription() );
    }
}
