package org.hisp.dhis.model;

import java.util.Objects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@NoArgsConstructor
public class AttributeValue
{
    @JsonProperty
    private Attribute attribute;

    @JsonProperty
    private String value;

    public AttributeValue( Attribute attribute, String value )
    {
        this.attribute = attribute;
        this.value = value;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( attribute.getId() );
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

        final AttributeValue other = (AttributeValue) o;

        return Objects.equals( attribute.getId(), other.getAttribute().getId() );
    }
}
