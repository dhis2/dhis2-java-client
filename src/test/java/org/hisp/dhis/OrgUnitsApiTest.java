package org.hisp.dhis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import org.hisp.dhis.category.IntegrationTest;
import org.hisp.dhis.model.OrgUnit;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.object.ErrorReport;
import org.hisp.dhis.response.object.ObjectReport;
import org.hisp.dhis.response.objects.ObjectsResponse;
import org.hisp.dhis.util.CollectionUtils;
import org.hisp.dhis.util.UidUtils;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category( IntegrationTest.class )
public class OrgUnitsApiTest
{
    @Test
    public void testSaveOrgUnitsWithUids()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEV_CONFIG );

        String uidA = UidUtils.generateUid();
        String uidB = UidUtils.generateUid();
        String uidC = UidUtils.generateUid();

        OrgUnit parent = new OrgUnit();
        parent.setId( "at6UHUQatSo" );

        OrgUnit ouA = new OrgUnit( uidA, uidA, uidA );
        ouA.setCode( uidA );
        ouA.setOpeningDate( new Date() );
        ouA.setParent( parent );
        OrgUnit ouB = new OrgUnit( uidB, uidB, uidB );
        ouB.setCode( uidB );
        ouB.setOpeningDate( new Date() );
        ouB.setParent( parent );
        OrgUnit ouC = new OrgUnit( uidC, uidC, uidC );
        ouC.setCode( uidC );
        ouC.setOpeningDate( new Date() );
        ouC.setParent( parent );

        List<OrgUnit> orgUnits = CollectionUtils.newImmutableList( ouA, ouB, ouC );

        ObjectsResponse response = dhis2.saveOrgUnits( orgUnits );

        assertNotNull( response );
        assertNotNull( response.getStats() );
        assertNotNull( response.getTypeReport() );

        assertEquals( Status.OK, response.getStatus() );
        assertEquals( new Integer( 3 ), response.getStats().getCreated() );

        assertEquals( Status.OK, dhis2.removeOrgUnit( uidA ).getStatus() );
        assertEquals( Status.OK, dhis2.removeOrgUnit( uidB ).getStatus() );
        assertEquals( Status.OK, dhis2.removeOrgUnit( uidC ).getStatus() );
    }

    @Test
    public void testSaveAndUpdateOrgUnitswithUids()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEV_CONFIG );

        String uidA = UidUtils.generateUid();
        String uidB = UidUtils.generateUid();
        String uidC = UidUtils.generateUid();

        OrgUnit parent = new OrgUnit();
        parent.setId( "at6UHUQatSo" );

        OrgUnit ouA = new OrgUnit( uidA, uidA, uidA );
        ouA.setCode( uidA );
        ouA.setOpeningDate( new Date() );
        ouA.setParent( parent );
        OrgUnit ouB = new OrgUnit( uidB, uidB, uidB );
        ouB.setCode( uidB );
        ouB.setOpeningDate( new Date() );
        ouB.setParent( parent );
        OrgUnit ouC = new OrgUnit( uidC, uidC, uidC );
        ouC.setCode( uidC );
        ouC.setOpeningDate( new Date() );
        ouC.setParent( parent );

        List<OrgUnit> orgUnits = CollectionUtils.newImmutableList( ouA, ouB );

        ObjectsResponse response = dhis2.saveOrgUnits( orgUnits );

        assertNotNull( response );
        assertNotNull( response.getStats() );
        assertNotNull( response.getTypeReport() );

        assertEquals( Status.OK, response.getStatus() );
        assertEquals( new Integer( 2 ), response.getStats().getCreated() );

        orgUnits = CollectionUtils.newImmutableList( ouB, ouC );

        response = dhis2.saveOrgUnits( orgUnits );

        assertNotNull( response );
        assertNotNull( response.getStats() );
        assertNotNull( response.getTypeReport() );

        assertEquals( Status.OK, response.getStatus() );
        assertEquals( new Integer( 1 ), response.getStats().getCreated() );
        assertEquals( new Integer( 1 ), response.getStats().getUpdated() );

        assertEquals( Status.OK, dhis2.removeOrgUnit( uidA ).getStatus() );
        assertEquals( Status.OK, dhis2.removeOrgUnit( uidB ).getStatus() );
        assertEquals( Status.OK, dhis2.removeOrgUnit( uidC ).getStatus() );
    }

    @Test
    public void testSaveOrgUnitsMissingRequiredProperty()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEV_CONFIG );

        String uidA = UidUtils.generateUid();
        String uidB = UidUtils.generateUid();

        OrgUnit parent = new OrgUnit();
        parent.setId( "at6UHUQatSo" );

        OrgUnit ouA = new OrgUnit( uidA, uidA, null );
        ouA.setParent( parent );
        OrgUnit ouB = new OrgUnit( uidB, uidB, null );
        ouB.setParent( parent );

        List<OrgUnit> orgUnits = CollectionUtils.newImmutableList( ouA, ouB );

        ObjectsResponse response = dhis2.saveOrgUnits( orgUnits );

        assertNotNull( response );
        assertNotNull( response.getStats() );
        assertNotNull( response.getTypeReport() );

        assertEquals( Status.ERROR, response.getStatus() );
        assertEquals( new Integer( 2 ), response.getStats().getIgnored() );
        assertEquals( "org.hisp.dhis.organisationunit.OrganisationUnit", response.getTypeReport().getKlass() );
        assertEquals( new Integer( 2 ), response.getTypeReport().getStats().getIgnored() );
        assertEquals( 1, response.getTypeReports().size() );
        assertEquals( 2, response.getTypeReport().getObjectReports().size() );

        ObjectReport objectReport1 = response.getTypeReport().getObjectReports().get( 0 );
        ObjectReport objectReport2 = response.getTypeReport().getObjectReports().get( 1 );

        assertNotNull( objectReport1 );
        assertEquals( "org.hisp.dhis.organisationunit.OrganisationUnit", objectReport1.getKlass() );
        assertEquals( 2, objectReport1.getErrorReports().size() );

        assertNotNull( objectReport2 );
        assertEquals( "org.hisp.dhis.organisationunit.OrganisationUnit", objectReport2.getKlass() );
        assertEquals( 2, objectReport2.getErrorReports().size() );

        ErrorReport errorReport1 = objectReport1.getErrorReports().get( 0 );
        ErrorReport errorReport2 = objectReport1.getErrorReports().get( 1 );

        assertNotNull( errorReport1 );
        assertEquals( "org.hisp.dhis.organisationunit.OrganisationUnit", errorReport1.getMainKlass() );
        assertEquals( "E4000", errorReport1.getErrorCode() );

        assertNotNull( errorReport2 );
        assertEquals( "org.hisp.dhis.organisationunit.OrganisationUnit", errorReport2.getMainKlass() );
        assertEquals( "E4000", errorReport2.getErrorCode() );
    }
}
