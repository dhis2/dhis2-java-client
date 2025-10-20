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

import static org.hisp.dhis.support.Assertions.assertContainsExactly;
import static org.hisp.dhis.util.CollectionUtils.anyStartsWith;
import static org.hisp.dhis.util.CollectionUtils.filterToList;
import static org.hisp.dhis.util.CollectionUtils.filterToSet;
import static org.hisp.dhis.util.CollectionUtils.first;
import static org.hisp.dhis.util.CollectionUtils.firstMatch;
import static org.hisp.dhis.util.CollectionUtils.get;
import static org.hisp.dhis.util.CollectionUtils.index;
import static org.hisp.dhis.util.CollectionUtils.list;
import static org.hisp.dhis.util.CollectionUtils.mapJoin;
import static org.hisp.dhis.util.CollectionUtils.mapToCommaSeparated;
import static org.hisp.dhis.util.CollectionUtils.mapToList;
import static org.hisp.dhis.util.CollectionUtils.mapToMap;
import static org.hisp.dhis.util.CollectionUtils.mapToSet;
import static org.hisp.dhis.util.CollectionUtils.mutableList;
import static org.hisp.dhis.util.CollectionUtils.mutableSet;
import static org.hisp.dhis.util.CollectionUtils.set;
import static org.hisp.dhis.util.CollectionUtils.toCommaSeparated;
import static org.hisp.dhis.util.CollectionUtils.toTypedList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hisp.dhis.model.DataElement;
import org.hisp.dhis.model.IdentifiableObject;
import org.hisp.dhis.model.Product;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.UNIT)
class CollectionUtilsTest {
  @Test
  void testIndex() {
    Product pA = new Product("P01", "Keyboard");
    Product pB = new Product("P02", "Mouse");
    Product pC = new Product("P03", "Monitor");

    List<Product> list = list(pA, pB, pC);

    Map<String, Product> map = index(list, value -> value.getId());

    assertEquals(3, map.keySet().size());
    assertEquals(pA, map.get("P01"));
    assertEquals(pB, map.get("P02"));
    assertEquals(pC, map.get("P03"));
    assertNull(map.get("P99"));
  }

  @Test
  void testFirstMatch() {
    List<String> list = list("a", "b", "c");

    assertEquals("a", firstMatch(list, (v) -> "a".equals(v)));
    assertEquals("b", firstMatch(list, (v) -> "b".equals(v)));
    assertNull(firstMatch(list, (v) -> "x".equals(v)));
  }

  @Test
  void testFilterToList() {
    Product pA = new Product("P01", "Keyboard");
    Product pB = new Product("P02", "Mouse");
    Product pC = new Product("P03", "Monitor");

    List<Product> list = List.of(pA, pB, pC);

    assertContainsExactly(filterToList(list, p -> "P01".equals(p.getId())), pA);
    assertContainsExactly(
        filterToList(list, p -> Set.of("P01", "P03").contains(p.getId())), pA, pC);
    assertContainsExactly(
        filterToList(list, p -> Set.of("P02", "P03").contains(p.getId())), pB, pC);
    assertContainsExactly(
        filterToList(list, p -> mutableSet("P02", null, "P03").contains(p.getId())), pB, pC);
    assertContainsExactly(
        filterToList(list, p -> mutableSet(null, "P01", "P02").contains(p.getId())), pA, pB);
  }

  @Test
  void testFilterToSet() {
    Product pA = new Product("P01", "Keyboard");
    Product pB = new Product("P02", "Mouse");
    Product pC = new Product("P03", "Monitor");

    List<Product> list = List.of(pA, pB, pC);

    assertContainsExactly(filterToSet(list, p -> "P01".equals(p.getId())), pA);
    assertContainsExactly(filterToSet(list, p -> Set.of("P01", "P03").contains(p.getId())), pA, pC);
    assertContainsExactly(filterToSet(list, p -> Set.of("P02", "P03").contains(p.getId())), pB, pC);
    assertContainsExactly(
        filterToSet(list, p -> mutableSet("P02", null, "P03").contains(p.getId())), pB, pC);
    assertContainsExactly(
        filterToSet(list, p -> mutableSet(null, "P01", "P02").contains(p.getId())), pA, pB);
  }

  @Test
  void testAnyStartsWith() {
    List<String> list =
        list("/ImspTQPwCqd/jUb8gELQApl", "/ImspTQPwCqd/qhqAxPSTUXp", "/ImspTQPwCqd/Vth0fbpFcsO");

    assertTrue(anyStartsWith(list, "/ImspTQPwCqd/jUb8gELQApl"));
    assertTrue(anyStartsWith(list, "/ImspTQPwCqd/jUb8gELQApl/U6Kr7Gtpidn"));
    assertTrue(anyStartsWith(list, "/ImspTQPwCqd/qhqAxPSTUXp/RndxKqQGzUl/aSfF9kuNINJ"));
    assertTrue(anyStartsWith(list, "/ImspTQPwCqd/Vth0fbpFcsO/U6Kr7Gtpidn/rx9ubw0UCqj"));

    assertFalse(anyStartsWith(list, "/MpcMjLmbATv"));
    assertFalse(anyStartsWith(list, "/ImspTQPwCqd/at6UHUQatSo/qtr8GGlm4gg"));
    assertFalse(anyStartsWith(list, "/ImspTQPwCqd/PMa2VCrupOd/FlBemv1NfEC/rxc497GUdDt"));
    assertFalse(anyStartsWith(list, null));
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

  @Test
  void testGetAtIndex() {
    List<String> list = list("a", "b", "c");

    assertEquals("a", get(list, 0));
    assertEquals("b", get(list, 1));
    assertEquals("c", get(list, 2));

    assertNull(get(list, -1));
    assertNull(get(list, 3));
    assertNull(get(list, 9));
  }

  @Test
  void testMapToList() {
    DataElement deA = new DataElement();
    deA.setId("jUb6fnbZPhV");
    DataElement deB = new DataElement();
    deB.setId("qEiCafULhoW");
    DataElement deC = new DataElement();
    deC.setId("wOahXFjLq4V");

    List<DataElement> list = List.of(deA, deB, deC);

    List<String> expected = List.of("jUb6fnbZPhV", "qEiCafULhoW", "wOahXFjLq4V");

    assertEquals(expected, mapToList(list, DataElement::getId));
  }

  @Test
  void testMapToListNull() {
    assertThrows(
        NullPointerException.class,
        () -> mapToList(List.of("a", null, "c"), str -> str.toUpperCase()));
  }

  @Test
  void testToTypedList() {
    DataElement deA = new DataElement();
    deA.setId("jUb6fnbZPhV");
    DataElement deB = new DataElement();
    deB.setId("qEiCafULhoW");

    List<IdentifiableObject> objects = List.of(deA, deB);

    List<DataElement> dataElements = toTypedList(objects, DataElement.class);

    assertContainsExactly(dataElements, deA, deB);
  }

  @Test
  void testMapToCommaSeparatedObject() {
    Product pA = new Product("P01", "Keyboard");
    Product pB = new Product("P02", "Mouse");
    Product pC = new Product("P03", "Monitor");

    List<Product> list = list(pA, pB, pC);

    String expected = "Keyboard,Mouse,Monitor";

    assertEquals(expected, mapToCommaSeparated(list, Product::getName));
  }

  @Test
  void testMapToCommaSeparatedInteger() {
    List<Integer> list = list(1, 2, 3);

    String expected = "1,2,3";

    assertEquals(expected, mapToCommaSeparated(list, String::valueOf));
  }

  @Test
  void testMapToCommaSeparatedNull() {
    List<Integer> list = list(1, null, 3);

    String expected = "1,3";

    assertEquals(expected, mapToCommaSeparated(list, String::valueOf));
  }

  @Test
  void testMapJoinObject() {
    Product pA = new Product("P01", "Keyboard");
    Product pB = new Product("P02", "Mouse");
    Product pC = new Product("P03", "Monitor");

    List<Product> list = list(pA, pB, pC);

    String expected = "Keyboard or Mouse or Monitor";

    assertEquals(expected, mapJoin(list, Product::getName, " or "));
  }

  @Test
  void testMapJoinInteger() {
    List<Integer> list = list(1, 2, 3);

    String expected = "1 then 2 then 3";

    assertEquals(expected, mapJoin(list, String::valueOf, " then "));
  }

  @Test
  void testMapJoinNull() {
    List<Integer> list = list(1, null, 3);

    String expected = "1 then 3";

    assertEquals(expected, mapJoin(list, String::valueOf, " then "));
  }

  @Test
  void testMapToSet() {
    DataElement deA = new DataElement();
    deA.setId("jUb6fnbZPhV");
    DataElement deB = new DataElement();
    deB.setId("qEiCafULhoW");
    DataElement deC = new DataElement();
    deC.setId("wOahXFjLq4V");

    Set<DataElement> set = Set.of(deA, deB, deC);

    Set<String> expected = Set.of("jUb6fnbZPhV", "qEiCafULhoW", "wOahXFjLq4V");

    assertEquals(expected, mapToSet(set, DataElement::getId));
  }

  @Test
  void testMapToSetNull() {
    assertThrows(
        NullPointerException.class,
        () -> mapToSet(Set.of("a", null, "c"), str -> str.toUpperCase()));
  }

  @Test
  void testMapToMapKeyMapper() {
    DataElement deA = new DataElement();
    deA.setId("jUb6fnbZPhV");
    deA.setName("Red");
    DataElement deB = new DataElement();
    deB.setId("qEiCafULhoW");
    deB.setName("Green");
    DataElement deC = new DataElement();
    deC.setId("wOahXFjLq4V");
    deC.setName("Blue");

    List<DataElement> list = List.of(deA, deB, deC);

    Map<String, DataElement> map = mapToMap(list, DataElement::getId);

    assertEquals(3, map.keySet().size());
    assertEquals(deA.getId(), map.get(deA.getId()).getId());
    assertEquals(deB.getId(), map.get(deB.getId()).getId());
    assertEquals(deC.getId(), map.get(deC.getId()).getId());
  }

  @Test
  void testMapToMapKeyAndValueMapper() {
    DataElement deA = new DataElement();
    deA.setId("jUb6fnbZPhV");
    deA.setName("Red");
    DataElement deB = new DataElement();
    deB.setId("qEiCafULhoW");
    deB.setName("Green");
    DataElement deC = new DataElement();
    deC.setId("wOahXFjLq4V");
    deC.setName("Blue");

    List<DataElement> list = List.of(deA, deB, deC);

    Map<String, String> map = mapToMap(list, DataElement::getId, DataElement::getName);

    assertEquals(3, map.keySet().size());
    assertEquals("Red", map.get(deA.getId()));
    assertEquals("Green", map.get(deB.getId()));
    assertEquals("Blue", map.get(deC.getId()));
  }

  @Test
  void testFirst() {
    assertEquals("a", first(list("a", "b", "c")).get());
    assertFalse(first(list("a", "b", "c")).isEmpty());
    assertTrue(first(null).isEmpty());
    assertTrue(first(list()).isEmpty());
    assertTrue(first(list(null, "b", "c")).isEmpty());
  }

  @Test
  void testToCommaSeparatedNullEmpty() {
    assertNull(toCommaSeparated(null));
    assertNull(toCommaSeparated(List.of()));
  }

  @Test
  void testToCommaSeparated() {
    assertEquals(
        "jUb6fnbZPhV,qEiCafULhoW,wOahXFjLq4V",
        toCommaSeparated(List.of("jUb6fnbZPhV", "qEiCafULhoW", "wOahXFjLq4V")));
  }
}
