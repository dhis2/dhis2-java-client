package org.hisp.dhis;

import java.util.ArrayList;
import java.util.List;

import org.hisp.dhis.query.Filter;
import org.junit.Test;

import static org.junit.Assert.*;

public class FilterTest
{
    @Test
    public void testGetFilterValue()
    {
        List<Object> inValues = new ArrayList<>();
        inValues.add( "NUMBER" );
        inValues.add( "TEXT" );
        inValues.add( "DATE" );

        Filter filterA = Filter.eq( "domainType", "AGGREGATE" );
        Filter filterB = Filter.in( "valueType", inValues );

        assertEquals( "AGGREGATE", filterA.getValue() );
        assertEquals( "[NUMBER,TEXT,DATE]", filterB.getValue() );
    }
}
