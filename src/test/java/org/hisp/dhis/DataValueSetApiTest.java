package org.hisp.dhis;

import static org.hisp.dhis.util.CollectionUtils.list;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.hisp.dhis.model.datavalueset.DataValue;
import org.hisp.dhis.model.datavalueset.DataValueSet;
import org.hisp.dhis.query.datavalue.DataValueSetQuery;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag( TestTags.INTEGRATION )
class DataValueSetApiTest
{
    @Test
    void testGetDataValueSets()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        DataValueSetQuery query = DataValueSetQuery.instance()
            .addDataSets( list( "lyLU2wR22tC" ) )
            .addPeriods( list( "202211" ) )
            .addOrgUnits( list( "ImspTQPwCqd" ) )
            .setChildren( true );

        DataValueSet dataValueSet = dhis2.getDataValueSet( query );

        List<DataValue> dataValues = dataValueSet.getDataValues();

        assertNotNull( dataValues );
    }
}
