package org.hisp.dhis.util.mock;

import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.metadata.MetadataResponseMessage;

public class MockOkResponseMessage
    extends MetadataResponseMessage
{
    public MockOkResponseMessage()
    {
        super( Status.OK, 200, "OK" );
    }
}
