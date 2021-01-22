package org.hisp.dhis.util;
import java.util.Collections;
import java.util.HashSet;
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
}
