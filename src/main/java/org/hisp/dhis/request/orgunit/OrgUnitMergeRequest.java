package org.hisp.dhis.request.orgunit;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@Accessors( chain = true )
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

    public OrgUnitMergeRequest addSource( String source )
    {
        this.sources.add( source );
        return this;
    }
}
