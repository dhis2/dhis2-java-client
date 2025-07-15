/*
 * Copyright (c) 2004-2025, University of Oslo
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

import static org.hisp.dhis.ApiTestUtils.assertSuccessResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.hisp.dhis.model.DataElementGroup;
import org.hisp.dhis.model.DataElementGroupSet;
import org.hisp.dhis.model.DimensionType;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.response.HttpStatus;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/*
 * Copyright (c) 2004-2025, University of Oslo
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
@Tag(TestTags.INTEGRATION)
class DataElementGroupSetApiTest {
  @Test
  void testGetDataElementGroupSet() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    DataElementGroupSet dataElementGroupSet = dhis2.getDataElementGroupSet("jp826jAJHUc");

    assertNotNull(dataElementGroupSet);
    assertEquals("jp826jAJHUc", dataElementGroupSet.getId());
    assertNull(dataElementGroupSet.getCode());
    assertEquals("Diagnosis", dataElementGroupSet.getName());
    assertEquals("Diagnosis", dataElementGroupSet.getShortName());
    assertEquals("Diagnosis", dataElementGroupSet.getDescription());
    assertEquals(DimensionType.DATA_ELEMENT_GROUP_SET, dataElementGroupSet.getDimensionType());
    assertNotNull(dataElementGroupSet.getCreated());
    assertNotNull(dataElementGroupSet.getLastUpdated());
  }

  @Test
  void testGetDataElementGroupSets() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    List<DataElementGroupSet> attributes = dhis2.getDataElementGroupSets(Query.instance());

    assertNotNull(attributes);
    assertFalse(attributes.isEmpty());
  }

  @Test
  void testCreateUpdateAndDeleteDataElementGroupSet() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    DataElementGroupSet dataElementGroupSet = new DataElementGroupSet();
    dataElementGroupSet.setName("Sample Data Element Group Set");
    dataElementGroupSet.setCode("DEGS_SAMPLE_CODE");
    dataElementGroupSet.setDescription("Sample description");
    dataElementGroupSet.setShortName("Sample short name");
    dataElementGroupSet.setDataDimension(true);
    dataElementGroupSet.setDimensionType(DimensionType.DATA_ELEMENT_GROUP_SET);
    dataElementGroupSet.setCompulsory(true);
    DataElementGroup deg = dhis2.getDataElementGroup("SriP0jBXMr6");
    dataElementGroupSet.setDataElementGroups(List.of(deg));

    // Create
    ObjectResponse createRespA = dhis2.saveDataElementGroupSet(dataElementGroupSet);

    assertSuccessResponse(createRespA, HttpStatus.CREATED, 201);

    String dataElementGroupSetUid = createRespA.getResponse().getUid();

    // Get
    dataElementGroupSet = dhis2.getDataElementGroupSet(dataElementGroupSetUid);

    assertNotNull(dataElementGroupSet);
    assertEquals(dataElementGroupSetUid, dataElementGroupSet.getId());
    assertEquals("Sample Data Element Group Set", dataElementGroupSet.getName());
    assertEquals("DEGS_SAMPLE_CODE", dataElementGroupSet.getCode());
    assertEquals("Sample description", dataElementGroupSet.getDescription());
    assertEquals("Sample short name", dataElementGroupSet.getShortName());
    assertEquals(DimensionType.DATA_ELEMENT_GROUP_SET, dataElementGroupSet.getDimensionType());
    assertTrue(dataElementGroupSet.getDataDimension());
    assertTrue(dataElementGroupSet.getCompulsory());
    assertNotNull(dataElementGroupSet.getDataElementGroups());
    assertFalse(dataElementGroupSet.getDataElementGroups().isEmpty());

    String updatedName = "Sample DEGS updated";
    dataElementGroupSet.setName(updatedName);

    // Update
    ObjectResponse updateRespA = dhis2.updateDataElementGroupSet(dataElementGroupSet);

    assertSuccessResponse(updateRespA, HttpStatus.OK, 200);
    assertEquals(
        dataElementGroupSetUid, updateRespA.getResponse().getUid(), updateRespA.toString());

    // Get Updated
    dataElementGroupSet = dhis2.getDataElementGroupSet(dataElementGroupSetUid);
    assertNotNull(dataElementGroupSet);
    assertEquals(dataElementGroupSetUid, dataElementGroupSet.getId());
    assertEquals(updatedName, dataElementGroupSet.getName());

    // Remove
    ObjectResponse removeRespA = dhis2.removeDataElementGroupSet(dataElementGroupSetUid);

    assertSuccessResponse(removeRespA, HttpStatus.OK, 200);
  }
}
