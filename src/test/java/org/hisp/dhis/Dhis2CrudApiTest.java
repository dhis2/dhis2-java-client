package org.hisp.dhis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import org.hisp.dhis.category.IntegrationTest;
import org.hisp.dhis.model.Attribute;
import org.hisp.dhis.model.AttributeValue;
import org.hisp.dhis.model.CategoryOption;
import org.hisp.dhis.model.OrgUnitGroup;
import org.hisp.dhis.response.Dhis2ClientException;
import org.hisp.dhis.response.HttpStatus;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.metadata.MetadataResponseMessage;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.function.ThrowingRunnable;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Category(IntegrationTest.class)
public class Dhis2CrudApiTest
{
    private static final Dhis2Config config = new Dhis2Config(
        "https://play.dhis2.org/2.35.5", "admin", "district" );

    @Test
    public void testCategoryOption()
    {
        Dhis2 dhis2 = new Dhis2( config );

        Attribute atA = new Attribute();
        atA.setId( "l1VmqIHKk6t" );

        AttributeValue avA = new AttributeValue( atA, "AT__A_Value__A" );

        String codeA = "CAT_OPT__A";
        String nameA = "Category name__A";
        String shortNameA = "Category short name__A";

        CategoryOption coA = new CategoryOption();
        coA.setCode( codeA );
        coA.setName( nameA );
        coA.setShortName( shortNameA );
        coA.addAttributeValue( avA );

        // Create

        MetadataResponseMessage createRespA = dhis2.saveCategoryOption( coA );

        log.info( "Status code: {}", createRespA.getHttpStatusCode() );
        log.info( "Message: '{}'", createRespA.getMessage() );
        log.info( "UID: {}", createRespA.getResponse().getUid() );
        log.info( "Response: {}", createRespA.getResponse().toString() );

        assertEquals( 201, createRespA.getHttpStatusCode().intValue() );
        assertEquals( HttpStatus.CREATED, createRespA.getHttpStatus() );
        assertEquals( Status.OK, createRespA.getStatus() );
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

        String name = "Category updated name__A";

        coA.setName( name );

        // Update

        MetadataResponseMessage updateRespA = dhis2.updateCategoryOption( coA );

        assertEquals( 200, updateRespA.getHttpStatusCode().intValue() );
        assertEquals( HttpStatus.OK, updateRespA.getHttpStatus() );
        assertEquals( Status.OK, updateRespA.getStatus() );
        assertEquals( uidA, updateRespA.getResponse().getUid() );

        // Remove

        MetadataResponseMessage removeRespA = dhis2.removeCategoryOption( uidA );

        assertEquals( 200, removeRespA.getHttpStatusCode().intValue() );
        assertEquals( HttpStatus.OK, removeRespA.getHttpStatus() );
        assertEquals( Status.OK, removeRespA.getStatus() );
        assertEquals( uidA, updateRespA.getResponse().getUid() );
    }

    @Test
    public void testOrgUnitGroup()
    {
        Dhis2 dhis2 = new Dhis2( config );

        OrgUnitGroup ougA = new OrgUnitGroup();
        ougA.setCode( "ORG_UNIT_GROUP__A" );
        ougA.setName( "OUG name__A" );
        ougA.setShortName( "OUG short name__A" );

        // Create

        MetadataResponseMessage createRespA = dhis2.saveOrgUnitGroup( ougA );

        assertEquals( 201, createRespA.getHttpStatusCode().intValue() );
        assertEquals( HttpStatus.CREATED, createRespA.getHttpStatus() );
        assertEquals( Status.OK, createRespA.getStatus() );
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

        MetadataResponseMessage updateRespA = dhis2.updateOrgUnitGroup( ougA );

        assertEquals( 200, updateRespA.getHttpStatusCode().intValue() );
        assertEquals( HttpStatus.OK, updateRespA.getHttpStatus() );
        assertEquals( Status.OK, updateRespA.getStatus() );
        assertEquals( uidA, updateRespA.getResponse().getUid() );

        // Remove

        MetadataResponseMessage removeRespA = dhis2.removeOrgUnitGroup( uidA );

        assertEquals( 200, removeRespA.getHttpStatusCode().intValue() );
        assertEquals( HttpStatus.OK, removeRespA.getHttpStatus() );
        assertEquals( Status.OK, removeRespA.getStatus() );
        assertNotNull( removeRespA.getResponse() );
        assertNotNull( removeRespA.getResponse().getUid() );
    }

    @Test
    public void testNotFound()
    {
        Dhis2 dhis2 = new Dhis2( config );

        ThrowingRunnable runnable = () -> dhis2.getCategoryOption( "kju6y2JHtR1" );
        Dhis2ClientException ex = assertThrows( Dhis2ClientException.class, runnable );
        assertEquals( 404, ex.getStatusCode() );
    }
}
