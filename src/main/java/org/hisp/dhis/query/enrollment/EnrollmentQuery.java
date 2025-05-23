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
package org.hisp.dhis.query.enrollment;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hisp.dhis.model.enrollment.EnrollmentStatus;
import org.hisp.dhis.query.event.OrgUnitSelectionMode;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EnrollmentQuery {
  /** Only return enrollments belonging to provided organisation units */
  private List<String> orgUnits = new ArrayList<>();

  /**
   * The mode of selecting organisation units, can be. Default is SELECTED, which refers to the
   * selected organisation units only.
   */
  private OrgUnitSelectionMode orgUnitMode;

  /** Identifier of a tracker program the enrollment is enrolled into. */
  private String program;

  /** The status of the enrollment. */
  private EnrollmentStatus status;

  /** Follow up status of the tracked entity for the given program. */
  private Boolean followUp;

  /** Only enrollments updated after this date. */
  private Date updatedAfter;

  /** Only enrollments updated since given duration. */
  private Duration updatedWithin;

  /** Only enrollments newer than this date. */
  private Date enrolledAfter;

  /** Only enrollments older than this date. */
  private Date enrolledBefore;

  /** Identifier of tracked entity type. */
  private String trackedEntityType;

  /** Identifier of tracked entity. */
  private String trackedEntity;

  /**
   * Property name or attribute or UID and sort direction pairs in format propName:sortDirection.
   * Supported fields: completedAt, createdAt, createdAtClient, enrolledAt, updatedAt,
   * updatedAtClient.
   */
  private List<String> order;

  /** Filter the result down to a limited set of IDs by using enrollments=id1,id2. */
  private List<String> enrollments;

  /** When true, soft deleted events will be included in your query result. */
  private Boolean includeDeleted;

  public static EnrollmentQuery instance() {
    return new EnrollmentQuery();
  }
}
