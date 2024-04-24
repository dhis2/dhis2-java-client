package org.hisp.dhis.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryOptionGroup
    extends NameableObject
{
    @JsonProperty
    private DataDimensionType dataDimensionType;

    @JsonProperty
    private DimensionItemType dimensionItemType;

    @JsonProperty
    private List<CategoryOption> categoryOptions = new ArrayList<>();

    @JsonProperty
    private List<CategoryOptionGroupSet> groupSets = new ArrayList<>();
}
