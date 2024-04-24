package org.hisp.dhis.model.datavalueset;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Accessors( chain = true )
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

    @ToString.Exclude
    @JsonProperty
    private List<DataValue> dataValues = new ArrayList<>();

    public boolean addDataValue( DataValue dataValue )
    {
        return this.dataValues.add( dataValue );
    }
}
