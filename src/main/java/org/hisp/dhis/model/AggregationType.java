package org.hisp.dhis.model;

import java.util.Set;

public enum AggregationType
{
    SUM,
    AVERAGE,
    AVERAGE_SUM_ORG_UNIT,
    LAST,
    LAST_AVERAGE_ORG_UNIT,
    LAST_LAST_ORG_UNIT,
    LAST_IN_PERIOD,
    LAST_IN_PERIOD_AVERAGE_ORG_UNIT,
    FIRST,
    FIRST_AVERAGE_ORG_UNIT,
    FIRST_FIRST_ORG_UNIT,
    COUNT,
    STDDEV,
    VARIANCE,
    MIN,
    MAX,
    MIN_SUM_ORG_UNIT,
    MAX_SUM_ORG_UNIT,
    NONE,
    CUSTOM,
    DEFAULT;

    public static final Set<AggregationType> AVERAGE_TYPES = Set.of(
        AVERAGE, AVERAGE_SUM_ORG_UNIT, LAST_AVERAGE_ORG_UNIT );

    public static final Set<AggregationType> LAST_TYPES = Set.of(
        LAST, LAST_AVERAGE_ORG_UNIT );

    public boolean isAverage()
    {
        return AVERAGE_TYPES.contains( this );
    }
}
