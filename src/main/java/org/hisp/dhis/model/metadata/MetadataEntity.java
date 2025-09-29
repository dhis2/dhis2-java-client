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
package org.hisp.dhis.model.metadata;

import static java.lang.String.format;
import static org.hisp.dhis.api.ApiFields.ANALYTICS_TABLE_HOOK_FIELDS;
import static org.hisp.dhis.api.ApiFields.ATTRIBUTE_FIELDS;
import static org.hisp.dhis.api.ApiFields.CATEGORY_COMBO_FIELDS;
import static org.hisp.dhis.api.ApiFields.CATEGORY_FIELDS;
import static org.hisp.dhis.api.ApiFields.CATEGORY_OPTION_COMBO_FIELDS;
import static org.hisp.dhis.api.ApiFields.CATEGORY_OPTION_EXT_FIELDS;
import static org.hisp.dhis.api.ApiFields.CATEGORY_OPTION_FIELDS;
import static org.hisp.dhis.api.ApiFields.CATEGORY_OPTION_GROUP_FIELDS;
import static org.hisp.dhis.api.ApiFields.CATEGORY_OPTION_GROUP_SET_FIELDS;
import static org.hisp.dhis.api.ApiFields.CONSTANT_FIELDS;
import static org.hisp.dhis.api.ApiFields.DASHBOARD_FIELDS;
import static org.hisp.dhis.api.ApiFields.DATA_ELEMENT_EXT_FIELDS;
import static org.hisp.dhis.api.ApiFields.DATA_ELEMENT_GROUP_EXT_FIELDS;
import static org.hisp.dhis.api.ApiFields.DATA_ELEMENT_GROUP_FIELDS;
import static org.hisp.dhis.api.ApiFields.DATA_ELEMENT_GROUP_SET_FIELDS;
import static org.hisp.dhis.api.ApiFields.DATA_ENTRY_FORM_FIELDS;
import static org.hisp.dhis.api.ApiFields.DATA_SET_EXT_FIELDS;
import static org.hisp.dhis.api.ApiFields.DATA_SET_FIELDS;
import static org.hisp.dhis.api.ApiFields.DIMENSION_FIELDS;
import static org.hisp.dhis.api.ApiFields.DOCUMENT_FIELDS;
import static org.hisp.dhis.api.ApiFields.INDICATOR_FIELDS;
import static org.hisp.dhis.api.ApiFields.INDICATOR_GROUP_EXT_FIELDS;
import static org.hisp.dhis.api.ApiFields.INDICATOR_GROUP_FIELDS;
import static org.hisp.dhis.api.ApiFields.INDICATOR_GROUP_SET_FIELDS;
import static org.hisp.dhis.api.ApiFields.INDICATOR_TYPE_FIELDS;
import static org.hisp.dhis.api.ApiFields.MAP_FIELDS;
import static org.hisp.dhis.api.ApiFields.OPTION_EXT_FIELDS;
import static org.hisp.dhis.api.ApiFields.OPTION_FIELDS;
import static org.hisp.dhis.api.ApiFields.OPTION_SET_EXT_FIELDS;
import static org.hisp.dhis.api.ApiFields.OPTION_SET_FIELDS;
import static org.hisp.dhis.api.ApiFields.ORG_UNIT_FIELDS;
import static org.hisp.dhis.api.ApiFields.ORG_UNIT_GROUP_EXT_FIELDS;
import static org.hisp.dhis.api.ApiFields.ORG_UNIT_GROUP_FIELDS;
import static org.hisp.dhis.api.ApiFields.ORG_UNIT_GROUP_SET_FIELDS;
import static org.hisp.dhis.api.ApiFields.ORG_UNIT_LEVEL_FIELDS;
import static org.hisp.dhis.api.ApiFields.PROGRAM_EXT_FIELDS;
import static org.hisp.dhis.api.ApiFields.PROGRAM_FIELDS;
import static org.hisp.dhis.api.ApiFields.PROGRAM_INDICATOR_FIELDS;
import static org.hisp.dhis.api.ApiFields.PROGRAM_RULE_ACTION_FIELDS;
import static org.hisp.dhis.api.ApiFields.PROGRAM_RULE_FIELDS;
import static org.hisp.dhis.api.ApiFields.PROGRAM_RULE_VARIABLE_FIELDS;
import static org.hisp.dhis.api.ApiFields.PROGRAM_SECTION_FIELDS;
import static org.hisp.dhis.api.ApiFields.PROGRAM_STAGE_FIELDS;
import static org.hisp.dhis.api.ApiFields.PROGRAM_STAGE_SECTION_FIELDS;
import static org.hisp.dhis.api.ApiFields.RELATIONSHIP_TYPE_FIELDS;
import static org.hisp.dhis.api.ApiFields.TRACKED_ENTITY_ATTRIBUTE_EXT_FIELDS;
import static org.hisp.dhis.api.ApiFields.TRACKED_ENTITY_ATTRIBUTE_FIELDS;
import static org.hisp.dhis.api.ApiFields.TRACKED_ENTITY_TYPE_FIELDS;
import static org.hisp.dhis.api.ApiFields.USER_FIELDS;
import static org.hisp.dhis.api.ApiFields.USER_GROUP_FIELDS;
import static org.hisp.dhis.api.ApiFields.USER_ROLE_FIELDS;
import static org.hisp.dhis.api.ApiFields.VISUALIZATION_FIELDS;
import java.util.List;
import java.util.function.Function;
import org.hisp.dhis.model.AnalyticsTableHook;
import org.hisp.dhis.model.Attribute;
import org.hisp.dhis.model.Category;
import org.hisp.dhis.model.CategoryCombo;
import org.hisp.dhis.model.CategoryOption;
import org.hisp.dhis.model.CategoryOptionCombo;
import org.hisp.dhis.model.CategoryOptionGroup;
import org.hisp.dhis.model.CategoryOptionGroupSet;
import org.hisp.dhis.model.Constant;
import org.hisp.dhis.model.DataElement;
import org.hisp.dhis.model.DataElementGroup;
import org.hisp.dhis.model.DataElementGroupSet;
import org.hisp.dhis.model.DataEntryForm;
import org.hisp.dhis.model.DataSet;
import org.hisp.dhis.model.Dhis2Objects;
import org.hisp.dhis.model.Document;
import org.hisp.dhis.model.GeoMap;
import org.hisp.dhis.model.IdentifiableObject;
import org.hisp.dhis.model.Indicator;
import org.hisp.dhis.model.IndicatorGroup;
import org.hisp.dhis.model.IndicatorGroupSet;
import org.hisp.dhis.model.IndicatorType;
import org.hisp.dhis.model.Option;
import org.hisp.dhis.model.OptionSet;
import org.hisp.dhis.model.OrgUnit;
import org.hisp.dhis.model.OrgUnitGroup;
import org.hisp.dhis.model.OrgUnitGroupSet;
import org.hisp.dhis.model.OrgUnitLevel;
import org.hisp.dhis.model.Program;
import org.hisp.dhis.model.ProgramIndicator;
import org.hisp.dhis.model.ProgramSection;
import org.hisp.dhis.model.ProgramStage;
import org.hisp.dhis.model.ProgramStageSection;
import org.hisp.dhis.model.dashboard.Dashboard;
import org.hisp.dhis.model.dimension.Dimension;
import org.hisp.dhis.model.programrule.ProgramRule;
import org.hisp.dhis.model.programrule.ProgramRuleAction;
import org.hisp.dhis.model.programrule.ProgramRuleVariable;
import org.hisp.dhis.model.relationship.RelationshipType;
import org.hisp.dhis.model.trackedentity.TrackedEntityAttribute;
import org.hisp.dhis.model.trackedentity.TrackedEntityType;
import org.hisp.dhis.model.user.User;
import org.hisp.dhis.model.user.UserGroup;
import org.hisp.dhis.model.user.UserRole;
import org.hisp.dhis.model.visualization.Visualization;
import org.hisp.dhis.response.Dhis2ClientException;
import org.hisp.dhis.response.HttpStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** Enumeration of DHIS2 metadata entities. */
@Getter
@RequiredArgsConstructor
public enum MetadataEntity {
  // spotless:off
  ANALYTICS_TABLE_HOOK(
      AnalyticsTableHook.class,
      ANALYTICS_TABLE_HOOK_FIELDS,
      ANALYTICS_TABLE_HOOK_FIELDS,
      "analyticsTableHooks",
      (objects) -> objects.getAnalyticsTableHooks()),
  ATTRIBUTE(
      Attribute.class,
      ATTRIBUTE_FIELDS,
      ATTRIBUTE_FIELDS,
      "attributes",
      (objects) -> objects.getAttributes()),
  CATEGORY_OPTION(
      CategoryOption.class,
      CATEGORY_OPTION_FIELDS,
      CATEGORY_OPTION_EXT_FIELDS,
      "categoryOptions",
      (objects) -> objects.getCategoryOptions()),
  CATEGORY(Category.class,
      CATEGORY_FIELDS,
      CATEGORY_FIELDS,
      "categories",
      (objects) -> objects.getCategories()),
  CATEGORY_COMBO(
      CategoryCombo.class,
      CATEGORY_COMBO_FIELDS,
      CATEGORY_COMBO_FIELDS,
      "categoryCombos",
      (objects) -> objects.getCategoryCombos()),
  CATEGORY_OPTION_COMBO(
      CategoryOptionCombo.class,
      CATEGORY_OPTION_COMBO_FIELDS,
      CATEGORY_OPTION_COMBO_FIELDS,
      "categoryOptionCombos",
      (objects) -> objects.getCategoryOptionCombos()),
  CATEGORY_OPTION_GROUP(
      CategoryOptionGroup.class,
      CATEGORY_OPTION_GROUP_FIELDS,
      CATEGORY_OPTION_GROUP_FIELDS,
      "categoryOptionGroups",
      (objects) -> objects.getCategoryOptionGroups()),
  CATEGORY_OPTION_GROUP_SET(
      CategoryOptionGroupSet.class,
      CATEGORY_OPTION_GROUP_SET_FIELDS,
      CATEGORY_OPTION_GROUP_SET_FIELDS,
      "categoryOptionGroupSets",
      (objects) -> objects.getCategoryOptionGroupSets()),
  CONSTANT(
      Constant.class,
      CONSTANT_FIELDS,
      CONSTANT_FIELDS,
      "constants",
      (objects) -> objects.getConstants()),
  DASHBOARD(
      Dashboard.class,
      DASHBOARD_FIELDS,
      DASHBOARD_FIELDS,
      "dashboards",
      (objects) -> objects.getDashboards()),
  DATA_ELEMENT(
      DataElement.class,
      DATA_ELEMENT_EXT_FIELDS,
      DATA_ELEMENT_EXT_FIELDS,
      "dataElements",
      (objects) -> objects.getDataElements()),
  DATA_ELEMENT_GROUP(
      DataElementGroup.class,
      DATA_ELEMENT_GROUP_FIELDS,
      DATA_ELEMENT_GROUP_EXT_FIELDS,
      "dataElementGroups",
      (objects) -> objects.getDataElementGroups()),
  DATA_ELEMENT_GROUP_SET(
      DataElementGroupSet.class,
      DATA_ELEMENT_GROUP_SET_FIELDS,
      DATA_ELEMENT_GROUP_SET_FIELDS,
      "dataElementGroupSets",
      (objects) -> objects.getDataElementGroupSets()),
  DATA_SET(
      DataSet.class,
      DATA_SET_FIELDS,
      DATA_SET_EXT_FIELDS,
      "dataSets",
      (objects) -> objects.getDataSets()),
  DATA_ENTRY_FORM(
      DataEntryForm.class,
      DATA_ENTRY_FORM_FIELDS,
      DATA_ENTRY_FORM_FIELDS,
      "dataEntryForms",
      (objects) -> objects.getDataEntryForms()),
  DIMENSION(
      Dimension.class,
      DIMENSION_FIELDS,
      DIMENSION_FIELDS,
      "dimensions",
      (objects) -> objects.getDimensions()),
  DOCUMENT(
      Document.class,
      DOCUMENT_FIELDS,
      DOCUMENT_FIELDS,
      "documents",
      (objects) -> objects.getDocuments()),
  INDICATOR(
      Indicator.class,
      INDICATOR_FIELDS,
      INDICATOR_FIELDS,
      "indicators",
      (objects) -> objects.getIndicators()),
  INDICATOR_GROUP(
      IndicatorGroup.class,
      INDICATOR_GROUP_FIELDS,
      INDICATOR_GROUP_EXT_FIELDS,
      "indicatorGroups",
      (objects) -> objects.getIndicatorGroups()),
  INDICATOR_GROUP_SET(
      IndicatorGroupSet.class,
      INDICATOR_GROUP_SET_FIELDS,
      INDICATOR_GROUP_SET_FIELDS,
      "indicatorGroupSets",
      (objects) -> objects.getIndicatorGroupSets()),
  INDICATOR_TYPE(
      IndicatorType.class,
      INDICATOR_TYPE_FIELDS,
      INDICATOR_TYPE_FIELDS,
      "indicatorTypes",
      (objects) -> objects.getIndicatorTypes()),
  ORG_UNIT(OrgUnit.class,
      ORG_UNIT_FIELDS,
      ORG_UNIT_FIELDS,
      "organisationUnits",
      (objects) -> objects.getOrganisationUnits()),
  ORG_UNIT_GROUP(
      OrgUnitGroup.class,
      ORG_UNIT_GROUP_FIELDS,
      ORG_UNIT_GROUP_EXT_FIELDS,
      "organisationUnitGroups",
      (objects) -> objects.getOrganisationUnitGroups()),
  ORG_UNIT_GROUP_SET(
      OrgUnitGroupSet.class,
      ORG_UNIT_GROUP_SET_FIELDS,
      ORG_UNIT_GROUP_SET_FIELDS,
      "organisationUnitGroupSets",
      (objects) -> objects.getOrganisationUnitGroupSets()),
  ORG_UNIT_LEVEL(
      OrgUnitLevel.class,
      ORG_UNIT_LEVEL_FIELDS,
      ORG_UNIT_LEVEL_FIELDS,
      "organisationUnitLevels",
      (objects) -> objects.getOrganisationUnitLevels()),
  MAP(
      GeoMap.class,
      MAP_FIELDS,
      MAP_FIELDS,
      "maps",
      (objects) -> objects.getMaps()),
  OPTION_SET(
      OptionSet.class,
      OPTION_SET_FIELDS,
      OPTION_SET_EXT_FIELDS,
      "optionSets",
      (objects) -> objects.getOptionSets()),
  OPTION(
      Option.class,
      OPTION_FIELDS,
      OPTION_EXT_FIELDS,
      "options",
      (objects) -> objects.getOptions()),
  PROGRAM(
      Program.class,
      PROGRAM_FIELDS,
      PROGRAM_EXT_FIELDS,
      "programs",
      (objects) -> objects.getPrograms()),
  PROGRAM_SECTION(
      ProgramSection.class,
      PROGRAM_SECTION_FIELDS,
      PROGRAM_SECTION_FIELDS,
      "programSections",
      (objects) -> objects.getProgramSections()),
  PROGRAM_STAGE(
      ProgramStage.class,
      PROGRAM_STAGE_FIELDS,
      PROGRAM_STAGE_FIELDS,
      "programStages",
      (objects) -> objects.getProgramStages()),
  PROGRAM_STAGE_SECTION(
      ProgramStageSection.class,
      PROGRAM_STAGE_SECTION_FIELDS,
      PROGRAM_STAGE_SECTION_FIELDS,
      "programStageSections",
      (objects) -> objects.getProgramSections()),
  PROGRAM_INDICATOR(
      ProgramIndicator.class,
      PROGRAM_INDICATOR_FIELDS,
      PROGRAM_INDICATOR_FIELDS,
      "programIndicators",
      (objects) -> objects.getProgramIndicators()),
  PROGRAM_RULE(
      ProgramRule.class,
      PROGRAM_RULE_FIELDS,
      PROGRAM_RULE_FIELDS,
      "programRules",
      (objects) -> objects.getProgramRules()),
  PROGRAM_RULE_ACTION(
      ProgramRuleAction.class,
      PROGRAM_RULE_ACTION_FIELDS,
      PROGRAM_RULE_ACTION_FIELDS,
      "programRuleActions",
      (objects) -> objects.getProgramRuleActions()),
  PROGRAM_RULE_VARIABLE(
      ProgramRuleVariable.class,
      PROGRAM_RULE_VARIABLE_FIELDS,
      PROGRAM_RULE_VARIABLE_FIELDS,
      "programRuleVariables",
      (objects) -> objects.getProgramRuleVariables()),
  RELATIONSHIP_TYPE(
      RelationshipType.class,
      RELATIONSHIP_TYPE_FIELDS,
      RELATIONSHIP_TYPE_FIELDS,
      "relationshipTypes",
      (objects) -> objects.getRelationshipTypes()),
  TRACKED_ENTITY_TYPE(
      TrackedEntityType.class,
      TRACKED_ENTITY_TYPE_FIELDS,
      TRACKED_ENTITY_TYPE_FIELDS,
      "trackedEntityTypes",
      (objects) -> objects.getTrackedEntityTypes()),
  TRACKED_ENTITY_ATTRIBUTE(
      TrackedEntityAttribute.class,
      TRACKED_ENTITY_ATTRIBUTE_FIELDS,
      TRACKED_ENTITY_ATTRIBUTE_EXT_FIELDS,
      "trackedEntityAttributes",
      (objects) -> objects.getTrackedEntityAttributes()),
  USER(
      User.class,
      USER_FIELDS,
      USER_FIELDS,
      "users",
      (objects) -> objects.getUsers()),
  USER_GROUP(
      UserGroup.class,
      USER_GROUP_FIELDS,
      USER_GROUP_FIELDS,
      "userGroups",
      (objects) -> objects.getUserGroups()),
  USER_ROLE(
      UserRole.class,
      USER_ROLE_FIELDS,
      USER_ROLE_FIELDS,
      "userRoles",
      (objects) -> objects.getUserRoles()),
  VISUALIZATION(
      Visualization.class,
      VISUALIZATION_FIELDS,
      VISUALIZATION_FIELDS,
      "visualizations",
      (objects) -> objects.getVisualizations());
  // spotless:on

  /** Class type. */
  private final Class<? extends IdentifiableObject> type;

  /** Regular API fields. */
  private final String fields;

  /** Extensive API fields. */
  private final String extFields;

  /** API path. */
  private final String path;

  /** Function for retrieving a list of objects from an {@link Dhis2Objects}. */
  private final Function<Dhis2Objects, List<? extends IdentifiableObject>> objectsFunc;

  /**
   * Converts the given object to the corresponding {@link MetadataEntity}.
   *
   * @param <T> the type.
   * @param object the object.
   * @return the {@link MetadataEntity}.
   */
  public static <T extends IdentifiableObject> MetadataEntity from(T object) {
    if (object == null) {
      throw new Dhis2ClientException("Object is null", HttpStatus.BAD_REQUEST);
    } else if (object instanceof AnalyticsTableHook) {
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
    } else if (object instanceof RelationshipType) {
      return RELATIONSHIP_TYPE;
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
    } else if (object instanceof ProgramRule) {
      return PROGRAM_RULE;
    } else if (object instanceof ProgramRuleAction) {
      return PROGRAM_RULE_ACTION;
    } else if (object instanceof ProgramRuleVariable) {
      return PROGRAM_RULE_VARIABLE;
    } else {
      String msg = format("Unsupported metadata type: %s", object.getClass().getSimpleName());
      throw new Dhis2ClientException(msg, HttpStatus.BAD_REQUEST);
    }
  }
}
