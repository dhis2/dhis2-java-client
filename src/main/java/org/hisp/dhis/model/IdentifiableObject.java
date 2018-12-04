package org.hisp.dhis.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IdentifiableObject
{
    @JsonProperty
    protected String id;

    @JsonProperty
    protected String code;

    @JsonProperty
    protected String name;

    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss")
    protected Date created;

    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss")
    protected Date lastUpdated;

    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode( String code )
    {
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated( Date created )
    {
        this.created = created;
    }

    public Date getLastUpdated()
    {
        return lastUpdated;
    }

    public void setLastUpdated( Date lastUpdated )
    {
        this.lastUpdated = lastUpdated;
    }

    // -------------------------------------------------------------------------
    // hashCode, equals, toString
    // -------------------------------------------------------------------------

    @Override
    public int hashCode()
    {
        return 31 * getId().hashCode();
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }

        if ( o == null )
        {
            return false;
        }

        if ( !getClass().isAssignableFrom( o.getClass() ) )
        {
            return false;
        }

        final IdentifiableObject other = (IdentifiableObject) o;

        return getId().equals( other.getId() );
    }

    @Override
    public String toString()
    {
        return new StringBuilder( "[")
            .append( "id: " ).append( id ).append( ", " )
            .append( "name" ).append( name ).append( "]" ).toString();
    }
}
