package org.hisp.dhis.query;

/**
 * Enumeration of order direction.
 *
 * @author Lars Helge Overland
 */
public enum Direction
{
    ASC( "asc" ),
    DESC( "desc" );

    private String value;

    Direction( String value )
    {
        this.value = value;
    }

    public static Direction fromValue( String value )
    {
        for ( Direction direction : Direction.values() )
        {
            if ( direction.value().equals( value ) )
            {
                return direction;
            }
        }

        throw new IllegalArgumentException( String.format( "No enum for value: '%s'", value ) );
    }

    public String value()
    {
        return value;
    }
}
