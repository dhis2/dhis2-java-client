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

import static org.hisp.dhis.support.Assertions.assertNotBlank;
import static org.hisp.dhis.support.Assertions.assertNotEmpty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import org.hisp.dhis.model.AggregationType;
import org.hisp.dhis.model.DataDomain;
import org.hisp.dhis.model.DataElement;
import org.hisp.dhis.model.OptionSet;
import org.hisp.dhis.model.ValueType;
import org.hisp.dhis.model.acl.Access;
import org.hisp.dhis.model.sharing.Sharing;
import org.hisp.dhis.model.sharing.UserAccess;
import org.hisp.dhis.model.sharing.UserGroupAccess;
import org.hisp.dhis.model.translation.Translation;
import org.hisp.dhis.response.HttpStatus;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.support.JsonClassPathFile;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class DataElementApiTest {
  @Test
  void testGetDataElement() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    DataElement dataElement = dhis2.getDataElement("a57FmdPj3Zl");

    assertNotNull(dataElement.getId());
    assertNotBlank(dataElement.getName());
    assertNotNull(dataElement.getSharing());
    assertNotNull(dataElement.getAccess());
    assertNotNull(dataElement.getSharing());
    assertNotNull(dataElement.getValueType());

    OptionSet optionSet = dataElement.getOptionSet();

    assertNotNull(optionSet);
    assertNotNull(optionSet.getId());
    assertNotNull(optionSet.getName());
    assertNotNull(optionSet.getValueType());
  }

  @Test
  void testGetDataElementWithSharingAndTranslations() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    DataElement dataElement = dhis2.getDataElement("fbfJHSPpUQD");

    assertNotNull(dataElement.getId());
    assertEquals("ANC 1st visit", dataElement.getName());
    assertEquals("ANC 1st visit", dataElement.getShortName());
    assertEquals(ValueType.NUMBER, dataElement.getValueType());
    assertEquals(DataDomain.AGGREGATE, dataElement.getDomainType());

    Set<Translation> translations = dataElement.getTranslations();

    assertNotEmpty(translations);

    Translation translation = translations.iterator().next();

    assertNotNull(translation);
    assertNotBlank(translation.getLocale());
    assertNotBlank(translation.getProperty());
    assertNotBlank(translation.getValue());

    Sharing sharing = dataElement.getSharing();

    assertNotNull(sharing);
    assertNotBlank(sharing.getOwner());
    assertNotNull(sharing.getPublicAccess());
    assertNotEmpty(sharing.getUserGroups());
  }

  @Test
  void testSaveGetDataElementWithSharing() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    DataElement dataElement =
        JsonClassPathFile.fromJson("metadata/data-element-color.json", DataElement.class);

    ObjectResponse saveResponse = dhis2.saveDataElement(dataElement);

    assertNotNull(saveResponse);
    assertNotNull(saveResponse.getResponse());
    assertEquals(Status.OK, saveResponse.getStatus());
    assertEquals(HttpStatus.CREATED, saveResponse.getHttpStatus());
    assertEquals(dataElement.getId(), saveResponse.getResponse().getId());

    DataElement retrieved = dhis2.getDataElement(dataElement.getId());

    assertNotNull(retrieved);
    assertEquals("cZtM3Zhg3FQ", retrieved.getId());
    assertEquals("DJC_COLOR", retrieved.getCode());
    assertEquals("DJC: Color", retrieved.getName());
    assertEquals("DJC: Color", retrieved.getShortName());

    Sharing sharing = retrieved.getSharing();

    assertNotNull(sharing);
    assertNotBlank(sharing.getOwner());
    assertNotBlank(sharing.getPublicAccess());
    assertEquals(2, sharing.getUsers().size());
    assertEquals(2, sharing.getUserGroups().size());

    UserAccess userAccess = sharing.getUsers().get("awtnYWiVEd5");

    assertNotNull(userAccess);
    assertNotBlank(userAccess.getAccess());
    assertNotBlank(userAccess.getId());

    UserGroupAccess userGroupAccess = sharing.getUserGroups().get("Rg8wusV7QYi");

    assertNotNull(userGroupAccess);
    assertNotBlank(userGroupAccess.getAccess());
    assertNotBlank(userGroupAccess.getId());

    assertEquals(AggregationType.SUM, retrieved.getAggregationType());
    assertEquals(ValueType.NUMBER, retrieved.getValueType());

    Access access = retrieved.getAccess();

    assertNotNull(access);
    assertTrue(access.getManage());
    assertTrue(access.getWrite());
    assertTrue(access.getRead());
    assertTrue(access.getUpdate());
    assertTrue(access.getDelete());

    ObjectResponse removeResponse = dhis2.removeDataElement(dataElement.getId());

    assertNotNull(removeResponse);
    assertEquals(Status.OK, removeResponse.getStatus());
    assertEquals(HttpStatus.OK, removeResponse.getHttpStatus());
  }
}
