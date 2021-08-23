package org.hisp.dhis.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class OrgUnit
    extends NameableObject
{
    @JsonProperty
    private String path;

    @JsonProperty
    private Integer level;

    @Setter
    @JsonProperty
    private OrgUnit parent;

    @Setter
    @JsonProperty
    private Date openingDate;

    @Setter
    @JsonProperty
    private Date closedDate;

    @Setter
    @JsonProperty
    private String comment;

    @Setter
    @JsonProperty
    private String url;

    @Setter
    @JsonProperty
    private String contactPerson;

    @Setter
    @JsonProperty
    private String address;

    @Setter
    @JsonProperty
    private String email;

    @Setter
    @JsonProperty
    private String phoneNumber;

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
        this( id, name );
        this.shortName = shortName;
    }

    public OrgUnit( String id, String name, String shortName, OrgUnit parent )
    {
        this( id, name, shortName );
        this.parent = parent;
    }

    public OrgUnit( String id, String name, String shortName, OrgUnit parent, Date openingDate )
    {
        this( id, name, shortName, parent );
        this.openingDate = openingDate;
    }
}
