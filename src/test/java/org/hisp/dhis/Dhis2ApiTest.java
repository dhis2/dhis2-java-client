package org.hisp.dhis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;
import java.util.List;

import org.hisp.dhis.model.CategoryOption;
import org.hisp.dhis.model.DataElement;
import org.hisp.dhis.model.OrgUnit;
import org.hisp.dhis.model.OrgUnitGroup;
import org.hisp.dhis.model.Program;
import org.hisp.dhis.model.ProgramStage;
import org.hisp.dhis.model.ProgramStageDataElement;
import org.hisp.dhis.model.ProgramType;
import org.hisp.dhis.model.TrackedEntityAttribute;
import org.hisp.dhis.query.Filter;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.response.Dhis2ClientException;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.support.TestTags;
import org.hisp.dhis.util.UidUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag( TestTags.INTEGRATION )
class Dhis2ApiTest
{
    @Test
    void testGetUserAuthorization()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        List<String> authorization = dhis2.getUserAuthorization();

        assertNotNull( authorization );
        assertFalse( authorization.isEmpty() );
    }

    @Test
    void testGetCategoryOptions()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        List<CategoryOption> categoryOptions = dhis2.getCategoryOptions( Query.instance()
            .setPaging( 1, 10 ) );

        assertNotNull( categoryOptions );
        assertFalse( categoryOptions.isEmpty() );
        assertNotNull( categoryOptions.get( 0 ).getId() );
        assertNotNull( categoryOptions.get( 0 ).getName() );
        assertFalse( categoryOptions.get( 0 ).getCategories().isEmpty() );
        assertNotNull( categoryOptions.get( 0 ).getSharing() );
    }

    @Test
    void testGetOrgUnits()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        List<OrgUnit> orgUnits = dhis2.getOrgUnits( Query.instance()
            .setPaging( 1, 100 ) );

        assertNotNull( orgUnits );
        assertFalse( orgUnits.isEmpty() );
        assertNotNull( orgUnits.get( 0 ) );
        assertNotNull( orgUnits.get( 0 ).getId() );
        assertNotNull( orgUnits.get( 0 ).getName() );
    }

    @Test
    void testGetOrgUnitGroups()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        List<OrgUnitGroup> orgUnitGroups = dhis2.getOrgUnitGroups( Query.instance() );

        assertNotNull( orgUnitGroups );
        assertFalse( orgUnitGroups.isEmpty() );
        assertNotNull( orgUnitGroups.get( 0 ) );
        assertNotNull( orgUnitGroups.get( 0 ).getId() );
        assertNotNull( orgUnitGroups.get( 0 ).getName() );
    }

    @Test
    void testGetOrgUnitGroup()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        OrgUnitGroup orgUnitGroup = dhis2.getOrgUnitGroup( "CXw2yu5fodb" );

        assertNotNull( orgUnitGroup );
        assertEquals( "CXw2yu5fodb", orgUnitGroup.getId() );
    }

    @Test
    void testGetProgram()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

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
    void testGetPrograms()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        List<Program> programs = dhis2.getPrograms( Query.instance() );

        assertNotNull( programs );
        assertFalse( programs.isEmpty() );
        assertNotNull( programs.get( 0 ) );
    }

    @Test
    void testGetProgramsExpandAssociations()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

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
    void testSaveAndRemoveOrgUnits()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        String uidA = UidUtils.generateUid();
        String uidB = UidUtils.generateUid();
        String uidC = UidUtils.generateUid();

        OrgUnit ouA = new OrgUnit( uidA, uidA, uidA );
        ouA.setOpeningDate( new Date() );
        OrgUnit ouB = new OrgUnit( uidB, uidB, uidB );
        ouB.setOpeningDate( new Date() );
        OrgUnit ouC = new OrgUnit( uidC, uidC, uidC );
        ouC.setOpeningDate( new Date() );

        assertEquals( Status.OK, dhis2.saveOrgUnit( ouA ).getStatus() );
        assertEquals( Status.OK, dhis2.saveOrgUnit( ouB ).getStatus() );
        assertEquals( Status.OK, dhis2.saveOrgUnit( ouC ).getStatus() );

        assertNotNull( dhis2.getOrgUnit( uidA ) );
        assertNotNull( dhis2.getOrgUnit( uidB ) );
        assertNotNull( dhis2.getOrgUnit( uidC ) );

        assertEquals( Status.OK, dhis2.removeOrgUnit( uidA ).getStatus() );
        assertEquals( Status.OK, dhis2.removeOrgUnit( uidB ).getStatus() );
        assertEquals( Status.OK, dhis2.removeOrgUnit( uidC ).getStatus() );
    }

    @Test
    void testGetInvalidUrl()
    {
        Dhis2 dhis2 = new Dhis2( new Dhis2Config( "https://not_a_domain.abc", "username", "pw" ) );

        assertThrows( Dhis2ClientException.class,
            () -> dhis2.getOrgUnitGroups( Query.instance() ) );
    }

    @Test
    void testGetAccessDenied()
    {
        Dhis2 dhis2 = new Dhis2( new Dhis2Config( "https://play.dhis2.org/demo", "notauser", "invalidpw" ) );

        Dhis2ClientException ex = assertThrows( Dhis2ClientException.class,
            () -> dhis2.getOrgUnitGroups( Query.instance() ) );

        assertEquals( 401, ex.getStatusCode() );
        assertEquals( "Authentication failed (401)", ex.getMessage() );
    }

    @Test
    void testGetNotFound()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        Dhis2ClientException ex = assertThrows( Dhis2ClientException.class,
            () -> dhis2.getOrgUnitGroup( "NonExisting" ) );

        assertEquals( 404, ex.getStatusCode() );
        assertEquals( "Object not found (404)", ex.getMessage() );
    }

    @Test
    void testSaveNullObject()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        Dhis2ClientException ex = assertThrows( Dhis2ClientException.class,
            () -> dhis2.saveOrgUnitGroup( null ) );

        assertEquals( 400, ex.getStatusCode() );
    }
}
