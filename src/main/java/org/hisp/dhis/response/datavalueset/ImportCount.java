package org.hisp.dhis.response.datavalueset;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImportCount
{
    @JsonProperty
    private int imported;

    @JsonProperty
    private int updated;

    @JsonProperty
    private int ignored;

    @JsonProperty
    private int deleted;

    public ImportCount()
    {
    }

    public int getImported()
    {
        return imported;
    }

    public void setImported( int imported )
    {
        this.imported = imported;
    }

    public int getUpdated()
    {
        return updated;
    }

    public void setUpdated( int updated )
    {
        this.updated = updated;
    }

    public int getIgnored()
    {
        return ignored;
    }

    public void setIgnored( int ignored )
    {
        this.ignored = ignored;
    }

    public int getDeleted()
    {
        return deleted;
    }

    public void setDeleted( int deleted )
    {
        this.deleted = deleted;
    }

    @Override
    public String toString()
    {
        return new StringBuilder( "[")
            .append( "imported: " ).append( imported ).append( ", " )
            .append( "updated: " ).append( updated ).append( ", " )
            .append( "ignored: " ).append( ignored ).append( ", " )
            .append( "deleted" ).append( deleted ).append( "]" ).toString();
    }
}
