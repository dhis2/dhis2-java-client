package org.hisp.dhis.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class CategoryOptionGroupSet
    extends NameableObject
{
    @JsonProperty
    private Boolean dataDimension;

    @JsonProperty
    private DataDimensionType dataDimensionType;

    @JsonProperty
    private List<CategoryOptionGroup> categoryOptionGroups = new ArrayList<>();
}
