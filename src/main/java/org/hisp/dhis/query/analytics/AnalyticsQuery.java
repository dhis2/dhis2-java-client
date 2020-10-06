package org.hisp.dhis.query.analytics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hisp.dhis.model.AggregationType;
import org.hisp.dhis.model.IdScheme;

import lombok.Getter;

@Getter
public class AnalyticsQuery
{
    private List<Dimension> dimensions = new ArrayList<Dimension>();

    private List<Dimension> filters = new ArrayList<Dimension>();

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

    public AnalyticsQuery withAggregationType( AggregationType aggregationType )
    {
        this.aggregationType = aggregationType;
        return this;
    }

    public AnalyticsQuery withStartDate( String startDate )
    {
        this.startDate = startDate;
        return this;
    }

    public AnalyticsQuery withEndDate( String endDate )
    {
        this.endDate = endDate;
        return this;
    }

    public AnalyticsQuery withSkipMeta()
    {
        this.skipMeta = true;
        return this;
    }

    public AnalyticsQuery withSkipData()
    {
        this.skipData = true;
        return this;
    }

    public AnalyticsQuery withSkipRounding()
    {
        this.skipRounding = true;
        return this;
    }

    public AnalyticsQuery withIgnoreLimit()
    {
        this.ignoreLimit = true;
        return this;
    }

    public AnalyticsQuery withOutputIdScheme( IdScheme outputIdScheme )
    {
        this.outputIdScheme = outputIdScheme;
        return this;
    }

    public AnalyticsQuery withInputIdScheme( IdScheme inputIdScheme )
    {
        this.inputIdScheme = inputIdScheme;
        return this;
    }
}
