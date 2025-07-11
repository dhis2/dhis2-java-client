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
package org.hisp.dhis.query.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hisp.dhis.model.IdScheme;
import org.hisp.dhis.model.event.EventStatus;
import org.hisp.dhis.model.event.ProgramStatus;
import org.hisp.dhis.query.BaseQuery;
import org.hisp.dhis.query.Filter;
import org.hisp.dhis.query.Order;
import org.hisp.dhis.query.Paging;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventQuery implements BaseQuery {
  private List<Filter> filters = new ArrayList<>();

  private List<Order> order = new ArrayList<>();

  private Paging paging = new Paging();

  private String program;

  private String programStage;

  private ProgramStatus programStatus;

  private Boolean followUp;

  private String trackedEntityInstance;

  private String orgUnit;

  private OrgUnitSelectionMode ouMode;

  private EventStatus status;

  private Date occurredAfter;

  private Date occurredBefore;

  private Date scheduledAfter;

  private Date scheduledBefore;

  private Date updatedAfter;

  private Date updatedBefore;

  private IdScheme dataElementIdScheme;

  private IdScheme categoryOptionComboIdScheme;

  private IdScheme orgUnitIdScheme;

  private IdScheme programIdScheme;

  private IdScheme programStageIdScheme;

  private IdScheme idScheme;

  public static EventQuery instance() {
    return new EventQuery();
  }
}
