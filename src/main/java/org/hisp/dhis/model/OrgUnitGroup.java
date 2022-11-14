package org.hisp.dhis.model;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OrgUnitGroup
    extends NameableObject
{
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
