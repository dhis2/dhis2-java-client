package org.hisp.dhis;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.hisp.dhis.model.Me;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag( TestTags.INTEGRATION )
class MeApiTest
{
    @Test
    void testGetMe()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        Me me = dhis2.getMe();

        System.out.println( me );

        assertNotNull( me.getUsername() );
        assertNotNull( me.getFirstName() );
        assertNotNull( me.getSurname() );
        assertNotNull( me.getSettings() );
        assertNotNull( me.getSettings().getKeyUiLocale() );
        assertNotNull( me.getSettings().getKeyDbLocale() );
    }
}
