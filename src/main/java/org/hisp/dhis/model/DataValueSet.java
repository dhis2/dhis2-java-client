package org.hisp.dhis.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    public String getDataSet()
    {
        return dataSet;
    }

    public void setDataSet( String dataSet )
    {
        this.dataSet = dataSet;
    }

    public String getCompleteDate()
    {
        return completeDate;
    }

    public void setCompleteDate( String completeDate )
    {
        this.completeDate = completeDate;
    }

    public String getPeriod()
    {
        return period;
    }

    public void setPeriod( String period )
    {
        this.period = period;
    }

    public String getOrgUnit()
    {
        return orgUnit;
    }

    public void setOrgUnit( String orgUnit )
    {
        this.orgUnit = orgUnit;
    }

    public String getAttributeOptionCombo()
    {
        return attributeOptionCombo;
    }

    public void setAttributeOptionCombo( String attributeOptionCombo )
    {
        this.attributeOptionCombo = attributeOptionCombo;
    }

    public List<DataValue> getDataValues()
    {
        return dataValues;
    }

    public void setDataValues( List<DataValue> dataValues )
    {
        this.dataValues = dataValues;
    }
}
