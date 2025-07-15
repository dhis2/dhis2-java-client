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
package org.hisp.dhis.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hisp.dhis.model.dashboard.Dashboard;
import org.hisp.dhis.model.trackedentity.TrackedEntityAttribute;
import org.hisp.dhis.model.trackedentity.TrackedEntityType;
import org.hisp.dhis.model.user.User;
import org.hisp.dhis.model.user.UserGroup;
import org.hisp.dhis.model.user.UserRole;

/** Enumeration of DHIS2 metadata entities. */
@Getter
@RequiredArgsConstructor
public enum MetadataEntity {
  ANALYTICS_TABLE_HOOK("analyticsTableHooks"),
  ATTRIBUTE("attributes"),
  CATEGORY_OPTION("categoryOptions"),
  CATEGORY("categories"),
  CATEGORY_COMBO("categoryCombos"),
  CATEGORY_OPTION_COMBO("categoryOptionCombos"),
  CATEGORY_OPTION_GROUP("categoryOptionGroups"),
  CATEGORY_OPTION_GROUP_SET("categoryOptionGroupSets"),
  DASHBOARD("dashboards"),
  DATA_ELEMENT("dataElements"),
  DATA_ELEMENT_GROUP("dataElementGroups"),
  DATA_ELEMENT_GROUP_SET("dataElementGroupSets"),
  DATA_SET("dataSets"),
  DOCUMENT("documents"),
  INDICATOR("indicators"),
  INDICATOR_GROUP("indicatorGroups"),
  INDICATOR_GROUP_SET("indicatorGroupSets"),
  INDICATOR_TYPE("indicatorTypes"),
  ORG_UNIT("organisationUnits"),
  ORG_UNIT_GROUP("organisationUnitGroups"),
  ORG_UNIT_GROUP_SET("organisationUnitGroupSets"),
  ORG_UNIT_LEVEL("organisationUnitLevels"),
  MAP("maps"),
  OPTION_SET("optionSets"),
  OPTION("options"),
  PROGRAM("programs"),
  PROGRAM_SECTION("programSections"),
  PROGRAM_STAGE("programStages"),
  PROGRAM_STAGE_SECTION("programStageSections"),
  PROGRAM_INDICATOR("programIndicators"),
  TRACKED_ENTITY_TYPE("trackedEntityTypes"),
  TRACKED_ENTITY_ATTRIBUTE("trackedEntityAttributes"),
  USER("users"),
  USER_GROUP("userGroups"),
  USER_ROLE("userRoles"),
  VISUALIZATION("visualizations");

  /** API path. */
  private final String path;

  public <T extends IdentifiableObject> MetadataEntity fromType(T object) {
    if (object instanceof AnalyticsTableHook) {
      return ANALYTICS_TABLE_HOOK;
    } else if (object instanceof Attribute) {
      return ATTRIBUTE;
    } else if (object instanceof CategoryOption) {
      return CATEGORY_OPTION;
    } else if (object instanceof Category) {
      return CATEGORY;
    } else if (object instanceof CategoryCombo) {
      return CATEGORY_COMBO;
    } else if (object instanceof CategoryOptionCombo) {
      return CATEGORY_OPTION_COMBO;
    } else if (object instanceof CategoryOptionGroup) {
      return CATEGORY_OPTION_GROUP;
    } else if (object instanceof CategoryOptionGroupSet) {
      return CATEGORY_OPTION_GROUP_SET;
    } else if (object instanceof Dashboard) {
      return DASHBOARD;
    } else if (object instanceof DataElement) {
      return DATA_ELEMENT;
    } else if (object instanceof DataElementGroup) {
      return DATA_ELEMENT_GROUP;
    } else if (object instanceof DataElementGroupSet) {
      return DATA_ELEMENT_GROUP_SET;
    } else if (object instanceof DataSet) {
      return DATA_SET;
    } else if (object instanceof Document) {
      return DOCUMENT;
    } else if (object instanceof Indicator) {
      return INDICATOR;
    } else if (object instanceof IndicatorGroup) {
      return INDICATOR_GROUP;
    } else if (object instanceof IndicatorGroupSet) {
      return INDICATOR_GROUP_SET;
    } else if (object instanceof IndicatorType) {
      return INDICATOR_TYPE;
    } else if (object instanceof OrgUnit) {
      return ORG_UNIT;
    } else if (object instanceof OrgUnitGroup) {
      return ORG_UNIT_GROUP;
    } else if (object instanceof OrgUnitGroupSet) {
      return ORG_UNIT_GROUP_SET;
    } else if (object instanceof OrgUnitLevel) {
      return ORG_UNIT_LEVEL;
    } else if (object instanceof GeoMap) {
      return MAP;
    } else if (object instanceof OptionSet) {
      return OPTION_SET;
    } else if (object instanceof Option) {
      return OPTION;
    } else if (object instanceof Program) {
      return PROGRAM;
    } else if (object instanceof ProgramSection) {
      return PROGRAM_SECTION;
    } else if (object instanceof ProgramStage) {
      return PROGRAM_STAGE;
    } else if (object instanceof ProgramStageSection) {
      return PROGRAM_STAGE_SECTION;
    } else if (object instanceof ProgramIndicator) {
      return PROGRAM_INDICATOR;
    } else if (object instanceof TrackedEntityType) {
      return TRACKED_ENTITY_TYPE;
    } else if (object instanceof TrackedEntityAttribute) {
      return TRACKED_ENTITY_ATTRIBUTE;
    } else if (object instanceof User) {
      return USER;
    } else if (object instanceof UserGroup) {
      return USER_GROUP;
    } else if (object instanceof UserRole) {
      return USER_ROLE;
    } else if (object instanceof Visualization) {
      return VISUALIZATION;
    } else {
      throw new IllegalArgumentException(
          "Unsupported metadata type: " + object.getClass().getSimpleName());
    }
  }
}
