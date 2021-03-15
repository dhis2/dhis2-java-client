package org.hisp.dhis.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

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
