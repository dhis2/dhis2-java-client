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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Program extends NameableObject {
  @JsonProperty private ProgramType programType;

  @JsonProperty private CategoryCombo categoryCombo;

  @JsonProperty
  private List<ProgramTrackedEntityAttribute> programTrackedEntityAttributes = new ArrayList<>();

  @JsonProperty private List<ProgramStage> programStages = new ArrayList<>();

  public Program(String id, String name) {
    this.id = id;
    this.name = name;
  }

  /**
   * Returns all tracked entity attributes which are part of the program.
   *
   * @return a list of {@link TrackedEntityAttribute}.
   */
  public List<TrackedEntityAttribute> getTrackedEntityAttributes() {
    return programTrackedEntityAttributes.stream()
        .map(ProgramTrackedEntityAttribute::getTrackedEntityAttribute)
        .collect(Collectors.toList());
  }

  /**
   * Returns all tracked entity attributes which are not confidential and part of the program.
   *
   * @return a list of {@link TrackedEntityAttribute}.
   */
  public List<TrackedEntityAttribute> getNonConfidentialTrackedEntityAttributes() {
    return programTrackedEntityAttributes.stream()
        .map(ProgramTrackedEntityAttribute::getTrackedEntityAttribute)
        .filter(java.util.Objects::nonNull)
        .filter(tea -> (tea.getConfidential() == null || tea.getConfidential() == false))
        .collect(Collectors.toList());
  }

  /**
   * Returns all data elements which are part of the stages of this program.
   *
   * @return a set of {@link DataElement}.
   */
  @JsonIgnore
  public Set<DataElement> getDataElements() {
    return programStages.stream()
        .flatMap(ps -> ps.getDataElements().stream())
        .collect(Collectors.toSet());
  }

  /**
   * Returns data elements which are part of the stages of this program which have a legend set and
   * is of numeric value type.
   *
   * @return a set of {@link DataElement}.
   */
  @JsonIgnore
  public Set<DataElement> getDataElementsWithLegendSet() {
    return getDataElements().stream()
        .filter(de -> !de.getLegendSets().isEmpty() && de.getValueType().isNumeric())
        .collect(Collectors.toSet());
  }

  @JsonIgnore
  public boolean hasCategoryCombo() {
    return categoryCombo != null;
  }
}
