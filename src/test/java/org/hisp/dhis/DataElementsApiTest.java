package org.hisp.dhis;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.hisp.dhis.model.DataElement;
import org.hisp.dhis.model.OptionSet;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag( TestTags.INTEGRATION )
class DataElementsApiTest
{
    @Test
    void testGetDataElement()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        DataElement dataElement = dhis2.getDataElement( "a57FmdPj3Zl" );

        assertNotNull( dataElement.getId() );
        assertNotNull( dataElement.getName() );
        assertNotNull( dataElement.getValueType() );

        OptionSet optionSet = dataElement.getOptionSet();

        assertNotNull( optionSet );
        assertNotNull( optionSet.getId() );
        assertNotNull( optionSet.getName() );
        assertNotNull( optionSet.getValueType() );
    }
}
