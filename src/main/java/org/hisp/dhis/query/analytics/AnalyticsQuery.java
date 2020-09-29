package org.hisp.dhis.query.analytics;

import java.util.ArrayList;
import java.util.List;

import org.hisp.dhis.model.AggregationType;
import org.hisp.dhis.model.IdScheme;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    private IdScheme outputIdScheme;

    private IdScheme inputIdScheme;

    private AnalyticsQuery()
    {
    }
}
