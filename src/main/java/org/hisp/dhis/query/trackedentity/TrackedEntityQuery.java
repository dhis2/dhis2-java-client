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
package org.hisp.dhis.query.trackedentity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hisp.dhis.model.IdScheme;
import org.hisp.dhis.model.enrollment.EnrollmentStatus;
import org.hisp.dhis.model.event.EventStatus;
import org.hisp.dhis.query.BaseQuery;
import org.hisp.dhis.query.Filter;
import org.hisp.dhis.query.Order;
import org.hisp.dhis.query.Paging;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.query.event.OrgUnitSelectionMode;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TrackedEntityQuery implements BaseQuery {
  private List<Filter> filters = new ArrayList<>();

  private List<Order> order = new ArrayList<>();

  private Paging paging = new Paging();

  private List<String> orgUnits = new ArrayList<>();

  private OrgUnitSelectionMode orgUnitMode;

  private String program;

  private String programStage;

  private Boolean followUp;

  private Date updatedAfter;

  private Date updatedBefore;

  private EnrollmentStatus enrollmentStatus;

  private Date enrollmentEnrolledAfter;

  private Date enrollmentEnrolledBefore;

  private Date enrollmentOccurredAfter;

  private Date enrollmentOccurredBefore;

  private String trackedEntityType;

  private List<String> trackedEntities = new ArrayList<>();

  private EventStatus eventStatus;

  private Date eventOccurredAfter;

  private Date eventOccurredBefore;

  private Boolean includeDeleted;

  private Boolean potentialDuplicate;

  private IdScheme idScheme;

  private IdScheme orgUnitIdScheme;

  private boolean expandAssociations = false;

  public TrackedEntityQuery setOrgUnit(String orgUnit) {
    this.orgUnits = List.of(orgUnit);
    return this;
  }

  public static TrackedEntityQuery instance() {
    return new TrackedEntityQuery();
  }

  public TrackedEntityQuery addFilter(Filter filter) {
    this.filters.add(filter);
    return this;
  }

  public TrackedEntityQuery addOrder(Order order) {
    this.order.add(order);
    return this;
  }

  /**
   * Enables expansion of associations, i.e. that all properties of associated objects will be
   * present. Applies to lists of objects only (not single objects).
   *
   * @return this {@link Query}.
   */
  public TrackedEntityQuery withExpandAssociations() {
    this.expandAssociations = true;
    return this;
  }
}
