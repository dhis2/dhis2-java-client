package org.hisp.dhis.model;

import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;

@NoArgsConstructor
public class DataElementGroupSet
    extends NameableObject
{
    @JsonProperty
    private Boolean compulsory;

    @JsonProperty
    private Boolean dataDimension;

    @JsonProperty
    private DataDimensionType dataDimensionType;
}
