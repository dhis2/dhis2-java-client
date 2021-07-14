package org.hisp.dhis.model;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class OrgUnitLevel
    extends IdentifiableObject
{
    @JsonProperty
    private int level;
}
