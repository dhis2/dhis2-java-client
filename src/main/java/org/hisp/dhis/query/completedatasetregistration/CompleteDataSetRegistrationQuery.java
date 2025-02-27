package org.hisp.dhis.query.completedatasetregistration;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hisp.dhis.model.IdScheme;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
public class CompleteDataSetRegistrationQuery {
    /**
     * Data set identifier, multiple data sets are allowed
     */
    private final Set<String> dataSets = new HashSet<>();

    /**
     * Period identifier in ISO format. Multiple periods are allowed.
     */
    private final Set<String> periods = new HashSet<>();

    /**
     * Start date for the time span of the values to export
     */
    private String startDate;

    /**
     * End date for the time span of the values to export
     */
    private String endDate;

    /**
     * Include only registrations which were created since the given timestamp
     */
    private String created;

    /**
     * Include only registrations which were created within the given duration.
     * The format is <value><time-unit>, where the supported time units are "d", "h", "m", "s"
     * (days, hours, minutes, seconds). The time unit is relative to the current time.
     */
    private String createdDuration;

    /**
     * Organisation unit identifier, can be specified multiple times.
     * Not applicable if orgUnitGroup is given.
     */
    private final Set<String> orgUnits = new HashSet<>();

    /**
     * Organisation unit group identifier, can be specified multiple times. Not applicable if orgUnit is given.
     */
    private String orgUnitGroup;

    /**
     * Whether to include the children in the hierarchy of the organisation units
     */
    private Boolean children;

    /**
     * The maximum number of registrations to include in the response
     */
    private Integer limit;

    /**
     * Identifier property used for metadata objects in the response.
     */
    private IdScheme idScheme;

    /**
     * Identifier property used for data sets in the response. Overrides idScheme.
     */
    private IdScheme dataSetIdScheme;

    /**
     * Identifier property used for organisation units in the response. Overrides idScheme.
     */
    private IdScheme orgUnitIdScheme;

    /**
     * attributeOptionComboIdScheme
     */
    private IdScheme attributeOptionComboIdScheme;

    public static CompleteDataSetRegistrationQuery instance() {
        return new CompleteDataSetRegistrationQuery();
    }

    public CompleteDataSetRegistrationQuery addDataSets(Collection<String> dataSets) {
        this.dataSets.addAll(dataSets);
        return this;
    }

    public CompleteDataSetRegistrationQuery addDataSet(String dataSets) {
        this.dataSets.add(dataSets);
        return this;
    }

    public CompleteDataSetRegistrationQuery addOrgUnits(Collection<String> orgUnits) {
        this.orgUnits.addAll(orgUnits);
        return this;
    }

    public CompleteDataSetRegistrationQuery addOrgUnit(String orgUnit) {
        this.orgUnits.add(orgUnit);
        return this;
    }

    public CompleteDataSetRegistrationQuery addPeriods(Collection<String> periods) {
        this.periods.addAll(periods);
        return this;
    }

    public CompleteDataSetRegistrationQuery addPeriod(String period) {
        this.periods.add(period);
        return this;
    }
}
