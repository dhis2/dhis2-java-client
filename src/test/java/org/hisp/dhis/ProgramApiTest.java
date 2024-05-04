package org.hisp.dhis;

import static org.hisp.dhis.support.Assertions.assertNotBlank;
import static org.hisp.dhis.support.Assertions.assertNotEmpty;
import static org.hisp.dhis.support.Assertions.assertSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.hisp.dhis.model.DataElement;
import org.hisp.dhis.model.Program;
import org.hisp.dhis.model.ProgramStage;
import org.hisp.dhis.model.ProgramStageDataElement;
import org.hisp.dhis.model.ProgramType;
import org.hisp.dhis.query.Filter;
import org.hisp.dhis.query.Order;
import org.hisp.dhis.query.Query;
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

        Program pr = dhis2.getProgram( "IpHINAT79UW" );

        assertNotNull( pr );
        assertEquals( "IpHINAT79UW", pr.getId() );
        assertNotBlank( pr.getShortName() );
        assertNotBlank( pr.getName() );
        assertNotNull( pr.getCreated() );
        assertNotNull( pr.getLastUpdated() );
        assertEquals( ProgramType.WITH_REGISTRATION, pr.getProgramType() );
        assertNotEmpty( pr.getProgramStages() );

        ProgramStage ps = pr.getProgramStages().iterator().next();

        assertNotNull( ps );
        assertNotBlank( ps.getId() );
        assertNotBlank( ps.getName() );
        assertNotEmpty( ps.getProgramStageDataElements() );

        ProgramStageDataElement psde = ps.getProgramStageDataElements().iterator().next();

        assertNotNull( psde );

        assertNotNull( psde.getDataElement() );
        assertNotNull( psde.getCompulsory() );
        assertNotNull( psde.getDisplayInReports() );
        assertNotNull( psde.getSkipSynchronization() );
        assertNotNull( psde.getSkipAnalytics() );

        DataElement de = psde.getDataElement();

        assertNotNull( de );
        assertNotBlank( de.getId() );
        assertNotBlank( de.getShortName() );
        assertNotBlank( de.getName() );
    }

    @Test
    void testGetPrograms()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        List<Program> programs = dhis2.getPrograms( Query.instance()
            .addFilter( Filter.in( "id", List.of( "WSGAb5XwJ3Y", "M3xtLkYBlKI", "IpHINAT79UW" ) ) )
            .setOrder( Order.asc( "id" ) ) );

        assertSize( 3, programs );
        assertEquals( "IpHINAT79UW", programs.get( 0 ).getId() );
        assertEquals( "M3xtLkYBlKI", programs.get( 1 ).getId() );
        assertEquals( "WSGAb5XwJ3Y", programs.get( 2 ).getId() );
    }
}
