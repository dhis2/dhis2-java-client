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
package org.hisp.dhis.model.trackedentity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hisp.dhis.model.enrollment.Enrollment;
import org.locationtech.jts.geom.Geometry;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TrackedEntity {
  @JsonProperty private String trackedEntity;

  @JsonProperty private String trackedEntityType;

  /** Read-only. */
  @JsonProperty private Date createdAt;

  @JsonProperty private Date createdAtClient;

  /** Read-only. */
  @JsonProperty private Date updatedAt;

  @JsonProperty private Date updatedAtClient;

  @JsonProperty private String orgUnit;

  @JsonProperty private Boolean inactive;

  @JsonProperty private Boolean deleted;

  @JsonProperty private Boolean potentialDuplicate;

  @JsonProperty private Geometry geometry;

  @JsonProperty private String storedBy;

  @JsonProperty private List<TrackedEntityAttributeValue> attributes = new ArrayList<>();

  @JsonProperty private List<Enrollment> enrollments = new ArrayList<>();

  public TrackedEntity(String id) {
    this.trackedEntity = id;
  }

  public TrackedEntity(String id, String trackedEntityType, String orgUnit) {
    this(id);
    this.trackedEntityType = trackedEntityType;
    this.orgUnit = orgUnit;
  }

  /**
   * Adds an attribute value to the tracked entity.
   *
   * @param attributeValue the {@link TrackedEntityAttributeValue} to add.
   * @return true if the attribute was added, false if it already exists.
   */
  public boolean addAttributeValue(TrackedEntityAttributeValue attributeValue) {
    return attributes.add(attributeValue);
  }

  /**
   * Adds an attribute value to the tracked entity by specifying the attribute and value.
   *
   * @param attribute the attribute identifier.
   * @param value the value of the attribute.
   * @return true if the attribute was added, false if it already exists.
   */
  public boolean addAttributeValue(String attribute, String value) {
    return attributes.add(new TrackedEntityAttributeValue(attribute, value));
  }

  /**
   * Returns the value of the specified attribute.
   *
   * @param attribute the attribute identifier.
   * @return the value of the attribute, or null if not found.
   */
  @JsonIgnore
  public String getAttributeValue(String attribute) {
    return attributes.stream()
        .filter(at -> attribute.equals(at.getAttribute()))
        .map(TrackedEntityAttributeValue::getValue)
        .findFirst()
        .orElse(null);
  }
}
