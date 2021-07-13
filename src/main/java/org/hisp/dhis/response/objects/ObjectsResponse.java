package org.hisp.dhis.response.objects;

import org.hisp.dhis.response.BaseHttpResponse;
import org.hisp.dhis.response.Status;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ObjectsResponse
    extends BaseHttpResponse
{
    @JsonProperty
    protected Status status;


}
