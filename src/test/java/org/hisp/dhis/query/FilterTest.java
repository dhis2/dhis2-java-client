package org.hisp.dhis.query;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FilterTest
{
    @Test
    void testGetFilterEq()
    {
        Filter eq = Filter.eq( "valueType", "NUMBER" );
        assertEquals( "valueType", eq.getProperty() );
        assertEquals( Operator.EQ, eq.getOperator() );
        assertEquals( "NUMBER", eq.getValue() );
    }

    @Test
    void testGetFilterIlike()
    {
        Filter ilike = Filter.ilike( "name", "vaccination" );
        assertEquals( "name", ilike.getProperty() );
        assertEquals( Operator.ILIKE, ilike.getOperator() );
        assertEquals( "vaccination", ilike.getValue() );
    }
}
