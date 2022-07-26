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
        return Collections.unmodifiableSet( mutableSet( items ) );
    }

    /**
     * Returns an mutable set containing the given items.
     *
     * @param <T> type.
     * @param items the items.
     * @return an immutable set.
     */
    @SafeVarargs
    public static <T> Set<T> mutableSet( T... items )
    {
        Set<T> set = new HashSet<>();

        for ( T item : items )
        {
            set.add( item );
        }

        return set;
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
        return Collections.unmodifiableList( mutableList( items ) );
    }

    /**
     * Returns an mutable list containing the given items.
     *
     * @param <T> type.
     * @param items the items.
     * @return an immutable list.
     */
    @SafeVarargs
    public static <T> List<T> mutableList( T... items )
    {
        List<T> list = new ArrayList<>();

        for ( T item : items )
        {
            list.add( item );
        }

        return list;
    }
}
