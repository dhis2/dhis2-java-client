package org.hisp.dhis.response.datavalueset;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataValueSetResponseMessage
{
    @JsonProperty
    private Status status;

    @JsonProperty
    private String description;

    @JsonProperty
    private ImportCount importCount;

    private List<Conflict> conflicts = new ArrayList<>();

    @JsonIgnore
    private Integer httpStatusCode;

    @JsonIgnore
    private HttpHeaders headers;

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
            .append( "httpStatusCode: " ).append( httpStatusCode ).append( ", " )
            .append( "headers: " ).append( headers ).append( "]" ).toString();
    }
}
