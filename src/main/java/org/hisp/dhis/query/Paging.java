package org.hisp.dhis.query;

/**
 * Query paging.
 */
public class Paging
{
    private Integer page;
    
    private Integer pageSize;
    
    public Paging()
    {
    }
    
    public Paging( Integer page, Integer pageSize )
    {
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
}
