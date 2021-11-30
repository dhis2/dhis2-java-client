package org.hisp.dhis.model;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class SystemInfo
{
    @JsonProperty
    private String version;

    @JsonProperty
    private String revision;

    @JsonProperty
    private String nodeId;

    @JsonProperty
    private String systemId;

    /**
     * Returns the {@link SystemVersion}.
     *
     * @return the {@link SystemVersion}.
     */
    public SystemVersion getSystemVersion()
    {
        return new SystemVersion( version );
    }
}
