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
}
