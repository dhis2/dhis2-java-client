package org.hisp.dhis.query;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FilterTest
{
    @Test
    public void testGetFilterEq()
    {
        Filter eq = Filter.eq( "valueType", "NUMBER" );
        assertEquals( "valueType", eq.getProperty() );
        assertEquals( Operator.EQ, eq.getOperator() );
        assertEquals( "NUMBER", eq.getValue() );
    }

    @Test
    public void testGetFilterIlike()
    {
        Filter ilike = Filter.ilike( "name", "vaccination" );
        assertEquals( "name", ilike.getProperty() );
        assertEquals( Operator.ILIKE, ilike.getOperator() );
        assertEquals( "vaccination", ilike.getValue() );

    }
}
