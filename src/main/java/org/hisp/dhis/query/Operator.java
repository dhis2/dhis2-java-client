package org.hisp.dhis.query;

/**
 * Enumeration of query filter operators.
 *
 * @author Lars Helge Overland
 */
public enum Operator
{
    EQ( "eq" ), GE( "ge" ), GT( "gt" ), LE( "le" ), LT( "lt" ), LIKE( "like" ), IN( "in" );

    private String value;

    Operator( String value )
    {
        this.value = value;
    }

    public String value()
    {
        return value;
    }
}
