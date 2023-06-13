package org.hisp.dhis.model;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class DataSetElement
{
    @JsonProperty
    private CategoryCombo categoryCombo;

    @JsonProperty
    private DataSet dataSet;

    @JsonProperty
    private DataElement dataElement;
}
