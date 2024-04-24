package org.hisp.dhis.response.objects;

import static org.hisp.dhis.util.CollectionUtils.notEmpty;

import java.util.ArrayList;
import java.util.List;

import org.hisp.dhis.response.BaseHttpResponse;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.objects.internal.Response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class ObjectsResponse
    extends BaseHttpResponse
{
    @JsonProperty
    private Status status;

    @JsonProperty
    private List<TypeReport> typeReports = new ArrayList<>();

    @JsonProperty
    private ObjectStatistics stats;

    /**
     * From DHIS 2.38.
     */
    @JsonProperty
    private Response response;

    public ObjectsResponse( Status status, Integer httpStatusCode, ObjectStatistics stats )
    {
        this.status = status;
        this.httpStatusCode = httpStatusCode;
        this.stats = stats;
    }

    @JsonIgnore
    public TypeReport getTypeReport()
    {
        List<TypeReport> reports = getTypeReports();

        return notEmpty( reports ) ? reports.get( 0 ) : new TypeReport();
    }

    private boolean hasResponse()
    {
        return response != null;
    }

    @Override
    public String toString()
    {
        return new StringBuilder( "[" )
            .append( "status: " ).append( status ).append( ", " )
            .append( "httpStatusCode: " ).append( httpStatusCode ).append( ", " )
            .append( "stats: " ).append( stats ).append( "," )
            .append( "typeReport: " ).append( getTypeReport() ).append( "]" ).toString();
    }

    public Status getStatus()
    {
        return hasResponse() ? response.getStatus() : status;
    }

    public List<TypeReport> getTypeReports()
    {
        return hasResponse() ? response.getTypeReports() : typeReports;
    }

    public ObjectStatistics getStats()
    {
        return hasResponse() ? response.getStats() : stats;
    }
}
