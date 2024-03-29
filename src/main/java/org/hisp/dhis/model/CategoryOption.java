package org.hisp.dhis.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class CategoryOption
    extends NameableObject
{
    @JsonProperty
    private Date startDate;

    @JsonProperty
    private Date endDate;

    @JsonProperty
    private String formName;

    @JsonProperty
    private Set<Category> categories = new HashSet<>();

    @JsonProperty
    private Set<OrgUnit> organisationUnits = new HashSet<>();
}
