package org.hisp.dhis.response.metadata;

import org.hisp.dhis.response.ResponseMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MetadataResponseMessage
    extends ResponseMessage
{
    @JsonProperty
    private ObjectReport response;
}
