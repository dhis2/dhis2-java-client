package org.hisp.dhis.model;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class TrackedEntityAttribute
    extends NameableObject
{
    @JsonProperty
    private ValueType valueType;

    @JsonProperty
    private Boolean confidential = false;

    @JsonProperty
    private Boolean unique = false;
}
