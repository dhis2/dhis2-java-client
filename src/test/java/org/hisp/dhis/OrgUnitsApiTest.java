package org.hisp.dhis;

import static org.hisp.dhis.util.CollectionUtils.list;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import org.hisp.dhis.model.OrgUnit;
import org.hisp.dhis.query.Filter;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.object.ErrorReport;
import org.hisp.dhis.response.object.ObjectReport;
import org.hisp.dhis.response.objects.ObjectsResponse;
import org.hisp.dhis.util.UidUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag( "integration" )
public class OrgUnitsApiTest
{
    @Test
    public void testSaveOrgUnitsWithUids()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

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

        List<OrgUnit> orgUnits = list( ouA, ouB, ouC );

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
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

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

        List<OrgUnit> orgUnits = list( ouA, ouB );

        ObjectsResponse response = dhis2.saveOrgUnits( orgUnits );

        assertNotNull( response );
        assertNotNull( response.getStats() );
        assertNotNull( response.getTypeReport() );

        assertEquals( Status.OK, response.getStatus() );
        assertEquals( new Integer( 2 ), response.getStats().getCreated() );

        orgUnits = list( ouB, ouC );

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
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        String uidA = UidUtils.generateUid();
        String uidB = UidUtils.generateUid();

        OrgUnit parent = new OrgUnit();
        parent.setId( "at6UHUQatSo" );

        OrgUnit ouA = new OrgUnit( uidA, uidA, null );
        ouA.setParent( parent );
        OrgUnit ouB = new OrgUnit( uidB, uidB, null );
        ouB.setParent( parent );

        List<OrgUnit> orgUnits = list( ouA, ouB );

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

    @Test
    public void testGetOrgUnits()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        List<OrgUnit> orgUnits = dhis2.getOrgUnits( Query.instance()
            .addFilter( Filter.in( "id", list( "YuQRtpLP10I", "jPidqyo7cpF", "vWbkYPRmKyS" ) ) ) );

        assertNotNull( orgUnits );
        assertEquals( 3, orgUnits.size() );

        OrgUnit ou = orgUnits.get( 0 );

        assertEquals( "YuQRtpLP10I", ou.getId() );
        assertEquals( "OU_539", ou.getCode() );
        assertNotNull( ou.getCreated() );
        assertNotNull( ou.getLastUpdated() );
        assertEquals( "Badjia", ou.getName() );
        assertEquals( "Badjia", ou.getShortName() );
        assertEquals( "/ImspTQPwCqd/O6uvpzGd5pu/YuQRtpLP10I", ou.getPath() );
        assertEquals( new Integer( 3 ), ou.getLevel() );
        assertNotNull( ou.getParent() );
        assertEquals( "O6uvpzGd5pu", ou.getParent().getId() );
        assertNotNull( ou.getOpeningDate() );
    }

    @Test
    public void testGetOrgUnitsByAttributeValue()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        List<OrgUnit> orgUnits = dhis2.getOrgUnits( Query.instance()
            .addFilter( Filter.eq( "attributeValues.attribute.id", "l1VmqIHKk6t" ) )
            .addFilter( Filter.eq( "attributeValues.value", "KE02" ) ) );

        assertEquals( 1, orgUnits.size() );
        assertTrue( orgUnits.contains( new OrgUnit( "g8upMTyEZGZ", "Njandama MCHP" ) ) );
    }

    @Test
    public void testGetOrgUnitSubHierarchy()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        List<OrgUnit> orgUnits = dhis2.getOrgUnitSubHierarchy( "O6uvpzGd5pu", 1, Query.instance() );

        assertEquals( 15, orgUnits.size() );
        assertTrue( orgUnits.contains( new OrgUnit( "YuQRtpLP10I", "Badjia" ) ) );
        assertTrue( orgUnits.contains( new OrgUnit( "dGheVylzol6", "Bargbe" ) ) );
        assertTrue( orgUnits.contains( new OrgUnit( "kU8vhUkAGaT", "Lugbu" ) ) );
    }

    @Test
    public void testInFilter()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        List<String> values = list( "Rp268JB6Ne4", "cDw53Ej8rju", "GvFqTavdpGE" );

        List<OrgUnit> orgUnits = dhis2.getOrgUnits( Query.instance()
            .addFilter( Filter.in( "id", values ) ) );

        assertEquals( 3, orgUnits.size() );
    }
}
