package org.hisp.dhis.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataElement
    extends NameableObject
{
    @JsonProperty
    private ValueType valueType;

    @JsonProperty
    private DataDomain domainType;

    public DataElement()
    {
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
