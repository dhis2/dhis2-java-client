/*
 * Copyright (c) 2004-2024, University of Oslo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the HISP project nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.hisp.dhis;

import static org.hisp.dhis.support.Assertions.assertNotEmpty;
import static org.hisp.dhis.support.Assertions.assertSize;
import static org.hisp.dhis.util.CollectionUtils.list;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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

@Tag(TestTags.INTEGRATION)
class DataSetApiTest {
  @Test
  void testGetDataSet() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    DataSet dataSet = dhis2.getDataSet("pBOMPrpg1QX");

    assertNotNull(dataSet);
    assertEquals("pBOMPrpg1QX", dataSet.getId());
    assertEquals("Mortality < 5 years", dataSet.getName());
    assertEquals("Mortality < 5 years", dataSet.getDisplayFormName());
    assertEquals("bjDvmb4bfuf", dataSet.getCategoryCombo().getId());
    assertNotEmpty(dataSet.getDataSetElements());
    assertEquals("pBOMPrpg1QX", dataSet.getDimensionItem());
    assertNotNull(dataSet.getOpenFuturePeriods());
    assertNotNull(dataSet.getExpiryDays());
    assertEquals(FormType.DEFAULT, dataSet.getFormType());
    assertEquals("Monthly", dataSet.getPeriodType());
    assertNotNull(dataSet.getVersion());
    assertEquals(DimensionItemType.REPORTING_RATE, dataSet.getDimensionItemType());
    assertNull(dataSet.getAggregationType());
    assertNotEmpty(dataSet.getOrganisationUnits());
    assertEquals("Mortality < 5 years", dataSet.getWorkflow().getName());
  }

  @Test
  void testGetDataSets() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    List<DataSet> dataSets =
        dhis2.getDataSets(
            Query.instance()
                .addFilter(Filter.in("id", list("pBOMPrpg1QX", "VTdjfLXXmoi", "Lpw6GcnTrmS")))
                .setOrder(Order.asc("id")));

    assertSize(3, dataSets);
    assertEquals("Lpw6GcnTrmS", dataSets.get(0).getId());
    assertEquals("VTdjfLXXmoi", dataSets.get(1).getId());
    assertEquals("pBOMPrpg1QX", dataSets.get(2).getId());
  }

  @Test
  void testGetDataSetsWithExpandAssociations() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    List<DataSet> dataSets =
        dhis2.getDataSets(
            Query.instance()
                .withExpandAssociations()
                .addFilter(Filter.in("id", list("BfMAe6Itzgt"))));

    assertEquals(1, dataSets.size());

    DataSet dataSet = dataSets.get(0);

    assertEquals("BfMAe6Itzgt", dataSet.getId());

    List<DataSetElement> dataSetElements = dataSet.getDataSetElements();

    assertFalse(dataSetElements.isEmpty());

    DataSetElement dataSetElement = dataSetElements.get(0);

    assertNotNull(dataSetElement.getDataElement());
    assertNotNull(dataSetElement.getDataElement().getId());
    assertNotNull(dataSetElement.getDataElement().getName());
    assertNotNull(dataSetElement.getDataSet());
    assertNotNull(dataSetElement.getDataSet().getId());
    assertNotNull(dataSetElement.getDataSet().getName());
  }
}
