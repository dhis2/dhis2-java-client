package org.hisp.dhis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import org.hisp.dhis.model.Attribute;
import org.hisp.dhis.model.AttributeValue;
import org.hisp.dhis.model.OrgUnit;
import org.hisp.dhis.model.OrgUnitGroup;
import org.hisp.dhis.query.Filter;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.response.Response;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.util.UidUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag( "integration" )
public class OrgUnitApiTest
{
    @Test
    void testGetOrgUnit()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        OrgUnit ou = dhis2.getOrgUnit( "YuQRtpLP10I" );

        assertEquals( "YuQRtpLP10I", ou.getId() );
        assertEquals( "OU_539", ou.getCode() );
        assertNotNull( ou.getCreated() );
        assertNotNull( ou.getLastUpdated() );
        assertEquals( "Badjia", ou.getName() );
        assertEquals( "Badjia", ou.getShortName() );
        assertEquals( "/ImspTQPwCqd/O6uvpzGd5pu/YuQRtpLP10I", ou.getPath() );
        assertEquals( 3, ou.getLevel() );
        assertNotNull( ou.getParent() );
        assertEquals( "O6uvpzGd5pu", ou.getParent().getId() );
        assertNotNull( ou.getOpeningDate() );
    }

    @Test
    void testIsOrgUnit()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        assertTrue( dhis2.isOrgUnit( "qhqAxPSTUXp" ) );
        assertFalse( dhis2.isOrgUnit( "VhZoBbbnMeO" ) );
    }

    @Test
    void testSaveOrgUnitWithAttributes()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        String uidA = UidUtils.generateUid();

        Attribute atA = new Attribute();
        atA.setId( "l1VmqIHKk6t" );

        Attribute atB = new Attribute();
        atB.setId( "n2xYlNbsfko" );

        AttributeValue avA = new AttributeValue( atA, "KE7651" );
        AttributeValue avB = new AttributeValue( atB, "NG8749" );

        OrgUnit ouA = new OrgUnit( uidA, uidA, uidA );
        ouA.setOpeningDate( new Date() );
        ouA.addAttributeValue( avA );
        ouA.addAttributeValue( avB );

        ObjectResponse response = dhis2.saveOrgUnit( ouA );

        assertEquals( Status.OK, response.getStatus() );

        OrgUnit orgUnit = dhis2.getOrgUnit( uidA );

        assertNotNull( orgUnit );
        assertEquals( uidA, orgUnit.getId() );
        assertEquals( 2, orgUnit.getAttributeValues().size() );

        response = dhis2.removeOrgUnit( uidA );

        assertEquals( Status.OK, response.getStatus(), response.toString() );
    }

    @Test
    void testAddOrgUnitToGroup()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        String ouId = UidUtils.generateUid();
        String ougId = UidUtils.generateUid();

        OrgUnit ou = new OrgUnit( ouId, ouId, ouId );
        ou.setOpeningDate( new Date() );

        ObjectResponse ouResp = dhis2.saveOrgUnit( ou );

        assertEquals( Status.OK, ouResp.getStatus(), ouResp.toString() );

        OrgUnitGroup oug = new OrgUnitGroup( ougId, ougId );

        ObjectResponse ougResp = dhis2.saveOrgUnitGroup( oug );

        assertEquals( Status.OK, ougResp.getStatus(), ougResp.toString() );

        Response resp = dhis2.addOrgUnitToOrgUnitGroup( ougId, ouId );

        assertEquals( Status.OK, resp.getStatus(), resp.toString() );
    }

    @Test
    void testGetOrgUnitWithoutRootJunctionOr()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        List<OrgUnit> orgUnits = dhis2.getOrgUnits( Query.instance()
            .addFilter( Filter.like( "name", "Sierra Leone" ) )
            .addFilter( Filter.like( "name", "Agape CHP" ) ) );

        assertEquals( 0, orgUnits.size() );
    }

    @Test
    void testGetOrgUnitWithRootJunctionOr()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        List<OrgUnit> orgUnits = dhis2.getOrgUnits( Query.instance().withRootJunctionOr()
            .addFilter( Filter.like( "name", "Sierra Leone" ) )
            .addFilter( Filter.like( "name", "Agape CHP" ) ) );

        assertEquals( 2, orgUnits.size() );
    }
}
