package org.hisp.dhis.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SystemInfo
{
    @JsonProperty
    private String version;

    @JsonProperty
    private String revision;

    public SystemInfo()
    {
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion( String version )
    {
        this.version = version;
    }

    public String getRevision()
    {
        return revision;
    }

    public void setRevision( String revision )
    {
        this.revision = revision;
    }
}
