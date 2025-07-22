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
package org.hisp.dhis.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class IdentifiableObject implements Serializable {
  @JsonProperty protected String id;

  @JsonProperty protected String code;

  @JsonProperty protected String name;

  @JsonProperty protected Date created;

  @JsonProperty protected Date lastUpdated;

  @JsonProperty protected Set<AttributeValue> attributeValues = new HashSet<>();

  // -------------------------------------------------------------------------
  // Constructors
  // -------------------------------------------------------------------------

  /**
   * Constructor.
   * 
   * @param id the identifier.
   */
  public IdentifiableObject(String id) {
    this.id = id;
  }
  
  // -------------------------------------------------------------------------
  // Logic methods
  // -------------------------------------------------------------------------

  /**
   * Adds an attribute value.
   *
   * @param attributeValue the {@link AttributeValue}.
   * @return true if this set did not already contain the attribute value.
   */
  public boolean addAttributeValue(AttributeValue attributeValue) {
    return attributeValues.add(attributeValue);
  }

  /**
   * Updates an attribute value. Removes an existing attribute value if necessary before adding the
   * given attribute value.
   *
   * @param attributeValue the {@link AttributeValue}.
   */
  public void updateAttributeValue(AttributeValue attributeValue) {
    removeAttributeValue(attributeValue.getAttribute().getId());
    addAttributeValue(attributeValue);
  }

  /**
   * Returns the attribute value with the given attribute identifier.
   *
   * @param attribute the attribute identifier.
   * @return the attribute value with the given attribute identifier, or null.
   */
  public AttributeValue getAttributeValue(String attribute) {
    return attributeValues.stream()
        .filter(av -> av.getAttribute().getId().equals(attribute))
        .findFirst()
        .orElse(null);
  }

  /**
   * Indicates whether an attribute value exists for the given attribute identifier.
   *
   * @param attribute the attribute identifier.
   * @return true of an attribute value exists.
   */
  public boolean hasAttributeValue(String attribute) {
    return attributeValues.stream().anyMatch(av -> av.getAttribute().getId().equals(attribute));
  }

  /**
   * Returns the attribute value with the given attribute identifier as a string.
   *
   * @param attribute the attribute identifier.
   * @return the attribute value with the given attribute identifier, or null.
   */
  public String getAttributeValueAsString(String attribute) {
    AttributeValue attributeValue = getAttributeValue(attribute);
    return attributeValue != null ? attributeValue.getValue() : null;
  }

  /**
   * Removes the attribute value with the given attribute identifier.
   *
   * @param attribute the attribute identifier.
   * @return true if an attribute value was removed.
   */
  public boolean removeAttributeValue(String attribute) {
    return attributeValues.removeIf(av -> av.getAttribute().getId().equals(attribute));
  }

  /** Removes all attribute values. */
  public void clearAttributeValues() {
    attributeValues.clear();
  }

  // -------------------------------------------------------------------------
  // hashCode and equals
  // -------------------------------------------------------------------------

  @Override
  public int hashCode() {
    return Objects.hash(getId());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null) {
      return false;
    }

    if (!getClass().isAssignableFrom(o.getClass())) {
      return false;
    }

    final IdentifiableObject other = (IdentifiableObject) o;

    return Objects.equals(getId(), other.getId());
  }
}
