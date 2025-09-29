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

import static org.hisp.dhis.support.Assertions.assertNotEmpty;
import static org.hisp.dhis.support.Assertions.assertSuccessResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.hisp.dhis.model.Indicator;
import org.hisp.dhis.model.IndicatorType;
import org.hisp.dhis.model.metadata.Metadata;
import org.hisp.dhis.query.Filter;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.response.HttpStatus;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.support.TestTags;
import org.hisp.dhis.util.UidUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class IndicatorApiTest {
  @Test
  void testGetIndicator() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    Indicator indicator = dhis2.getIndicator("dwEq7wi6nXV");
    assertNotNull(indicator);
    assertEquals("dwEq7wi6nXV", indicator.getId());
    assertEquals("IN_52496", indicator.getCode());
    assertEquals("ANC IPT 1 Coverage", indicator.getName());
    assertNotNull(indicator.getSharing());
    assertNotNull(indicator.getAccess());
    assertEquals("ANC IPT 1 Coverage", indicator.getShortName());
    assertNotNull(indicator.getCreated());
    assertNotNull(indicator.getLastUpdated());
    assertFalse(indicator.isAnnualized());
    assertNotNull(indicator.getUrl());

    IndicatorType indicatorType = indicator.getIndicatorType();
    assertNotNull(indicatorType.getId());
    assertNotNull(indicatorType.getName());

    assertNotNull(indicator.getNumerator());
    assertEquals("IPT 1st dose total given", indicator.getNumeratorDescription());
    assertNotNull(indicator.getDenominator());
    assertEquals("ANC 1st visit total", indicator.getDenominatorDescription());
  }

  @Test
  void testGetIndicatorsWithInFilter() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    Query query =
        Query.instance()
            .addFilter(Filter.in("id", List.of("Uvn6LCg7dVU", "OdiHJayrsKo", "sB79w2hiLp8")));

    List<Indicator> indicators = dhis2.getIndicators(query);

    assertNotEmpty(indicators);
    assertEquals(3, indicators.size());
  }

  @Test
  void testGetIndicatorsPaged() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    Metadata<Indicator> metadata = dhis2.getIndicatorsPaged(Query.instance());

    assertNotNull(metadata);
    assertNotNull(metadata.getPager());
    assertNotEmpty(metadata.getObjects());
  }

  @Test
  void testCreateUpdateAndDeleteIndicator() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);
    String uidA = UidUtils.generateUid();
    String uidB = UidUtils.generateUid();

    Indicator indicator = new Indicator();
    indicator.setName(uidA);
    indicator.setCode(uidA);
    indicator.setShortName(uidA);
    indicator.setDescription(uidA);
    indicator.setNumerator("Sample numerator");
    indicator.setDenominator("Sample denominator");
    indicator.setAnnualized(true);
    indicator.setNumeratorDescription("Sample numerator description");
    indicator.setDenominatorDescription("Sample denominator description");
    indicator.setUrl(TestFixture.DEFAULT_URL);
    indicator.setIndicatorType(dhis2.getIndicatorType("bWuNrMHEoZ0"));

    // Create
    ObjectResponse createRespA = dhis2.saveIndicator(indicator);

    assertSuccessResponse(createRespA, HttpStatus.CREATED, 201);

    String indicatorUid = createRespA.getResponse().getUid();

    // Get
    indicator = dhis2.getIndicator(indicatorUid);

    assertNotNull(indicator);
    assertEquals(indicatorUid, indicator.getId());
    assertEquals(uidA, indicator.getName());
    assertEquals(uidA, indicator.getCode());
    assertEquals(uidA, indicator.getShortName());
    assertEquals(uidA, indicator.getDescription());
    assertEquals("Sample numerator", indicator.getNumerator());
    assertEquals("Sample denominator", indicator.getDenominator());
    assertTrue(indicator.isAnnualized());
    assertEquals("Sample numerator description", indicator.getNumeratorDescription());
    assertEquals("Sample denominator description", indicator.getDenominatorDescription());
    assertEquals(TestFixture.DEFAULT_URL, indicator.getUrl());
    assertNotNull(indicator.getCreated());
    assertNotNull(indicator.getLastUpdated());
    assertNotNull(indicator.getIndicatorType());

    indicator.setName(uidB);

    // Update
    ObjectResponse updateRespA = dhis2.updateIndicator(indicator);

    assertSuccessResponse(updateRespA, HttpStatus.OK, 200);
    assertEquals(indicatorUid, updateRespA.getResponse().getUid(), updateRespA.toString());

    // Get Updated
    indicator = dhis2.getIndicator(indicatorUid);

    assertNotNull(indicator);
    assertEquals(indicatorUid, indicator.getId());
    assertEquals(uidB, indicator.getName());

    // Remove
    ObjectResponse removeRespA = dhis2.removeIndicator(indicatorUid);

    assertSuccessResponse(removeRespA, HttpStatus.OK, 200);
  }
}
