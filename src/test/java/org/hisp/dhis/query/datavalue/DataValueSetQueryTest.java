package org.hisp.dhis.query.datavalue;

import static org.hisp.dhis.util.CollectionUtils.list;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DataValueSetQueryTest
{
    @Test
    void testAddSingleDataElement()
    {
        DataValueSetQuery query = DataValueSetQuery.instance()
            .addDataElements( list( "xEW3d2waDed" ) );

        assertEquals( 1, query.getDataElements().size() );
        assertTrue( query.getDataElements().contains( "xEW3d2waDed" ) );
    }

    @Test
    void testAddMultipleDataElements()
    {
        DataValueSetQuery query = DataValueSetQuery.instance()
            .addDataElements( list( "xEW3d2waDed", "rFew4sdfare" ) )
            .addDataElements( list( "merSed1wrwL" ) );

        assertEquals( 3, query.getDataElements().size() );
        assertTrue( query.getDataElements().contains( "merSed1wrwL" ) );
        assertTrue( query.getDataElements().contains( "rFew4sdfare" ) );
    }
}
