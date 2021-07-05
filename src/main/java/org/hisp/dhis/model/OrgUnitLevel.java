package org.hisp.dhis.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrgUnitLevel
    extends IdentifiableObject
{
    @JsonProperty
    private int level;
}
