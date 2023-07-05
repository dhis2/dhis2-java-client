package org.hisp.dhis.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class Category
    extends NameableObject
{
    @JsonProperty
    private DataDimensionType dataDimensionType;

    @JsonProperty
    private Boolean dataDimension;

    @JsonProperty
    private List<CategoryOption> categoryOptions = new ArrayList<>();

    @JsonIgnore
    public boolean isDataDimension()
    {
        return dataDimension;
    }
}
