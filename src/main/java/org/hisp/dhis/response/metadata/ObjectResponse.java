package org.hisp.dhis.response.metadata;

import org.hisp.dhis.response.Response;
import org.hisp.dhis.response.Status;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ObjectResponse
    extends Response
{
    @JsonProperty
    protected ObjectReport response;

    public ObjectResponse( Status status, Integer httpStatusCode, String message )
    {
        super( status, httpStatusCode, message );
    }

    @Override
    public String toString()
    {
        return new StringBuilder( "[")
            .append( "status: " ).append( status ).append( ", " )
            .append( "code: " ).append( code ).append( ", " )
            .append( "httpStatusCode: " ).append( httpStatusCode ).append( ", " )
            .append( "devMessage: " ).append( devMessage ).append( ", " )
            .append( "response:" ).append( response ).append( "]" ).toString();
    }
}
