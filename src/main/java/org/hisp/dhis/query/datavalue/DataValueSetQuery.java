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

    public DataValueSetQuery addDataElements( String... dataElements )
    {
        this.dataElements.addAll( Arrays.asList( dataElements ) );
        return this;
    }

    public DataValueSetQuery addOrgUnits( String... orgUnits )
    {
        this.orgUnits.addAll( Arrays.asList( orgUnits ) );
        return this;
    }

    public DataValueSetQuery addPeriods( String... periods )
    {
        this.periods.addAll( Arrays.asList( periods ) );
        return this;
    }

    public DataValueSetQuery addDataSets( String... dataSets )
    {
        this.dataSets.addAll( Arrays.asList( dataSets ) );
        return this;
    }

    public DataValueSetQuery addDataElementGroups( String... dataElementGroups )
    {
        this.dataElementGroups.addAll( Arrays.asList( dataElementGroups ) );
        return this;
    }

    public DataValueSetQuery addOrgUnitGroups( String... orgUnitGroups )
    {
        this.orgUnitGroups.addAll( Arrays.asList( orgUnitGroups ) );
        return this;
    }

    public DataValueSetQuery addAttributeOptionCombos( String... attributeOptionCombos )
    {
        this.attributeOptionCombos.addAll( Arrays.asList( attributeOptionCombos ) );
        return this;
    }
}
