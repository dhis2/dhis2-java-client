package org.hisp.dhis.response;

import java.util.List;

import org.apache.http.Header;

public interface HttpResponseMessage
{
    Integer getHttpStatusCode();

    void setHttpStatusCode( Integer httpStatusCode );

    List<Header> getHeaders();

    void setHeaders( List<Header> headers );
}
