package org.hisp.dhis;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.hisp.dhis.category.IntegrationTest;
import org.hisp.dhis.model.OrgUnit;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.objects.ObjectsResponse;
import org.hisp.dhis.util.CollectionUtils;
import org.hisp.dhis.util.UidUtils;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class OrgUnitsApiTest
{
    //TODO test validation errors

    @Test
    public void testSaveOrgUnits()
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
    public void testSaveAndUpdateOrgUnits()
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
        assertNotNull( response.getTypeReport().getErrorReports() );

        assertEquals( Status.OK, response.getStatus() );
        assertEquals( new Integer( 1 ), response.getStats().getCreated() );
        assertEquals( new Integer( 1 ), response.getStats().getUpdated() );

        assertEquals( Status.OK, dhis2.removeOrgUnit( uidA ).getStatus() );
        assertEquals( Status.OK, dhis2.removeOrgUnit( uidB ).getStatus() );
        assertEquals( Status.OK, dhis2.removeOrgUnit( uidC ).getStatus() );
    }
}
