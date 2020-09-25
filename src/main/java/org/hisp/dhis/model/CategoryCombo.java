package org.hisp.dhis.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryCombo
    extends NameableObject
{
    @JsonProperty
    private DataDimensionType dataDimensionType;

    @JsonProperty
    private List<Category> categories;
}
