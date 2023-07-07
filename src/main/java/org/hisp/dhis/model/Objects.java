package org.hisp.dhis.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@Accessors( chain = true )
@NoArgsConstructor
public class Objects
{
    @JsonProperty
    private List<DataElement> dataElements = new ArrayList<>();

    @JsonProperty
    private List<DataElementGroup> dataElementGroups = new ArrayList<>();

    @JsonProperty
    private List<OrgUnit> organisationUnits = new ArrayList<>();

    @JsonProperty
    private List<OrgUnitGroup> organisationUnitGroups = new ArrayList<>();

    @JsonProperty
    private List<OrgUnitGroupSet> organisationUnitGroupSets = new ArrayList<>();

    @JsonProperty
    private List<OrgUnitLevel> organisationUnitLevels = new ArrayList<>();

    @JsonProperty
    private List<CategoryOption> categoryOptions = new ArrayList<>();

    @JsonProperty
    private List<Category> categories = new ArrayList<>();

    @JsonProperty
    private List<CategoryCombo> categoryCombos = new ArrayList<>();

    @JsonProperty
    private List<CategoryOptionCombo> categoryOptionCombos = new ArrayList<>();

    @JsonProperty
    private List<OptionSet> optionSets = new ArrayList<>();

    @JsonProperty
    private List<DataElementGroupSet> dataElementGroupSets = new ArrayList<>();

    @JsonProperty
    private List<IndicatorType> indicatorTypes = new ArrayList<>();

    @JsonProperty
    private List<Indicator> indicators = new ArrayList<>();

    @JsonProperty
    private List<DataSet> dataSets = new ArrayList<>();

    @JsonProperty
    private List<Program> programs = new ArrayList<>();

    @JsonProperty
    private List<ProgramIndicator> programIndicators = new ArrayList<>();

    @JsonProperty
    private List<CategoryOptionGroup> categoryOptionGroups = new ArrayList<>();

    @JsonProperty
    private List<CategoryOptionGroupSet> categoryOptionGroupSets = new ArrayList<>();

    @JsonProperty
    private List<TableHook> analyticsTableHooks = new ArrayList<>();

    @JsonProperty
    private List<Dimension> dimensions = new ArrayList<>();

    @JsonProperty
    private List<PeriodType> periodTypes = new ArrayList<>();
}
