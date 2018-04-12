package org.hisp.dhis.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    
    public Set<OrgUnitGroup> getOrganisationUnitGroups()
    {
        return organisationUnitGroups;
    }

    public void setOrganisationUnitGroups( Set<OrgUnitGroup> organisationUnitGroups )
    {
        this.organisationUnitGroups = organisationUnitGroups;
    }
}
