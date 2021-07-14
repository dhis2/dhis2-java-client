package org.hisp.dhis.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@ToString
public class IdentifiableObject
{
    @JsonProperty
    protected String id;

    @JsonProperty
    protected String code;

    @JsonProperty
    protected String name;

    @JsonProperty
    @JsonFormat( shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss" )
    protected Date created;

    @JsonProperty
    @JsonFormat( shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss" )
    protected Date lastUpdated;

    @JsonProperty
    protected Set<AttributeValue> attributeValues = new HashSet<>();

    // -------------------------------------------------------------------------
    // Loigc methods
    // -------------------------------------------------------------------------

    public boolean addAttributeValue( AttributeValue attributeValue )
    {
        return this.attributeValues.add( attributeValue );
    }

    public void clearAttributeValues()
    {
        this.attributeValues.clear();
    }

    // -------------------------------------------------------------------------
    // hashCode, equals, toString
    // -------------------------------------------------------------------------

    @Override
    public int hashCode()
    {
        return Objects.hash( getId() );
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

        return Objects.equals( getId(), other.getId() );
    }
}
