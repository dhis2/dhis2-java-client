package org.hisp.dhis.util.mock;

import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.objects.ObjectStatistics;
import org.hisp.dhis.response.objects.ObjectsResponse;

public class MockObjectsResponse
    extends ObjectsResponse
{
    public MockObjectsResponse()
    {
        super( Status.OK, 201, new ObjectStatistics() );
    }
}
