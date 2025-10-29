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
import java.util.Set;

public class AnalyticsDataIndex extends HashMap<String, String> {
  private static final String SEP = "==";

  private final int keyCount;

  public AnalyticsDataIndex(Map<? extends String, ? extends String> data, int keyCount) {
    super(data);
    this.keyCount = keyCount;
  }

  public String getValue(String... keys) {
    if (keys.length != keyCount) {
      String msg =
          String.format(
              "Provided key count: %d must be equal to index key count: %d", keys.length, keyCount);
      throw new IllegalArgumentException(msg);
    }

    String key = toKey(keys);
    return super.get(key);
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
   * @param valueIndex the index of the value item.
   * @return a key.
   */
  public static String toKey(List<String> row, Set<Integer> keyIndexes) {
    List<String> keys = new ArrayList<>();
    keyIndexes.forEach(i -> keys.add(row.get(i)));
    return String.join(SEP, keys);
  }
}
