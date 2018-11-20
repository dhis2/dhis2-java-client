package org.hisp.dhis.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Objects
{
    @JsonProperty
    private List<OrgUnit> organisationUnits = new ArrayList<>();

    @JsonProperty
    private List<OrgUnitGroup> organisationUnitGroups = new ArrayList<>();

    @JsonProperty
    private List<OrgUnitGroupSet> organisationUnitGroupSets = new ArrayList<>();

    @JsonProperty
    private List<OrgUnitLevel> organisationUnitLevels = new ArrayList<>();

    @JsonProperty
    private List<TableHook> analyticsTableHooks = new ArrayList<>();

    public List<OrgUnit> getOrganisationUnits()
    {
        return organisationUnits;
    }

    public void setOrganisationUnits( List<OrgUnit> organisationUnits )
    {
        this.organisationUnits = organisationUnits;
    }

    public List<OrgUnitGroup> getOrganisationUnitGroups()
    {
        return organisationUnitGroups;
    }

    public void setOrganisationUnitGroups( List<OrgUnitGroup> organisationUnitGroups )
    {
        this.organisationUnitGroups = organisationUnitGroups;
    }

    public List<OrgUnitGroupSet> getOrganisationUnitGroupSets()
    {
        return organisationUnitGroupSets;
    }

    public void setOrganisationUnitGroupSets( List<OrgUnitGroupSet> organisationUnitGroupSets )
    {
        this.organisationUnitGroupSets = organisationUnitGroupSets;
    }

    public List<OrgUnitLevel> getOrganisationUnitLevels()
    {
        return organisationUnitLevels;
    }

    public void setOrganisationUnitLevels( List<OrgUnitLevel> organisationUnitLevels )
    {
        this.organisationUnitLevels = organisationUnitLevels;
    }

    public List<TableHook> getAnalyticsTableHooks()
    {
        return analyticsTableHooks;
    }

    public void setAnalyticsTableHooks( List<TableHook> analyticsTableHooks )
    {
        this.analyticsTableHooks = analyticsTableHooks;
    }
}
