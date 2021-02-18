package org.hisp.dhis.response.metadata;

import org.hisp.dhis.response.ResponseMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetadataResponseMessage
    extends ResponseMessage
{
    @JsonProperty
    private ObjectReport response;

    @Override
    public String toString()
    {
        return new StringBuilder( "[")
            .append( "status: " ).append( status ).append( ", " )
            .append( "code: " ).append( code ).append( ", " )
            .append( "httpStatusCode: " ).append( httpStatusCode ).append( ", " )
            .append( "devMessage: " ).append( devMessage ).append( ", " )
            .append( "response." ).append( response ).append( "]" ).toString();
    }
}
