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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.hisp.dhis.model.CategoryOption;
import org.hisp.dhis.model.OrgUnit;
import org.hisp.dhis.model.OrgUnitGroup;
import org.hisp.dhis.response.Dhis2ClientException;
import org.hisp.dhis.response.HttpStatus;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.support.TestTags;
import org.hisp.dhis.util.UidUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class Dhis2CrudApiTest {
  @Test
  void testCategoryOption() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    String uid = UidUtils.generateUid();
    OrgUnit ouA = new OrgUnit("ueuQlqb8ccl", null);
    OrgUnit ouB = new OrgUnit("Rp268JB6Ne4", null);

    String codeA = "CAT_OPT__" + uid;
    String nameA = "Category option name__" + uid;
    String shortNameA = "Category option short name__" + uid;

    CategoryOption coA = new CategoryOption();
    coA.setCode(codeA);
    coA.setName(nameA);
    coA.setShortName(shortNameA);
    coA.getOrganisationUnits().add(ouA);
    coA.getOrganisationUnits().add(ouB);

    // Create

    ObjectResponse createRespA = dhis2.saveCategoryOption(coA);

    assertEquals(201, createRespA.getHttpStatusCode().intValue(), createRespA.toString());
    assertEquals(HttpStatus.CREATED, createRespA.getHttpStatus(), createRespA.toString());
    assertEquals(Status.OK, createRespA.getStatus(), createRespA.toString());
    assertNotNull(createRespA.getResponse());
    assertNotNull(createRespA.getResponse().getUid());

    String uidA = createRespA.getResponse().getUid();

    // Get

    coA = dhis2.getCategoryOption(uidA);

    assertNotNull(coA);
    assertNotNull(coA.getAttributeValues());
    assertEquals(uidA, coA.getId());
    assertEquals(codeA, coA.getCode());
    assertEquals(nameA, coA.getName());
    assertEquals(shortNameA, coA.getShortName());
    assertEquals(2, coA.getOrganisationUnits().size());
    assertTrue(coA.getOrganisationUnits().contains(ouA));
    assertTrue(coA.getOrganisationUnits().contains(ouB));

    String name = "Category updated name__A";

    coA.setName(name);

    // Update

    ObjectResponse updateRespA = dhis2.updateCategoryOption(coA);

    assertEquals(200, updateRespA.getHttpStatusCode().intValue(), updateRespA.toString());
    assertEquals(HttpStatus.OK, updateRespA.getHttpStatus(), updateRespA.toString());
    assertEquals(Status.OK, updateRespA.getStatus(), updateRespA.toString());
    assertEquals(uidA, updateRespA.getResponse().getUid(), updateRespA.toString());

    // Get

    coA = dhis2.getCategoryOption(uidA);

    assertNotNull(coA);
    assertNotNull(coA.getAttributeValues());
    assertEquals(uidA, coA.getId());
    assertEquals(codeA, coA.getCode());
    assertEquals(2, coA.getOrganisationUnits().size());
    assertTrue(coA.getOrganisationUnits().contains(ouA));
    assertTrue(coA.getOrganisationUnits().contains(ouB));

    // Remove

    ObjectResponse removeRespA = dhis2.removeCategoryOption(uidA);

    assertEquals(200, removeRespA.getHttpStatusCode().intValue(), removeRespA.toString());
    assertEquals(HttpStatus.OK, removeRespA.getHttpStatus(), removeRespA.toString());
    assertEquals(Status.OK, removeRespA.getStatus(), removeRespA.toString());
    assertEquals(uidA, updateRespA.getResponse().getUid(), removeRespA.toString());
  }

  @Test
  void testOrgUnitGroup() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    OrgUnitGroup ougA = new OrgUnitGroup();
    ougA.setCode("ORG_UNIT_GROUP__A");
    ougA.setName("OUG name__A");
    ougA.setShortName("OUG short name__A");

    // Create

    ObjectResponse createRespA = dhis2.saveOrgUnitGroup(ougA);

    assertEquals(201, createRespA.getHttpStatusCode().intValue(), createRespA.toString());
    assertEquals(HttpStatus.CREATED, createRespA.getHttpStatus(), createRespA.toString());
    assertEquals(Status.OK, createRespA.getStatus(), createRespA.toString());
    assertNotNull(createRespA.getResponse());
    assertNotNull(createRespA.getResponse().getUid());

    String uidA = createRespA.getResponse().getUid();

    // Get

    ougA = dhis2.getOrgUnitGroup(uidA);

    assertNotNull(ougA);
    assertEquals(uidA, ougA.getId());

    String name = "Category updated name__A";

    ougA.setName(name);

    // Update

    ObjectResponse updateRespA = dhis2.updateOrgUnitGroup(ougA);

    assertEquals(200, updateRespA.getHttpStatusCode().intValue(), updateRespA.toString());
    assertEquals(HttpStatus.OK, updateRespA.getHttpStatus(), updateRespA.toString());
    assertEquals(Status.OK, updateRespA.getStatus(), updateRespA.toString());
    assertEquals(uidA, updateRespA.getResponse().getUid(), updateRespA.toString());

    // Remove

    ObjectResponse removeRespA = dhis2.removeOrgUnitGroup(uidA);

    assertEquals(200, removeRespA.getHttpStatusCode().intValue(), removeRespA.toString());
    assertEquals(HttpStatus.OK, removeRespA.getHttpStatus(), removeRespA.toString());
    assertEquals(Status.OK, removeRespA.getStatus(), removeRespA.toString());
    assertNotNull(removeRespA.getResponse());
    assertNotNull(removeRespA.getResponse().getUid());
  }

  @Test
  void testNotFound() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    Dhis2ClientException ex =
        assertThrows(Dhis2ClientException.class, () -> dhis2.getCategoryOption("kju6y2JHtR1"));
    assertEquals(404, ex.getStatusCode());
  }
}
