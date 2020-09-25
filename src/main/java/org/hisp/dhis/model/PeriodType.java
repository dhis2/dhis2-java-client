package org.hisp.dhis.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

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
