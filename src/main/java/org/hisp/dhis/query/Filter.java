package org.hisp.dhis.query;

/**
 * Query filter.
 */
public class Filter
{
    private String property;
    
    private Operator operator;
    
    private Object value;
    
    private Filter()
    {
    }
    
    private Filter( String property, Operator operator, Object value )
    {
        this.property = property;
        this.operator = operator;
        this.value = value;
    }
    
    public static Filter eq( String property, Object value )
    {
        return new Filter( property, Operator.EQ, value );
    }

    public static Filter ge( String property, Object value )
    {
        return new Filter( property, Operator.GE, value );
    }

    public static Filter gt( String property, Object value )
    {
        return new Filter( property, Operator.GT, value );
    }

    public static Filter le( String property, Object value )
    {
        return new Filter( property, Operator.LE, value );
    }
    
    public static Filter lt( String property, Object value )
    {
        return new Filter( property, Operator.LT, value );
    }
    
    public static Filter isTrue( String property )
    {
        return new Filter( property, Operator.EQ, "true" );
    }

    public static Filter isFalse( String property )
    {
        return new Filter( property, Operator.EQ, "false" );
    }

    public static Filter like( String property, Object value )
    {
        return new Filter( property, Operator.LIKE, value );
    }
    
    public String getProperty()
    {
        return property;
    }

    public Operator getOperator()
    {
        return operator;
    }

    public Object getValue()
    {
        return value;
    }
}
