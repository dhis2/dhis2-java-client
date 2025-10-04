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

import static org.hisp.dhis.support.Assertions.assertNotBlank;
import static org.hisp.dhis.support.Assertions.assertNotEmpty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import org.hisp.dhis.model.dimension.DataDimensionType;
import org.hisp.dhis.model.dimension.Dimension;
import org.hisp.dhis.model.dimension.DimensionItem;
import org.hisp.dhis.model.dimension.DimensionType;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class DimensionApiTest {
  @Test
  void getDimensionA() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    Dimension dimension = dhis2.getDimension("EC40NXmsTVu");

    assertNotNull(dimension);
    assertEquals("EC40NXmsTVu", dimension.getId());
    assertEquals("Rural and Urban", dimension.getName());
    assertEquals(DimensionType.CATEGORY, dimension.getDimensionType());
    assertEquals(DataDimensionType.DISAGGREGATION, dimension.getDataDimensionType());
    assertNotEmpty(dimension.getItems());

    DimensionItem item = dimension.getItems().get(0);

    assertNotNull(item);
    assertNotBlank(item.getId());
    assertNotBlank(item.getName());
    assertNotNull(item.getDimensionItemType());
  }

  @Test
  void getDimensionB() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    Dimension dimension = dhis2.getDimension("SooXFOUnciJ");

    assertNotNull(dimension);
    assertEquals("SooXFOUnciJ", dimension.getId());
    assertEquals("Funding Agency", dimension.getName());
    assertNotBlank(dimension.getDescription());
    assertEquals(DimensionType.CATEGORY_OPTION_GROUP_SET, dimension.getDimensionType());
    assertEquals(DataDimensionType.ATTRIBUTE, dimension.getDataDimensionType());
    assertNotEmpty(dimension.getItems());

    DimensionItem item = dimension.getItems().get(0);

    assertNotNull(item);
    assertNotBlank(item.getId());
    assertNotBlank(item.getName());
    assertNotNull(item.getDimensionItemType());
  }

  @Test
  void getDimensions() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    List<Dimension> dimensions = dhis2.getDimensions(Query.instance());

    assertNotEmpty(dimensions);
  }
}
