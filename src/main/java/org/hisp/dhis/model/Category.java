package org.hisp.dhis.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

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
