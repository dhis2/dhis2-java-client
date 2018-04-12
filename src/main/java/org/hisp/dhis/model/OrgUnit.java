package org.hisp.dhis.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrgUnit
    extends NameableObject
{
    @JsonProperty
    private OrgUnit parent;

    public OrgUnit()
    {
    }
    
    public OrgUnit( String id, String name )
    {
        this.id = id;
        this.name = name;
    }
    
    public OrgUnit getParent()
    {
        return parent;
    }

    public void setParent( OrgUnit parent )
    {
        this.parent = parent;
    }
}
