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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

@Getter
@Setter
@NoArgsConstructor
public class AttributeValue {
  @JsonProperty private Attribute attribute;

  @JsonProperty private String value;

  public AttributeValue(Attribute attribute, String value) {
    this.attribute = attribute;
    this.value = value;
  }

  /**
   * Indicates whether the value is not null or empty.
   *
   * @return true if the value is not null or empty.
   */
  @JsonIgnore
  public boolean hasValue() {
    return StringUtils.isNotEmpty(value);
  }

  /**
   * Indicates whether the value represents a double.
   *
   * @return true if the value represents a double.
   */
  @JsonIgnore
  public boolean isDouble() {
    return NumberUtils.isCreatable(value);
  }

  /**
   * Returns the value as a Double, only if the value represents a double. Returns null if not.
   *
   * @return a double value.
   */
  @JsonIgnore
  public Double getDoubleValue() {
    return isDouble() ? Double.valueOf(value) : null;
  }

  /**
   * Indicates whether the value represents an integer.
   *
   * @return true if the value represents an integer.
   */
  @JsonIgnore
  public boolean isInteger() {
    return StringUtils.isNumeric(value);
  }

  /**
   * Returns the value as an integer, only if the value represents an integer. Return null if not.
   *
   * @return an integer value.
   */
  @JsonIgnore
  public Integer getIntegerValue() {
    return isInteger() ? Integer.valueOf(value) : null;
  }

  @Override
  public int hashCode() {
    return attribute != null ? Objects.hash(attribute.getId()) : 0;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || attribute == null) {
      return false;
    }

    if (!getClass().isAssignableFrom(o.getClass())) {
      return false;
    }

    final AttributeValue other = (AttributeValue) o;

    return Objects.equals(attribute.getId(), other.getAttribute().getId());
  }
}
