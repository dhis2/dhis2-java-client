package org.hisp.dhis.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

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
