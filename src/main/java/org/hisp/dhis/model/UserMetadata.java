package org.hisp.dhis.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserMetadata
{
    @JsonProperty
    private String id;

    @JsonProperty
    private String name;

    @JsonProperty
    private String displayName;

    @JsonProperty
    private String username;
}
