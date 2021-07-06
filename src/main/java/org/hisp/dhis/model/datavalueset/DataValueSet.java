package org.hisp.dhis.model.datavalueset;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain=true)
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

    @Override
    public String toString()
    {
        return new StringBuilder( "[" )
            .append( "dataSet: " ).append( dataSet ).append( ", " )
            .append( "completeDate: " ).append( completeDate ).append( ", " )
            .append( "period: " ).append( period ).append( ", " )
            .append( "orgUnit: " ).append( orgUnit ).append( ", " )
            .append( "attributeOptionCombo: " ).append( attributeOptionCombo ).append( ", " )
            .toString();
    }
}
