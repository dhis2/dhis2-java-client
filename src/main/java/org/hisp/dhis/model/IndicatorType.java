package org.hisp.dhis.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IndicatorType extends NameableObject
{
    @JsonProperty
    private Integer factor;

    @JsonProperty
    private Boolean number;
}
