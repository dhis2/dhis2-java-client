package org.hisp.dhis;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.hisp.dhis.model.CategoryCombo;
import org.hisp.dhis.model.CategoryOptionCombo;
import org.hisp.dhis.model.DataDimensionType;
import org.junit.Test;
import org.junit.jupiter.api.Tag;

@Tag( "integration" )
public class CategoryComboApiTest
{
    @Test
    public void testGetCategoryCombo()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        CategoryCombo categoryCombo = dhis2.getCategoryCombo( "m2jTvAj5kkm" );

        assertEquals( "Births", categoryCombo.getName() );
        assertEquals( DataDimensionType.DISAGGREGATION, categoryCombo.getDataDimensionType() );
        assertFalse( categoryCombo.getSkipTotal() );

        List<CategoryOptionCombo> categoryOptionCombos = categoryCombo.getCategoryOptionCombos();
        assertFalse( categoryOptionCombos.get( 0 ).getIgnoreApproval() );
        assertFalse( categoryOptionCombos.get( 0 ).getCategoryOptions().isEmpty() );
    }
}
