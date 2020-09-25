package org.hisp.dhis.model;

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

    @JsonIgnore
    public boolean isDataDimension()
    {
        return dataDimension;
    }
}
