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
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hisp.dhis.model.dimension.DimensionItem;
import org.hisp.dhis.model.dimension.DimensionItemType;

@Getter
@Setter
@NoArgsConstructor
public class DataSet extends DimensionItem {
  @JsonProperty private String formName;

  @JsonProperty private String displayFormName;

  @JsonProperty private CategoryCombo categoryCombo;

  @JsonProperty private List<OrgUnit> organisationUnits = new ArrayList<>();

  @JsonProperty private List<DataSetElement> dataSetElements = new ArrayList<>();

  @JsonProperty private List<Section> sections = new ArrayList<>();

  @JsonProperty private List<Indicator> indicators = new ArrayList<>();

  @JsonProperty private DataApprovalWorkflow workflow;

  @JsonProperty private DataEntryForm dataEntryForm;

  @JsonProperty private String dimensionItem;

  @JsonProperty private Integer openFuturePeriods;

  @JsonProperty private Integer expiryDays;

  @JsonProperty private Integer openPeriodsAfterCoEndDate;

  @JsonProperty private Integer timelyDays;

  @JsonProperty private String url;

  @JsonProperty private FormType formType;

  @JsonProperty private String periodType;

  @JsonProperty private Integer version;

  @JsonProperty private List<LegendSet> legendSets = new ArrayList<>();

  @JsonProperty private DimensionItemType dimensionItemType;

  @JsonProperty private AggregationType aggregationType;

  @JsonProperty private Boolean favorite;

  @JsonProperty private Boolean compulsoryFieldsCompleteOnly;

  @JsonProperty private Boolean skipOffline;

  @JsonProperty private Boolean validCompleteOnly;

  @JsonProperty private Boolean dataElementDecoration;

  @JsonProperty private Boolean notifyCompletingUser;

  @JsonProperty private Boolean noValueRequiresComment;

  @JsonProperty private Boolean fieldCombinationRequired;

  @JsonProperty private Boolean mobile;

  @Override
  public DimensionItemType getDimensionItemType() {
    return DimensionItemType.REPORTING_RATE;
  }
}
