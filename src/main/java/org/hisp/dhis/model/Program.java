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

import static org.hisp.dhis.util.CollectionUtils.notEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hisp.dhis.model.trackedentity.ProgramTrackedEntityAttribute;
import org.hisp.dhis.model.trackedentity.TrackedEntityAttribute;
import org.hisp.dhis.model.trackedentity.TrackedEntityType;
import org.hisp.dhis.model.trackedentity.TrackedEntityTypeAttribute;

@Getter
@Setter
@NoArgsConstructor
public class Program extends NameableObject {
  @JsonProperty private ProgramType programType;

  /**
   * Only relevant for {@link ProgramType#WITH_REGISTRATION}, null for {@link
   * ProgramType#WITHOUT_REGISTRATION}.
   */
  @JsonProperty private TrackedEntityType trackedEntityType;

  @JsonProperty private CategoryCombo categoryCombo;

  @JsonProperty private List<OrgUnit> organisationUnits = new ArrayList<>();

  @JsonProperty
  private List<ProgramTrackedEntityAttribute> programTrackedEntityAttributes = new ArrayList<>();

  @JsonProperty private List<ProgramSection> programSections = new ArrayList<>();

  @JsonProperty private List<ProgramStage> programStages = new ArrayList<>();

  @JsonProperty private Integer version;

  @JsonProperty private Boolean displayIncidentDate;

  @JsonProperty private Boolean onlyEnrollOnce;

  @JsonProperty private Boolean displayFrontPageList;

  @JsonProperty private Integer minAttributesRequiredToSearch;

  @JsonProperty private Integer maxTeiCountToReturn;

  @JsonProperty private ProgramAccessLevel accessLevel;

  public Program(String id, String name) {
    this.id = id;
    this.name = name;
  }

  /**
   * Returns tracked entity attributes which are part of the tracked entity type of the program.
   *
   * @return an immutable list of {@link TrackedEntityAttribute}.
   */
  @JsonIgnore
  public List<TrackedEntityAttribute> getTrackedEntityTypeAttributes() {
    if (ProgramType.WITHOUT_REGISTRATION == programType || trackedEntityType == null) {
      return List.of();
    }

    return trackedEntityType.getTrackedEntityTypeAttributes().stream()
        .map(TrackedEntityTypeAttribute::getTrackedEntityAttribute)
        .toList();
  }

  /**
   * Returns tracked entity attributes which are not confidential and part of the tracked entity
   * type of the program.
   *
   * @return an immutable list of {@link TrackedEntityAttribute}.
   */
  public List<TrackedEntityAttribute> getNonConfidentialTrackedEntityTypeAttributes() {
    if (ProgramType.WITHOUT_REGISTRATION == programType || trackedEntityType == null) {
      return List.of();
    }

    return trackedEntityType.getTrackedEntityTypeAttributes().stream()
        .map(TrackedEntityTypeAttribute::getTrackedEntityAttribute)
        .filter(tea -> !tea.isConfidentialNullSafe())
        .collect(Collectors.toUnmodifiableList());
  }

  /**
   * Returns tracked entity attributes which are part of the program.
   *
   * @return an immutable list of {@link TrackedEntityAttribute}.
   */
  @JsonIgnore
  public List<TrackedEntityAttribute> getTrackedEntityAttributes() {
    return programTrackedEntityAttributes.stream()
        .map(ProgramTrackedEntityAttribute::getTrackedEntityAttribute)
        .toList();
  }

  /**
   * Returns tracked entity attributes which are not confidential and part of the program.
   *
   * @return an immutable list of {@link TrackedEntityAttribute}.
   */
  @JsonIgnore
  public List<TrackedEntityAttribute> getNonConfidentialTrackedEntityAttributes() {
    return programTrackedEntityAttributes.stream()
        .map(ProgramTrackedEntityAttribute::getTrackedEntityAttribute)
        .filter(tea -> !tea.isConfidentialNullSafe())
        .toList();
  }

  /**
   * Returns all data elements which are part of the stages of this program.
   *
   * @return an immutable set of {@link DataElement}.
   */
  @JsonIgnore
  public List<DataElement> getDataElements() {
    return programStages.stream().flatMap(ps -> ps.getDataElements().stream()).distinct().toList();
  }

  /**
   * Returns data elements enabled for analytics which are part of the stages of this program.
   *
   * @return an immutable set of {@link DataElement}.
   */
  @JsonIgnore
  public List<DataElement> getAnalyticsDataElements() {
    return programStages.stream()
        .flatMap(ps -> ps.getAnalyticsDataElements().stream())
        .distinct()
        .toList();
  }

  /**
   * Returns data elements enabled for analytics which are part of the stages of this program which
   * have a legend set and is of numeric value type.
   *
   * @return an immutable set of {@link DataElement}.
   */
  @JsonIgnore
  public List<DataElement> getAnalyticsDataElementsWithLegendSet() {
    return programStages.stream()
        .flatMap(ps -> ps.getAnalyticsDataElements().stream())
        .filter(de -> notEmpty(de.getLegendSets()) && de.getValueType().isNumeric())
        .distinct()
        .toList();
  }

  /**
   * Returns program stage sections which are part of the stages of this program.
   *
   * @return an immutable set of {@link ProgramStageSection}.
   */
  @JsonIgnore
  public List<ProgramStageSection> getProgramStageSections() {
    return programStages.stream().flatMap(ps -> ps.getProgramStageSections().stream()).toList();
  }

  /**
   * Indicates whether this program has a category combination.
   *
   * @return true if this program has a category combination.
   */
  @JsonIgnore
  public boolean hasCategoryCombo() {
    return categoryCombo != null;
  }

  /**
   * Indicates whether this program has a tracked entity type.
   *
   * @return true if this program has a tracked entity type.
   */
  @JsonIgnore
  public boolean hasTrackedEntityType() {
    return trackedEntityType != null;
  }

  /**
   * Indicates whether this program is with registration, i.e. a tracker program.
   *
   * @return true if this program is with registration.
   */
  @JsonIgnore
  public boolean isTrackerProgram() {
    return ProgramType.WITH_REGISTRATION == programType;
  }

  /**
   * Indicates whether this program is without registration, i.e. an event program.
   *
   * @return true if this program is without registration.
   */
  @JsonIgnore
  public boolean isEventProgram() {
    return ProgramType.WITHOUT_REGISTRATION == programType;
  }
}
