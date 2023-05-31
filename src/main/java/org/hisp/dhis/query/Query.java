package org.hisp.dhis.query;

import java.util.ArrayList;
import java.util.List;

/**
 * Metadata query.
 *
 * @author Lars Helge Overland
 */
public class Query
{
    private final List<Filter> filters = new ArrayList<>();

    private Paging paging;

    private Order order;

    private RootJunction rootJunction;

    private boolean expandAssociations = false;

    private Query()
    {
    }

    /**
     * Produces a query instance.
     *
     * @return a {@link Query} instance.
     */
    public static Query instance()
    {
        return new Query();
    }

    /**
     * Adds a filter to this query.
     *
     * @param filter the {@link Filter}.
     * @return this {@link Query}.
     */
    public Query addFilter( Filter filter )
    {
        this.filters.add( filter );
        return this;
    }

    /**
     * Returns filters.
     *
     * @return a list of {@link Filter}.
     */
    public List<Filter> getFilters()
    {
        return filters;
    }

    /**
     * Enables paging for this query.
     *
     * @param page the page to return.
     * @param pageSize the page size to return.
     * @return this {@link Query}.
     */
    public Query setPaging( Integer page, Integer pageSize )
    {
        this.paging = new Paging( page, pageSize );
        return this;
    }

    /**
     * Returns the paging for this query.
     *
     * @return the {@link Paging}.
     */
    public Paging getPaging()
    {
        return paging != null ? paging : new Paging( null, null );
    }

    /**
     * Enables ordering for this query.
     *
     * @param order the {@link Order}.
     * @return this {@link Query}.
     */
    public Query setOrder( Order order )
    {
        this.order = order;
        return this;
    }

    /**
     * Returns the order of this query.
     *
     * @return the {@link Order}.
     */
    public Order getOrder()
    {
        return order != null ? order : new Order( null, null );
    }

    /**
     * Returns the root junction of this query.
     *
     * @return the {@link RootJunction}
     */
    public RootJunction getRootJunction()
    {
        return rootJunction != null ? rootJunction : RootJunction.AND;
    }

    /**
     * Enables expansion of associations, i.e. that all properties of associated
     * objects will be present. Applies to lists of objects only (not single
     * objects).
     *
     * @return this {@link Query}.
     */
    public Query withExpandAssociations()
    {
        this.expandAssociations = true;
        return this;
    }

    /**
     * Determines the logic to use when combining filters.
     *
     * @return this {@link Query}
     */
    public Query withRootJunction( RootJunction rootJunction )
    {
        this.rootJunction = rootJunction;
        return this;
    }

    /**
     * Indicates whether expansion of associations is enabled. Applies to lists
     * of objects only (not single objects).
     *
     * @return true if expansion of associations is enabled, false if not.
     */
    public boolean isExpandAssociations()
    {
        return expandAssociations;
    }
}
