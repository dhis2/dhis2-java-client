package org.hisp.dhis.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TableHook
{
    @JsonProperty
    private String id;
    
    @JsonProperty
    private String name;
    
    @JsonProperty
    private TablePhase phase;
    
    @JsonProperty
    private ResourceTableType resourceTableType;
    
    @JsonProperty
    private AnalyticsTableType analyticsTableType;
    
    @JsonProperty
    private String sql;
    
    public TableHook()
    {
    }
        
    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public TablePhase getPhase()
    {
        return phase;
    }

    public void setPhase( TablePhase phase )
    {
        this.phase = phase;
    }

    public ResourceTableType getResourceTableType()
    {
        return resourceTableType;
    }

    public void setResourceTableType( ResourceTableType resourceTableType )
    {
        this.resourceTableType = resourceTableType;
    }

    public AnalyticsTableType getAnalyticsTableType()
    {
        return analyticsTableType;
    }

    public void setAnalyticsTableType( AnalyticsTableType analyticsTableType )
    {
        this.analyticsTableType = analyticsTableType;
    }

    public String getSql()
    {
        return sql;
    }

    public void setSql( String sql )
    {
        this.sql = sql;
    }
    
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
