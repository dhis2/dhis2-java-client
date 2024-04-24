package org.hisp.dhis.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrgUnitGroup
    extends NameableObject
{
    @JsonProperty( "organisationUnits" )
    private List<OrgUnit> orgUnits = new ArrayList<>();

    public OrgUnitGroup( String id, String name )
    {
        this.id = id;
        this.name = name;
    }

    public OrgUnitGroup( String id, String name, String shortName )
    {
        this( id, name );
        this.shortName = shortName;
    }
}
