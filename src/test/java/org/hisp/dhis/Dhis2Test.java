package org.hisp.dhis;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertNotNull;

import org.hisp.dhis.model.DataValue;
import org.hisp.dhis.model.DataValueSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class Dhis2Test
{
    private static final String BASE_URL = "http://localhost/dhis";

    private Dhis2Config dhis2Config;
    private Dhis2 dhis2;
    private RestTemplate restTemplate;

    @Before
    public void before()
    {
        dhis2Config = new Dhis2Config( BASE_URL, "admin", "district" );
        dhis2 = new Dhis2( dhis2Config );

        ResponseMessage message = new ResponseMessage();

        when( restTemplate.exchange( "http://localhost/dhis/api/dataValueSets", HttpMethod.POST, Mockito.any( HttpEntity.class ), ResponseMessage.class ) )
            .thenReturn( new ResponseEntity<ResponseMessage>( message, HttpStatus.OK ) );
    }

    @Test
    public void testDhis2()
    {
        Dhis2 dhis2 = new Dhis2( dhis2Config );

        assertNotNull( dhis2 );
    }

    @Test
    public void testDataValueSetImport()
    {
        Dhis2 dhis2 = new Dhis2( dhis2Config );

        DataValue dataValue1 = new DataValue();
        dataValue1.setDataElement( "f7n9E0hX8qk" );
        dataValue1.setValue( "12" );

        DataValue dataValue2 = new DataValue();
        dataValue2.setDataElement( "Ix2HsbDMLea" );
        dataValue1.setValue( "13" );

        DataValue dataValue3 = new DataValue();
        dataValue2.setDataElement( "eY5ehpbEsB7" );
        dataValue1.setValue( "14" );

        DataValueSet dataValueSet = new DataValueSet();
        dataValueSet.setDataSet( "pBOMPrpg1QX" );
        dataValueSet.setCompleteDate( "2014-02-03" );
        dataValueSet.setPeriod( "201901" );
        dataValueSet.setOrgUnit( "DiszpKrYNg8" );

        dataValueSet.addDataValue( dataValue1 );
        dataValueSet.addDataValue( dataValue2 );
        dataValueSet.addDataValue( dataValue3 );

        dhis2.saveDataValueSet( dataValueSet );
    }
}
