package org.hisp.dhis.response.job;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    public JobInfo()
    {
    }

    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public JobCategory getJobType()
    {
        return jobType;
    }

    public void setJobType( JobCategory jobType )
    {
        this.jobType = jobType;
    }

    public String getRelativeNotifierEndpoint()
    {
        return relativeNotifierEndpoint;
    }

    public void setRelativeNotifierEndpoint( String relativeNotifierEndpoint )
    {
        this.relativeNotifierEndpoint = relativeNotifierEndpoint;
    }
}
