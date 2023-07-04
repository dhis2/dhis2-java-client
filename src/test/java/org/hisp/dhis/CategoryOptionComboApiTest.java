package org.hisp.dhis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.hisp.dhis.model.CategoryOptionCombo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag( "integration" )
public class CategoryOptionComboApiTest
{
    @Test
    void testGetCategoryOptionCombo()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        CategoryOptionCombo coc = dhis2.getCategoryOptionCombo( "KQ50BVoUrd6" );

        assertEquals( "Male", coc.getName() );
        assertFalse( coc.getIgnoreApproval() );
        assertFalse( coc.getCategoryOptions().isEmpty() );
    }
}
