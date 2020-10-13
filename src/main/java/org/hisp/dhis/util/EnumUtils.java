package org.hisp.dhis.util;

public class EnumUtils
{
    public static <E extends Enum<E>> E getEnum( final Class<E> enumClass, final String enumName )
    {
        if ( enumName == null )
        {
            return null;
        }

        try
        {
            return Enum.valueOf( enumClass, enumName );
        }
        catch ( final IllegalArgumentException ex )
        {
            return null;
        }
    }
}
