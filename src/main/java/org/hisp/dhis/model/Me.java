package org.hisp.dhis.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class Me
    extends IdentifiableObject
{
    @JsonProperty
    private String username;

    @JsonProperty
    private String surname;

    @JsonProperty
    private String firstName;

    @JsonProperty
    private String email;

    @JsonProperty
    private UserSettings settings;

    @JsonProperty
    private Set<String> programs = new HashSet<>();

    @JsonProperty
    private Set<String> dataSets = new HashSet<>();

    @JsonProperty
    private Set<String> authorities = new HashSet<>();

    @JsonProperty
    private Set<OrgUnit> organisationUnits = new HashSet<>();
}
