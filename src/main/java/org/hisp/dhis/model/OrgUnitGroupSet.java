package org.hisp.dhis.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrgUnitGroupSet
    extends NameableObject
{
    @JsonProperty
    private Boolean dataDimension;

    @JsonProperty
    private Boolean compulsory;

    @JsonProperty
    private Set<OrgUnitGroup> organisationUnitGroups = new HashSet<>();

    public OrgUnitGroupSet( String id, String name )
    {
        this.id = id;
        this.name = name;
    }

    public OrgUnitGroupSet( String id, String name, String shortName )
    {
        this( id, name );
        this.shortName = shortName;
    }
}
