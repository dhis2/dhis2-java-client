package org.hisp.dhis.query;

import java.util.ArrayList;
import java.util.List;

/**
 * Query.
 */
public class Query
{
    private List<Filter> filters = new ArrayList<>();

    private Paging paging;

    private Order order;

    private Query()
    {
    }

    public static Query instance()
    {
        return new Query();
    }

    public Query addFilter( Filter filter )
    {
        this.filters.add( filter );
        return this;
    }

    public List<Filter> getFilters()
    {
        return filters;
    }

    public Query withPaging( Integer page, Integer pageSize )
    {
        this.paging = new Paging( page, pageSize );
        return this;
    }

    public Paging getPaging()
    {
        return paging != null ? paging : new Paging();
    }

    public Query withOrder( Order order )
    {
        this.order = order;
        return this;
    }

    public Order getOrder()
    {
        return order != null ? order : new Order();
    }
}
