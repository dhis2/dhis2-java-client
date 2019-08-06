package org.hisp.dhis.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Objects
{
    @JsonProperty
    private List<DataElement> dataElements = new ArrayList<>();

    @JsonProperty
    private List<OrgUnit> organisationUnits = new ArrayList<>();

    @JsonProperty
    private List<OrgUnitGroup> organisationUnitGroups = new ArrayList<>();

    @JsonProperty
    private List<OrgUnitGroupSet> organisationUnitGroupSets = new ArrayList<>();

    @JsonProperty
    private List<OrgUnitLevel> organisationUnitLevels = new ArrayList<>();

    @JsonProperty
    private List<Category> categories = new ArrayList<>();

    @JsonProperty
    private List<CategoryCombo> categoryCombos = new ArrayList<>();

    @JsonProperty
    private List<DataElementGroupSet> dataElementGroupSets = new ArrayList<>();

    @JsonProperty
    private List<Program> programs = new ArrayList<>();

    @JsonProperty
    private List<CategoryOptionGroupSet> categoryOptionGroupSets = new ArrayList<>();

    @JsonProperty
    private List<TableHook> analyticsTableHooks = new ArrayList<>();

    @JsonProperty
    private List<Dimension> dimensions = new ArrayList<>();

    @JsonProperty
    private List<PeriodType> periodTypes = new ArrayList<>();

    public List<DataElement> getDataElements()
    {
        return dataElements;
    }

    public void setDataElements( List<DataElement> dataElements )
    {
        this.dataElements = dataElements;
    }

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

    public List<Category> getCategories()
    {
        return categories;
    }

    public void setCategories( List<Category> categories )
    {
        this.categories = categories;
    }

    public List<CategoryCombo> getCategoryCombos()
    {
        return categoryCombos;
    }

    public void setCategoryCombos( List<CategoryCombo> categoryCombos )
    {
        this.categoryCombos = categoryCombos;
    }

    public List<OrgUnitLevel> getOrganisationUnitLevels()
    {
        return organisationUnitLevels;
    }

    public void setOrganisationUnitLevels( List<OrgUnitLevel> organisationUnitLevels )
    {
        this.organisationUnitLevels = organisationUnitLevels;
    }

    public List<DataElementGroupSet> getDataElementGroupSets()
    {
        return dataElementGroupSets;
    }

    public void setDataElementGroupSets( List<DataElementGroupSet> dataElementGroupSets )
    {
        this.dataElementGroupSets = dataElementGroupSets;
    }

    public List<Program> getPrograms()
    {
        return programs;
    }

    public void setPrograms( List<Program> programs )
    {
        this.programs = programs;
    }

    public List<CategoryOptionGroupSet> getCategoryOptionGroupSets()
    {
        return categoryOptionGroupSets;
    }

    public void setCategoryOptionGroupSets( List<CategoryOptionGroupSet> categoryOptionGroupSets )
    {
        this.categoryOptionGroupSets = categoryOptionGroupSets;
    }

    public List<TableHook> getAnalyticsTableHooks()
    {
        return analyticsTableHooks;
    }

    public void setAnalyticsTableHooks( List<TableHook> analyticsTableHooks )
    {
        this.analyticsTableHooks = analyticsTableHooks;
    }

    public List<Dimension> getDimensions()
    {
        return dimensions;
    }

    public void setDimensions( List<Dimension> dimensions )
    {
        this.dimensions = dimensions;
    }

    public List<PeriodType> getPeriodTypes()
    {
        return periodTypes;
    }

    public void setPeriodTypes( List<PeriodType> periodTypes )
    {
        this.periodTypes = periodTypes;
    }
}
