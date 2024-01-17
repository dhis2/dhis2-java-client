package org.hisp.dhis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.hisp.dhis.model.CategoryOption;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag( TestTags.INTEGRATION )
class CategoryOptionTest
{
    @Test
    void testGetCategoryOption()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        CategoryOption categoryOption = dhis2.getCategoryOption( "jRbMi0aBjYn" );

        assertEquals( "Male", categoryOption.getName() );
        assertEquals( "MLE", categoryOption.getCode() );
    }

    @Test
    void testUpdateCategoryOption()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        Date startDate = Date.from( LocalDate.now().atStartOfDay().atZone( ZoneId.systemDefault() ).toInstant() );
        CategoryOption categoryOption = dhis2.getCategoryOption( "jRbMi0aBjYn" );
        categoryOption.setStartDate( startDate );

        dhis2.updateCategoryOption( categoryOption );

        categoryOption = dhis2.getCategoryOption( "jRbMi0aBjYn" );
        assertEquals( startDate, categoryOption.getStartDate() );
    }
}
