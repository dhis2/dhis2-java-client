package org.hisp.dhis.response.objects;

import java.util.ArrayList;
import java.util.List;

import org.hisp.dhis.response.BaseHttpResponse;
import org.hisp.dhis.response.Status;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObjectsResponse
    extends BaseHttpResponse
{
    @JsonProperty
    private Status status;

    @JsonProperty
    private List<TypeReport> typeReports = new ArrayList<>();

    @JsonProperty
    private ObjectStatistics stats;

    @JsonIgnore
    public TypeReport getTypeReport()
    {
         return typeReports != null && !typeReports.isEmpty() ?
             typeReports.get( 0 ) : new TypeReport();
    }

    @Override
    public String toString()
    {
        return new StringBuilder( "[")
            .append( "status: " ).append( status ).append( ", " )
            .append( "httpStatusCode: " ).append( httpStatusCode ).append( ", " )
            .append( "stats: " ).append( stats ).append( "]" ).toString();
    }
}
