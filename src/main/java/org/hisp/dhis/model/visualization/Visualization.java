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
package org.hisp.dhis.model.visualization;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.BooleanUtils;
import org.hisp.dhis.model.NameableObject;
import org.hisp.dhis.model.OrgUnit;
import org.hisp.dhis.model.dimension.DataDimensionItem;
import org.hisp.dhis.model.period.Period;

@Getter
@Setter
@NoArgsConstructor
public class Visualization extends NameableObject {
  /** Type of visualization. */
  @JsonProperty private VisualizationType type;

  /** Dimensions to cross tabulate / use as columns. */
  @JsonProperty private List<String> columnDimensions = new ArrayList<>();

  /** Dimensions to use as rows. */
  @JsonProperty private List<String> rowDimensions = new ArrayList<>();

  /** Data dimension items. */
  @JsonProperty private List<DataDimensionItem> dataDimensionItems = new ArrayList<>();

  /** Fixed periods. */
  @JsonProperty private List<Period> periods = new ArrayList<>();

  /** Include user org unit. */
  @JsonProperty private Boolean userOrganisationUnit;

  /** Include user org unit children. */
  @JsonProperty private Boolean userOrganisationUnitChildren;

  /** Include user org unit grand children. */
  @JsonProperty private Boolean userOrganisationUnitGrandChildren;

  /** Organisation units. */
  @JsonProperty private List<OrgUnit> organisationUnits = new ArrayList<>();

  @JsonIgnore
  public boolean isUserOrganisationUnit() {
    return BooleanUtils.isTrue(userOrganisationUnit);
  }

  @JsonIgnore
  public boolean isUserOrganisationUnitChildren() {
    return BooleanUtils.isTrue(userOrganisationUnitChildren);
  }

  @JsonIgnore
  public boolean isUserOrganisationUnitGrandChildren() {
    return BooleanUtils.isTrue(userOrganisationUnitGrandChildren);
  }
}
