package org.hisp.dhis.response.datavalueset;

import java.util.ArrayList;
import java.util.List;

import org.hisp.dhis.response.BaseHttpResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataValueSetResponse
    extends BaseHttpResponse
{
    @JsonProperty
    private Status status;

    @JsonProperty
    private String description;

    @JsonProperty
    private ImportCount importCount;

    @JsonProperty
    private List<Conflict> conflicts = new ArrayList<>();

    @JsonProperty
    private String dataSetComplete;

    public DataValueSetResponse()
    {
    }

    @Override
    public String toString()
    {
        return new StringBuilder( "[")
            .append( "status: " ).append( status ).append( ", " )
            .append( "description: " ).append( description ).append( ", " )
            .append( "importCount: " ).append( importCount ).append( ", " )
            .append( "conflicts: " ).append( conflicts ).append( ", " )
            .append( "dataSetComplete: " ).append( dataSetComplete ).append( ", " )
            .append( "httpStatusCode: " ).append( httpStatusCode ).append( "]" ).toString();
    }
}
