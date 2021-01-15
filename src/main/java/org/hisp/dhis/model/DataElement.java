package org.hisp.dhis.model;

import java.util.ArrayList;
import java.util.List;

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
    private List<LegendSet> legendSets = new ArrayList<>();
}
