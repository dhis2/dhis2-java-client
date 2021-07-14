package org.hisp.dhis.model;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class NameableObject
    extends IdentifiableObject
{
    @JsonProperty
    protected String shortName;

    @JsonProperty
    protected String description;
}
