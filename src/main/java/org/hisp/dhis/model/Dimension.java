package org.hisp.dhis.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dimension
    extends IdentifiableObject
{
    private String shortName;

    private DimensionType dimensionType;
}
