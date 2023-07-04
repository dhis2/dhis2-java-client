package org.hisp.dhis;

import static org.hisp.dhis.util.CollectionUtils.list;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.hisp.dhis.model.DataElement;
import org.hisp.dhis.model.DataElementGroup;
import org.hisp.dhis.model.DataElementGroupSet;
import org.hisp.dhis.model.DataSet;
import org.hisp.dhis.model.DataSetElement;
import org.hisp.dhis.model.OptionSet;
import org.hisp.dhis.query.Filter;
import org.hisp.dhis.query.Order;
import org.hisp.dhis.query.Query;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag( "integration" )
public class DataElementsApiTest
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

    @Test
    void testGetDataElementsWithAssociations()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        List<DataElement> dataElements = dhis2.getDataElements( Query.instance().withExpandAssociations()
            .addFilter( Filter.in( "id", list( "fazCI2ygYkq", "eMyVanycQSC", "FTRrcoaog83" ) ) )
            .setOrder( Order.asc( "id" ) ) );

        assertEquals( 3, dataElements.size() );

        DataElement de1 = dataElements.get( 0 );
        DataElementGroup deg1 = de1.getDataElementGroups().get( 0 );
        DataElementGroupSet degs1 = deg1.getGroupSets().get( 0 );

        assertEquals( deg1.getId(), "oDkJh5Ddh7d" );
        assertEquals( deg1.getName().trim(), "Acute Flaccid Paralysis (AFP)" );
        assertEquals( degs1.getId(), "jp826jAJHUc" );
        assertEquals( degs1.getName(), "Diagnosis" );

        DataElement de2 = dataElements.get( 1 );
        assertTrue( de2.getDataElementGroups().isEmpty() );

        List<DataSetElement> dataSetElements = dataElements.get( 2 ).getDataSetElements();

        DataSet workflowDataSet = dataSetElements.stream()
            .filter( dse -> dse.getDataSet().getWorkflow() != null )
            .findFirst().get().getDataSet();

        assertEquals( 3, dataSetElements.size() );
        assertEquals( "rIUL3hYOjJc", workflowDataSet.getWorkflow().getId() );
        assertEquals( "Monthly", dataSetElements.get( 1 ).getDataSet().getPeriodType() );
    }
}
