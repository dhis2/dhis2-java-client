package org.hisp.dhis.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Generic utility class for null-safe comparison.
 */
@NoArgsConstructor( access = AccessLevel.PRIVATE )
public class Comparing
{
    /**
     * Compares two objects for order. Returns a negative integer, zero, or a
     * positive integer if the first object is less than, equal to, or greater
     * than the second object. Any of the objects can be null. A null value is
     * considered to be less than a non-null value.
     *
     * @param <T>
     * @param a the first object.
     * @param b the second object.
     * @return an integer value.
     */
    public static <T extends Comparable<T>> int compareTo( T a, T b )
    {
        if ( a == b )
        {
            return 0;
        }

        return a != null ? b != null ? a.compareTo( b ) : 1 : -1;
    }

    /**
     * Compares two objects for order. Returns a negative integer, zero, or a
     * positive integer if the first object is less than, equal to, or greater
     * than the second object. Any of the objects can be null. A null value is
     * considered to be greater than a non-null value.
     *
     * @param <T>
     * @param a the first object.
     * @param b the second object.
     * @return an integer value.
     */
    public static <T extends Comparable<T>> int compareToNullLast( T a, T b )
    {
        if ( a == b )
        {
            return 0;
        }

        return a != null ? b != null ? a.compareTo( b ) : -1 : 1;
    }
}
