package org.hisp.dhis.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataApprovalWorkflow
    extends NameableObject
{
    @JsonProperty
    private String periodType;

    @JsonProperty
    private List<DataSet> dataSets = new ArrayList<>();

}
