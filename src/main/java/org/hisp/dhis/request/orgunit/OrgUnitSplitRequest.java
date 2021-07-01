package org.hisp.dhis.request.orgunit;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class OrgUnitSplitRequest
{
    @JsonProperty
    private String source;

    @JsonProperty
    private List<String> targets = new ArrayList<>();

    @JsonProperty
    private String primaryTarget;

    @JsonProperty
    private Boolean deleteSource;

    public OrgUnitSplitRequest addTarget( String target )
    {
        this.targets.add( target );
        return this;
    }
}
