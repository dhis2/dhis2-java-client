/*
 * Copyright (c) 2004-2026, University of Oslo
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

import static org.hisp.dhis.support.Assertions.*;
import static org.hisp.dhis.support.Assertions.assertNotEmpty;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.hisp.dhis.model.Program;
import org.hisp.dhis.model.metadata.Metadata;
import org.hisp.dhis.model.visualization.event.EventVisualization;
import org.hisp.dhis.model.visualization.event.EventVisualizationType;
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
public class EventVisualizationApiTest {
  @Test
  void testGetVisualization() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    // "Child health: Active status cases by gender last 12 months", a PIVOT_TABLE event
    // visualization with a populated attributeDimensions, program and programStage.
    EventVisualization visualization = dhis2.getEventVisualization("YZzuVprU7aZ");

    assertNotNull(visualization);
    assertEquals("YZzuVprU7aZ", visualization.getId());
    assertNotBlank(visualization.getName());
    assertNotNull(visualization.getCreated());
    assertNotNull(visualization.getCreatedBy());
    assertNotNull(visualization.getLastUpdated());
    assertNotNull(visualization.getSharing());
    assertNotNull(visualization.getAccess());
    assertNotNull(visualization.getType());

    assertNotEmpty(visualization.getColumnDimensions());
    assertNotEmpty(visualization.getRowDimensions());

    assertNotNull(visualization.getProgram());
    assertNotBlank(visualization.getProgram().getId());
    assertNotNull(visualization.getProgramStage());
    assertNotBlank(visualization.getProgramStage().getId());
    assertNotNull(visualization.getOutputType());
    assertNotNull(visualization.getDigitGroupSeparator());
    assertNotNull(visualization.getRelativePeriods());

    assertNotEmpty(visualization.getAttributeDimensions());
    assertNotNull(visualization.getAttributeDimensions().get(0).getAttribute());
    assertNotBlank(visualization.getAttributeDimensions().get(0).getAttribute().getId());

    assertNotEmpty(visualization.getColumns());
    assertNotEmpty(visualization.getRows());
  }

  @Test
  void testGetVisualizationWithDataElementDimensions() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    // "Inpatient: Cases 5 to 15 years this year (case)", a LINE_LIST event visualization with a
    // populated dataElementDimensions. attributeDimensions is empty for this object, so this
    // second fixture is needed to cover the fields that the pivot table fixture above does not.
    EventVisualization visualization = dhis2.getEventVisualization("TIuOzZ0ID0V");

    assertNotNull(visualization);
    assertEquals("TIuOzZ0ID0V", visualization.getId());
    assertNotNull(visualization.getType());

    assertNotEmpty(visualization.getDataElementDimensions());
    assertNotNull(visualization.getDataElementDimensions().get(0).getDataElement());
    assertNotBlank(visualization.getDataElementDimensions().get(0).getDataElement().getId());
  }

  @Test
  void testGetEventVisualizations() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    List<EventVisualization> visualizations =
        dhis2.getEventVisualizations(Query.instance().addFilter(Filter.like("name", "Child")));

    assertNotEmpty(visualizations);
    assertFirstNotNull(visualizations);
    assertNotNull(visualizations.get(0).getId());
  }

  @Test
  void testGetEventVisualizationsPaged() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    Metadata<EventVisualization> metadata = dhis2.getEventVisualizationsPaged(Query.instance());

    assertNotNull(metadata);
    assertNotNull(metadata.getPager());
    assertEquals(1, metadata.getPager().getPage());
    assertNotEmpty(metadata.getObjects());
  }

  @Test
  void testSaveRemoveEventVisualization() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    String uidA = UidUtils.generateUid();
    EventVisualization vz = new EventVisualization();
    vz.setId(uidA);
    vz.setName(uidA);
    vz.setType(EventVisualizationType.LINE_LIST);
    // "Child Programme".
    vz.setProgram(new Program("IpHINAT79UW", null));

    ObjectResponse createResponse = dhis2.saveEventVisualization(vz);

    assertNotNull(createResponse);
    assertEquals(Status.OK, createResponse.getStatus());
    assertEquals(HttpStatus.CREATED, createResponse.getHttpStatus());
    assertEquals(201, createResponse.getHttpStatusCode());

    assertTrue(dhis2.isEventVisualization(uidA));

    ObjectResponse removeResponse = dhis2.removeEventVisualization(uidA);

    assertNotNull(removeResponse);
    assertEquals(Status.OK, removeResponse.getStatus());

    assertEquals(Status.OK, removeResponse.getStatus());
    assertEquals(HttpStatus.OK, removeResponse.getHttpStatus());
    assertEquals(200, removeResponse.getHttpStatusCode());

    assertFalse(dhis2.isEventVisualization(uidA));
  }
}
