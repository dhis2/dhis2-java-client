package org.hisp.dhis.query;

/**
 * Enumeration of query filter operators.
 *
 * @author Lars Helge Overland
 */
public enum Operator
{
    EQ( "eq", "=" ),
    GE( "ge", ">=" ),
    GT( "gt", ">" ),
    LE( "le", "<=" ),
    LT( "lt", "<" ),
    LIKE( "like", "like" ),
    ILIKE( "ilike", "ilike "),
    IN( "in", "in" );

    private String value;

    private String sqlOperator;

    Operator( String value, String sqlOperator )
    {
        this.value = value;
        this.sqlOperator = sqlOperator;
    }

    public static Operator fromValue( String value )
    {
        for ( Operator operator : Operator.values() )
        {
            if ( operator.value().equals( value ) )
            {
                return operator;
            }
        }

        throw new IllegalArgumentException( String.format( "No enum for value: '%s'", value ) );
    }

    public String value()
    {
        return value;
    }

    public String sqlOperator()
    {
        return sqlOperator;
    }
}
