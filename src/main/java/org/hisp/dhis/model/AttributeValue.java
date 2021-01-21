package org.hisp.dhis.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
}
