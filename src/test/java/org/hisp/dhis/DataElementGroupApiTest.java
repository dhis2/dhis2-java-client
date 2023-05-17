package org.hisp.dhis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.hisp.dhis.model.DataElement;
import org.hisp.dhis.model.DataElementGroup;
import org.hisp.dhis.model.IdentifiableObject;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag( "integration" )
public class DataElementGroupApiTest
{
    @Test
    void testGetDataElementGroup()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        DataElementGroup deg = dhis2.getDataElementGroup( "SriP0jBXMr6" );

        assertEquals( "SriP0jBXMr6", deg.getId() );
        assertNull( deg.getCode() );
        assertEquals( "Lassa Fever", deg.getName() );
        assertEquals( "Lassa Fever", deg.getShortName() );
        assertNotNull( deg.getCreated() );
        assertNotNull( deg.getLastUpdated() );
        assertNull( deg.getDescription() );

        // Group members assertions

        List<DataElement> dataElements = deg.getDataElements();

        assertNotNull( dataElements );
        assertEquals( 3, dataElements.size() );

        List<String> deIds = dataElements.stream()
            .map( IdentifiableObject::getId )
            .collect( Collectors.toList() );

        List<String> deNames = dataElements.stream()
            .map( IdentifiableObject::getName )
            .collect( Collectors.toList() );

        assertTrue( deIds.contains( "dVdxnTNL2jZ" ) );
        assertTrue( deIds.contains( "NCteyX2xpMf" ) );
        assertTrue( deIds.contains( "uz8dqEzuxyc" ) );

        assertTrue( deNames.contains( "Lassa fever new" ) );
        assertTrue( deNames.contains( "Lassa fever follow-up" ) );
        assertTrue( deNames.contains( "Lassa fever referrals" ) );
    }
}
