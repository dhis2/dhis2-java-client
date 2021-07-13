package org.hisp.dhis.model;

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
}
