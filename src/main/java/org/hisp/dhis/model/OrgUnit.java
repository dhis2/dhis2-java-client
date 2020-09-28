package org.hisp.dhis.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
public class OrgUnit
    extends NameableObject
{
    @Setter
    @JsonProperty
    private OrgUnit parent;

    @Setter
    @JsonProperty
    private Date openingDate;

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
}
