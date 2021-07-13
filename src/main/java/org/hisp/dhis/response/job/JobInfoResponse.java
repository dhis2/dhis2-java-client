package org.hisp.dhis.response.job;

import org.hisp.dhis.response.Response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobInfoResponse
    extends Response
{
    @JsonProperty
    private JobInfo response;
}
