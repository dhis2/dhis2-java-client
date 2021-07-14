package org.hisp.dhis.model;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class PeriodType
{
    @JsonProperty
    private String name;

    @JsonProperty
    private Integer frequencyOrder;

    @JsonProperty
    private String isoDuration;

    @JsonProperty
    private String isoFormat;
}
