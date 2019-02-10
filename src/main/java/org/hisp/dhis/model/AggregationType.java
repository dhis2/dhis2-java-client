package org.hisp.dhis.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum AggregationType
{
    SUM,
    AVERAGE,
    AVERAGE_SUM_ORG_UNIT,
    LAST,
    LAST_AVERAGE_ORG_UNIT,
    COUNT,
    STDDEV,
    VARIANCE,
    MIN,
    MAX,
    NONE;

    public static final Set<AggregationType> AVERAGE_TYPES = new HashSet<>( Arrays.asList(
        AVERAGE, AVERAGE_SUM_ORG_UNIT, LAST_AVERAGE_ORG_UNIT ) );

    public static final Set<AggregationType> LAST_TYPES = new HashSet<>( Arrays.asList(
        LAST, LAST_AVERAGE_ORG_UNIT ) );

    public boolean isAverage()
    {
        return AVERAGE_TYPES.contains( this );
    }
}
