package org.hisp.dhis.model;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class IndicatorType extends NameableObject
{
    @JsonProperty
    private Integer factor;

    @JsonProperty
    private Boolean number;
}
