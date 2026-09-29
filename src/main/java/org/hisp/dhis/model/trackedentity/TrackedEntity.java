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

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hisp.dhis.model.enrollment.Enrollment;
import org.locationtech.jts.geom.Geometry;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonPropertyOrder({
  "trackedEntity",
  "trackedEntityType",
  "createdAt",
  "createdAtClient",
  "updatedAt",
  "updatedAtClient",
  "orgUnit"
})
public class TrackedEntity extends TrackedEntityAttributeValueObjects implements Serializable {
  private static final long serialVersionUID = 1L;

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

  @JsonProperty private List<Enrollment> enrollments = new ArrayList<>();

  @JsonProperty private List<ProgramOwner> programOwners = new ArrayList<>();

  public TrackedEntity(String id) {
    this.trackedEntity = id;
  }

  public TrackedEntity(String id, String trackedEntityType, String orgUnit) {
    this(id);
    this.trackedEntityType = trackedEntityType;
    this.orgUnit = orgUnit;
  }

  /**
   * Returns the tracked entity identifier. Alias for {@code getTrackedEntity()}.
   *
   * @return the tracked entity identifier.
   */
  @JsonIgnore
  public String getId() {
    return trackedEntity;
  }

  /**
   * Indicates whether at least one enrollment exists.
   *
   * @return true if at least one enrollment exists.
   */
  public boolean hasEnrollments() {
    return isNotEmpty(enrollments);
  }

  /**
   * Returns the first enrollment with the given program, or null if no enrollment with the given
   * program exists.
   *
   * @param program the program identifier.
   * @return an {@link Enrollment}.
   */
  public Enrollment getEnrollment(String program) {
    return enrollments.stream()
        .filter(en -> en.getProgram().equals(program))
        .filter(Objects::nonNull)
        .findFirst()
        .orElse(null);
  }
}
