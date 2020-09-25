package org.hisp.dhis.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrgUnitGroupSet
    extends NameableObject
{
    @JsonProperty
    private Set<OrgUnitGroup> organisationUnitGroups = new HashSet<>();

    public OrgUnitGroupSet()
    {
    }

    public OrgUnitGroupSet( String id, String name )
    {
        this.id = id;
        this.name = name;
    }

    public OrgUnitGroupSet( String id, String name, String shortName )
    {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
    }
}
