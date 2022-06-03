package org.hisp.dhis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import org.apache.hc.core5.net.URIBuilder;
import org.hisp.dhis.model.IdScheme;
import org.hisp.dhis.model.datavalueset.DataValueSetImportOptions;
import org.junit.jupiter.api.Test;

public class Dhis2Test
{
    @Test
    void testGetDhis2()
    {
        Dhis2Config config = new Dhis2Config( "https://play.dhis2.org/demo", "admin", "district" );

        Dhis2 dhis2 = new Dhis2( config );

        assertEquals( "https://play.dhis2.org/demo", dhis2.getDhis2Url() );
    }

    @Test
    void testGetDataValueSetImportQuery()
    {
        Dhis2Config config = new Dhis2Config( "https://dhis2.org", "admin", "district" );

        Dhis2 dhis2 = new Dhis2( config );

        URIBuilder uriBuilder = config.getResolvedUriBuilder().appendPath( "dataValueSets" );

        DataValueSetImportOptions options = DataValueSetImportOptions.instance()
            .setDataElementIdScheme( IdScheme.CODE )
            .setDryRun( true )
            .setPreheatCache( true )
            .setSkipAudit( true );

        URI uri = dhis2.getDataValueSetImportQuery( uriBuilder, options );

        String expected = "https://dhis2.org/api/dataValueSets?" +
            "async=true&dataElementIdScheme=code&dryRun=true&preheatCache=true&skipAudit=true";

        assertEquals( expected, uri.toString() );
    }
}
