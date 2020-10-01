package org.hisp.dhis.query.analytics;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dimension
{
    public static final String DIMENSION_DX = "dx";
    public static final String DIMENSION_PE = "pe";
    public static final String DIMENSION_OU = "ou";

    private String dimension;

    private List<String> items;

    public Dimension()
    {
    }

    public Dimension( String dimension, List<String> items )
    {
        this.dimension = dimension;
        this.items = items;
    }

    public String getDimensionValue()
    {
        return dimension.concat( ":" ).concat( StringUtils.join( items, ";" ) );
    }
}
