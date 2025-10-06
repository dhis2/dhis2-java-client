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

import static org.hisp.dhis.support.Assertions.assertFirstNotNull;
import static org.hisp.dhis.support.Assertions.assertNotBlank;
import static org.hisp.dhis.support.Assertions.assertNotEmpty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.hisp.dhis.model.metadata.Metadata;
import org.hisp.dhis.model.visualization.Visualization;
import org.hisp.dhis.model.visualization.VisualizationType;
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
class VisualizationApiTest {
  @Test
  void testGetVisualizationA() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    Visualization visualization = dhis2.getVisualization("UlfTKWZWV4u");

    assertNotNull(visualization);
    assertEquals("UlfTKWZWV4u", visualization.getId());
    assertNotBlank(visualization.getName());
    assertNotNull(visualization.getCreated());
    assertNotNull(visualization.getLastUpdated());
    assertNotNull(visualization.getSharing());
    assertNotNull(visualization.getAccess());
    assertNotNull(visualization.getType());

    assertNotEmpty(visualization.getColumnDimensions());
    assertNotEmpty(visualization.getRowDimensions());

    assertNotEmpty(visualization.getDataDimensionItems());
    assertTrue(visualization.isUserOrganisationUnit());
  }

  @Test
  void testGetVisualizationB() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    Visualization visualization = dhis2.getVisualization("hrDweynvx7G");

    assertNotNull(visualization);
    assertEquals("hrDweynvx7G", visualization.getId());
    assertNotBlank(visualization.getName());
    assertNotNull(visualization.getCreated());
    assertNotNull(visualization.getLastUpdated());
    assertNotNull(visualization.getSharing());
    assertNotNull(visualization.getAccess());
    assertNotNull(visualization.getType());

    assertNotEmpty(visualization.getColumnDimensions());
    assertNotEmpty(visualization.getRowDimensions());

    assertNotEmpty(visualization.getDataDimensionItems());
    assertNotEmpty(visualization.getOrganisationUnits());
  }

  @Test
  void testGetVisualizations() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    List<Visualization> visualizations =
        dhis2.getVisualizations(Query.instance().addFilter(Filter.like("name", "ANC")));

    assertNotEmpty(visualizations);
    assertFirstNotNull(visualizations);
    assertNotNull(visualizations.get(0).getId());
  }

  @Test
  void testGetVisualizationsPaged() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    Metadata<Visualization> metadata = dhis2.getVisualizationsPaged(Query.instance());

    assertNotNull(metadata);
    assertNotNull(metadata.getPager());
    assertEquals(1, metadata.getPager().getPage());
    assertNotEmpty(metadata.getObjects());
  }

  @Test
  void testSaveRemoveVisualization() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    String uidA = UidUtils.generateUid();
    Visualization vz = new Visualization(uidA, uidA, VisualizationType.PIVOT_TABLE);

    ObjectResponse createResponse = dhis2.saveVisualization(vz);

    assertNotNull(createResponse);
    assertEquals(Status.OK, createResponse.getStatus());
    assertEquals(HttpStatus.CREATED, createResponse.getHttpStatus());
    assertEquals(201, createResponse.getHttpStatusCode());

    assertTrue(dhis2.isVisualization(uidA));

    ObjectResponse removeResponse = dhis2.removeVisualization(uidA);

    assertNotNull(removeResponse);
    assertEquals(Status.OK, removeResponse.getStatus());

    assertEquals(Status.OK, removeResponse.getStatus());
    assertEquals(HttpStatus.OK, removeResponse.getHttpStatus());
    assertEquals(200, removeResponse.getHttpStatusCode());

    assertFalse(dhis2.isVisualization(uidA));
  }
}
