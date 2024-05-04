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
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import org.hisp.dhis.model.DataElement;
import org.hisp.dhis.model.Program;
import org.hisp.dhis.model.ProgramStage;
import org.hisp.dhis.model.ProgramStageDataElement;
import org.hisp.dhis.model.ProgramType;
import org.hisp.dhis.query.Filter;
import org.hisp.dhis.query.Order;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
public class ProgramApiTest {
  @Test
  void testGetProgram() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    Program pr = dhis2.getProgram("IpHINAT79UW");

    assertNotNull(pr);
    assertEquals("IpHINAT79UW", pr.getId());
    assertNotBlank(pr.getShortName());
    assertNotBlank(pr.getName());
    assertNotNull(pr.getCreated());
    assertNotNull(pr.getLastUpdated());
    assertEquals(ProgramType.WITH_REGISTRATION, pr.getProgramType());
    assertNotEmpty(pr.getProgramStages());

    ProgramStage ps = pr.getProgramStages().iterator().next();

    assertNotNull(ps);
    assertNotBlank(ps.getId());
    assertNotBlank(ps.getName());
    assertNotEmpty(ps.getProgramStageDataElements());

    ProgramStageDataElement psde = ps.getProgramStageDataElements().iterator().next();

    assertNotNull(psde);

    assertNotNull(psde.getDataElement());
    assertNotNull(psde.getCompulsory());
    assertNotNull(psde.getDisplayInReports());
    assertNotNull(psde.getSkipSynchronization());
    assertNotNull(psde.getSkipAnalytics());

    DataElement de = psde.getDataElement();

    assertNotNull(de);
    assertNotBlank(de.getId());
    assertNotBlank(de.getShortName());
    assertNotBlank(de.getName());
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
