package org.hisp.dhis.response.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.hisp.dhis.response.Response;
import org.hisp.dhis.response.Stats;
import org.hisp.dhis.response.Status;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class EventResponse
    extends Response
{
    @JsonProperty
    private ValidationReport validationReport;

    @JsonProperty
    private Stats stats;

    public EventResponse( Status status, Integer httpStatusCode, String message )
    {
        super( status, httpStatusCode, message );
    }
}
