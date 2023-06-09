package org.hisp.dhis.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@NoArgsConstructor
public class DataElementGroup
    extends NameableObject
{
    @JsonProperty
    private List<DataElement> dataElements = new ArrayList<>();

    @JsonProperty
    public List<DataElementGroupSet> groupSets = new ArrayList<>();
}
