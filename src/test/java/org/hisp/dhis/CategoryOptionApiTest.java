package org.hisp.dhis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.hisp.dhis.model.CategoryOption;
import org.hisp.dhis.model.user.sharing.Sharing;
import org.hisp.dhis.support.TestTags;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag( TestTags.INTEGRATION )
class CategoryOptionApiTest
{
    @Test
    void testGetCategoryOption()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        CategoryOption categoryOption = dhis2.getCategoryOption( "jRbMi0aBjYn" );

        assertEquals( "jRbMi0aBjYn", categoryOption.getId() );
        assertEquals( "MLE", categoryOption.getCode() );
        assertNotNull( categoryOption.getCreated() );
        assertNotNull( categoryOption.getLastUpdated() );
        assertEquals( "Male", categoryOption.getName() );
        assertEquals( "Male", categoryOption.getShortName() );

        Sharing sharing = categoryOption.getSharing();

        assertNotNull( sharing );
        assertNotNull( sharing.getOwner() );
        assertNotNull( sharing.isExternal() );
        assertNotNull( sharing.getPublicAccess() );
        assertNotNull( sharing.getUsers() );
        assertNotNull( sharing.getUserGroups() );
    }
}
