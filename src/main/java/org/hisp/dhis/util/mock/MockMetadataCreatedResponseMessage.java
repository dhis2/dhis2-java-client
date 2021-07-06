package org.hisp.dhis.util.mock;

import java.util.ArrayList;

import org.hisp.dhis.model.IdentifiableObject;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.metadata.MetadataResponseMessage;
import org.hisp.dhis.response.metadata.ObjectReport;
import org.hisp.dhis.util.UidUtils;

public class MockMetadataCreatedResponseMessage
    extends MetadataResponseMessage
{
    public MockMetadataCreatedResponseMessage( Class<? extends IdentifiableObject> type )
    {
        super( Status.OK, 201, "Object created" );
        super.code = 201;
        super.response = new ObjectReport( type.getClass().getName(), UidUtils.generateUid(), new ArrayList<>() );
    }
}
