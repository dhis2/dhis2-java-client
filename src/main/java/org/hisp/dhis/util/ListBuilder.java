package org.hisp.dhis.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ListBuilder<T>
{
    private final List<T> list;

    public ListBuilder()
    {
        list = new ArrayList<>();
    }

    public final ListBuilder<T> addAll( List<T> items )
    {
        this.list.addAll( items );
        return this;
    }

    @SafeVarargs
    public final ListBuilder<T> add( T... items )
    {
        this.list.addAll( Arrays.asList( items ) );
        return this;
    }

    public final ListBuilder<T> add( T item )
    {
        this.list.add( item );
        return this;
    }

    public List<T> build()
    {
        return Collections.unmodifiableList( list );
    }
}
