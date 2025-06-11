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
import static org.hisp.dhis.support.Assertions.assertSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import org.hisp.dhis.model.DataElement;
import org.hisp.dhis.model.Program;
import org.hisp.dhis.model.ProgramIndicator;
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
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class ProgramApiTest {
  @Test
  void testGetProgramChildProgram() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    Program pr = dhis2.getProgram("IpHINAT79UW");

    assertNotNull(pr);
    assertEquals("IpHINAT79UW", pr.getId());
    assertNotBlank(pr.getName());
    assertNotBlank(pr.getShortName());
    assertNotNull(pr.getCreated());
    assertNotNull(pr.getLastUpdated());
    assertEquals(ProgramType.WITH_REGISTRATION, pr.getProgramType());
    assertNotEmpty(pr.getTrackedEntityTypeAttributes());
    assertNotEmpty(pr.getNonConfidentialTrackedEntityAttributes());
    assertNotEmpty(pr.getTrackedEntityAttributes());
    assertNotEmpty(pr.getNonConfidentialTrackedEntityAttributes());

    TrackedEntityType tet = pr.getTrackedEntityType();
    assertNotNull(tet);
    assertEquals("nEenWmSyUEp", tet.getId());
    assertNotBlank(tet.getName());
    assertNotEmpty(tet.getTrackedEntityTypeAttributes());

    TrackedEntityTypeAttribute teta = tet.getTrackedEntityTypeAttributes().iterator().next();
    assertNotNull(teta);
    assertEquals("Jdd8hMStmvF", teta.getId());
    assertNotNull(teta.getTrackedEntityAttribute());
    assertEquals("lZGmxYbs97q", teta.getTrackedEntityAttribute().getId());
    assertEquals("MMD_PER_ID", teta.getTrackedEntityAttribute().getCode());
    assertNotBlank(teta.getTrackedEntityAttribute().getName());

    assertNotEmpty(pr.getProgramTrackedEntityAttributes());
    assertNotEmpty(pr.getProgramStages());

    ProgramTrackedEntityAttribute ptea = pr.getProgramTrackedEntityAttributes().iterator().next();

    assertNotNull(ptea);
    assertNotBlank(ptea.getId());
    assertNotNull(ptea.getProgram());
    assertNotNull(ptea.getSortOrder());
    assertNotNull(ptea.getDisplayInList());
    assertNotNull(ptea.getMandatory());

    TrackedEntityAttribute tea = ptea.getTrackedEntityAttribute();

    assertNotNull(tea);
    assertNotBlank(tea.getId());

    ProgramStage ps = pr.getProgramStages().iterator().next();

    assertNotNull(ps);
    assertNotBlank(ps.getId());
    assertNotBlank(ps.getName());
    assertNotEmpty(ps.getProgramStageDataElements());

    assertNotEmpty(ps.getDataElements());
    assertNotEmpty(ps.getAnalyticsDataElements());

    ProgramStageDataElement psde = ps.getProgramStageDataElements().iterator().next();

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
  void testGetProgramInpatientCase() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    Program pr = dhis2.getProgram("eBAyeGv0exc");

    assertNotNull(pr);
    assertEquals("eBAyeGv0exc", pr.getId());
    assertNotBlank(pr.getName());
    assertNotBlank(pr.getShortName());
    assertNotNull(pr.getProgramStages());
    assertNotNull(pr.getProgramStageSections());
    assertFalse(pr.getProgramStageSections().isEmpty());

    ProgramStage ps = pr.getProgramStages().get(0);

    assertNotNull(ps);
    assertEquals("Zj7UnCAulEk", ps.getId());
    assertNotNull(ps.getProgramStageSections());

    ProgramStageSection pss = ps.getProgramStageSections().get(0);

    assertNotNull(pss);
    assertEquals("d7ZILSbPgYh", pss.getId());
    assertNotBlank(pss.getName());
    assertNotBlank(pss.getDescription());
    assertNotNull(pss.getSortOrder());
    assertNotNull(pss.getDataElements());
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
  void testGetProgramMalariaCaseDiagnosis() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    Program pr = dhis2.getProgram("qDkgAbB5Jlk");

    assertNotNull(pr);
    assertEquals("qDkgAbB5Jlk", pr.getId());
    assertNotBlank(pr.getName());
    assertNotBlank(pr.getShortName());
    assertNotNull(pr.getProgramStages());
    assertNotNull(pr.getProgramStageSections());
    assertFalse(pr.getProgramStageSections().isEmpty());

    List<ProgramSection> sections = pr.getProgramSections();
    assertNotNull(sections);
    assertNotEmpty(sections);

    ProgramSection section = sections.get(0);
    assertNotBlank(section.getId());
    assertNotBlank(section.getName());
    assertNotNull(section.getProgram());
    assertNotBlank(section.getProgram().getId());
    assertNotNull(section.getSortOrder());
    assertEquals("qDkgAbB5Jlk", section.getProgram().getId());

    List<TrackedEntityAttribute> attributes = section.getTrackedEntityAttributes();
    assertNotNull(attributes);
    assertNotEmpty(attributes);

    TrackedEntityAttribute attribute = attributes.get(0);
    assertNotNull(attribute);
    assertNotBlank(attribute.getId());
    assertNotBlank(attribute.getName());
  }

  @Test
  void testGetPrograms() {
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
