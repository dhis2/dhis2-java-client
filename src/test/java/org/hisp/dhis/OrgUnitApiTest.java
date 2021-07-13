package org.hisp.dhis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import java.util.Date;

import org.hisp.dhis.category.IntegrationTest;
import org.hisp.dhis.model.OrgUnit;
import org.hisp.dhis.request.orgunit.OrgUnitMergeRequest;
import org.hisp.dhis.response.Dhis2ClientException;
import org.hisp.dhis.response.Response;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.util.CollectionUtils;
import org.hisp.dhis.util.UidUtils;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class OrgUnitApiTest
{
    @Test
    public void testOrgUnitMerge()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEV_CONFIG );

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

        OrgUnitMergeRequest request = new OrgUnitMergeRequest()
            .setSources( CollectionUtils.newImmutableList( uidA, uidB ) )
            .setTarget( uidC )
            .setDeleteSources( true );

        Response response = dhis2.mergeOrgUnits( request );

        assertEquals( Status.OK, response.getStatus() );

        assertNotNull( dhis2.getOrgUnit( uidC ) );
        assertEquals( Status.OK, dhis2.removeOrgUnit( uidC ).getStatus() );

        assertEquals( 404, assertThrows( Dhis2ClientException.class,
            () -> dhis2.getOrgUnit( uidA ) ).getStatusCode() );
        assertEquals( 404, assertThrows( Dhis2ClientException.class,
            () -> dhis2.getOrgUnit( uidB ) ).getStatusCode() );
        assertEquals( 404, assertThrows( Dhis2ClientException.class,
            () -> dhis2.getOrgUnit( uidC ) ).getStatusCode() );
    }
}
