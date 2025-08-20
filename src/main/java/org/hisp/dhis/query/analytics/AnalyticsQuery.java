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
package org.hisp.dhis.query.analytics;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hisp.dhis.model.AggregationType;
import org.hisp.dhis.model.IdScheme;

/**
 * Encapsulation of an analytics data query. Example usage: <code><pre>
 * AnalyticsQuery query = AnalyticsQuery.instance()
 *   .addDataDimension(List.of("s46m5MS0hxu", "YtbsuPPo010"))
 *   .addPeriodDimension(List.of("202501"))
 *   .addOrgUnitFilter("O6uvpzGd5pu");
 * </pre></code>
 *
 * @author Lars Helge Overland
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnalyticsQuery {
  private final List<Dimension> dimensions = new ArrayList<>();

  private final List<Dimension> filters = new ArrayList<>();

  private AggregationType aggregationType;

  private String startDate;

  private String endDate;

  private Boolean skipMeta;

  private Boolean skipData;

  private Boolean skipRounding;

  private Boolean ignoreLimit;

  private Boolean tableLayout;

  private Boolean showHierarchy;

  private Boolean includeNumDen;

  private Boolean includeMetadataDetails;

  private IdScheme outputIdScheme;

  private IdScheme outputOrgUnitIdScheme;

  private IdScheme outputDataElementIdScheme;

  private IdScheme inputIdScheme;

  private List<String> columns = new ArrayList<>();

  private List<String> rows = new ArrayList<>();

  /**
   * Creates a new instance of this query.
   *
   * @return an {@link AnalyticsQuery}.
   */
  public static AnalyticsQuery instance() {
    return new AnalyticsQuery();
  }

  /**
   * Adds a dimension to this query.
   *
   * @param dimension the {@link Dimension}.
   * @return this {@link AnalyticsQuery}.
   */
  public AnalyticsQuery addDimension(Dimension dimension) {
    this.dimensions.add(dimension);
    return this;
  }

  /**
   * Adds a dimension and items to this query.
   *
   * @param dimension the dimension identifier.
   * @param items the list of dimension items.
   * @return this {@link AnalyticsQuery}.
   */
  public AnalyticsQuery addDimension(String dimension, List<String> items) {
    return addDimension(new Dimension(dimension, items));
  }

  /**
   * Adds a data dimension and items to this query.
   *
   * @param dataItems the list of data dimension items.
   * @return this {@link AnalyticsQuery}.
   */
  public AnalyticsQuery addDataDimension(List<String> dataItems) {
    return addDimension(Dimension.DIMENSION_DX, dataItems);
  }

  /**
   * Adds a period dimension and items to this query.
   *
   * @param periods the list of period dimension items.
   * @return this {@link AnalyticsQuery}.
   */
  public AnalyticsQuery addPeriodDimension(List<String> periods) {
    return addDimension(Dimension.DIMENSION_PE, periods);
  }

  /**
   * Adds an org unit dimension and items to this query.
   *
   * @param orgUnits the list of org unit dimension items.
   * @return this {@link AnalyticsQuery}.
   */
  public AnalyticsQuery addOrgUnitDimension(List<String> orgUnits) {
    return addDimension(Dimension.DIMENSION_OU, orgUnits);
  }

  /**
   * Adds a filter to this query.
   *
   * @param filter the {@link Dimension}.
   * @return this {@link AnalyticsQuery}.
   */
  public AnalyticsQuery addFilter(Dimension filter) {
    this.filters.add(filter);
    return this;
  }

  /**
   * Adds a filter and items to this query.
   *
   * @param filter the filter identifier.
   * @param items the filter items.
   * @return this {@link AnalyticsQuery}.
   */
  public AnalyticsQuery addFilter(String filter, List<String> items) {
    return addFilter(new Dimension(filter, items));
  }

  /**
   * Adds a data filter and items to this query.
   *
   * @param dataItems the data filter items.
   * @return this {@link AnalyticsQuery}.
   */
  public AnalyticsQuery addDataFilter(List<String> dataItems) {
    return addFilter(new Dimension(Dimension.DIMENSION_DX, dataItems));
  }

  /**
   * Adds a period filter and items to this query.
   *
   * @param periods the period filter items.
   * @return this {@link AnalyticsQuery}.
   */
  public AnalyticsQuery addPeriodFilter(List<String> periods) {
    return addFilter(new Dimension(Dimension.DIMENSION_PE, periods));
  }

  /**
   * Adds an org unit filter and items to this query.
   *
   * @param orgUnits the org unit filter items.
   * @return this {@link AnalyticsQuery}.
   */
  public AnalyticsQuery addOrgUnitFilter(List<String> orgUnits) {
    return addFilter(new Dimension(Dimension.DIMENSION_OU, orgUnits));
  }

  /**
   * Indicates whether any columns exist.
   *
   * @return true if any columns exist.
   */
  public boolean hasColumns() {
    return isNotEmpty(columns);
  }

  /**
   * Indicates whether any rows exist.
   *
   * @return true if any rows exist.
   */
  public boolean hasRows() {
    return isNotEmpty(rows);
  }
}
