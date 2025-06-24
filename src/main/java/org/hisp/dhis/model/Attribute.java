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
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Attribute extends NameableObject {
  @JsonProperty private ValueType valueType;

  @JsonProperty private Boolean mandatory;

  @JsonProperty private Boolean unique;

  @JsonProperty private Boolean categoryAttribute;

  @JsonProperty private Boolean categoryOptionAttribute;

  @JsonProperty private Boolean categoryOptionComboAttribute;

  @JsonProperty private Boolean categoryOptionGroupAttribute;

  @JsonProperty private Boolean categoryOptionGroupSetAttribute;

  @JsonProperty private Boolean constantAttribute;

  @JsonProperty private Boolean dataElementAttribute;

  @JsonProperty private Boolean dataElementGroupAttribute;

  @JsonProperty private Boolean dataElementGroupSetAttribute;

  @JsonProperty private Boolean dataSetAttribute;

  @JsonProperty private Boolean documentAttribute;

  @JsonProperty private Boolean indicatorAttribute;

  @JsonProperty private Boolean indicatorGroupAttribute;

  @JsonProperty private Boolean legendSetAttribute;

  @JsonProperty private Boolean mapAttribute;

  @JsonProperty private Boolean optionAttribute;

  @JsonProperty private Boolean optionSetAttribute;

  @JsonProperty private Boolean organisationUnitAttribute;

  @JsonProperty private Boolean organisationUnitGroupAttribute;

  @JsonProperty private Boolean organisationUnitGroupSetAttribute;

  @JsonProperty private Boolean programAttribute;

  @JsonProperty private Boolean programIndicatorAttribute;

  @JsonProperty private Boolean programStageAttribute;

  @JsonProperty private Boolean relationshipTypeAttribute;

  @JsonProperty private Boolean sectionAttribute;

  @JsonProperty private Boolean sqlViewAttribute;

  @JsonProperty private Boolean trackedEntityAttributeAttribute;

  @JsonProperty private Boolean trackedEntityTypeAttribute;

  @JsonProperty private Boolean userAttribute;

  @JsonProperty private Boolean userGroupAttribute;

  @JsonProperty private Boolean validationRuleAttribute;

  @JsonProperty private Boolean validationRuleGroupAttribute;

  @JsonProperty private Boolean visualizationAttribute;
}
