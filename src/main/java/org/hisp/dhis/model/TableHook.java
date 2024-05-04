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

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableHook extends IdentifiableObject {
  @JsonProperty private TablePhase phase;

  @JsonProperty private ResourceTableType resourceTableType;

  @JsonProperty private AnalyticsTableType analyticsTableType;

  @JsonProperty private String sql;

  public enum TablePhase {
    RESOURCE_TABLE_POPULATED,
    ANALYTICS_TABLE_POPULATED
  }

  public enum ResourceTableType {
    ORG_UNIT_STRUCTURE,
    DATA_SET_ORG_UNIT_CATEGORY,
    CATEGORY_OPTION_COMBO_NAME,
    DATA_ELEMENT_GROUP_SET_STRUCTURE,
    INDICATOR_GROUP_SET_STRUCTURE,
    ORG_UNIT_GROUP_SET_STRUCTURE,
    CATEGORY_STRUCTURE,
    DATA_ELEMENT_STRUCTURE,
    PERIOD_STRUCTURE,
    DATE_PERIOD_STRUCTURE,
    DATA_ELEMENT_CATEGORY_OPTION_COMBO,
    DATA_APPROVAL_MIN_LEVEL;
  }

  public enum AnalyticsTableType {
    DATA_VALUE,
    COMPLETENESS,
    COMPLETENESS_TARGET,
    ORG_UNIT_TARGET,
    EVENT,
    ENROLLMENT,
    VALIDATION_RESULT;
  }
}
