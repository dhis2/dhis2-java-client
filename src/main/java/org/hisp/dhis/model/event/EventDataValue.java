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
package org.hisp.dhis.model.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hisp.dhis.util.DateTimeUtils;

@Getter
@Setter
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class EventDataValue implements Serializable {
  public static final String VALUE_TRUE = "true";

  public static final String VALUE_FALSE = "false";

  @ToString.Include @JsonProperty private String dataElement;

  @ToString.Include @JsonProperty private String value;

  /** Default false. */
  @JsonProperty private Boolean providedElsewhere;

  /** Read-only. */
  @JsonProperty private Date createdAt;

  /** Read-only. */
  @JsonProperty private Date updatedAt;

  @JsonProperty private String storedBy;

  /** Read-only. */
  @JsonProperty private String createdBy;

  /** Read-only. */
  @JsonProperty private String updatedBy;

  /**
   * Constructor.
   *
   * @param dataElement the data element identifier.
   * @param value the value.
   */
  public EventDataValue(String dataElement, String value) {
    this.dataElement = dataElement;
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
   * Indicates whether the value represents a boolean.
   *
   * @return true if the value represents a boolean.
   */
  @JsonIgnore
  public boolean isBoolean() {
    return VALUE_TRUE.equalsIgnoreCase(value) || VALUE_FALSE.equalsIgnoreCase(value);
  }

  /**
   * Returns the value as a boolean, only if this value represents a boolean. Returns null if not.
   *
   * @return a boolean value.
   */
  @JsonIgnore
  public Boolean getBooleanValue() {
    return isBoolean() ? Boolean.valueOf(value) : null;
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

  /**
   * Indicates whether the value represents a {@link LocalDate}.
   *
   * @return true if the value represents a {@link LocalDate}.
   */
  @JsonIgnore
  public boolean isLocalDate() {
    return DateTimeUtils.isValidLocalDate(value);
  }

  /**
   * Returns the value as a {@link LocalDate}, only if the value represents a {@link LocalDate} or a
   * {@link LocalDateTime}. If the value represents the latter, the {@link LocalDateTime} is
   * converted to a {@link LocalDate}. Returns null if not.
   *
   * @return a {@link LocalDate} or null.
   */
  @JsonIgnore
  public LocalDate getLocalDateValue() {
    if (DateTimeUtils.isValidLocalDate(value)) {
      return DateTimeUtils.getLocalDate(value);
    }

    return DateTimeUtils.isValidLocalDateTime(value)
        ? DateTimeUtils.getLocalDateTimeAsDate(value)
        : null;
  }

  /**
   * Indicates whether the value represents a {@link LocalDateTime}.
   *
   * @return true if the value represents a {@link LocalDateTime}.
   */
  @JsonIgnore
  public boolean isLocalDateTime() {
    return DateTimeUtils.isValidLocalDateTime(value);
  }

  /**
   * Returns the value as a {@link LocalDateTime}, only if the value represents a {@link
   * LocalDateTime}. Returns null if not.
   *
   * @return a {@link LocalDateTime} or null.
   */
  @JsonIgnore
  public LocalDateTime getLocalDateTimeValue() {
    return DateTimeUtils.isValidLocalDateTime(value) ? DateTimeUtils.getLocalDateTime(value) : null;
  }
}
