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
package org.hisp.dhis.model.analytics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.math.NumberUtils;

/** Mapping of analytics dimension items to values (metrics). */
public class AnalyticsDataIndex extends HashMap<String, String> {
  private static final String SEP = "::";

  private final List<Integer> keyIndexes;

  /**
   * Constructor.
   *
   * @param data the mapping of analytics dimension items to values (metrics).
   * @param keyIndexes the indexes of dimension items in data keys.
   */
  public AnalyticsDataIndex(Map<String, String> data, List<Integer> keyIndexes) {
    super(data);
    this.keyIndexes = keyIndexes;
  }

  /**
   * Returns a value for the given dimension item keys.
   *
   * @param keys the dimension item keys.
   * @return a value.
   */
  public String getValue(String... keys) {
    if (keys.length != keyIndexes.size()) {
      String msg =
          String.format(
              "Provided key count: %d must be equal to index key count: %d",
              keys.length, keyIndexes.size());
      throw new IllegalArgumentException(msg);
    }

    return super.get(toKey(keys));
  }

  /**
   * Returns a value as a {@link Double} for the given dimension item keys. Returns null if no value
   * exists or value is not numeric.
   *
   * @param keys the dimension item keys.
   * @return a value as {@link Double}.
   */
  public Double getDoubleValue(String... keys) {
    String value = getValue(keys);
    return NumberUtils.isCreatable(value) ? Double.parseDouble(value) : null;
  }

  /**
   * Returns an index map key, where each given key is separated by {@link #SEP}.
   *
   * @param keys the array of key items.
   * @return a key.
   */
  private String toKey(String... keys) {
    return String.join(SEP, Arrays.asList(keys));
  }

  /**
   * Returns a row index key for the given row based on the given key indexes.
   *
   * @param row the data row.
   * @param keyIndexes the list of indexes for keys.
   * @return a key.
   */
  public static String toKey(List<String> row, List<Integer> keyIndexes) {
    List<String> keys = new ArrayList<>();
    keyIndexes.forEach(i -> keys.add(row.get(i)));
    return String.join(SEP, keys);
  }
}
