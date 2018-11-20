package org.hisp.dhis.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrgUnit
    extends NameableObject
{
    @JsonProperty
    private OrgUnit parent;

    @JsonProperty
    private String path;

    @JsonProperty
    private Integer level;

    public OrgUnit()
    {
    }

    public OrgUnit( String id, String name )
    {
        this.id = id;
        this.name = name;
    }

    public OrgUnit( String id, String name, String shortName )
    {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
    }

    public OrgUnit getParent()
    {
        return parent;
    }

    public void setParent( OrgUnit parent )
    {
        this.parent = parent;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath( String path )
    {
        this.path = path;
    }

    public Integer getLevel()
    {
        return level;
    }

    public void setLevel( Integer level )
    {
        this.level = level;
    }
}
