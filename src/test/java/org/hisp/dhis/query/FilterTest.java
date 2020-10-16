package org.hisp.dhis.query;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FilterTest
{
    @Test
    public void testGetFilter()
    {
        Filter eq = Filter.eq( "valueType", "NUMBER" );
        assertEquals( "valueType", eq.getProperty() );
        assertEquals( Operator.EQ, eq.getOperator() );
        assertEquals( "NUMBER", eq.getValue() );
    }
}
