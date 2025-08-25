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
package org.hisp.dhis.support;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import org.hisp.dhis.response.HttpStatus;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.util.CollectionUtils;

public class Assertions {
  private static final String NULL_COLLECTION_MSG = "Actual collection is null";
  private static final String NULL_VALUE_MSG = "Actual value is null";
  private static final String EMPTY_COLLECTION_MSG = "Actual collection is empty";
  private static final String EMPTY_MAP_MSG = "Actual map is empty";

  /**
   * Asserts that the actual collection contains exactly the expected items.
   *
   * @param <E>
   * @param actual the collection.
   * @param expected the items.
   */
  @SafeVarargs
  public static <E> void assertContainsExactly(Collection<E> actual, E... expected) {
    assertNotNull(actual, NULL_COLLECTION_MSG);
    assertContains(actual, expected);

    List<E> expected_ = CollectionUtils.list(expected);

    for (E item : actual) {
      String message =
          format(
              "Actual item is not present in expected: '%s' (expected size: %d)",
              item, expected_.size());

      assertTrue(expected_.contains(item), message);
    }
  }

  /**
   * Asserts that the actual collection contains exactly the expected items in the same order.
   *
   * @param <E>
   * @param actual the collection.
   * @param expected the items.
   */
  @SafeVarargs
  public static <E> void assertContainsExactlyInOrder(List<E> actual, E... expected) {
    assertNotNull(actual, NULL_COLLECTION_MSG);
    assertContainsExactly(actual, expected);

    List<E> expected_ = CollectionUtils.list(expected);

    for (int i = 0; i < actual.size(); i++) {
      E item = actual.get(i);
      String message =
          format(
              "Actual item is not present in expected: '%s' (expected size: %d)",
              item, expected_.size());

      assertEquals(expected_.get(i), item, message);
    }
  }

  /**
   * Asserts that the actual collection contains the expected items.
   *
   * @param <E>
   * @param actual the collection.
   * @param expected the items.
   */
  @SafeVarargs
  public static <E> void assertContains(Collection<E> actual, E... expected) {
    assertNotNull(actual, NULL_COLLECTION_MSG);

    for (E item : expected) {
      String message =
          format(
              "Expected item is not present in actual: '%s' (actual size: %d)",
              item, actual.size());

      assertTrue(actual.contains(item), message);
    }
  }

  /**
   * Asserts that the actual collection does not contain the expected items.
   *
   * @param actual the collection.
   * @param expected the items.
   */
  @SafeVarargs
  public static <E> void assertNotContains(Collection<E> actual, E... expected) {
    assertNotNull(actual, NULL_COLLECTION_MSG);

    for (E item : expected) {
      String message =
          format("Expected item is present in actual: '%s' (actual size: %d)", item, actual.size());

      assertFalse(actual.contains(item), message);
    }
  }

  /**
   * Asserts that the actual collection is empty and not null.
   *
   * @param actual the collection.
   */
  public static void assertEmpty(Collection<?> actual) {
    assertNotNull(actual, NULL_COLLECTION_MSG);
    assertEquals(
        0, actual.size(), format("Actual collection is not empty with size: %d", actual.size()));
  }

  /**
   * Asserts that the actual collection is empty.
   *
   * @param actual the collection.
   */
  public static void assertNotEmpty(Collection<?> actual) {
    assertNotNull(actual, NULL_COLLECTION_MSG);
    assertTrue(!actual.isEmpty(), EMPTY_COLLECTION_MSG);
  }

  /**
   * Asserts that the first item in the actual collection is not null.
   *
   * @param actual the collection.
   */
  public static void assertFirstNotNull(Collection<?> actual) {
    assertNotEmpty(actual);
    assertNotNull(actual.iterator().next(), "First item in actual collection is null");
  }

  /**
   * Asserts that the actual collection is empty.
   *
   * @param actual the collection.
   */
  public static void assertNotEmpty(Map<?, ?> actual) {
    assertNotNull(actual, NULL_COLLECTION_MSG);
    assertTrue(!actual.isEmpty(), EMPTY_MAP_MSG);
  }

  /**
   * Asserts that the actual collection is of the expected size.
   *
   * @param expected the expected size.
   * @param actual the collection.
   */
  public static void assertSize(int expected, Collection<?> actual) {
    assertNotNull(actual, NULL_COLLECTION_MSG);
    assertEquals(expected, actual.size(), "Expected size does not match actual size");
  }

  /**
   * Asserts that the given collection contains any elements which match the given predicate.
   *
   * @param <E>
   * @param actual the collection.
   * @param predicate the {@link Predicate}.
   */
  public static <E> void assertContains(Collection<E> actual, Predicate<E> predicate) {
    assertNotNull(actual, NULL_COLLECTION_MSG);
    assertTrue(actual.stream().anyMatch(predicate), "No item in actual matches predicate");
  }

  /**
   * Asserts that the given string is not null, a length of zero nor whitespace only.
   *
   * @param actual the string.
   */
  public static void assertNotBlank(String actual) {
    assertNotNull(actual, NULL_VALUE_MSG);
    assertTrue(!actual.isBlank(), "Value is blank");
  }

  /**
   * Asserts that the given integer is not null and greater than zero.
   *
   * @param actual the integer.
   */
  public static void assertGreaterThanZero(Integer actual) {
    assertNotNull(actual, NULL_VALUE_MSG);
    assertTrue(actual > 0, "Value is not greater than zero");
  }

  /**
   * Asserts successful {@link ObjectResponse}.
   *
   * @param objectResponse the {@link ObjectResponse}.
   * @param httpStatus the {@link HttpStatus}.
   * @param statusCode the status code.
   */
  public static void assertSuccessResponse(
      ObjectResponse objectResponse, HttpStatus httpStatus, int statusCode) {
    assertEquals(
        statusCode, objectResponse.getHttpStatusCode().intValue(), objectResponse.toString());
    assertEquals(httpStatus, objectResponse.getHttpStatus(), objectResponse.toString());
    assertEquals(Status.OK, objectResponse.getStatus(), objectResponse.toString());
    assertNotNull(objectResponse.getResponse());
    assertNotNull(objectResponse.getResponse().getUid());
  }
}
