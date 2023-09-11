package org.hisp.dhis.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor( access = AccessLevel.PRIVATE )
public class CollectionUtils
{
    /**
     * Returns an immutable set containing the given items. Accepts null items.
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
     * Returns a mutable set containing the given items. Accepts null items.
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
     * Returns an immutable list containing the given items. Accepts null items.
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
     * Returns a mutable list containing the given items. Accepts null items.
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

    /**
     * Converts the given array to an {@link ArrayList}.
     *
     * @param array the array.
     * @param <T> class.
     * @return a list.
     */
    public static <T> List<T> asList( T[] array )
    {
        return new ArrayList<>( Arrays.asList( array ) );
    }

    /**
     * Returns the first matching item in the given collection based on the
     * given predicate. Returns null if no match is found.
     *
     * @param <T> type.
     * @param collection the collection.
     * @param predicate the predicate.
     * @return the first matching item, or null if no match is found.
     */
    public static <T> T firstMatch( Collection<T> collection, Predicate<T> predicate )
    {
        return collection.stream()
            .filter( predicate )
            .findFirst()
            .orElse( null );
    }

    /**
     * Returns a new mutable list of the items in the given collection which
     * match the given predicate.
     *
     * @param <T> type.
     * @param collection the collection.
     * @param predicate the predicate.
     * @return a new mutable list.
     */
    public static <T> List<T> list( Collection<T> collection, Predicate<T> predicate )
    {
        return collection.stream()
            .filter( predicate )
            .collect( Collectors.toList() );
    }
}
