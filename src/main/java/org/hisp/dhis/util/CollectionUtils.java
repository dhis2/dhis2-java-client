package org.hisp.dhis.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollectionUtils
{
    /**
     * Returns an immutable set containing the given items.
     *
     * @param <T> type.
     * @param items the items.
     * @return an immutable set.
     */
    @SafeVarargs
    public static <T> Set<T> set( T... items )
    {
        Set<T> set = new HashSet<>();

        for ( T item : items )
        {
            set.add( item );
        }

        return Collections.unmodifiableSet( set );
    }

    /**
     * Returns an immutable list containing the given items.
     *
     * @param <T> type.
     * @param items the items.
     * @return an immutable list.
     */
    @SafeVarargs
    public static <T> List<T> list( T... items )
    {
        List<T> list = new ArrayList<>();

        for ( T item : items )
        {
            list.add( item );
        }

        return Collections.unmodifiableList( list );
    }
}
