package org.hisp.dhis.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

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
