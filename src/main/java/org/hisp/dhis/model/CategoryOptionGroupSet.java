package org.hisp.dhis.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

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
