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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import org.hisp.dhis.model.Category;
import org.hisp.dhis.model.CategoryCombo;
import org.hisp.dhis.model.CategoryOption;
import org.hisp.dhis.model.CategoryOptionCombo;
import org.hisp.dhis.model.dimension.DataDimensionType;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class CategoryComboApiTest {
  @Test
  void testGetCategoryCombo() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    CategoryCombo categoryCombo = dhis2.getCategoryCombo("m2jTvAj5kkm");

    assertEquals("m2jTvAj5kkm", categoryCombo.getId());
    assertEquals("Births", categoryCombo.getName());
    assertNotNull(categoryCombo.getCreated());
    assertNotNull(categoryCombo.getLastUpdated());
    assertNotNull(categoryCombo.getSharing());
    assertNotNull(categoryCombo.getAccess());
    assertEquals(DataDimensionType.DISAGGREGATION, categoryCombo.getDataDimensionType());
    assertFalse(categoryCombo.getSkipTotal());

    List<Category> categories = categoryCombo.getCategories();
    assertNotEmpty(categories);

    List<CategoryOptionCombo> categoryOptionCombos = categoryCombo.getCategoryOptionCombos();
    assertNotEmpty(categoryOptionCombos);

    CategoryOptionCombo categoryOptionCombo = categoryOptionCombos.get(0);
    assertFalse(categoryOptionCombo.getIgnoreApproval());
    assertNotEmpty(categoryOptionCombo.getCategoryOptions());

    CategoryOption categoryOption = categoryOptionCombo.getCategoryOptions().iterator().next();
    assertNotNull(categoryOption);
    assertNotBlank(categoryOption.getId());
  }

  @Test
  void testGetCategoryCombos() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    List<CategoryCombo> categoryCombos = dhis2.getCategoryCombos(Query.instance());

    assertNotNull(categoryCombos);
    assertNotEmpty(categoryCombos);

    CategoryCombo categoryCombo = categoryCombos.get(0);
    assertNotNull(categoryCombo.getId());
    assertNotEmpty(categoryCombo.getCategories());
    assertNotEmpty(categoryCombo.getCategoryOptionCombos());
  }

  @Test
  void testGetCategoryCombosExpandAssociations() {
    Dhis2 dhis2 = new Dhis2(TestFixture.V42_CONFIG);

    List<CategoryCombo> categoryCombos =
        dhis2.getCategoryCombos(Query.instance().withExpandAssociations());

    assertNotNull(categoryCombos);
    assertNotEmpty(categoryCombos);

    CategoryCombo categoryCombo = categoryCombos.get(0);
    assertNotNull(categoryCombo.getId());
    assertNotEmpty(categoryCombo.getCategories());
    assertNotEmpty(categoryCombo.getCategoryOptionCombos());
  }
}
