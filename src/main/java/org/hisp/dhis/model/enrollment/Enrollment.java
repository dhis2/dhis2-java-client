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
package org.hisp.dhis.model.enrollment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hisp.dhis.model.trackedentity.TrackedEntityAttributeValue;
import org.locationtech.jts.geom.Geometry;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Enrollment {
  @JsonProperty private String enrollment;

  @JsonProperty private String program;

  @JsonProperty private String trackedEntity;

  @JsonProperty private EnrollmentStatus status = EnrollmentStatus.ACTIVE;

  @JsonProperty private String orgUnit;

  /** Read-only. */
  @JsonProperty private Date createdAt;

  @JsonProperty private Date createdAtClient;

  /** Read-only. */
  @JsonProperty private Date updatedAt;

  @JsonProperty private Date updatedAtClient;

  @JsonProperty private Date enrolledAt;

  @JsonProperty private Date occurredAt;

  @JsonProperty private Date completedAt;

  @JsonProperty private Boolean followUp = false;

  @JsonProperty private Boolean deleted = false;

  @JsonProperty private Geometry geometry;

  /** Read-only. */
  @JsonProperty private String completedBy;

  @JsonProperty private String storedBy;

  @JsonProperty private List<TrackedEntityAttributeValue> attributes;

  public Enrollment(String id) {
    this.enrollment = id;
  }

  public Enrollment(
      String id, String program, String trackedEntity, EnrollmentStatus status, String orgUnit) {
    this(id);
    this.program = program;
    this.trackedEntity = trackedEntity;
    this.status = status;
    this.orgUnit = orgUnit;
  }

  /**
   * Returns the enrollment idenfifier. Alias for {@code getEnrollment()}.
   *
   * @return the enrollment idenfifier.
   */
  @JsonIgnore
  public String getId() {
    return enrollment;
  }
}
