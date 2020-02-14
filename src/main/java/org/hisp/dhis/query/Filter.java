package org.hisp.dhis.query;

import java.util.List;

import org.springframework.util.StringUtils;

/**
 * Query filter.
 *
 * @author Lars Helge Overland
 */
public class Filter
{
    private String property;

    private Operator operator;

    private Object value;

    Filter()
    {
    }

    public Filter( String property, Operator operator, Object value )
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

    public static Filter in( String property, List<Object> values )
    {
        return new Filter( property, Operator.IN, values );
    }

    public String getProperty()
    {
        return property;
    }

    public Operator getOperator()
    {
        return operator;
    }

    @SuppressWarnings("unchecked")
    public Object getValue()
    {
        if ( Operator.IN == operator && value != null )
        {
            return new StringBuilder( "[" )
                .append( StringUtils.collectionToCommaDelimitedString( (List<Object>) value ) )
                .append( "]" ).toString();
        }

        return value;
    }
}
