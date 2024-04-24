package org.hisp.dhis.query.analytics;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Dimension
{
    public static final String DIMENSION_DX = "dx";

    public static final String DIMENSION_PE = "pe";

    public static final String DIMENSION_OU = "ou";

    private final String dimension;

    private final List<String> items;

    public String getDimensionValue()
    {
        return dimension.concat( ":" ).concat( StringUtils.join( items, ";" ) );
    }
}
