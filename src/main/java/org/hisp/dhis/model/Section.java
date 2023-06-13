package org.hisp.dhis.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Section
    extends NameableObject
{
    @JsonProperty
    private DataSet dataSet;

    @JsonProperty
    private List<DataElement> dataElements = new ArrayList<>();

    @JsonProperty
    private List<Indicator> indicators = new ArrayList<>();
}
