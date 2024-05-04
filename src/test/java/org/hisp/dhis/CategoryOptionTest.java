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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import org.hisp.dhis.model.CategoryOption;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class CategoryOptionTest {
  @Test
  void testGetCategoryOption() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    CategoryOption categoryOption = dhis2.getCategoryOption("jRbMi0aBjYn");

    assertEquals("Male", categoryOption.getName());
    assertEquals("MLE", categoryOption.getCode());
  }

  @Test
  void testUpdateCategoryOption() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    Date startDate =
        Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    CategoryOption categoryOption = dhis2.getCategoryOption("jRbMi0aBjYn");
    categoryOption.setStartDate(startDate);

    dhis2.updateCategoryOption(categoryOption);

    categoryOption = dhis2.getCategoryOption("jRbMi0aBjYn");
    assertEquals(startDate, categoryOption.getStartDate());
  }
}
