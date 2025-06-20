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
package org.hisp.dhis.model.tracker;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hisp.dhis.model.enrollment.Enrollment;
import org.hisp.dhis.model.event.Event;
import org.hisp.dhis.model.relationship.Relationship;
import org.hisp.dhis.model.trackedentity.TrackedEntity;

@Getter
@Setter
@Accessors(chain = true)
@ToString
@NoArgsConstructor
public class TrackedEntityObjects {
  @JsonProperty private List<TrackedEntity> trackedEntities = new ArrayList<>();

  @JsonProperty private List<Enrollment> enrollments = new ArrayList<>();

  @JsonProperty private List<Event> events = new ArrayList<>();

  @JsonProperty private List<Relationship> relationships = new ArrayList<>();

  public TrackedEntityObjects addTrackedEntity(TrackedEntity trackedEntity) {
    this.trackedEntities.add(trackedEntity);
    return this;
  }

  public TrackedEntityObjects addEnrollment(Enrollment enrollment) {
    this.enrollments.add(enrollment);
    return this;
  }

  public TrackedEntityObjects addEvent(Event event) {
    this.events.add(event);
    return this;
  }

  public TrackedEntityObjects addRelationship(Relationship relationship) {
    this.relationships.add(relationship);
    return this;
  }
}
