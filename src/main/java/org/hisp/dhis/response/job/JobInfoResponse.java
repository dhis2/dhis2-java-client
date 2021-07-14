package org.hisp.dhis.response.job;

import lombok.Getter;
import lombok.Setter;

import org.hisp.dhis.response.Response;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class JobInfoResponse
    extends Response
{
    @JsonProperty
    private JobInfo response;
}
