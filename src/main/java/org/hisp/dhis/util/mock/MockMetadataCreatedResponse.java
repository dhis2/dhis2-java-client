package org.hisp.dhis.util.mock;

import java.util.ArrayList;

import org.hisp.dhis.model.IdentifiableObject;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.object.ObjectReport;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.util.UidUtils;

public class MockMetadataCreatedResponse
    extends ObjectResponse
{
    public MockMetadataCreatedResponse( Class<? extends IdentifiableObject> type )
    {
        super( Status.OK, 201, "Object created" );
        super.code = 201;

        ObjectReport report = new ObjectReport();
        report.setKlass( type.getClass().getName() );
        report.setUid( UidUtils.generateUid() );
        report.setErrorReports( new ArrayList<>() );
        super.response = report;
    }
}
