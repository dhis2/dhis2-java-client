package org.hisp.dhis.response.job;

import org.hisp.dhis.response.ResponseMessage;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobInfoResponseMessage
    extends ResponseMessage
{
    @JsonProperty
    private JobInfo response;
}
