package org.hisp.dhis.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Category
    extends NameableObject
{
    @JsonProperty
    private DataDimensionType dataDimensionType;

    public DataDimensionType getDataDimensionType()
    {
        return dataDimensionType;
    }

    public void setDataDimensionType( DataDimensionType dataDimensionType )
    {
        this.dataDimensionType = dataDimensionType;
    }
}
