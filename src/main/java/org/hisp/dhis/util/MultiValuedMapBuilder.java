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

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

/**
 * Builder of multivalued maps.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * MultiValuedMap<K, V> = new MultiValuedMapBuilder<K, V>()
 *   .put(key, value)
 *   .put(key, value2)
 *   .put(key2, value3)
 *   .build();
 * }</pre>
 */
public class MultiValuedMapBuilder<K, V> {
  private final MultiValuedMap<K, V> map;

  public MultiValuedMapBuilder() {
    map = new ArrayListValuedHashMap<>();
  }

  /**
   * Associates the specified value with the specified key in this map.
   *
   * @param key the key.
   * @param value the value.
   * @return this {@link MultiValuedMapBuilder}.
   */
  public MultiValuedMapBuilder<K, V> put(K key, V value) {
    this.map.put(key, value);
    return this;
  }

  /**
   * Builds the multivalued map.
   *
   * @return the {@link MultiValuedMap}.
   */
  public MultiValuedMap<K, V> build() {
    return this.map;
  }
}
