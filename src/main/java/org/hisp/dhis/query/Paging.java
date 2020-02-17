package org.hisp.dhis.query;

import org.springframework.util.Assert;

/**
 * Query paging.
 *
 * @author Lars Helge Overland
 */
public class Paging
{
    private static final int DEFAULT_PAGE_SIZE = 50;
    private static final int DEFAULT_PAGE = 1;

    private Integer page;

    private Integer pageSize;

    public Paging()
    {
    }

    /**
     * Constructor.
     *
     * @param page the page number, starting on 1, can be null, cannot be negative.
     * @param pageSize the page size, can be null, cannot be negative.
     */
    public Paging( Integer page, Integer pageSize )
    {
        Assert.isTrue( !( page != null && page < 1 ), "Page must be greater than zero if specified" );
        Assert.isTrue( !( pageSize != null && pageSize < 1 ), "Page size must be greater than zero if specified" );
        this.page = page;
        this.pageSize = pageSize;
    }

    public Integer getPage()
    {
        return page;
    }

    public Integer getPageSize()
    {
        return pageSize;
    }

    public boolean hasPage()
    {
        return page != null && page >= 0;
    }

    public boolean hasPageSize()
    {
        return pageSize != null && pageSize >= 0;
    }

    public boolean hasPaging()
    {
        return hasPage() || hasPageSize();
    }

    public int getOffset()
    {
        int pgs = hasPageSize() ? pageSize : DEFAULT_PAGE_SIZE;
        int pg = hasPage() ? page : DEFAULT_PAGE;
        return pgs * ( pg - 1 );
    }
}
