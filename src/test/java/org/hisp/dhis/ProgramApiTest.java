package org.hisp.dhis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.hisp.dhis.model.Program;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag( TestTags.INTEGRATION )
public class ProgramApiTest
{
    @Test
    void testGetProgram()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        Program program = dhis2.getProgram( "IpHINAT79UW" );

        assertNotNull( program );
        assertEquals( "IpHINAT79UW", program.getId() );
    }

}
