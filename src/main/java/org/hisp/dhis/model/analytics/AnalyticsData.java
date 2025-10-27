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

import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.hisp.dhis.util.ObjectUtils.isNotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AnalyticsData {
  /** Analytics column headers. */
  @JsonProperty private List<AnalyticsHeader> headers;

  /** Response metadata. */
  @JsonProperty private AnalyticsMetaData metaData;

  /** Analytics data rows. */
  @JsonProperty private List<List<String>> rows;

  /** Whether the data rows were truncatd to max limit. */
  @JsonProperty private boolean truncated;

  /** Default constructor. */
  public AnalyticsData() {
    this.headers = new ArrayList<>();
    this.rows = new ArrayList<List<String>>();
    this.truncated = false;
  }

  /**
   * Constructor.
   *
   * @param headers the {@link AnalyticsHeader}.
   * @param rows the rows.
   */
  public AnalyticsData(List<AnalyticsHeader> headers, List<List<String>> rows) {
    this.headers = headers;
    this.rows = rows;
  }

  /**
   * Gets the number ofd ata rows.
   *
   * @return the number of data rows.
   */
  @JsonProperty
  public int getHeight() {
    return rows != null ? rows.size() : 0;
  }

  /**
   * Gets the number of columns in the data rows.
   *
   * @return the number of columns in the data rows.
   */
  @JsonProperty
  public int getWidth() {
    return rows != null && !rows.isEmpty() ? rows.get(0).size() : 0;
  }

  /**
   * Gets the number of headers.
   *
   * @return the number of headers.
   */
  @JsonProperty
  public int getHeaderWidth() {
    return headers != null ? headers.size() : 0;
  }

  // Non-serializable logic methods

  /**
   * Indicates whether a header with the given name exists.
   *
   * @param name the header name.
   * @return true if a header with the given name exists, false otherwise.
   */
  @JsonIgnore
  public boolean headerExists(String name) {
    return headerIndex(name) != -1;
  }

  /**
   * Gets the index of the header with the given name.
   *
   * @param name the header name.
   * @return the index of the header with the given name, or -1 if not found.
   */
  @JsonIgnore
  public int headerIndex(String name) {
    return headers.indexOf(new AnalyticsHeader(name));
  }

  /**
   * Returns a set of indexes (positions) of metadata headers which represent metadata.
   *
   * @return an immutable set of indexes of metadata headers which represent metadata.
   */
  @JsonIgnore
  public Set<Integer> getHeaderMetaIndexes() {
    return headers.stream()
        .filter(AnalyticsHeader::isMeta)
        .map(headers::indexOf)
        .collect(Collectors.toUnmodifiableSet());
  }

  /**
   * Gets a copy of the headers.
   *
   * @return a copy of the headers.
   */
  @JsonIgnore
  public List<AnalyticsHeader> getCopyOfHeaders() {
    return headers.stream()
        .map(
            h ->
                new AnalyticsHeader(
                    h.getName(), h.getColumn(), h.getValueType(), h.getHidden(), h.getMeta()))
        .collect(Collectors.toList());
  }

  /**
   * Indicates whether metadata exists.
   *
   * @return true if metadata exists, false otherwise.
   */
  @JsonIgnore
  public boolean hasMetaData() {
    return metaData != null;
  }

  /**
   * Adds a data row.
   *
   * @param row the data row.
   */
  @JsonIgnore
  public void addRow(List<String> row) {
    this.rows.add(row);
  }

  /**
   * Gets the data row at the specified index.
   *
   * @param index the row index, starting with 0.
   * @return the data row at the specified index, or null if out of bounds.
   */
  @JsonIgnore
  public List<String> getRow(int index) {
    if (rows == null || index < 0 || index >= rows.size()) {
      return null;
    }

    return rows.get(index);
  }

  /**
   * Truncates the data rows to the specified maximum number of rows.
   *
   * @param maxRows the maximum number of rows to retain.
   */
  @JsonIgnore
  public void truncate(int maxRows) {
    if (rows != null && rows.size() > maxRows) {
      rows = rows.subList(0, maxRows);
      truncated = true;
    }
  }

  @JsonIgnore
  public List<List<String>> getRowsWithNames() {
    if (headers == null || metaData == null || metaData.getItems() == null || rows == null) {
      throw new IllegalStateException("Headers, metadata and rows must be present");
    }

    List<List<String>> _rows = new ArrayList<>();

    for (List<String> row : rows) {
      List<String> _row = new ArrayList<>();

      for (int i = 0; i < row.size(); i++) {
        String value = row.get(i);
        boolean meta = headers.get(i).isMeta();

        if (meta) {
          _row.add(metaData.getItemName(value));
        } else {
          _row.add(value);
        }
      }

      _rows.add(_row);
    }

    return _rows;
  }

  /** Orders the data rows in natural order based on their metadata values. */
  @JsonIgnore
  public void sortRows() {
    if (isEmpty(rows)) {
      return;
    }

    Set<Integer> metaIndexes = getHeaderMetaIndexes();
    Map<String, String> peMap = isNotNull(metaData) ? metaData.getPeriodNameIsoIdMap() : Map.of();
    int peIndex = headerIndex(AnalyticsDimension.PERIOD);

    // TODO sort period dimension by ISO name using metadata items details

    rows.sort(
        (rowA, rowB) -> {
          int maxLength = Math.min(rowA.size(), rowB.size());

          for (int i = 0; i < maxLength; i++) {
            // Only sort metadata column values
            if (metaIndexes.contains(i)) {
              String val1 = rowA.get(i);
              String val2 = rowB.get(i);

              // Retrieve and sort by period ISO ID instead of name
              if (i == peIndex && !peMap.isEmpty()) {
                val1 = peMap.get(val1);
                val2 = peMap.get(val2);
              }

              int comparison = Objects.compare(val1, val2, Comparator.naturalOrder());

              if (comparison != 0) {
                return comparison;
              }
            }
          }

          return 0;
        });
  }
}
