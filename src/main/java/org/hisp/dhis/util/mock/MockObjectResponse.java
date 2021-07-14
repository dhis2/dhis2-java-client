package org.hisp.dhis.util.mock;

import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.object.ObjectResponse;

public class MockObjectResponse
    extends ObjectResponse
{
    public MockObjectResponse()
    {
        super( Status.OK, 200, "OK" );
    }
}
