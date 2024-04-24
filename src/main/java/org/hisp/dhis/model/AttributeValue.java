package org.hisp.dhis.model;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    /**
     * Indicates whether the value is not null or empty.
     *
     * @return true if the value is not null or empty.
     */
    @JsonIgnore
    public boolean hasValue()
    {
        return StringUtils.isNotEmpty( value );
    }

    /**
     * Indicates whether the value represents a double.
     *
     * @return true if the value represents a double.
     */
    @JsonIgnore
    public boolean isDouble()
    {
        return NumberUtils.isCreatable( value );
    }

    /**
     * Returns the value as a Double, only if the value represents a double.
     * Returns null if not.
     *
     * @return a double value.
     */
    @JsonIgnore
    public Double getDoubleValue()
    {
        return isDouble() ? Double.valueOf( value ) : null;
    }

    /**
     * Indicates whether the value represents an integer.
     *
     * @return true if the value represents an integer.
     */
    @JsonIgnore
    public boolean isInteger()
    {
        return StringUtils.isNumeric( value );
    }

    /**
     * Returns the value as an integer, only if the value represents an integer.
     * Return null if not.
     *
     * @return an integer value.
     */
    @JsonIgnore
    public Integer getIntegerValue()
    {
        return isInteger() ? Integer.valueOf( value ) : null;
    }

    @Override
    public int hashCode()
    {
        return attribute != null ? Objects.hash( attribute.getId() ) : 0;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }

        if ( o == null || attribute == null )
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
