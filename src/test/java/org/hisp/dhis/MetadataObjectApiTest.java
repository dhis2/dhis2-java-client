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

import static org.hisp.dhis.support.Assertions.assertNotEmpty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.hisp.dhis.model.AggregationType;
import org.hisp.dhis.model.DataDomain;
import org.hisp.dhis.model.DataElement;
import org.hisp.dhis.model.Dhis2Objects;
import org.hisp.dhis.model.MetadataEntity;
import org.hisp.dhis.model.ValueType;
import org.hisp.dhis.query.Filter;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.response.HttpStatus;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.support.TestTags;
import org.hisp.dhis.util.UidUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class MetadataObjectApiTest {
  @Test
  void testSaveRemove() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    String uidA = UidUtils.generateUid();
    String uidB = UidUtils.generateUid();
    MetadataEntity entity = MetadataEntity.DATA_ELEMENT;
    DataElement object = getDataElement(uidA);

    // Save

    ObjectResponse createResponse = dhis2.saveMetadataObject(object);

    assertEquals(Status.OK, createResponse.getStatus());
    assertEquals(HttpStatus.CREATED, createResponse.getHttpStatus());

    // Get

    DataElement retrieved = dhis2.getMetadataObject(entity, uidA);

    assertNotNull(retrieved);

    // Is

    assertTrue(dhis2.isMetadataObject(entity, uidA));

    // Query

    Dhis2Objects objects =
        dhis2.getMetadataObjects(entity, Query.instance().addFilter(Filter.eq("id", uidA)));

    assertNotEmpty(objects.getDataElements());

    // Update

    object.setName(uidB);

    ObjectResponse updateResponse = dhis2.updateMetadataObject(object);

    assertEquals(Status.OK, updateResponse.getStatus());
    assertEquals(HttpStatus.OK, updateResponse.getHttpStatus());

    // Remove

    ObjectResponse removeResponse = dhis2.removeMetadataObject(entity, uidA);

    assertEquals(Status.OK, removeResponse.getStatus());
    assertEquals(HttpStatus.OK, removeResponse.getHttpStatus());
  }

  /**
   * Creates a {@link DataElement}.
   *
   * @param uid the identifier.
   * @return a {@link DataElement}.
   */
  private DataElement getDataElement(String uid) {
    DataElement object = new DataElement();
    object.setId(uid);
    object.setCode(uid);
    object.setName(uid);
    object.setShortName(uid);
    object.setValueType(ValueType.INTEGER);
    object.setAggregationType(AggregationType.SUM);
    object.setDomainType(DataDomain.AGGREGATE);
    return object;
  }
}
