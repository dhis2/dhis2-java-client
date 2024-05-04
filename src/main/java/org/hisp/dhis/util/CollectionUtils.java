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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CollectionUtils {
  /**
   * Returns an immutable set containing the given items. Accepts null items.
   *
   * @param <T> type.
   * @param items the items.
   * @return an immutable set.
   */
  @SafeVarargs
  public static <T> Set<T> set(T... items) {
    return Collections.unmodifiableSet(mutableSet(items));
  }

  /**
   * Returns a mutable set containing the given items. Accepts null items.
   *
   * @param <T> type.
   * @param items the items.
   * @return an immutable set.
   */
  @SafeVarargs
  public static <T> Set<T> mutableSet(T... items) {
    Set<T> set = new HashSet<>();

    for (T item : items) {
      set.add(item);
    }

    return set;
  }

  /**
   * Returns an immutable list containing the given items. Accepts null items.
   *
   * @param <T> type.
   * @param items the items.
   * @return an immutable list.
   */
  @SafeVarargs
  public static <T> List<T> list(T... items) {
    return Collections.unmodifiableList(mutableList(items));
  }

  /**
   * Returns a mutable list containing the given items. Accepts null items.
   *
   * @param <T> type.
   * @param items the items.
   * @return an immutable list.
   */
  @SafeVarargs
  public static <T> List<T> mutableList(T... items) {
    List<T> list = new ArrayList<>();

    for (T item : items) {
      list.add(item);
    }

    return list;
  }

  /**
   * Converts the given array to an {@link ArrayList}.
   *
   * @param array the array.
   * @param <T> type.
   * @return a list.
   */
  public static <T> List<T> asList(T[] array) {
    return new ArrayList<>(Arrays.asList(array));
  }

  /**
   * Returns the first matching item in the given collection based on the given predicate. Returns
   * null if no match is found.
   *
   * @param <T> type.
   * @param collection the collection.
   * @param predicate the predicate.
   * @return the first matching item, or null if no match is found.
   */
  public static <T> T firstMatch(Collection<T> collection, Predicate<T> predicate) {
    return collection.stream().filter(predicate).findFirst().orElse(null);
  }

  /**
   * Returns a new mutable list of the items in the given collection which match the given
   * predicate.
   *
   * @param <T> type.
   * @param collection the collection.
   * @param predicate the predicate.
   * @return a new mutable list.
   */
  public static <T> List<T> list(Collection<T> collection, Predicate<T> predicate) {
    return collection.stream().filter(predicate).collect(Collectors.toList());
  }

  /**
   * Indicates if the given collection is not null and not empty.
   *
   * @param <T> type.
   * @param collection the collection.
   * @return true if the given collection is not null and not empty.
   */
  public static <T> boolean notEmpty(Collection<T> collection) {
    return collection != null && !collection.isEmpty();
  }
}
