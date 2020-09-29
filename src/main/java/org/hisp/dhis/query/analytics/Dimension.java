package org.hisp.dhis.query.analytics;

import java.util.ArrayList;
import java.util.Arrays;
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

    public Dimension( String dimension, String... items )
    {
        this.dimension = dimension;
        this.items = new ArrayList<>( Arrays.asList( items ) );
    }

    public String getDimensionValue()
    {
        return dimension.concat( ":" ).concat( StringUtils.join( items, ";" ) );
    }
}
