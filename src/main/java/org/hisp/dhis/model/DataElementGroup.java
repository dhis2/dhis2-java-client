package org.hisp.dhis.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DataElementGroup
    extends NameableObject
{
    @JsonProperty
    private List<DataElement> dataElements = new ArrayList<>();

    @JsonProperty
    private List<DataElementGroupSet> groupSets = new ArrayList<>();
}
