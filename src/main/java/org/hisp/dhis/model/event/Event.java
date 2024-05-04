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

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Event {
  @EqualsAndHashCode.Include
  @ToString.Include
  @JsonProperty(value = "event")
  private String id;

  @JsonProperty private String program;

  @ToString.Include @JsonProperty private String programStage;

  @ToString.Include @JsonProperty private String enrollment;

  @ToString.Include @JsonProperty private String attributeOptionCombo;

  @JsonProperty private EventStatus status = EventStatus.ACTIVE;

  @ToString.Include @JsonProperty private String orgUnit;

  @JsonProperty private Date createdAt;

  @JsonProperty private Date createdAtClient;

  @JsonProperty private Date updatedAt;

  @JsonProperty private Date updatedAtClient;

  @JsonProperty private Date scheduledAt;

  @JsonProperty private Date occurredAt;

  @JsonProperty private String completedBy;

  @JsonProperty private String storedBy;

  @JsonProperty private Boolean followUp;

  @JsonProperty private Boolean deleted;

  private List<EventDataValue> dataValues = new ArrayList<>();

  public Event(String id) {
    this.id = id;
  }

  public Event(
      String id,
      String program,
      String programStage,
      String orgUnit,
      Date occurredAt,
      List<EventDataValue> dataValues) {
    this(id);
    this.program = program;
    this.programStage = programStage;
    this.orgUnit = orgUnit;
    this.occurredAt = occurredAt;
    this.dataValues = dataValues;
  }

  /**
   * Returns the first {@link EventDataValue} which data element matches the given data element
   * identifier, or null if no match.
   *
   * @param dataElement the data element identifier.
   * @return a {@link EventDataValue} or null.
   */
  public EventDataValue getEventDataValue(String dataElement) {
    return dataValues.stream()
        .filter(dv -> dataElement.equals(dv.getDataElement()))
        .findFirst()
        .orElse(null);
  }

  /**
   * Returns the value of the first {@link EventDataValue} which data element matches the given data
   * element identifier, or null if no match.
   *
   * @param dataElement the data element identifier.
   * @return a {@link EventDataValue} or null.
   */
  public String getDataValue(String dataElement) {
    EventDataValue eventDataValue = getEventDataValue(dataElement);
    return eventDataValue != null ? eventDataValue.getValue() : null;
  }

  /**
   * Adds a data value. If a data value with the same data element identifier exists, the given
   * value will overwrite the existing data value. The new data value is added to the end of the
   * list of data values.
   *
   * @param dataValue the {@link EventDataValue}, not null.
   */
  public void addDataValue(EventDataValue dataValue) {
    Objects.requireNonNull(dataValue);
    Objects.requireNonNull(dataValue.getDataElement());

    this.dataValues.removeIf(dv -> dv.getDataElement().equals(dataValue.getDataElement()));
    this.dataValues.add(dataValue);
  }

  /**
   * Adds a data value. If a data value with the same data element identifier exists, the given
   * value will overwrite the existing data value. The new data value is added to the end of the
   * list of data values.
   *
   * @param dataElement the data element identifier, not null.
   * @param value the data value.
   */
  public void addDataValue(String dataElement, String value) {
    addDataValue(new EventDataValue(dataElement, value));
  }
}
