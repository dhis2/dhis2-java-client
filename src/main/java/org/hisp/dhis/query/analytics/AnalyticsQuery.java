package org.hisp.dhis.query.analytics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.hisp.dhis.model.AggregationType;
import org.hisp.dhis.model.IdScheme;

@Getter
@Setter
@Accessors( chain = true )
public class AnalyticsQuery
{
    private final List<Dimension> dimensions = new ArrayList<>();

    private final List<Dimension> filters = new ArrayList<>();

    private AggregationType aggregationType;

    private String startDate;

    private String endDate;

    private Boolean skipMeta;

    private Boolean skipData;

    private Boolean skipRounding;

    private Boolean ignoreLimit;

    private IdScheme outputIdScheme;

    private IdScheme inputIdScheme;

    private AnalyticsQuery()
    {
    }

    public static AnalyticsQuery instance()
    {
        return new AnalyticsQuery();
    }

    public AnalyticsQuery addDimension( Dimension dimension )
    {
        this.dimensions.add( dimension );
        return this;
    }

    public AnalyticsQuery addDimension( String dimension, String... items )
    {
        return addDimension( new Dimension( dimension, new ArrayList<>( Arrays.asList( items ) ) ) );
    }

    public AnalyticsQuery addFilter( Dimension filter )
    {
        this.filters.add( filter );
        return this;
    }
}
