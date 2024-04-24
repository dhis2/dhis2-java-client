package org.hisp.dhis.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataEntryForm
    extends NameableObject
{
    @JsonProperty
    private String htmlCode;

    @JsonProperty
    private Integer format;

    @JsonProperty
    private FormStyle style;
}
