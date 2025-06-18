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

import static org.hisp.dhis.util.CollectionUtils.list;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.hisp.dhis.model.datavalueset.DataValue;
import org.hisp.dhis.model.datavalueset.DataValueSet;
import org.hisp.dhis.model.datavalueset.DataValueSetImportOptions;
import org.hisp.dhis.query.datavalue.DataValueSetQuery;
import org.hisp.dhis.response.Dhis2ClientException;
import org.hisp.dhis.response.data.ImportCount;
import org.hisp.dhis.response.data.Status;
import org.hisp.dhis.response.datavalueset.DataValueSetResponse;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.SLOW_INTEGRATION)
class DataValueSetApiTest {
  @Test
  void testGetDataValueSets() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    DataValueSetQuery query =
        DataValueSetQuery.instance()
            .addDataSets(list("lyLU2wR22tC"))
            .addPeriods(list("202211"))
            .addOrgUnits(list("ImspTQPwCqd"))
            .setChildren(true);

    DataValueSet dataValueSet = dhis2.getDataValueSet(query);

    List<DataValue> dataValues = dataValueSet.getDataValues();

    assertNotNull(dataValues);
  }

  @Test
  void testGetDataValueSetsWithoutOrgUnitThrowsException() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    DataValueSetQuery query =
        DataValueSetQuery.instance().addDataSets(list("lyLU2wR22tC")).addPeriods(list("202211"));

    Dhis2ClientException ex =
        assertThrows(Dhis2ClientException.class, () -> dhis2.getDataValueSet(query));

    assertNotNull(ex);
    assertEquals(409, ex.getStatusCode());
    assertEquals("E2006", ex.getErrorCode());
  }

  @Test
  void testSaveDataValueSets() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    DataValueSet dataValueSet = getDataValueSet();
    DataValueSetImportOptions importOptions = DataValueSetImportOptions.instance();
    DataValueSetResponse response = dhis2.saveDataValueSet(dataValueSet, importOptions);

    assertNotNull(response);
    assertEquals(Status.SUCCESS, response.getStatus());

    ImportCount importCount = response.getImportCount();
    assertNotNull(importCount);
  }

  private DataValueSet getDataValueSet() {
    DataValueSet dataValueSet = new DataValueSet();
    dataValueSet.setDataSet("lyLU2wR22tC");
    dataValueSet.setPeriod("202411");
    dataValueSet.setOrgUnit("ImspTQPwCqd");
    dataValueSet.addDataValue(getDataValue());
    return dataValueSet;
  }

  private DataValue getDataValue() {
    DataValue dataValue = new DataValue();
    dataValue.setDataElement("BOSZApCrBni");
    dataValue.setPeriod("202411");
    dataValue.setOrgUnit("ImspTQPwCqd");
    dataValue.setAttributeOptionCombo("");
    dataValue.setValue("23");
    dataValue.setStoredBy("system");
    dataValue.setFollowup(false);
    return dataValue;
  }
}
