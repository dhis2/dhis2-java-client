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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;
import org.hisp.dhis.model.OrgUnit;
import org.hisp.dhis.query.Filter;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.object.ErrorReport;
import org.hisp.dhis.response.object.ObjectReport;
import org.hisp.dhis.response.objects.ObjectsResponse;
import org.hisp.dhis.support.TestTags;
import org.hisp.dhis.util.UidUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class OrgUnitsApiTest {

  @Test
  void testSaveOrgUnitsWithUids() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    String uidA = UidUtils.generateUid();
    String uidB = UidUtils.generateUid();
    String uidC = UidUtils.generateUid();

    OrgUnit parent = new OrgUnit();
    parent.setId("at6UHUQatSo");

    OrgUnit ouA = new OrgUnit(uidA, uidA, uidA);
    ouA.setCode(uidA);
    ouA.setOpeningDate(new Date());
    ouA.setParent(parent);
    OrgUnit ouB = new OrgUnit(uidB, uidB, uidB);
    ouB.setCode(uidB);
    ouB.setOpeningDate(new Date());
    ouB.setParent(parent);
    OrgUnit ouC = new OrgUnit(uidC, uidC, uidC);
    ouC.setCode(uidC);
    ouC.setOpeningDate(new Date());
    ouC.setParent(parent);

    List<OrgUnit> orgUnits = list(ouA, ouB, ouC);

    ObjectsResponse response = dhis2.saveOrgUnits(orgUnits);

    assertNotNull(response);
    assertNotNull(response.getStats());
    assertNotNull(response.getTypeReport());

    assertEquals(Status.OK, response.getStatus(), response.toString());
    assertEquals(3, response.getStats().getCreated());

    assertEquals(Status.OK, dhis2.removeOrgUnit(uidA).getStatus());
    assertEquals(Status.OK, dhis2.removeOrgUnit(uidB).getStatus());
    assertEquals(Status.OK, dhis2.removeOrgUnit(uidC).getStatus());
  }

  @Test
  void testSaveAndUpdateOrgUnitsWithUids() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    String uidA = UidUtils.generateUid();
    String uidB = UidUtils.generateUid();
    String uidC = UidUtils.generateUid();

    OrgUnit parent = new OrgUnit();
    parent.setId("at6UHUQatSo");

    OrgUnit ouA = new OrgUnit(uidA, uidA, uidA);
    ouA.setCode(uidA);
    ouA.setOpeningDate(new Date());
    ouA.setParent(parent);
    OrgUnit ouB = new OrgUnit(uidB, uidB, uidB);
    ouB.setCode(uidB);
    ouB.setOpeningDate(new Date());
    ouB.setParent(parent);
    OrgUnit ouC = new OrgUnit(uidC, uidC, uidC);
    ouC.setCode(uidC);
    ouC.setOpeningDate(new Date());
    ouC.setParent(parent);

    List<OrgUnit> orgUnits = list(ouA, ouB);

    ObjectsResponse response = dhis2.saveOrgUnits(orgUnits);

    assertNotNull(response);
    assertNotNull(response.getStats());
    assertNotNull(response.getTypeReport());

    assertEquals(Status.OK, response.getStatus(), response.toString());
    assertEquals(2, response.getStats().getCreated());

    orgUnits = list(ouB, ouC);

    response = dhis2.saveOrgUnits(orgUnits);

    assertNotNull(response);
    assertNotNull(response.getStats());
    assertNotNull(response.getTypeReport());

    assertEquals(Status.OK, response.getStatus(), response.toString());
    assertEquals(1, response.getStats().getCreated());
    assertEquals(1, response.getStats().getUpdated());

    assertEquals(Status.OK, dhis2.removeOrgUnit(uidA).getStatus());
    assertEquals(Status.OK, dhis2.removeOrgUnit(uidB).getStatus());
    assertEquals(Status.OK, dhis2.removeOrgUnit(uidC).getStatus());
  }

  @Test
  void testSaveOrgUnitsMissingRequiredProperty() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    String uidA = UidUtils.generateUid();
    String uidB = UidUtils.generateUid();

    OrgUnit parent = new OrgUnit();
    parent.setId("at6UHUQatSo");

    OrgUnit ouA = new OrgUnit(uidA, uidA, null);
    ouA.setParent(parent);
    OrgUnit ouB = new OrgUnit(uidB, uidB, null);
    ouB.setParent(parent);

    List<OrgUnit> orgUnits = list(ouA, ouB);

    ObjectsResponse response = dhis2.saveOrgUnits(orgUnits);

    assertNotNull(response);
    assertNotNull(response.getStats());
    assertNotNull(response.getTypeReport());

    assertEquals(Status.ERROR, response.getStatus(), response.toString());
    assertEquals(2, response.getStats().getIgnored());
    assertEquals(
        "org.hisp.dhis.organisationunit.OrganisationUnit", response.getTypeReport().getKlass());
    assertEquals(2, response.getTypeReport().getStats().getIgnored());
    assertEquals(1, response.getTypeReports().size());
    assertEquals(2, response.getTypeReport().getObjectReports().size());

    ObjectReport objectReport1 = response.getTypeReport().getObjectReports().get(0);
    ObjectReport objectReport2 = response.getTypeReport().getObjectReports().get(1);

    assertNotNull(objectReport1);
    assertEquals("org.hisp.dhis.organisationunit.OrganisationUnit", objectReport1.getKlass());
    assertEquals(2, objectReport1.getErrorReports().size());

    assertNotNull(objectReport2);
    assertEquals("org.hisp.dhis.organisationunit.OrganisationUnit", objectReport2.getKlass());
    assertEquals(2, objectReport2.getErrorReports().size());

    ErrorReport errorReport1 = objectReport1.getErrorReports().get(0);
    ErrorReport errorReport2 = objectReport1.getErrorReports().get(1);

    assertNotNull(errorReport1);
    assertEquals("org.hisp.dhis.organisationunit.OrganisationUnit", errorReport1.getMainKlass());
    assertEquals("E4000", errorReport1.getErrorCode());

    assertNotNull(errorReport2);
    assertEquals("org.hisp.dhis.organisationunit.OrganisationUnit", errorReport2.getMainKlass());
    assertEquals("E4000", errorReport2.getErrorCode());
  }

  @Test
  void testGetOrgUnits() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    List<OrgUnit> orgUnits =
        dhis2.getOrgUnits(
            Query.instance()
                .addFilter(Filter.in("id", list("YuQRtpLP10I", "jPidqyo7cpF", "vWbkYPRmKyS"))));

    assertNotNull(orgUnits);
    assertEquals(3, orgUnits.size());

    OrgUnit ou = orgUnits.get(0);

    assertEquals("YuQRtpLP10I", ou.getId());
    assertEquals("OU_539", ou.getCode());
    assertNotNull(ou.getCreated());
    assertNotNull(ou.getLastUpdated());
    assertEquals("Badjia", ou.getName());
    assertEquals("Badjia", ou.getShortName());
    assertEquals("/ImspTQPwCqd/O6uvpzGd5pu/YuQRtpLP10I", ou.getPath());
    assertEquals(3, ou.getLevel());
    assertNotNull(ou.getParent());
    assertEquals("O6uvpzGd5pu", ou.getParent().getId());
    assertNotNull(ou.getOpeningDate());
  }

  @Test
  void testGetOrgUnitsWithPaging() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    List<OrgUnit> orgUnits = dhis2.getOrgUnits(Query.instance().setPaging(1, 100));

    assertNotNull(orgUnits);
    assertFalse(orgUnits.isEmpty());
    assertNotNull(orgUnits.get(0));
    assertNotNull(orgUnits.get(0).getId());
    assertNotNull(orgUnits.get(0).getName());
  }

  @Test
  void testGetOrgUnitsByAttributeValue() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    List<OrgUnit> orgUnits =
        dhis2.getOrgUnits(
            Query.instance()
                .addFilter(Filter.eq("attributeValues.attribute.id", "l1VmqIHKk6t"))
                .addFilter(Filter.eq("attributeValues.value", "KE02")));

    assertEquals(1, orgUnits.size());
    assertTrue(orgUnits.contains(new OrgUnit("g8upMTyEZGZ", "Njandama MCHP")));
  }

  @Test
  void testGetOrgUnitSubHierarchy() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    List<OrgUnit> orgUnits = dhis2.getOrgUnitSubHierarchy("O6uvpzGd5pu", 1, Query.instance());

    assertEquals(15, orgUnits.size());
    assertTrue(orgUnits.contains(new OrgUnit("YuQRtpLP10I", "Badjia")));
    assertTrue(orgUnits.contains(new OrgUnit("dGheVylzol6", "Bargbe")));
    assertTrue(orgUnits.contains(new OrgUnit("kU8vhUkAGaT", "Lugbu")));
  }

  @Test
  void testInFilter() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    List<String> values = list("Rp268JB6Ne4", "cDw53Ej8rju", "GvFqTavdpGE");

    List<OrgUnit> orgUnits = dhis2.getOrgUnits(Query.instance().addFilter(Filter.in("id", values)));

    assertEquals(3, orgUnits.size());
  }
}
