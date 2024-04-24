package org.hisp.dhis;

import static org.hisp.dhis.util.CollectionUtils.list;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.hisp.dhis.model.DataSet;
import org.hisp.dhis.model.DataSetElement;
import org.hisp.dhis.model.DimensionItemType;
import org.hisp.dhis.model.FormType;
import org.hisp.dhis.query.Filter;
import org.hisp.dhis.query.Order;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag( TestTags.INTEGRATION )
class DataSetApiTest
{
    @Test
    void testGetDataSet()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        DataSet dataSet = dhis2.getDataSet( "pBOMPrpg1QX" );

        assertNotNull( dataSet );
        assertEquals( "pBOMPrpg1QX", dataSet.getId() );
        assertEquals( "Mortality < 5 years", dataSet.getName() );
        assertEquals( "Mortality < 5 years", dataSet.getDisplayFormName() );
        assertEquals( "bjDvmb4bfuf", dataSet.getCategoryCombo().getId() );
        assertEquals( 14, dataSet.getDataSetElements().size() );
        assertEquals( "pBOMPrpg1QX", dataSet.getDimensionItem() );
        assertNotNull( dataSet.getOpenFuturePeriods() );
        assertNotNull( dataSet.getExpiryDays() );
        assertEquals( FormType.DEFAULT, dataSet.getFormType() );
        assertEquals( "Monthly", dataSet.getPeriodType() );
        assertNotNull( dataSet.getVersion() );
        assertEquals( DimensionItemType.REPORTING_RATE, dataSet.getDimensionItemType() );
        assertNull( dataSet.getAggregationType() );
        assertEquals( 1169, dataSet.getOrganisationUnits().size() );
        assertEquals( 0, dataSet.getSections().size() );
        assertEquals( 0, dataSet.getIndicators().size() );
        assertEquals( "Mortality < 5 years", dataSet.getWorkflow().getName() );
    }

    @Test
    void testGetDataSets()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        List<DataSet> dataSets = dhis2.getDataSets( Query.instance()
            .addFilter( Filter.in( "id", list( "pBOMPrpg1QX", "VTdjfLXXmoi", "Lpw6GcnTrmS" ) ) )
            .setOrder( Order.asc( "id" ) ) );

        assertEquals( 3, dataSets.size() );
        assertNull( dataSets.get( 0 ).getWorkflow() );
        assertTrue( dataSets.get( 1 ).getOrganisationUnits().isEmpty() );
        assertTrue( dataSets.get( 2 ).getIndicators().isEmpty() );
        assertTrue( dataSets.get( 0 ).getSections().isEmpty() );
    }

    @Test
    void testGetDataSetsWithExpandAssociations()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        List<DataSet> dataSets = dhis2.getDataSets( Query.instance()
            .withExpandAssociations()
            .addFilter( Filter.in( "id", list( "BfMAe6Itzgt" ) ) ) );

        assertEquals( 1, dataSets.size() );

        DataSet dataSet = dataSets.get( 0 );

        assertEquals( "BfMAe6Itzgt", dataSet.getId() );

        List<DataSetElement> dataSetElements = dataSet.getDataSetElements();

        assertFalse( dataSetElements.isEmpty() );

        DataSetElement dataSetElement = dataSetElements.get( 0 );

        assertNotNull( dataSetElement.getDataElement() );
        assertNotNull( dataSetElement.getDataElement().getId() );
        assertNotNull( dataSetElement.getDataElement().getName() );
        assertNotNull( dataSetElement.getDataSet() );
        assertNotNull( dataSetElement.getDataSet().getId() );
        assertNotNull( dataSetElement.getDataSet().getName() );
    }
}
