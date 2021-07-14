package org.hisp.dhis.model;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class TableHook
    extends IdentifiableObject
{
    @JsonProperty
    private TablePhase phase;

    @JsonProperty
    private ResourceTableType resourceTableType;

    @JsonProperty
    private AnalyticsTableType analyticsTableType;

    @JsonProperty
    private String sql;

    public enum TablePhase
    {
        RESOURCE_TABLE_POPULATED,
        ANALYTICS_TABLE_POPULATED
    }

    public enum ResourceTableType
    {
        ORG_UNIT_STRUCTURE,
        DATA_SET_ORG_UNIT_CATEGORY,
        CATEGORY_OPTION_COMBO_NAME,
        DATA_ELEMENT_GROUP_SET_STRUCTURE,
        INDICATOR_GROUP_SET_STRUCTURE,
        ORG_UNIT_GROUP_SET_STRUCTURE,
        CATEGORY_STRUCTURE,
        DATA_ELEMENT_STRUCTURE,
        PERIOD_STRUCTURE,
        DATE_PERIOD_STRUCTURE,
        DATA_ELEMENT_CATEGORY_OPTION_COMBO,
        DATA_APPROVAL_MIN_LEVEL;
    }

    public enum AnalyticsTableType
    {
        DATA_VALUE,
        COMPLETENESS,
        COMPLETENESS_TARGET,
        ORG_UNIT_TARGET,
        EVENT,
        ENROLLMENT,
        VALIDATION_RESULT;
    }
}
