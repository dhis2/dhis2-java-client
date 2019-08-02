package org.hisp.dhis.model;

import java.util.ArrayList;
import java.util.List;

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

    @JsonProperty
    private List<LegendSet> legendSets = new ArrayList<>();

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

    public List<LegendSet> getLegendSets()
    {
        return legendSets;
    }

    public void setLegendSets( List<LegendSet> legendSets )
    {
        this.legendSets = legendSets;
    }
}
