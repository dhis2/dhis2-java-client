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

import static org.hisp.dhis.support.Assertions.assertEmpty;
import static org.hisp.dhis.support.Assertions.assertNotBlank;
import static org.hisp.dhis.support.Assertions.assertNotEmpty;
import static org.hisp.dhis.support.Assertions.assertSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.hisp.dhis.model.DataElement;
import org.hisp.dhis.model.OrgUnit;
import org.hisp.dhis.model.Program;
import org.hisp.dhis.model.ProgramAccessLevel;
import org.hisp.dhis.model.ProgramIndicator;
import org.hisp.dhis.model.ProgramObjects;
import org.hisp.dhis.model.ProgramSection;
import org.hisp.dhis.model.ProgramStage;
import org.hisp.dhis.model.ProgramStageDataElement;
import org.hisp.dhis.model.ProgramStageSection;
import org.hisp.dhis.model.ProgramType;
import org.hisp.dhis.model.trackedentity.ProgramTrackedEntityAttribute;
import org.hisp.dhis.model.trackedentity.TrackedEntityAttribute;
import org.hisp.dhis.model.trackedentity.TrackedEntityType;
import org.hisp.dhis.model.trackedentity.TrackedEntityTypeAttribute;
import org.hisp.dhis.query.Filter;
import org.hisp.dhis.query.Order;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.response.HttpStatus;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.response.objects.ObjectsResponse;
import org.hisp.dhis.support.JsonClassPathFile;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class ProgramApiTest {
  private static final String PR_ID = "dIFNZrYGcOB";

  @Test
  void testSaveUpdateRemoveProgramObjects() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    ProgramObjects objects =
        JsonClassPathFile.fromJson("metadata/program-address-book.json", ProgramObjects.class);

    assertNotNull(objects);
    assertNotEmpty(objects.getPrograms());

    Program pr = objects.getPrograms().get(0);

    assertNotNull(pr);
    assertEquals(PR_ID, pr.getId());

    ObjectsResponse saveResponse = dhis2.saveProgram(objects);

    assertNotNull(saveResponse);
    assertEquals(Status.OK, saveResponse.getStatus());
    assertNotNull(saveResponse.getStats());

    pr.setName("Telephone Book");

    ObjectsResponse updateResponse = dhis2.saveProgram(objects);

    assertNotNull(updateResponse);
    assertEquals(Status.OK, updateResponse.getStatus());
    assertNotNull(updateResponse.getStats());

    ObjectResponse removeResponse = dhis2.removeProgram(PR_ID);

    assertNotNull(removeResponse);
    assertEquals(Status.OK, removeResponse.getStatus());
    assertEquals(HttpStatus.OK, removeResponse.getHttpStatus());
  }

  @Test
  void testSaveGetRemoveProgramObjects() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    ProgramObjects objects =
        JsonClassPathFile.fromJson("metadata/program-address-book.json", ProgramObjects.class);

    ObjectsResponse saveResponse = dhis2.saveProgram(objects);

    assertNotNull(saveResponse);
    assertEquals(Status.OK, saveResponse.getStatus());
    assertNotNull(saveResponse.getStats());

    ProgramObjects retrieved = dhis2.getProgramObjects(PR_ID);

    assertNotNull(retrieved);
    assertSize(1, retrieved.getPrograms());
    assertSize(1, retrieved.getProgramSections());
    assertSize(1, retrieved.getProgramStages());
    assertSize(1, retrieved.getProgramStageSections());

    Program pr = retrieved.getPrograms().get(0);

    assertNotNull(pr);
    assertEquals(PR_ID, pr.getId());
    assertEquals("Address Book", pr.getName());
    assertEquals("Address Book", pr.getShortName());
    assertEquals("ADDRESS_BOOK", pr.getCode());
    assertEquals("Registration Date", pr.getEnrollmentDateLabel());
    assertEquals("Registration", pr.getEnrollmentLabel());
    assertFalse(pr.getDisplayIncidentDate());
    assertTrue(pr.getOnlyEnrollOnce());
    assertTrue(pr.getSelectEnrollmentDatesInFuture());
    assertTrue(pr.getSelectIncidentDatesInFuture());
    assertTrue(pr.getDisplayFrontPageList());
    assertFalse(pr.getSkipOffline());
    assertFalse(pr.getUseFirstStageDuringRegistration());
    assertEquals(90, pr.getExpiryDays());
    assertEquals(1, pr.getMinAttributesRequiredToSearch());
    assertEquals(100, pr.getMaxTeiCountToReturn());
    assertSize(5, pr.getProgramTrackedEntityAttributes());
    assertSize(2, pr.getOrganisationUnits());
    assertSize(1, pr.getProgramSections());
    assertSize(1, pr.getProgramStages());
    assertEquals(ProgramType.WITH_REGISTRATION, pr.getProgramType());
    assertEquals(1, pr.getVersion());
    assertNotNull(pr.getDisplayIncidentDate());
    assertNotNull(pr.getOnlyEnrollOnce());
    assertNotNull(pr.getDisplayFrontPageList());
    assertNotNull(pr.getMinAttributesRequiredToSearch());
    assertNotNull(pr.getMaxTeiCountToReturn());
    assertEquals(ProgramAccessLevel.OPEN, pr.getAccessLevel());

    ProgramTrackedEntityAttribute ptea = pr.getProgramTrackedEntityAttributes().get(0);

    assertNotNull(ptea);
    assertEquals("NkvU4urhVNv", ptea.getId());
    assertNotNull(ptea.getProgram());
    assertEquals(PR_ID, ptea.getProgram().getId());
    assertNotNull(ptea.getTrackedEntityAttribute());
    assertEquals("lZGmxYbs97q", ptea.getTrackedEntityAttribute().getId());
    assertTrue(ptea.getDisplayInList());
    assertTrue(ptea.getMandatory());
    assertFalse(ptea.getAllowFutureDate());
    assertTrue(ptea.getSearchable());

    ProgramStage ps = retrieved.getProgramStages().get(0);

    assertNotNull(ps);
    assertEquals("ArL19QmQUd1", ps.getId());
    assertEquals("Vital Statistics", ps.getName());
    assertSize(2, ps.getProgramStageDataElements());
    assertSize(1, ps.getProgramStageSections());
    assertEquals("Registration Date", ps.getExecutionDateLabel());
    assertEquals("Vital Statistics", ps.getProgramStageLabel());
    assertFalse(ps.getRepeatable());
    assertFalse(ps.getAutoGenerateEvent());
    assertTrue(ps.getRemindCompleted());
    assertFalse(ps.getOpenAfterEnrollment());
    assertTrue(ps.getHideDueDate());
    assertFalse(ps.getDisplayGenerateEventBox());

    ProgramStageDataElement psde = ps.getProgramStageDataElements().get(0);

    assertNotNull(psde);
    assertNotNull(psde.getProgramStage());
    assertNotNull(psde.getDataElement());
    assertTrue(psde.getCompulsory());
    assertTrue(psde.getDisplayInReports());
    assertFalse(psde.getSkipSynchronization());
    assertFalse(psde.getSkipAnalytics());

    ProgramSection sc = retrieved.getProgramSections().get(0);

    assertNotNull(sc);
    assertEquals("msLqIoBRMva", sc.getId());
    assertEquals("Information", sc.getName());
    assertEquals("Information", sc.getDescription());
    assertEquals(5, sc.getTrackedEntityAttributes().size());

    ProgramStageSection pss = retrieved.getProgramStageSections().get(0);

    assertNotNull(pss);
    assertEquals("Information", pss.getName());
    assertEquals("Information", pss.getDescription());
    assertNotNull(pss.getProgramStage());
    assertEmpty(pss.getProgramIndicators());
    assertSize(2, pss.getDataElements());

    ObjectResponse removeResponse = dhis2.removeProgram(PR_ID);

    assertNotNull(removeResponse);
    assertEquals(Status.OK, removeResponse.getStatus());
    assertEquals(HttpStatus.OK, removeResponse.getHttpStatus());
  }

  @Test
  void testGetProgramObjectsChildProgramme() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    ProgramObjects objects = dhis2.getProgramObjects("IpHINAT79UW");

    assertNotEmpty(objects.getPrograms());
    assertNotEmpty(objects.getProgramStages());

    Program pr = objects.getPrograms().get(0);

    assertNotNull(pr);
    assertEquals("IpHINAT79UW", pr.getId());
    assertNotBlank(pr.getName());
    assertNotBlank(pr.getShortName());
    assertNotNull(pr.getSharing());
    assertNotNull(pr.getAccess());
    assertNotNull(pr.getCreated());
    assertNotNull(pr.getLastUpdated());
    assertEquals(ProgramType.WITH_REGISTRATION, pr.getProgramType());
    assertNotEmpty(pr.getOrganisationUnits());
    assertNotNull(pr.getVersion());
    assertNotNull(pr.getDisplayIncidentDate());
    assertNotNull(pr.getOnlyEnrollOnce());
    assertNotNull(pr.getDisplayFrontPageList());
    assertNotNull(pr.getMinAttributesRequiredToSearch());
    assertNotNull(pr.getMaxTeiCountToReturn());
    assertNotNull(pr.getAccessLevel());

    ProgramStage ps = objects.getProgramStages().get(0);

    assertNotNull(ps);
    assertNotBlank(ps.getId());
    assertNotBlank(ps.getName());
  }

  @Test
  void testGetProgramChildProgramme() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    Program pr = dhis2.getProgram("IpHINAT79UW");

    assertNotNull(pr);
    assertEquals("IpHINAT79UW", pr.getId());
    assertNotBlank(pr.getName());
    assertNotBlank(pr.getShortName());
    assertNotNull(pr.getSharing());
    assertNotNull(pr.getAccess());
    assertNotNull(pr.getCreated());
    assertNotNull(pr.getLastUpdated());
    assertEquals(ProgramType.WITH_REGISTRATION, pr.getProgramType());
    assertNotEmpty(pr.getOrganisationUnits());
    assertNotEmpty(pr.getTrackedEntityTypeAttributes());
    assertNotEmpty(pr.getNonConfidentialTrackedEntityAttributes());
    assertNotEmpty(pr.getTrackedEntityAttributes());
    assertNotEmpty(pr.getNonConfidentialTrackedEntityAttributes());
    assertNotNull(pr.getVersion());
    assertNotNull(pr.getDisplayIncidentDate());
    assertNotNull(pr.getOnlyEnrollOnce());
    assertNotNull(pr.getDisplayFrontPageList());
    assertNotNull(pr.getMinAttributesRequiredToSearch());
    assertNotNull(pr.getMaxTeiCountToReturn());
    assertNotNull(pr.getAccessLevel());

    TrackedEntityType tet = pr.getTrackedEntityType();

    assertNotNull(tet);
    assertEquals("nEenWmSyUEp", tet.getId());
    assertNotBlank(tet.getName());
    assertNotEmpty(tet.getTrackedEntityTypeAttributes());

    TrackedEntityTypeAttribute teta = tet.getTrackedEntityTypeAttributes().get(0);

    assertNotNull(teta);
    assertEquals("Jdd8hMStmvF", teta.getId());
    assertNotNull(teta.getTrackedEntityAttribute());
    assertEquals("lZGmxYbs97q", teta.getTrackedEntityAttribute().getId());
    assertEquals("MMD_PER_ID", teta.getTrackedEntityAttribute().getCode());
    assertNotBlank(teta.getTrackedEntityAttribute().getName());

    OrgUnit ou = pr.getOrganisationUnits().get(0);

    assertNotNull(ou);
    assertNotBlank(ou.getId());

    assertNotEmpty(pr.getProgramTrackedEntityAttributes());
    assertNotEmpty(pr.getProgramStages());

    ProgramTrackedEntityAttribute ptea = pr.getProgramTrackedEntityAttributes().get(0);

    assertNotNull(ptea);
    assertNotBlank(ptea.getId());
    assertNotNull(ptea.getProgram());
    assertNotNull(ptea.getSortOrder());
    assertNotNull(ptea.getDisplayInList());
    assertNotNull(ptea.getMandatory());

    TrackedEntityAttribute tea = ptea.getTrackedEntityAttribute();

    assertNotNull(tea);
    assertNotBlank(tea.getId());

    ProgramStage ps = pr.getProgramStages().get(0);

    assertNotNull(ps);
    assertNotBlank(ps.getId());
    assertNotBlank(ps.getName());
    assertNotEmpty(ps.getProgramStageDataElements());

    assertNotEmpty(ps.getDataElements());
    assertNotEmpty(ps.getAnalyticsDataElements());

    ProgramStageDataElement psde = ps.getProgramStageDataElements().get(0);

    assertNotNull(psde);

    assertNotNull(psde.getProgramStage());
    assertNotNull(psde.getDataElement());
    assertNotNull(psde.getCompulsory());
    assertNotNull(psde.getDisplayInReports());
    assertNotNull(psde.getSkipSynchronization());
    assertNotNull(psde.getSkipAnalytics());
    assertNotNull(psde.getSortOrder());

    DataElement de = psde.getDataElement();

    assertNotNull(de);
    assertNotBlank(de.getId());
    assertNotBlank(de.getShortName());
    assertNotBlank(de.getName());

    assertNotEmpty(pr.getTrackedEntityAttributes());
    assertNotEmpty(pr.getNonConfidentialTrackedEntityAttributes());
    assertNotEmpty(pr.getDataElements());
    assertNotEmpty(pr.getAnalyticsDataElements());
  }

  @Test
  void testGetProgramObjectsInpatientCase() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    ProgramObjects objects = dhis2.getProgramObjects("eBAyeGv0exc");

    assertNotEmpty(objects.getPrograms());
    assertNotEmpty(objects.getProgramStages());

    Program pr = objects.getPrograms().get(0);

    assertNotNull(pr);
    assertEquals("eBAyeGv0exc", pr.getId());
    assertNotBlank(pr.getName());
    assertNotBlank(pr.getShortName());
    assertNotNull(pr.getSharing());
    assertNotNull(pr.getAccess());
    assertNotEmpty(pr.getOrganisationUnits());
    assertNotNull(pr.getVersion());
    assertNotNull(pr.getDisplayIncidentDate());
    assertNotNull(pr.getOnlyEnrollOnce());
    assertNotNull(pr.getDisplayFrontPageList());
    assertNotNull(pr.getMinAttributesRequiredToSearch());
    assertNotNull(pr.getMaxTeiCountToReturn());
    assertNotNull(pr.getAccessLevel());

    ProgramStage ps = objects.getProgramStages().get(0);

    assertNotNull(ps);
    assertEquals("Zj7UnCAulEk", ps.getId());
    assertNotBlank(ps.getName());
    assertNotNull(ps.getProgramStageSections());
  }

  @Test
  void testGetProgramInpatientCase() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    Program pr = dhis2.getProgram("eBAyeGv0exc");

    assertNotNull(pr);
    assertEquals("eBAyeGv0exc", pr.getId());
    assertNotBlank(pr.getName());
    assertNotBlank(pr.getShortName());
    assertNotNull(pr.getSharing());
    assertNotNull(pr.getAccess());
    assertNotEmpty(pr.getOrganisationUnits());
    assertNotNull(pr.getProgramStages());
    assertNotEmpty(pr.getProgramStageSections());
    assertNotNull(pr.getVersion());
    assertNotNull(pr.getDisplayIncidentDate());
    assertNotNull(pr.getOnlyEnrollOnce());
    assertNotNull(pr.getDisplayFrontPageList());
    assertNotNull(pr.getMinAttributesRequiredToSearch());
    assertNotNull(pr.getMaxTeiCountToReturn());
    assertNotNull(pr.getAccessLevel());

    OrgUnit ou = pr.getOrganisationUnits().get(0);

    assertNotNull(ou);
    assertNotBlank(ou.getId());

    ProgramStage ps = pr.getProgramStages().get(0);

    assertNotNull(ps);
    assertEquals("Zj7UnCAulEk", ps.getId());
    assertNotBlank(ps.getName());
    assertNotNull(ps.getProgramStageSections());

    ProgramStageSection pss = ps.getProgramStageSections().get(0);

    assertNotNull(pss);
    assertEquals("d7ZILSbPgYh", pss.getId());
    assertNotBlank(pss.getName());
    assertNotBlank(pss.getDescription());
    assertNotNull(pss.getSortOrder());
    assertNotEmpty(pss.getDataElements());
    assertNotNull(pss.getProgramIndicators());

    DataElement de = pss.getDataElements().get(0);

    assertNotNull(de);
    assertEquals("oZg33kd9taw", de.getId());
    assertNotBlank(de.getName());
    assertNotBlank(de.getShortName());

    ProgramIndicator pi = pss.getProgramIndicators().get(0);

    assertNotNull(pi);
    assertEquals("x7PaHGvgWY2", pi.getId());
    assertNotBlank(pi.getName());
    assertNotBlank(pi.getShortName());
  }

  @Test
  void testGetProgramObjectsMalariaCaseDiagnosis() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    ProgramObjects objects = dhis2.getProgramObjects("qDkgAbB5Jlk");

    assertNotEmpty(objects.getPrograms());
    assertNotEmpty(objects.getProgramSections());
    assertNotEmpty(objects.getProgramStages());

    Program pr = objects.getPrograms().get(0);

    assertNotNull(pr);
    assertEquals("qDkgAbB5Jlk", pr.getId());
    assertNotBlank(pr.getName());
    assertNotBlank(pr.getShortName());
    assertNotNull(pr.getSharing());
    assertNotEmpty(pr.getOrganisationUnits());
    assertNotNull(pr.getVersion());
    assertNotNull(pr.getDisplayIncidentDate());
    assertNotNull(pr.getOnlyEnrollOnce());
    assertNotNull(pr.getDisplayFrontPageList());
    assertNotNull(pr.getMinAttributesRequiredToSearch());
    assertNotNull(pr.getMaxTeiCountToReturn());
    assertNotNull(pr.getAccessLevel());

    ProgramSection sc = objects.getProgramSections().get(0);

    assertNotNull(sc);
    assertNotBlank(sc.getId());
    assertNotBlank(sc.getName());
    assertNotNull(sc.getProgram());
    assertNotBlank(sc.getProgram().getId());
    assertNotNull(sc.getSortOrder());
    assertEquals("qDkgAbB5Jlk", sc.getProgram().getId());

    ProgramStage ps = objects.getProgramStages().get(0);

    assertNotNull(ps);
    assertEquals("hYyB7FUS5eR", ps.getId());
    assertNotBlank(ps.getName());
  }

  @Test
  void testGetProgramMalariaCaseDiagnosis() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    Program pr = dhis2.getProgram("qDkgAbB5Jlk");

    assertNotNull(pr);
    assertEquals("qDkgAbB5Jlk", pr.getId());
    assertNotBlank(pr.getName());
    assertNotBlank(pr.getShortName());
    assertNotNull(pr.getSharing());
    assertNotEmpty(pr.getOrganisationUnits());
    assertNotEmpty(pr.getProgramStages());
    assertNotEmpty(pr.getProgramStageSections());
    assertNotNull(pr.getVersion());
    assertNotNull(pr.getDisplayIncidentDate());
    assertNotNull(pr.getOnlyEnrollOnce());
    assertNotNull(pr.getDisplayFrontPageList());
    assertNotNull(pr.getMinAttributesRequiredToSearch());
    assertNotNull(pr.getMaxTeiCountToReturn());
    assertNotNull(pr.getAccessLevel());

    OrgUnit ou = pr.getOrganisationUnits().get(0);

    assertNotNull(ou);
    assertNotBlank(ou.getId());

    List<ProgramSection> sections = pr.getProgramSections();
    assertNotEmpty(sections);

    ProgramSection sc = sections.get(0);
    assertNotNull(sc);
    assertNotBlank(sc.getId());
    assertNotBlank(sc.getName());
    assertNotNull(sc.getProgram());
    assertNotBlank(sc.getProgram().getId());
    assertNotNull(sc.getSortOrder());
    assertEquals("qDkgAbB5Jlk", sc.getProgram().getId());

    List<TrackedEntityAttribute> attributes = sc.getTrackedEntityAttributes();
    assertNotNull(attributes);
    assertNotEmpty(attributes);

    TrackedEntityAttribute tea = attributes.get(0);
    assertNotNull(tea);
    assertNotBlank(tea.getId());
    assertNotBlank(tea.getName());

    ProgramStage ps = pr.getProgramStages().get(0);

    assertNotNull(ps);
    assertEquals("hYyB7FUS5eR", ps.getId());
    assertNotBlank(ps.getName());
  }

  @Test
  void testGetProgramsExpandAssociations() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    List<Program> programs =
        dhis2.getPrograms(
            Query.instance().withExpandAssociations().addFilter(Filter.eq("id", "IpHINAT79UW")));

    assertNotNull(programs);
    assertSize(1, programs);
    assertNotNull(programs.get(0));
    assertEquals("IpHINAT79UW", programs.get(0).getId());

    Program program = programs.get(0);

    assertNotNull(program);
    assertEquals("IpHINAT79UW", program.getId());
    assertEquals(ProgramType.WITH_REGISTRATION, program.getProgramType());

    assertNotEmpty(program.getTrackedEntityAttributes());
    assertNotNull(program.getTrackedEntityAttributes().get(0));

    assertNotEmpty(program.getProgramTrackedEntityAttributes());
    assertNotNull(program.getProgramTrackedEntityAttributes().get(0));
    assertNotNull(program.getProgramTrackedEntityAttributes().get(0).getId());

    TrackedEntityAttribute tea =
        program.getProgramTrackedEntityAttributes().get(0).getTrackedEntityAttribute();

    assertNotNull(tea);
    assertNotNull(tea.getId());
    assertNotNull(tea.getValueType());

    assertNotEmpty(program.getProgramStages());

    ProgramStage ps = program.getProgramStages().get(0);

    assertNotNull(ps);
    assertNotNull(ps.getId());
    assertNotBlank(ps.getName());
    assertNotEmpty(ps.getProgramStageDataElements());

    ProgramStageDataElement psde = ps.getProgramStageDataElements().get(0);

    assertNotNull(psde);
    assertNotNull(psde.getId());

    DataElement de = psde.getDataElement();

    assertNotNull(de);
    assertNotNull(de.getId());
    assertNotNull(de.getAggregationType());
    assertNotNull(de.getValueType());
  }

  @Test
  void testGetPrograms() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    List<Program> programs = dhis2.getPrograms(Query.instance());

    assertNotEmpty(programs);
    assertNotNull(programs.get(0));
  }

  @Test
  void testGetProgramsWithFilter() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    List<Program> programs =
        dhis2.getPrograms(
            Query.instance()
                .addFilter(Filter.in("id", List.of("WSGAb5XwJ3Y", "M3xtLkYBlKI", "IpHINAT79UW")))
                .setOrder(Order.asc("id")));

    assertSize(3, programs);
    assertEquals("IpHINAT79UW", programs.get(0).getId());
    assertEquals("M3xtLkYBlKI", programs.get(1).getId());
    assertEquals("WSGAb5XwJ3Y", programs.get(2).getId());
  }
}
