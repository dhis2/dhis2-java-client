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
package org.hisp.dhis.util.query;

import static org.hisp.dhis.query.Operator.EQ;
import static org.hisp.dhis.query.Operator.GT;
import static org.hisp.dhis.query.Operator.ILIKE;
import static org.hisp.dhis.query.Operator.IN;
import static org.hisp.dhis.query.Operator.LT;
import static org.hisp.dhis.query.Operator.TOKEN;
import static org.hisp.dhis.util.query.FilterUtils.asFilter;
import static org.hisp.dhis.util.query.FilterUtils.asString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.hisp.dhis.query.Filter;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.UNIT)
class FilterUtilsTest {
  @Test
  void testAsFilter() {
    Filter filter = asFilter("name:eq:DHIS2");
    assertEquals("name", filter.getProperty());
    assertEquals(EQ, filter.getOperator());
    assertEquals("DHIS2", filter.getValue());

    filter = asFilter("weight:gt:2");
    assertEquals("weight", filter.getProperty());
    assertEquals(GT, filter.getOperator());
    assertEquals("2", filter.getValue());

    filter = asFilter("retryAttempts:lt:3");
    assertEquals("retryAttempts", filter.getProperty());
    assertEquals(LT, filter.getOperator());
    assertEquals("3", filter.getValue());

    filter = asFilter("name:ilike:mae.ngo.org: Exit interview");
    assertEquals("name", filter.getProperty());
    assertEquals(ILIKE, filter.getOperator());
    assertEquals("mae.ngo.org: Exit interview", filter.getValue());

    filter = asFilter("name:in:[mae,ngo,org]");
    assertEquals("name", filter.getProperty());
    assertEquals(IN, filter.getOperator());
    assertEquals("[mae,ngo,org]", filter.getValue());
  }

  @Test
  void testAsString() {
    Filter filter = new Filter("name", EQ, "DHIS2");
    assertEquals("name:eq:DHIS2", asString(filter));

    filter = new Filter("weight", GT, 2);
    assertEquals("weight:gt:2", asString(filter));

    filter = new Filter("retryAttempts", LT, 3);
    assertEquals("retryAttempts:lt:3", asString(filter));

    filter = new Filter("name", ILIKE, "mae.ngo.org: Exit interview");
    assertEquals("name:ilike:mae.ngo.org: Exit interview", asString(filter));

    filter = new Filter("name", TOKEN, "Exit interview");
    assertEquals("name:token:Exit interview", asString(filter));

    filter = new Filter("name", IN, List.of("mae", "lta"));
    assertEquals("name:in:[mae,lta]", asString(filter));

    filter = new Filter("name", IN, "[mae,lta]");
    assertEquals("name:in:[mae,lta]", asString(filter));
  }

  @Test
  void testInvalidFilterFormat() {
    IllegalArgumentException ex =
        assertThrows(IllegalArgumentException.class, () -> asFilter("disabled:true"));
    assertEquals(
        "Filter must be on the format '{property}:{operator}:{value}': 'disabled:true'",
        ex.getMessage());
  }

  @Test
  void testInvalidFilterOperator() {
    IllegalArgumentException ex =
        assertThrows(IllegalArgumentException.class, () -> asFilter("name:notanoperator:MySQL"));
    assertEquals("Filter operator is invalid: 'notanoperator'", ex.getMessage());
  }
}
