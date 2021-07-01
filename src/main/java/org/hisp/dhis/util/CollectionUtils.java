package org.hisp.dhis.util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollectionUtils
{
    @SafeVarargs
    public static <T> Set<T> newImmutableSet( T... items )
    {
        Set<T> set = new HashSet<>();

        for ( T item : items )
        {
            set.add( item );
        }

        return Collections.unmodifiableSet( set );
    }

    @SafeVarargs
    public static <T> List<T> newImmutableList( T... items )
    {
        List<T> list = new ArrayList<>();

        for ( T item : items )
        {
            list.add( item );
        }

        return Collections.unmodifiableList( list );
    }
}
