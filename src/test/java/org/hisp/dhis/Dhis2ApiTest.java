package org.hisp.dhis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.io.UncheckedIOException;
import java.util.List;

import org.hisp.dhis.category.IntegrationTest;
import org.hisp.dhis.model.DataElement;
import org.hisp.dhis.model.OrgUnitGroup;
import org.hisp.dhis.model.Program;
import org.hisp.dhis.model.ProgramStage;
import org.hisp.dhis.model.ProgramStageDataElement;
import org.hisp.dhis.model.ProgramType;
import org.hisp.dhis.model.TrackedEntityAttribute;
import org.hisp.dhis.query.Filter;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.response.Dhis2ClientException;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class Dhis2ApiTest
{
    private static final Dhis2Config config = new Dhis2Config(
        "https://play.dhis2.org/2.35.3", "admin", "district" );

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
    public void testGetProgram()
    {
        Dhis2 dhis2 = new Dhis2( config );

        Program program = dhis2.getProgram( "IpHINAT79UW" );

        assertNotNull( program );
        assertEquals( "IpHINAT79UW", program.getId() );
        assertEquals( ProgramType.WITH_REGISTRATION, program.getProgramType() );

        assertFalse( program.getTrackedEntityAttributes().isEmpty() );
        assertNotNull( program.getTrackedEntityAttributes().get( 0 ) );

        assertFalse( program.getProgramTrackedEntityAttributes().isEmpty() );
        assertNotNull( program.getProgramTrackedEntityAttributes().get( 0 ) );
        assertNotNull( program.getProgramTrackedEntityAttributes().get( 0 ).getId() );

        TrackedEntityAttribute tea = program.getProgramTrackedEntityAttributes().get( 0 ).getTrackedEntityAttribute();

        assertNotNull( tea );
        assertNotNull( tea.getId() );
        assertNotNull( tea.getValueType() );

        assertFalse( program.getProgramStages().isEmpty() );

        ProgramStage ps = program.getProgramStages().get( 0 );

        assertNotNull( ps );
        assertNotNull( ps.getId() );
        assertFalse( ps.getProgramStageDataElements().isEmpty() );

        ProgramStageDataElement psde = ps.getProgramStageDataElements().get( 0 );

        assertNotNull( psde );
        assertNotNull( psde.getId() );

        DataElement de = psde.getDataElement();

        assertNotNull( de );
        assertNotNull( de.getId() );
        assertNotNull( de.getAggregationType() );
        assertNotNull( de.getValueType() );
    }

    @Test
    public void testGetPrograms()
    {
        Dhis2 dhis2 = new Dhis2( config );

        List<Program> programs = dhis2.getPrograms( Query.instance() );

        assertNotNull( programs );
        assertFalse( programs.isEmpty() );
        assertNotNull( programs.get( 0 ) );
    }

    @Test
    public void testGetProgramsExpandAssociations()
    {
        Dhis2 dhis2 = new Dhis2( config );

        List<Program> programs = dhis2.getPrograms( Query.instance()
            .withExpandAssociations()
            .addFilter( Filter.eq( "id", "IpHINAT79UW" ) ) );

        assertNotNull( programs );
        assertEquals( 1, programs.size() );
        assertNotNull( programs.get( 0 ) );
        assertEquals( "IpHINAT79UW", programs.get( 0 ).getId() );

        Program program = programs.get( 0 );

        assertNotNull( program );
        assertEquals( "IpHINAT79UW", program.getId() );
        assertEquals( ProgramType.WITH_REGISTRATION, program.getProgramType() );

        assertFalse( program.getTrackedEntityAttributes().isEmpty() );
        assertNotNull( program.getTrackedEntityAttributes().get( 0 ) );

        assertFalse( program.getProgramTrackedEntityAttributes().isEmpty() );
        assertNotNull( program.getProgramTrackedEntityAttributes().get( 0 ) );
        assertNotNull( program.getProgramTrackedEntityAttributes().get( 0 ).getId() );

        TrackedEntityAttribute tea = program.getProgramTrackedEntityAttributes().get( 0 ).getTrackedEntityAttribute();

        assertNotNull( tea );
        assertNotNull( tea.getId() );
        assertNotNull( tea.getValueType() );

        assertFalse( program.getProgramStages().isEmpty() );

        ProgramStage ps = program.getProgramStages().get( 0 );

        assertNotNull( ps );
        assertNotNull( ps.getId() );
        assertFalse( ps.getProgramStageDataElements().isEmpty() );

        ProgramStageDataElement psde = ps.getProgramStageDataElements().get( 0 );

        assertNotNull( psde );
        assertNotNull( psde.getId() );

        DataElement de = psde.getDataElement();

        assertNotNull( de );
        assertNotNull( de.getId() );
        assertNotNull( de.getAggregationType() );
        assertNotNull( de.getValueType() );
    }

    @Test
    public void testGetInvalidUrl()
    {
        Dhis2 dhis2 = new Dhis2( new Dhis2Config( "https://not_a_domain.abc", "username", "pw" ) );

        assertThrows( UncheckedIOException.class,
            () -> dhis2.getOrgUnitGroups( Query.instance() ) );
    }

    @Test
    public void testGetAccessDenied()
    {
        Dhis2 dhis2 = new Dhis2( new Dhis2Config( "https://play.dhis2.org/demo", "notauser", "invalidpw" ) );

        Dhis2ClientException ex = assertThrows( Dhis2ClientException.class,
            () -> dhis2.getOrgUnitGroups( Query.instance() ) );

        assertEquals( 401, ex.getStatusCode() );
    }

    @Test
    public void testGetNotFound()
    {
        Dhis2 dhis2 = new Dhis2( config );

        Dhis2ClientException ex = assertThrows( Dhis2ClientException.class,
            () -> dhis2.getOrgUnitGroup( "NonExisting" ) );

        assertEquals( 404, ex.getStatusCode() );
    }
}
