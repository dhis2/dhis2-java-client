package org.hisp.dhis.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

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
