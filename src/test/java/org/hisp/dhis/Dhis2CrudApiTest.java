package org.hisp.dhis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import lombok.extern.slf4j.Slf4j;

import org.hisp.dhis.model.Attribute;
import org.hisp.dhis.model.AttributeValue;
import org.hisp.dhis.model.CategoryOption;
import org.hisp.dhis.model.OrgUnit;
import org.hisp.dhis.model.OrgUnitGroup;
import org.hisp.dhis.response.Dhis2ClientException;
import org.hisp.dhis.response.HttpStatus;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Slf4j
@Tag( TestTags.INTEGRATION )
class Dhis2CrudApiTest
{
    @Test
    void testCategoryOption()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        Attribute atA = new Attribute();
        atA.setId( "l1VmqIHKk6t" );

        AttributeValue avA = new AttributeValue( atA, "AT__A_Value__A" );

        OrgUnit ouA = new OrgUnit( "ueuQlqb8ccl", null );
        OrgUnit ouB = new OrgUnit( "Rp268JB6Ne4", null );

        String codeA = "CAT_OPT__A";
        String nameA = "Category option name__A";
        String shortNameA = "Category option short name__A";

        CategoryOption coA = new CategoryOption();
        coA.setCode( codeA );
        coA.setName( nameA );
        coA.setShortName( shortNameA );
        coA.addAttributeValue( avA );
        coA.getOrganisationUnits().add( ouA );
        coA.getOrganisationUnits().add( ouB );

        // Create

        ObjectResponse createRespA = dhis2.saveCategoryOption( coA );

        log.info( "Status code: {}", createRespA.getHttpStatusCode() );
        log.info( "Message: '{}'", createRespA.getMessage() );
        log.info( "UID: {}", createRespA.getResponse().getUid() );
        log.info( "Response: {}", createRespA.getResponse().toString() );

        assertEquals( 201, createRespA.getHttpStatusCode().intValue(), createRespA.toString() );
        assertEquals( HttpStatus.CREATED, createRespA.getHttpStatus(), createRespA.toString() );
        assertEquals( Status.OK, createRespA.getStatus(), createRespA.toString() );
        assertNotNull( createRespA.getResponse() );
        assertNotNull( createRespA.getResponse().getUid() );

        String uidA = createRespA.getResponse().getUid();

        // Get

        coA = dhis2.getCategoryOption( uidA );

        assertNotNull( coA );
        assertNotNull( coA.getAttributeValues() );
        assertEquals( uidA, coA.getId() );
        assertEquals( codeA, coA.getCode() );
        assertEquals( nameA, coA.getName() );
        assertEquals( shortNameA, coA.getShortName() );
        assertEquals( 1, coA.getAttributeValues().size() );
        assertTrue( coA.getAttributeValues().contains( avA ) );
        assertEquals( 2, coA.getOrganisationUnits().size() );
        assertTrue( coA.getOrganisationUnits().contains( ouA ) );
        assertTrue( coA.getOrganisationUnits().contains( ouB ) );

        String name = "Category updated name__A";

        coA.setName( name );

        // Update

        ObjectResponse updateRespA = dhis2.updateCategoryOption( coA );

        assertEquals( 200, updateRespA.getHttpStatusCode().intValue(), updateRespA.toString() );
        assertEquals( HttpStatus.OK, updateRespA.getHttpStatus(), updateRespA.toString() );
        assertEquals( Status.OK, updateRespA.getStatus(), updateRespA.toString() );
        assertEquals( uidA, updateRespA.getResponse().getUid(), updateRespA.toString() );

        // Get

        coA = dhis2.getCategoryOption( uidA );

        assertNotNull( coA );
        assertNotNull( coA.getAttributeValues() );
        assertEquals( uidA, coA.getId() );
        assertEquals( codeA, coA.getCode() );
        assertEquals( 1, coA.getAttributeValues().size() );
        assertTrue( coA.getAttributeValues().contains( avA ) );
        assertEquals( 2, coA.getOrganisationUnits().size() );
        assertTrue( coA.getOrganisationUnits().contains( ouA ) );
        assertTrue( coA.getOrganisationUnits().contains( ouB ) );

        // Remove

        ObjectResponse removeRespA = dhis2.removeCategoryOption( uidA );

        assertEquals( 200, removeRespA.getHttpStatusCode().intValue(), removeRespA.toString() );
        assertEquals( HttpStatus.OK, removeRespA.getHttpStatus(), removeRespA.toString() );
        assertEquals( Status.OK, removeRespA.getStatus(), removeRespA.toString() );
        assertEquals( uidA, updateRespA.getResponse().getUid(), removeRespA.toString() );
    }

    @Test
    void testOrgUnitGroup()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        OrgUnitGroup ougA = new OrgUnitGroup();
        ougA.setCode( "ORG_UNIT_GROUP__A" );
        ougA.setName( "OUG name__A" );
        ougA.setShortName( "OUG short name__A" );

        // Create

        ObjectResponse createRespA = dhis2.saveOrgUnitGroup( ougA );

        assertEquals( 201, createRespA.getHttpStatusCode().intValue(), createRespA.toString() );
        assertEquals( HttpStatus.CREATED, createRespA.getHttpStatus(), createRespA.toString() );
        assertEquals( Status.OK, createRespA.getStatus(), createRespA.toString() );
        assertNotNull( createRespA.getResponse() );
        assertNotNull( createRespA.getResponse().getUid() );

        String uidA = createRespA.getResponse().getUid();

        // Get

        ougA = dhis2.getOrgUnitGroup( uidA );

        assertNotNull( ougA );
        assertEquals( uidA, ougA.getId() );

        String name = "Category updated name__A";

        ougA.setName( name );

        // Update

        ObjectResponse updateRespA = dhis2.updateOrgUnitGroup( ougA );

        assertEquals( 200, updateRespA.getHttpStatusCode().intValue(), updateRespA.toString() );
        assertEquals( HttpStatus.OK, updateRespA.getHttpStatus(), updateRespA.toString() );
        assertEquals( Status.OK, updateRespA.getStatus(), updateRespA.toString() );
        assertEquals( uidA, updateRespA.getResponse().getUid(), updateRespA.toString() );

        // Remove

        ObjectResponse removeRespA = dhis2.removeOrgUnitGroup( uidA );

        assertEquals( 200, removeRespA.getHttpStatusCode().intValue(), removeRespA.toString() );
        assertEquals( HttpStatus.OK, removeRespA.getHttpStatus(), removeRespA.toString() );
        assertEquals( Status.OK, removeRespA.getStatus(), removeRespA.toString() );
        assertNotNull( removeRespA.getResponse() );
        assertNotNull( removeRespA.getResponse().getUid() );
    }

    @Test
    void testNotFound()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        Dhis2ClientException ex = assertThrows( Dhis2ClientException.class,
            () -> dhis2.getCategoryOption( "kju6y2JHtR1" ) );
        assertEquals( 404, ex.getStatusCode() );
    }
}
