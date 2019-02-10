package org.hisp.dhis.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataElement
    extends NameableObject
{
    @JsonProperty
    private AggregationType aggregationType;

    @JsonProperty
    private ValueType valueType;

    @JsonProperty
    private DataDomain domainType;

    public DataElement()
    {
    }

    public AggregationType getAggregationType()
    {
        return aggregationType;
    }

    public void setAggregationType( AggregationType aggregationType )
    {
        this.aggregationType = aggregationType;
    }

    public ValueType getValueType()
    {
        return valueType;
    }

    public void setValueType( ValueType valueType )
    {
        this.valueType = valueType;
    }

    public DataDomain getDomainType()
    {
        return domainType;
    }

    public void setDomainType( DataDomain domainType )
    {
        this.domainType = domainType;
    }
}
