package org.hisp.dhis.query;

import lombok.RequiredArgsConstructor;

/**
 * Enumeration of query filter operators.
 *
 * @author Lars Helge Overland
 */
@RequiredArgsConstructor
public enum Operator
{
    EQ( "eq", "=" ),
    GE( "ge", ">=" ),
    GT( "gt", ">" ),
    LE( "le", "<=" ),
    LT( "lt", "<" ),
    LIKE( "like", "like" ),
    ILIKE( "ilike", "ilike " ),
    TOKEN( "token", "" ),
    IN( "in", "in" );

    private final String value;

    private final String sqlOperator;

    /**
     * Returns the operator with a value matching the given string.
     *
     * @param value the value.
     * @return an {@link Operator} or null if no match.
     */
    public static Operator fromValue( String value )
    {
        for ( Operator operator : Operator.values() )
        {
            if ( operator.value().equals( value ) )
            {
                return operator;
            }
        }

        return null;
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
