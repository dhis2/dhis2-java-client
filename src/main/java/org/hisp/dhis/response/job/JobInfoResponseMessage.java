package org.hisp.dhis.response.job;

import org.hisp.dhis.response.ResponseMessage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JobInfoResponseMessage
    extends ResponseMessage
{
    @JsonProperty
    private JobInfo response;

    public JobInfo getResponse()
    {
        return response;
    }

    public void setResponse( JobInfo response )
    {
        this.response = response;
    }
}
