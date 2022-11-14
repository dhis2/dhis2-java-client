package org.hisp.dhis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.hisp.dhis.model.Option;
import org.hisp.dhis.model.OptionSet;
import org.hisp.dhis.query.Query;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag( "integration" )
public class OptionSetsApiTest
{
    @Test
    void testGetOptionSet()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        OptionSet optionSet = dhis2.getOptionSet( "VQ2lai3OfVG" );

        assertNotNull( optionSet );
        assertEquals( "VQ2lai3OfVG", optionSet.getId() );
        assertNotNull( optionSet.getName() );
        assertNotNull( optionSet.getValueType() );
        assertNotNull( optionSet.getOptions() );
        assertFalse( optionSet.getOptions().isEmpty() );

        Option option = optionSet.getOptions().get( 0 );

        assertNotNull( option );
        assertNotNull( option.getId() );
        assertNotNull( option.getCode() );
        assertNotNull( option.getName() );
    }

    @Test
    void testGetOptionSets()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        List<OptionSet> optionSets = dhis2.getOptionSets( Query.instance() );

        assertNotNull( optionSets );
        assertTrue( optionSets.size() > 1 );

        OptionSet optionSet = optionSets.get( 0 );

        assertNotNull( optionSet );
        assertNotNull( optionSet.getId() );
        assertNotNull( optionSet.getName() );

        optionSets = dhis2.getOptionSets( Query.instance()
            .setPaging( 1, 10 )
            .withExpandAssociations() );

        assertNotNull( optionSets );
        assertTrue( optionSets.size() > 1 );

        optionSet = optionSets.get( 0 );

        assertNotNull( optionSet );
        assertNotNull( optionSet.getId() );
        assertNotNull( optionSet.getName() );
        assertNotNull( optionSet.getOptions() );
        assertFalse( optionSet.getOptions().isEmpty() );
    }
}
