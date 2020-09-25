package org.hisp.dhis.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

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
