package org.hisp.dhis.model;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

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
