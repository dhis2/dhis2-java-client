package org.hisp.dhis.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataValueSet
{
    @JsonProperty
    private String dataSet;

    @JsonProperty
    private String completeDate;

    @JsonProperty
    private String period;

    @JsonProperty
    private String orgUnit;

    @JsonProperty
    private String attributeOptionCombo;

    @JsonProperty
    private List<DataValue> dataValues = new ArrayList<>();

    public DataValueSet()
    {
    }

    public boolean addDataValue( DataValue dataValue )
    {
        return this.dataValues.add( dataValue );
    }
}
