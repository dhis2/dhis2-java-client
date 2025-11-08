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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hisp.dhis.model.ValueType;
import org.hisp.dhis.model.dimension.DimensionItemType;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MetaDataItem implements Serializable {
  @JsonProperty private String uid;

  @JsonProperty private String name;

  @JsonProperty private String dimensionType;

  @JsonProperty private String code;

  @JsonProperty private DimensionItemType dimensionItemType;

  @JsonProperty private ValueType valueType;

  @JsonProperty private String totalAggregationType;

  @JsonProperty private String startDate;

  @JsonProperty private String endDate;

  @JsonProperty private String legendSet;

  @JsonProperty private String aggregationType;

  @JsonProperty private AnalyticsIndicatorType indicatorType;

  /**
   * Constructor.
   *
   * @param name the name.
   */
  public MetaDataItem(String name) {
    this.name = name;
  }

  /**
   * Constructor.
   *
   * @param uid the identifier.
   * @param name the name.
   */
  public MetaDataItem(String uid, String name) {
    this.uid = uid;
    this.name = name;
  }

  /**
   * Constructor.
   *
   * @param uid the identifier.
   * @param name the name.
   * @param dimensionItemType the {@link DimensionItemType}.
   */
  public MetaDataItem(String uid, String name, DimensionItemType dimensionItemType) {
    this.uid = uid;
    this.name = name;
    this.dimensionItemType = dimensionItemType;
  }

  /**
   * Indicates whether the dimension item is of the given {@link DimensionItemType}.
   *
   * @param dimensionItemType the {@link DimensionItemType}.
   * @return true if the dimension item is of the given type.
   */
  @JsonIgnore
  public boolean isDimensionItemType(DimensionItemType dimensionItemType) {
    return this.dimensionItemType == dimensionItemType;
  }

  /**
   * Indicates whether an {@link AnalyticsIndicatorType} exists.
   *
   * @return true if an {@link AnalyticsIndicatorType} exists.
   */
  @JsonIgnore
  public boolean hasIndicatorType() {
    return indicatorType != null;
  }
}
