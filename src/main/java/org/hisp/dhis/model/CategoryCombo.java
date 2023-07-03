package org.hisp.dhis.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class CategoryCombo
    extends NameableObject
{
    @JsonProperty
    private DataDimensionType dataDimensionType;

    @JsonProperty
    private Boolean skipTotal;

    @JsonProperty
    private List<Category> categories;

    @JsonProperty
    private List<CategoryOptionCombo> categoryOptionCombos;
}
