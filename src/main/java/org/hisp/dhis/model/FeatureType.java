package org.hisp.dhis.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FeatureType {
    NONE("None"),
    MULTI_POLYGON("MultiPolygon"),
    POLYGON("Polygon"),
    POINT("Point"),
    SYMBOL("Symbol");

    private final String value;
}
