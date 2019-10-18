package org.hisp.dhis.response.datavalueset;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    public Status getStatus()
    {
        return status;
    }

    public void setStatus( Status status )
    {
        this.status = status;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public ImportCount getImportCount()
    {
        return importCount;
    }

    public void setImportCount( ImportCount importCount )
    {
        this.importCount = importCount;
    }

    public List<Conflict> getConflicts()
    {
        return conflicts;
    }

    public void setConflicts( List<Conflict> conflicts )
    {
        this.conflicts = conflicts;
    }

    public Integer getHttpStatusCode()
    {
        return httpStatusCode;
    }

    public void setHttpStatusCode( Integer httpStatusCode )
    {
        this.httpStatusCode = httpStatusCode;
    }

    public HttpHeaders getHeaders()
    {
        return headers;
    }

    public void setHeaders( HttpHeaders headers )
    {
        this.headers = headers;
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
