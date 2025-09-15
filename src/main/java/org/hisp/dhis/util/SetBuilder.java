/*
 * Copyright (c) 2004-2025, University of Oslo
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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Builder of sets.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * Set<T> = new SetBuilder<T>()
 *   .add(valueA)
 *   .add(valueB, valueC)
 *   .addAll(Set.of(valueD, valueE))
 *   .build();
 * }</pre>
 */
public class SetBuilder<T> {
  private final Set<T> set;

  /** Default constructor. */
  public SetBuilder() {
    this.set = new HashSet<>();
  }

  /**
   * Constructor.
   *
   * @param initial the initial set.
   */
  public SetBuilder(Set<T> initial) {
    this.set = new HashSet<>(initial);
  }

  /**
   * Adds the given item.
   *
   * @param item the item.
   * @return this {@link ListBuilder}.
   */
  public final SetBuilder<T> add(T item) {
    this.set.add(item);
    return this;
  }

  /**
   * Adds the given items.
   *
   * @param items the items.
   * @return this {@link ListBuilder}.
   */
  @SafeVarargs
  public final SetBuilder<T> add(T... items) {
    this.set.addAll(new HashSet<T>(Arrays.asList(items)));
    return this;
  }

  /**
   * Adds the given items.
   *
   * @param items the items.
   * @return this {@link ListBuilder}.
   */
  public final SetBuilder<T> addAll(Set<? extends T> items) {
    this.set.addAll(items);
    return this;
  }

  /**
   * Builds the set.
   *
   * @return a set.
   */
  public final Set<T> build() {
    return Collections.unmodifiableSet(set);
  }
}
