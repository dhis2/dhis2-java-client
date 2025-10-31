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
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.collections4.MapUtils.isEmpty;
import static org.hisp.dhis.util.ObjectUtils.isNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsMetaData implements Serializable {
  /**
   * Map between dimension item identifiers and metadata item objects. The {@code name} property is
   * always set. Other properties are only set if the API request parameter {@code
   * includeMetadataDetails} was enabled.
   */
  @JsonProperty private Map<String, MetaDataItem> items;

  /**
   * Map between dimension identifier and dimension item identifiers. Dynamic keywords including
   * relative periods and org unit levels are resolved to individual item identifiers. See {@link
   * AnalyticsDimension} for dimension identifier constants.
   */
  @JsonProperty private Map<String, List<String>> dimensions;

  /**
   * Returns a map of dimension item names to identifiers. If a dimension with items or metadata
   * items are not present, an empty map is returned. If the name of any metadata dimension item
   * item is not present, an empty map is returned.
   *
   * @return a map of dimension item names to identifiers.
   */
  @JsonIgnore
  public Map<String, String> getDimensionItemNameIdMap(String dimension) {
    if (isEmpty(items) || isEmpty(dimensions)) {
      return Map.of();
    }

    List<String> dimItems = dimensions.get(dimension);

    if (isEmpty(dimItems)) {
      return Map.of();
    }

    Map<String, String> output = new HashMap<>();

    for (String item : dimItems) {
      MetaDataItem metadataItem = items.get(item);

      if (isNull(metadataItem) || isNull(metadataItem.getName())) {
        return Map.of();
      }

      output.put(metadataItem.getName(), item);
    }

    return output;
  }

  /**
   * Returns the {@link MetaDataItem} with the given identifier.
   *
   * @param id the item identifier.
   * @return the {@link MetaDataItem}, or null if not item exists with the given identifier.
   */
  public MetaDataItem getMetadataItem(String id) {
    MetaDataItem item = items.get(id);
    if (item != null) {
      item.setUid(id);
    }
    return item;
  }

  /**
   * Returns the name of the metadata item with the given identifier.
   *
   * @param id the item identifier.
   * @return the name of the metadata item, or null if no item exists with the given identifier.
   */
  public String getMetadataItemName(String id) {
    MetaDataItem item = items.get(id);
    return item != null ? item.getName() : null;
  }

  /**
   * Returns a list of dimension item identifiers for the given dimension. Returns null if the
   * dimension does not exist.
   *
   * @param dimension the dimension identifier.
   * @return a list of dimension item identifiers.
   */
  public List<String> getDimensionItems(String dimension) {
    return dimensions.get(dimension);
  }

  /**
   * Returns the count of dimension items. Returns -1 if the dimension does not exist.
   *
   * @param dimension the dimension identifier.
   * @return the count of dimension items.
   */
  public int getDimensionItemCount(String dimension) {
    List<String> dimItems = getDimensionItems(dimension);
    return dimItems != null ? dimItems.size() : -1;
  }

  /**
   * Returns a list of {@link MetaDataItem} for the given dimension. Returns an empty list of the
   * dimension does not exist.
   *
   * @param dimension the dimension identifier.
   * @return a list of {@link MetaDataItem}.
   */
  public List<MetaDataItem> getMetadataItems(String dimension) {
    List<String> dimItems = getDimensionItems(dimension);

    if (isNotEmpty(dimItems)) {
      return dimItems.stream().map(this::getMetadataItem).filter(Objects::nonNull).toList();
    }

    return List.of();
  }
}
