package org.hisp.dhis.model;

public class OrgUnitGroup
    extends NameableObject
{
    public OrgUnitGroup()
    {
    }

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
