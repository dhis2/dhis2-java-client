package org.hisp.dhis.query;

import lombok.Getter;

import org.apache.commons.lang3.Validate;

/**
 * Query paging.
 *
 * @author Lars Helge Overland
 */
@Getter
public class Paging
{
    private static final int DEFAULT_PAGE_SIZE = 50;

    private static final int DEFAULT_PAGE = 1;

    private final Integer page;

    private final Integer pageSize;

    /**
     * Constructor.
     *
     * @param page the page number, starting on 1, can be null, cannot be
     *        negative.
     * @param pageSize the page size, can be null, cannot be negative.
     */
    public Paging( Integer page, Integer pageSize )
    {
        Validate.isTrue( !(page != null && page < 1), "Page must be greater than zero if specified" );
        Validate.isTrue( !(pageSize != null && pageSize < 1), "Page size must be greater than zero if specified" );
        this.page = page;
        this.pageSize = pageSize;
    }

    public boolean hasPage()
    {
        return page != null && page >= 0;
    }

    public int getPageOrDefault()
    {
        return hasPage() ? page : DEFAULT_PAGE;
    }

    public int getPageSizeOrDefault()
    {
        return hasPageSize() ? pageSize : DEFAULT_PAGE_SIZE;
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
        return getPageSizeOrDefault() * (getPageOrDefault() - 1);
    }
}
