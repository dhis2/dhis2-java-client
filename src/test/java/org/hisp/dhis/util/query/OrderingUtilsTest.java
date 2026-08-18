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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.hisp.dhis.query.Order;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.UNIT)
class OrderingUtilsTest {
  @Test
  void testAsOrder() {
    assertNull(OrderingUtils.asOrder(null));

    orderEquals(Order.asc("name"), OrderingUtils.asOrder("name:asc"));
    orderEquals(Order.desc("name"), OrderingUtils.asOrder("name:desc"));
    orderEquals(Order.asc("description"), OrderingUtils.asOrder("description:asc"));
    orderEquals(Order.desc("termsOfUse"), OrderingUtils.asOrder("termsOfUse:desc"));
  }

  @Test
  void testAsString() {
    assertNull(OrderingUtils.asString(null));

    assertEquals("name:asc", OrderingUtils.asString(Order.asc("name")));
    assertEquals("name:desc", OrderingUtils.asString(Order.desc("name")));
    assertEquals("description:asc", OrderingUtils.asString(Order.asc("description")));
    assertEquals("termsOfUse:desc", OrderingUtils.asString(Order.desc("termsOfUse")));
  }

  @Test
  void testInvalidOrderFormat() {
    IllegalArgumentException ex =
        assertThrows(IllegalArgumentException.class, () -> OrderingUtils.asOrder("name"));
    assertEquals("Order must be on the format '{property}:{direction}': 'name'", ex.getMessage());
  }

  @Test
  void testInvalidOrderDirection() {
    IllegalArgumentException ex =
        assertThrows(IllegalArgumentException.class, () -> OrderingUtils.asOrder("name:sideways"));
    assertEquals("Direction is invalid: 'sideways'", ex.getMessage());
  }

  private void orderEquals(Order expected, Order actual) {
    assertEquals(expected.getDirection(), actual.getDirection());
    assertEquals(expected.getProperty(), actual.getProperty());
  }
}
