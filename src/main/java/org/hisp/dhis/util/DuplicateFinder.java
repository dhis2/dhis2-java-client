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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/** Duplicate value finder. */
public class DuplicateFinder {

  private Map<String, Integer> valueCounts;

  /** Constructor. */
  public DuplicateFinder() {
    this.valueCounts = new HashMap<>();
  }

  /**
   * Constructor.
   *
   * @param values the collection of values.
   */
  public DuplicateFinder(Collection<String> values) {
    this();
    addAll(values);
  }

  /**
   * Adds a value.
   *
   * @param value the value.
   */
  public void add(String value) {
    valueCounts.put(value, valueCounts.getOrDefault(value, 0) + 1);
  }

  /**
   * Adds a collection of values.
   *
   * @param values the values.
   */
  public void addAll(Collection<String> values) {
    values.forEach(value -> add(value));
  }

  /**
   * Returns the duplicates as a map, where the key is the duplicate value and the value is the
   * number of occurrences.
   *
   * @return the duplicates as a map.
   */
  public Map<String, Integer> getDuplicates() {
    Map<String, Integer> duplicates = new HashMap<>();
    for (Map.Entry<String, Integer> entry : valueCounts.entrySet()) {
      if (entry.getValue() > 1) {
        duplicates.put(entry.getKey(), entry.getValue());
      }
    }
    return duplicates;
  }
}
