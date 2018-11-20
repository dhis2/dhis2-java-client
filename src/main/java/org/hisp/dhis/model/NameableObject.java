package org.hisp.dhis.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NameableObject
    extends IdentifiableObject
{
    @JsonProperty
    protected String shortName;

    @JsonProperty
    protected String description;

    public String getShortName()
    {
        return shortName;
    }

    public void setShortName( String shortName )
    {
        this.shortName = shortName;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }
}
