package org.hisp.dhis.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class DataSet
    extends NameableObject
{
    @JsonProperty
    private String formName;

    @JsonProperty
    private String displayFormName;

    @JsonProperty
    private CategoryCombo categoryCombo;

    @JsonProperty
    private List<OrgUnit> organisationUnits = new ArrayList<>();

    @JsonProperty
    private List<DataSetElement> dataSetElements = new ArrayList<>();

    @JsonProperty
    private List<Section> sections = new ArrayList<>();

    @JsonProperty
    private List<Indicator> indicators = new ArrayList<>();

    @JsonProperty
    private DataApprovalWorkflow workflow;

    @JsonProperty
    private DataEntryForm dataEntryForm;

    @JsonProperty
    private String dimensionItem;

    @JsonProperty
    private Integer openFuturePeriods;

    @JsonProperty
    private Integer expiryDays;

    @JsonProperty
    private Integer openPeriodsAfterCoEndDate;

    @JsonProperty
    private Integer timelyDays;

    @JsonProperty
    private String url;

    @JsonProperty
    private FormType formType;

    @JsonProperty
    private String periodType;

    @JsonProperty
    private Integer version;

    @JsonProperty
    private List<LegendSet> legendSets = new ArrayList<>();

    @JsonProperty
    private DimensionItemType dimensionItemType;

    @JsonProperty
    private AggregationType aggregationType;

    @JsonProperty
    private Boolean favorite;

    @JsonProperty
    private Boolean compulsoryFieldsCompleteOnly;

    @JsonProperty
    private Boolean skipOffline;

    @JsonProperty
    private Boolean validCompleteOnly;

    @JsonProperty
    private Boolean dataElementDecoration;

    @JsonProperty
    private Boolean notifyCompletingUser;

    @JsonProperty
    private Boolean noValueRequiresComment;

    @JsonProperty
    private Boolean fieldCombinationRequired;

    @JsonProperty
    private Boolean mobile;
}
