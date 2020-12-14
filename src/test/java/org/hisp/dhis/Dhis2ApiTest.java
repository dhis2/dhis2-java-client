package org.hisp.dhis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.UncheckedIOException;
import java.util.List;

import org.hisp.dhis.model.OrgUnitGroup;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.response.Dhis2ClientException;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class Dhis2ApiTest
{
    private final Dhis2Config config = new Dhis2Config(
        "https://play.dhis2.org/demo", "admin", "district" );

    @Test
    public void testGetOrgUnitGroups()
    {
        Dhis2 dhis2 = new Dhis2( config );

        List<OrgUnitGroup> orgUnitGroups = dhis2.getOrgUnitGroups( Query.instance() );

        assertNotNull( orgUnitGroups );
        assertTrue( orgUnitGroups.size() > 0 );
        assertNotNull( orgUnitGroups.get( 0 ) );
    }

    @Test
    public void testGetOrgUnitGroup()
    {
        Dhis2 dhis2 = new Dhis2( config );

        OrgUnitGroup orgUnitGroup = dhis2.getOrgUnitGroup( "CXw2yu5fodb" );

        assertNotNull( orgUnitGroup );
        assertEquals( "CXw2yu5fodb", orgUnitGroup.getId() );
    }

    @Test
    public void testGetInvalidUrl()
    {
        Dhis2 dhis2 = new Dhis2( new Dhis2Config( "https://not_a_domain.abc", "username", "pw" ) );

        assertThrows( UncheckedIOException.class, () -> dhis2.getOrgUnitGroups( Query.instance() ) );
    }

    @Test
    public void testGetAccessDenied()
    {
        Dhis2 dhis2 = new Dhis2( new Dhis2Config( "https://play.dhis2.org/demo", "notauser", "invalidpw" ) );

        Dhis2ClientException ex = assertThrows( Dhis2ClientException.class,
            () -> dhis2.getOrgUnitGroups( Query.instance() ) );

        assertEquals( 401, ex.getStatusCode() );
        assertEquals( "Access denied", ex.getMessage() );
    }

    @Test
    public void testGetNotFound()
    {
        Dhis2 dhis2 = new Dhis2( config );

        Dhis2ClientException ex = assertThrows( Dhis2ClientException.class,
            () -> dhis2.getOrgUnitGroup( "NonExisting" ) );

        assertEquals( 404, ex.getStatusCode() );
        assertEquals( "Object not found", ex.getMessage() );
    }
}
