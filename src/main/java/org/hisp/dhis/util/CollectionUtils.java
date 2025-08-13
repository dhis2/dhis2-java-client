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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/** Utilities for collections. */
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
   * Maps the given collection of objects of type U to an immutable list of objects of type T. Null
   * objects are not allowed.
   *
   * @param <T> type.
   * @param <U> type.
   * @param objects the objects of type U.
   * @param mapper the mapping function.
   * @return a list of objects of type T.
   */
  public static <T, U> List<T> mapToList(Collection<U> objects, Function<U, T> mapper) {
    return objects.stream().map(mapper).toList();
  }

  /**
   * Maps the given collection of objects of type T to a comma separated string value of the joined
   * string representation of each item. Null objects are excluded.
   *
   * @param <T> type.
   * @param objects the objects of type T.
   * @param mapper the mapping function to convert each object to a string.
   * @return a comma separated string.
   */
  public static <T> String mapToCommaSeparated(Collection<T> objects, Function<T, String> mapper) {
    return mapJoin(objects, mapper, ",");
  }

  /**
   * Maps the given collection of objects of type T to a list of strings, before joining the strings
   * using the given delimiter. Null objects are excluded.
   *
   * @param <T> type.
   * @param objects the objects of type T.
   * @param mapper the mapping function to convert each object to a string.
   * @param delimiter the string delimiter.
   * @return a joined string.
   */
  public static <T> String mapJoin(
      Collection<T> objects, Function<T, String> mapper, CharSequence delimiter) {
    return objects.stream()
        .filter(Objects::nonNull)
        .map(mapper)
        .collect(Collectors.joining(delimiter));
  }

  /**
   * Maps the given collection of objects of type U to an immutable set of objects of type T. Null
   * objects are not allowed.
   *
   * @param <T> type.
   * @param <U> type.
   * @param objects the objects of type U.
   * @param mapper the mapping function.
   * @return a set of objects of type T.
   */
  public static <T, U> Set<T> mapToSet(Collection<U> objects, Function<U, T> mapper) {
    return objects.stream().map(mapper).collect(Collectors.toUnmodifiableSet());
  }

  /**
   * Maps the given collection of objects of type U to a map where the keys are of type T. Null
   * objects are not allowed.
   *
   * @param <T> type for keys.
   * @param <U> type for values.
   * @param objects the objects of U.
   * @param keyMapper the function to map each object to a key of type T.
   * @return a map with keys of type T and values of type U.
   */
  public static <T, U> Map<T, U> mapToMap(Collection<U> objects, Function<U, T> keyMapper) {
    return objects.stream().collect(Collectors.toMap(keyMapper, Function.identity()));
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
   * Returns a new mutable map with the items in the given collection as values, indexed by keys
   * derived from those values using the given key function. *
   *
   * @param <K> key type.
   * @param <V> value type.
   * @param collection the collection.
   * @param keyFunction the key function.
   * @return a new mutable map.
   */
  public static <K, V> Map<K, V> index(Collection<V> collection, Function<V, K> keyFunction) {
    Objects.requireNonNull(keyFunction);
    Map<K, V> map = new HashMap<>();
    collection.forEach(value -> map.put(keyFunction.apply(value), value));
    return map;
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

  /**
   * Returns the item at the given index in the given list. Returns null if the index is out of
   * bounds.
   *
   * @param <T> type.
   * @param list the list.
   * @param index the index.
   * @return an item or null.
   */
  public static <T> T get(List<T> list, int index) {
    if (index >= 0 && list != null && index < list.size()) {
      return list.get(index);
    }

    return null;
  }

  /**
   * Returns an optional first item in the given collection. Returns an empty optional if the given
   * collection is empty or if the first item is null.
   *
   * @param <T> type.
   * @param collection the collection.
   * @return an optional first item in the given collection.
   */
  public static <T> Optional<T> first(Collection<T> collection) {
    return collection == null || collection.isEmpty()
        ? Optional.empty()
        : Optional.ofNullable(collection.iterator().next());
  }

  /**
   * Converts the given collection to a comma separated string value of the joined string
   * representation of each item. Returns null if the given collection is null or empty.
   *
   * @param <T> type.
   * @param collection the collection.
   * @return a comma separated string.
   */
  public static <T> String toCommaSeparated(Collection<T> collection) {
    if (collection == null || collection.isEmpty()) {
      return null;
    }
    return collection.stream().map(Object::toString).collect(Collectors.joining(","));
  }
}
