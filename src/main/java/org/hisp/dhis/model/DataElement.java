package org.hisp.dhis.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
    private String url;

    @JsonProperty
    private List<LegendSet> legendSets = new ArrayList<>();

    @JsonProperty
    private OptionSet optionSet;

    @JsonProperty
    private List<DataElementGroup> dataElementGroups = new ArrayList<>();

    @JsonProperty
    private List<DataSetElement> dataSetElements = new ArrayList<>();

    @JsonIgnore
    public boolean hasOptionSet()
    {
        return optionSet != null;
    }
}
