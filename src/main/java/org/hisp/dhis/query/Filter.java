package org.hisp.dhis.query;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

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

    /**
     * Creates an equals to {@link Filter}.
     *
     * @param property the filter property.
     * @param value the filter value.
     * @return a {@link Filter}.
     */
    public static Filter eq( String property, Object value )
    {
        return new Filter( property, Operator.EQ, value );
    }

    /**
     * Creates a greater than or equals to {@link Filter}.
     *
     * @param property the filter property.
     * @param value the filter value.
     * @return a {@link Filter}.
     */
    public static Filter ge( String property, Object value )
    {
        return new Filter( property, Operator.GE, value );
    }

    /**
     * Creates a greater than {@link Filter}.
     *
     * @param property the filter property.
     * @param value the filter value.
     * @return a {@link Filter}.
     */
    public static Filter gt( String property, Object value )
    {
        return new Filter( property, Operator.GT, value );
    }

    /**
     * Creates a less than or equals to {@link Filter}.
     *
     * @param property the filter property.
     * @param value the filter value.
     * @return a {@link Filter}.
     */
    public static Filter le( String property, Object value )
    {
        return new Filter( property, Operator.LE, value );
    }

    /**
     * Creates a less than {@link Filter}.
     *
     * @param property the filter property.
     * @param value the filter value.
     * @return a {@link Filter}.
     */
    public static Filter lt( String property, Object value )
    {
        return new Filter( property, Operator.LT, value );
    }

    /**
     * Creates an is true {@link Filter}.
     *
     * @param property the filter property.
     * @return a {@link Filter}.
     */
    public static Filter isTrue( String property )
    {
        return new Filter( property, Operator.EQ, "true" );
    }

    /**
     * Creates an is false {@link Filter}.
     *
     * @param property the filter property.
     * @return a {@link Filter}.
     */
    public static Filter isFalse( String property )
    {
        return new Filter( property, Operator.EQ, "false" );
    }

    /**
     * Creates a like {@link Filter}.
     *
     * @param property the filter property.
     * @param value the filter value.
     * @return a {@link Filter}.
     */
    public static Filter like( String property, Object value )
    {
        return new Filter( property, Operator.LIKE, value );
    }

    /**
     * Creates an ilike {@link Filter}.
     *
     * @param property the filter property.
     * @param value the filter value.
     * @return a {@link Filter}.
     */
    public static Filter ilike( String property, Object value )
    {
        return new Filter( property, Operator.ILIKE, value );
    }

    /**
     * Creates an in {@link Filter}.
     *
     * @param property the filter property.
     * @param values the filter values.
     * @return a {@link Filter}.
     */
    public static Filter in( String property, List<String> values )
    {
        return new Filter( property, Operator.IN, StringUtils.join( values, ',' ) );
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
