package org.hisp.dhis.query.orgunit;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrgUnitMergeRequest
{
    @JsonProperty
    private List<String> sources = new ArrayList<>();

    @JsonProperty
    private String target;

    @JsonProperty
    private DataMergeStrategy dataValueMergeStrategy;

    @JsonProperty
    private DataMergeStrategy dataApprovalMergeStrategy;

    @JsonProperty
    private Boolean deleteSources;
}
