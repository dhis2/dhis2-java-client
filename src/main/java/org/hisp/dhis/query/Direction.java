package org.hisp.dhis.query;

import lombok.RequiredArgsConstructor;

/**
 * Enumeration of order direction.
 *
 * @author Lars Helge Overland
 */
@RequiredArgsConstructor
public enum Direction
{
    ASC( "asc" ),
    DESC( "desc" );

    private final String value;

    /**
     * Returns the direction with a value matching the given string.
     *
     * @param value the value.
     * @return an {@link Direction} or null if no match.
     */
    public static Direction fromValue( String value )
    {
        for ( Direction direction : Direction.values() )
        {
            if ( direction.value().equals( value ) )
            {
                return direction;
            }
        }

        return null;
    }

    public String value()
    {
        return value;
    }
}
