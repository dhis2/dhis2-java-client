package org.hisp.dhis.response.datavalueset;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.hisp.dhis.response.HttpResponseMessage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataValueSetResponseMessage
    implements HttpResponseMessage
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

    @JsonIgnore
    private Integer httpStatusCode;

    @JsonIgnore
    private List<Header> headers = new ArrayList<>();

    public DataValueSetResponseMessage()
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
            .append( "dataSetComplete" ).append( dataSetComplete ).append( ", " )
            .append( "httpStatusCode: " ).append( httpStatusCode ).append( "]" ).toString();
    }
}
