package org.hisp.dhis.response;

import java.util.ArrayList;
import java.util.List;

import org.apache.hc.core5.http.Header;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseHttpResponse
{
    @JsonProperty
    protected Integer httpStatusCode;

    @JsonIgnore
    protected List<Header> headers = new ArrayList<>();
}
