package org.hisp.dhis.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
    // Logic methods
    // -------------------------------------------------------------------------

    /**
     * Adds an attribute value.
     *
     * @param attributeValue the {@link AttributeValue}.
     * @return true if this set did not already contain the attribute value.
     */
    public boolean addAttributeValue( AttributeValue attributeValue )
    {
        return attributeValues.add( attributeValue );
    }

    /**
     * Updates an attribute value. Removes an existing attribute value if
     * necessary before adding the given attribute value.
     *
     * @param attributeValue the {@link AttributeValue}.
     */
    public void updateAttributeValue( AttributeValue attributeValue )
    {
        removeAttributeValue( attributeValue.getAttribute().getId() );
        addAttributeValue( attributeValue );
    }

    /**
     * Returns the attribute value with the given attribute identifier.
     *
     * @param attribute the attribute identifier.
     * @return the attribute value with the given attribute identifier, or null.
     */
    public AttributeValue getAttributeValue( String attribute )
    {
        return attributeValues.stream()
            .filter( av -> av.getAttribute().getId().equals( attribute ) )
            .findFirst()
            .orElse( null );
    }

    /**
     * Indicates whether an attribute value exists for the given attribute
     * identifier.
     *
     * @param attribute the attribute identifier.
     * @return true of an attribute value exists.
     */
    public boolean hasAttributeValue( String attribute )
    {
        return attributeValues.stream()
            .anyMatch( av -> av.getAttribute().getId().equals( attribute ) );
    }

    /**
     * Returns the attribute value with the given attribute identifier as a
     * string.
     *
     * @param attribute the attribute identifier.
     * @return the attribute value with the given attribute identifier, or null.
     */
    public String getAttributeValueAsString( String attribute )
    {
        AttributeValue attributeValue = getAttributeValue( attribute );
        return attributeValue != null ? attributeValue.getValue() : null;
    }

    /**
     * Removes the attribute value with the given attribute identifier.
     *
     * @param attribute the attribute identifier.
     * @return true if an attribute value was removed.
     */
    public boolean removeAttributeValue( String attribute )
    {
        return attributeValues.removeIf( av -> av.getAttribute().getId().equals( attribute ) );
    }

    /**
     * Removes all attribute values.
     */
    public void clearAttributeValues()
    {
        attributeValues.clear();
    }

    // -------------------------------------------------------------------------
    // hashCode and equals
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
