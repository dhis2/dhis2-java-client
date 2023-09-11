package org.hisp.dhis.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum ValueType
{
    TEXT,
    LONG_TEXT,
    MULTI_TEXT,
    LETTER,
    PHONE_NUMBER,
    EMAIL,
    BOOLEAN,
    TRUE_ONLY,
    DATE,
    DATETIME,
    TIME,
    NUMBER,
    UNIT_INTERVAL,
    PERCENTAGE,
    INTEGER,
    INTEGER_POSITIVE,
    INTEGER_NEGATIVE,
    INTEGER_ZERO_OR_POSITIVE,
    TRACKER_ASSOCIATE,
    USERNAME,
    COORDINATE,
    ORGANISATION_UNIT,
    REFERENCE,
    AGE,
    URL,
    FILE_RESOURCE,
    IMAGE,
    GEOJSON;

    public static final Set<ValueType> INTEGER_TYPES = new HashSet<>( Arrays.asList(
        INTEGER, INTEGER_POSITIVE, INTEGER_NEGATIVE, INTEGER_ZERO_OR_POSITIVE ) );

    public static final Set<ValueType> DECIMAL_TYPES = new HashSet<>( Arrays.asList(
        NUMBER, UNIT_INTERVAL, PERCENTAGE ) );

    public static final Set<ValueType> BOOLEAN_TYPES = new HashSet<>( Arrays.asList(
        BOOLEAN, TRUE_ONLY ) );

    public static final Set<ValueType> TEXT_TYPES = new HashSet<>( Arrays.asList(
        TEXT, LONG_TEXT, LETTER, TIME, USERNAME, EMAIL, PHONE_NUMBER, URL ) );

    public static final Set<ValueType> DATE_TYPES = new HashSet<>( Arrays.asList(
        DATE, DATETIME, AGE ) );

    public static final Set<ValueType> FILE_TYPES = new HashSet<>( Arrays.asList(
        FILE_RESOURCE, IMAGE ) );

    public static final Set<ValueType> GEO_TYPES = new HashSet<>( Arrays.asList(
        COORDINATE ) );

    public static final Set<ValueType> NUMERIC_TYPES = new HashSet<>( Arrays.asList(
        INTEGER, INTEGER_POSITIVE, INTEGER_NEGATIVE, INTEGER_ZERO_OR_POSITIVE,
        NUMBER, UNIT_INTERVAL, PERCENTAGE ) );

    public boolean isInteger()
    {
        return INTEGER_TYPES.contains( this );
    }

    public boolean isDecimal()
    {
        return DECIMAL_TYPES.contains( this );
    }

    public boolean isBoolean()
    {
        return BOOLEAN_TYPES.contains( this );
    }

    public boolean isText()
    {
        return TEXT_TYPES.contains( this );
    }

    public boolean isDate()
    {
        return DATE_TYPES.contains( this );
    }

    public boolean isFile()
    {
        return FILE_TYPES.contains( this );
    }

    public boolean isGeo()
    {
        return GEO_TYPES.contains( this );
    }

    public boolean isOrganisationUnit()
    {
        return ORGANISATION_UNIT == this;
    }

    public boolean isNumeric()
    {
        return NUMERIC_TYPES.contains( this );
    }
}
