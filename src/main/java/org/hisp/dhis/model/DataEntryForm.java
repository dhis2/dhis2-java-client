package org.hisp.dhis.model;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    private Style style;
}
