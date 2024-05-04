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
package org.hisp.dhis.util;

import static org.hisp.dhis.util.CollectionUtils.firstMatch;
import static org.hisp.dhis.util.CollectionUtils.list;
import static org.hisp.dhis.util.CollectionUtils.mutableList;
import static org.hisp.dhis.util.CollectionUtils.mutableSet;
import static org.hisp.dhis.util.CollectionUtils.set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CollectionUtilsTest {
  @Test
  void testFirstMatch() {
    List<String> collection = list("a", "b", "c");

    assertEquals("a", firstMatch(collection, (v) -> "a".equals(v)));
    assertEquals("b", firstMatch(collection, (v) -> "b".equals(v)));
    assertNull(firstMatch(collection, (v) -> "x".equals(v)));
  }

  @Test
  void testSetAcceptsNull() {
    Set<String> set = set("a", null, "b");

    assertEquals(3, set.size());
  }

  @Test
  void testMutableSetAcceptsNull() {
    Set<String> set = mutableSet("a", null, "b");

    assertEquals(3, set.size());
  }

  @Test
  void testListAcceptsNull() {
    List<String> list = list("a", null, "b");

    assertEquals(3, list.size());
  }

  @Test
  void testMutableListAcceptsNull() {
    List<String> list = mutableList("a", null, "b");

    assertEquals(3, list.size());
  }
}
