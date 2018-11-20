package org.hisp.dhis.query;

/**
 * Enumeration of query filter operators.
 */
public enum Operator
{
    EQ( "eq" ), GE( "ge" ), GT( "gt" ), LE( "le" ), LT( "lt" ), LIKE( "like" );

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
