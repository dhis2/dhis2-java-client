package org.hisp.dhis.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    private List<LegendSet> legendSets = new ArrayList<>();

    @JsonProperty
    private OptionSet optionSet;

    @JsonIgnore
    public boolean hasOptionSet()
    {
        return optionSet != null;
    }
}
