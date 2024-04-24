package org.hisp.dhis.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Indicator extends NameableObject
{
    @JsonProperty
    private IndicatorType indicatorType;

    @JsonProperty
    private boolean annualized;

    @JsonProperty
    private String numerator;

    @JsonProperty
    private String numeratorDescription;

    @JsonProperty
    private String denominator;

    @JsonProperty
    private String denominatorDescription;

    @JsonProperty
    private String url;
}
