package org.hisp.dhis.response.job;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class JobInfo
{
    @JsonProperty
    private String id;

    @JsonProperty
    private String name;

    @JsonProperty
    private JobCategory jobType;

    @JsonProperty
    private String relativeNotifierEndpoint;
}
