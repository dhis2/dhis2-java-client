package org.hisp.dhis.model;

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
}
