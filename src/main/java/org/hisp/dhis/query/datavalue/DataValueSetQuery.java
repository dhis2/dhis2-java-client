package org.hisp.dhis.query.datavalue;

import java.util.*;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.hisp.dhis.model.IdScheme;

@Getter
@Setter
@Accessors( chain = true )
public class DataValueSetQuery
{
    private final Set<String> dataSets = new HashSet<>();

    private final Set<String> dataElements = new HashSet<>();

    private final Set<String> dataElementGroups = new HashSet<>();

    private final Set<String> orgUnits = new HashSet<>();

    private final Set<String> orgUnitGroups = new HashSet<>();

    private final Set<String> periods = new HashSet<>();

    private String startDate;

    private String endDate;

    private final Set<String> attributeOptionCombos = new HashSet<>();

    private Boolean children;

    private Boolean includeDeleted;

    private String lastUpdated;

    private String lastUpdatedDuration;

    private Integer limit;

    private IdScheme dataElementIdScheme;

    private IdScheme orgUnitIdScheme;

    private IdScheme categoryOptionComboIdScheme;

    private IdScheme attributeOptionComboIdScheme;

    private IdScheme dataSetIdScheme;

    private IdScheme categoryIdScheme;

    private IdScheme categoryOptionIdScheme;

    private IdScheme idScheme;

    private IdScheme inputOrgUnitIdScheme;

    private IdScheme inputDataSetIdScheme;

    private IdScheme inputDataElementGroupIdScheme;

    private IdScheme inputDataElementIdScheme;

    private IdScheme inputIdScheme;

    public static DataValueSetQuery instance()
    {
        return new DataValueSetQuery();
    }

    public DataValueSetQuery addDataElements( List<String> dataElements )
    {
        this.dataElements.addAll( dataElements );
        return this;
    }

    public DataValueSetQuery addOrgUnits( List<String> orgUnits )
    {
        this.orgUnits.addAll( orgUnits );
        return this;
    }

    public DataValueSetQuery addPeriods( List<String> periods )
    {
        this.periods.addAll( periods );
        return this;
    }

    public DataValueSetQuery addDataSets( List<String> dataSets )
    {
        this.dataSets.addAll( dataSets );
        return this;
    }

    public DataValueSetQuery addDataElementGroups( List<String> dataElementGroups )
    {
        this.dataElementGroups.addAll( dataElementGroups );
        return this;
    }

    public DataValueSetQuery addOrgUnitGroups( List<String> orgUnitGroups )
    {
        this.orgUnitGroups.addAll( orgUnitGroups );
        return this;
    }

    public DataValueSetQuery addAttributeOptionCombos( List<String> attributeOptionCombos )
    {
        this.attributeOptionCombos.addAll( attributeOptionCombos );
        return this;
    }
}
