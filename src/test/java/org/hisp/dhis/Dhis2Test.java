package org.hisp.dhis;

import static org.junit.Assert.assertNotNull;

import org.hisp.dhis.model.DataValue;
import org.hisp.dhis.model.DataValueSet;
import org.hisp.dhis.model.OrgUnitGroup;
import org.hisp.dhis.response.ResponseMessage;
import org.hisp.dhis.response.datavalueset.DataValueSetResponseMessage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;

public class Dhis2Test
{

    private static final String BASE_URL = "http://localhost/dhis";

    private Dhis2Config dhis2Config;

    @Mock
    private ResponseEntity<ResponseMessage> responseEntity;

    @Mock
    private RestTemplate restTemplate;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void before()
    {
        dhis2Config = new Dhis2Config( BASE_URL, "admin", "district" );

        applyMocks();
    }

    private void applyMocks()
    {
        ResponseMessage responseMessage = new ResponseMessage();
        DataValueSetResponseMessage dataValueSetResponseMessage = new DataValueSetResponseMessage();

        when( responseEntity.getBody() ).thenReturn( responseMessage );

        when( restTemplate.exchange( Mockito.anyString(),
            Mockito.eq( HttpMethod.POST ), Mockito.any( HttpEntity.class ), Mockito.eq( ResponseMessage.class ) ) )
            .thenReturn( new ResponseEntity<ResponseMessage>( responseMessage, HttpStatus.OK ) );

        when( restTemplate.exchange( Mockito.anyString(),
            Mockito.eq( HttpMethod.POST ), Mockito.any( HttpEntity.class ), Mockito.eq( DataValueSetResponseMessage.class ) ) )
            .thenReturn( new ResponseEntity<DataValueSetResponseMessage>( dataValueSetResponseMessage, HttpStatus.OK ) );
    }

    public void testDhis2()
    {
        Dhis2 dhis2 = new Dhis2( dhis2Config, restTemplate );

        assertNotNull( dhis2 );
    }

    @Test
    public void saveOrgUnitGroup()
    {
        Dhis2 dhis2 = new Dhis2( dhis2Config, restTemplate );

        OrgUnitGroup orgUnitGroup = new OrgUnitGroup();
        orgUnitGroup.setName( "OrgUnitGroupA" );
        orgUnitGroup.setShortName( "OrgUnitGroupA" );
        orgUnitGroup.setCode( "OUGA" );

        ResponseMessage response = dhis2.saveOrgUnitGroup( orgUnitGroup );

        assertNotNull( response );
    }

    @Test
    public void testDataValueSetImport()
    {
        Dhis2 dhis2 = new Dhis2( dhis2Config, restTemplate );

        DataValue dataValue1 = new DataValue();
        dataValue1.setDataElement( "f7n9E0hX8qk" );
        dataValue1.setValue( "12" );

        DataValue dataValue2 = new DataValue();
        dataValue2.setDataElement( "Ix2HsbDMLea" );
        dataValue2.setValue( "13" );

        DataValue dataValue3 = new DataValue();
        dataValue3.setDataElement( "eY5ehpbEsB7" );
        dataValue3.setValue( "14" );

        DataValueSet dataValueSet = new DataValueSet();
        dataValueSet.setDataSet( "pBOMPrpg1QX" );
        dataValueSet.setCompleteDate( "2014-02-03" );
        dataValueSet.setPeriod( "201910" );
        dataValueSet.setOrgUnit( "DiszpKrYNg8" );

        dataValueSet.addDataValue( dataValue1 );
        dataValueSet.addDataValue( dataValue2 );
        dataValueSet.addDataValue( dataValue3 );

        DataValueSetResponseMessage response = dhis2.saveDataValueSet( dataValueSet );

        assertNotNull( response );
    }
}
