package org.hisp.dhis.model;

public class Dimension
    extends IdentifiableObject
{
    private String shortName;

    private DimensionType dimensionType;

    public String getShortName()
    {
        return shortName;
    }

    public void setShortName( String shortName )
    {
        this.shortName = shortName;
    }

    public DimensionType getDimensionType()
    {
        return dimensionType;
    }

    public void setDimensionType( DimensionType dimensionType )
    {
        this.dimensionType = dimensionType;
    }
}
