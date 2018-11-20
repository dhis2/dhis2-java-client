package org.hisp.dhis.model;

public class OrgUnitLevel
    extends IdentifiableObject
{
    private int level;

    public OrgUnitLevel()
    {
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel( int level )
    {
        this.level = level;
    }
}
