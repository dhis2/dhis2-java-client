package org.hisp.dhis.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
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

    @JsonProperty
    private List<DataElementGroup> dataElementGroups = new ArrayList<>();
}
