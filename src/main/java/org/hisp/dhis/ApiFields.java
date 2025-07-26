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
package org.hisp.dhis;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiFields {
  /** Identifiable object fields. */
  public static final String ID_FIELDS = "id,code,name,created,lastUpdated,attributeValues";

  /** Identifiable object extended fields. */
  public static final String ID_EXT_FIELDS = String.format("%s,sharing,access", ID_FIELDS);

  /** Nameable object fields. */
  public static final String NAME_FIELDS = String.format("%s,shortName,description", ID_FIELDS);

  /** Nameable object extended fields. */
  public static final String NAME_EXT_FIELDS =
      String.format("%s,shortName,description", ID_EXT_FIELDS);

  /** Analytics table hook fields. */
  public static final String ANALYTICS_TABLE_HOOK_FIELDS =
      String.format("%s,phase,resourceTableType,analyticsTableType,sql", ID_FIELDS);

  /** Category option fields. */
  public static final String CATEGORY_OPTION_FIELDS =
      String.format(
          "%1$s,shortName,startDate,endDate,formName,categories[%2$s],organisationUnits[%2$s]",
          NAME_EXT_FIELDS, NAME_FIELDS);

  /** Category option extended fields. */
  public static final String CATEGORY_OPTION_EXT_FIELDS =
      String.format(
          "%1$s,categoryOptionCombos[%2$s],organisationUnits[%2$s]",
          CATEGORY_OPTION_FIELDS, ID_FIELDS);

  /** Category option combo fields. */
  public static final String CATEGORY_OPTION_COMBO_FIELDS =
      String.format(
          "%1$s,ignoreApproval,dimensionItem,categoryOptions[%2$s]", ID_EXT_FIELDS, ID_FIELDS);

  /** Category option group fields. */
  public static final String CATEGORY_OPTION_GROUP_FIELDS =
      String.format(
          "%1$s,dataDimensionType,dimensionItemType,categoryOptions[%2$s],groupSets[%2$s]",
          NAME_EXT_FIELDS, ID_FIELDS);

  /** Category option group set fields. */
  public static final String CATEGORY_OPTION_GROUP_SET_FIELDS =
      String.format(
          "%1$s,dataDimension,dataDimensionType,categoryOptionGroups[%2$s]",
          NAME_EXT_FIELDS, ID_FIELDS);

  /** Category combo fields. */
  public static final String CATEGORY_COMBO_FIELDS =
      String.format(
          "%1$s,dataDimensionType,skipTotal,categories[%2$s],categoryOptionCombos[%3$s]",
          ID_EXT_FIELDS, ID_FIELDS, CATEGORY_OPTION_COMBO_FIELDS);

  /** Category fields. */
  public static final String CATEGORY_FIELDS =
      String.format(
          "%1$s,dataDimensionType,dataDimension,categoryOptions[%2$s],categoryCombos[%2$s]",
          NAME_EXT_FIELDS, ID_FIELDS);

  /** Option fields. */
  public static final String OPTION_FIELDS = NAME_FIELDS;

  /** Option set fields. */
  public static final String OPTION_SET_FIELDS =
      String.format("%s,valueType,version", ID_EXT_FIELDS);

  /** Option set extended fields. */
  public static final String OPTION_SET_EXT_FIELDS =
      String.format("%1$s,options[%2$s]", OPTION_SET_FIELDS, NAME_FIELDS);

  /** Data element group set fields. */
  public static final String DASHBOARD_FIELDS = String.format("%1$s,embedded[*]", NAME_EXT_FIELDS);

  /** Data element fields. */
  public static final String DATA_ELEMENT_FIELDS =
      String.format(
          "%1$s,aggregationType,valueType,domainType,url,legendSets[%2$s],optionSet[%3$s]",
          NAME_EXT_FIELDS, NAME_FIELDS, OPTION_SET_FIELDS);

  /** Data element extended fields. */
  public static final String DATA_ELEMENT_EXT_FIELDS =
      String.format(
          """
          %1$s,dataElementGroups[id,code,name,groupSets[%2$s]],\
          dataSetElements[dataSet[id,name,periodType,workflow[%2$s]]]""",
          DATA_ELEMENT_FIELDS, ID_FIELDS);

  /** Data element group fields. */
  public static final String DATA_ELEMENT_GROUP_FIELDS = NAME_FIELDS;

  /** Data element group extended fields. */
  public static final String DATA_ELEMENT_GROUP_EXT_FIELDS =
      String.format("%1$s,dataElements[%2$s]", NAME_EXT_FIELDS, DATA_ELEMENT_FIELDS);

  /** Data element group set fields. */
  public static final String DATA_ELEMENT_GROUP_SET_FIELDS =
      String.format(
          "%1$s,compulsory,dataDimension,dimensionType,dataElementGroups[%2$s]",
          NAME_EXT_FIELDS, NAME_FIELDS);

  /** Dimension fields. */
  public static final String DIMENSION_FIELDS = String.format("%s,dimensionType", ID_EXT_FIELDS);

  /** Document fields. */
  public static final String DOCUMENT_FIELDS =
      String.format("%s,url,external,attachment", ID_EXT_FIELDS);

  /** File resource fields. */
  public static final String FILE_RESOURCE_FIELDS =
      String.format(
          "%s,contentType,contentLength,contentMd5,domain,hasMultipleStorageFiles,storageStatus",
          ID_EXT_FIELDS);

  /** Attributes fields. */
  public static final String ATTRIBUTE_FIELDS =
      String.format(
          """
          %1$s,valueType,objectTypes,mandatory,unique,\
          dataElementAttribute,programAttribute,programStageAttribute""",
          NAME_EXT_FIELDS);

  /** Indicator type fields. */
  public static final String INDICATOR_TYPE_FIELDS =
      String.format("%s,factor,number", ID_EXT_FIELDS);

  /** Indicator fields. */
  public static final String INDICATOR_FIELDS =
      String.format(
          """
          %1$s,annualized,numerator,numeratorDescription,\
          denominator,denominatorDescription,url,indicatorType[%2$s]""",
          NAME_EXT_FIELDS, INDICATOR_TYPE_FIELDS);

  /** Indicator group fields. */
  public static final String INDICATOR_GROUP_FIELDS = NAME_FIELDS;

  /** Indicator group fields. */
  public static final String INDICATOR_GROUP_EXT_FIELDS =
      String.format("%1$s,indicators[%2$s]", NAME_EXT_FIELDS, INDICATOR_FIELDS);

  /** Indicator group set fields. */
  public static final String INDICATOR_GROUP_SET_FIELDS =
      String.format("%1$s,compulsory,indicatorGroups[%2$s]", NAME_EXT_FIELDS, NAME_FIELDS);

  /** Data set fields. */
  public static final String DATA_SET_FIELDS =
      String.format(
          """
          %1$s,formName,displayFormName,\
          categoryCombo[%2$s],\
          dataSetElements[dataSet[%2$s],dataElement[%2$s],categoryCombo[%2$s]],\
          dimensionItem,openFuturePeriods,expiryDays,timelyDays,url,formType,periodType,version,\
          dimensionItemType,aggregationType,favorite,\
          compulsoryFieldsCompleteOnly,skipOffline,validCompleteOnly,dataElementDecoration,\
          openPeriodsAfterCoEndDate,notifyCompletingUser,noValueRequiresComment,\
          fieldCombinationRequired,mobile,dataEntryForm[%3$s]""",
          NAME_EXT_FIELDS, NAME_FIELDS, ID_FIELDS);

  /** Data set extended fields. */
  public static final String DATA_SET_EXT_FIELDS =
      String.format(
          "%1$s,organisationUnits[%2$s],workflow[%2$s],indicators[%2$s],sections[%2$s],legendSets[%2$s]",
          DATA_SET_FIELDS, NAME_FIELDS);

  /** Org unit fields. */
  public static final String ORG_UNIT_FIELDS =
      String.format(
          """
          %1$s,path,level,parent[%2$s],openingDate,closedDate,comment,url,
          contactPerson,address,email,phoneNumber""",
          NAME_EXT_FIELDS, NAME_FIELDS);

  /** Org unit group fields. */
  public static final String ORG_UNIT_GROUP_FIELDS = NAME_FIELDS;

  /** Org unit group fields. */
  public static final String ORG_UNIT_GROUP_EXT_FIELDS =
      String.format("%1$s,organisationUnits[%2$s]", NAME_EXT_FIELDS, ORG_UNIT_FIELDS);

  /** Org unit group set fields. */
  public static final String ORG_UNIT_GROUP_SET_FIELDS =
      String.format(
          "%1$s,dataDimension,compulsory,organisationUnitGroups[%2$s]",
          NAME_EXT_FIELDS, NAME_FIELDS);

  /** Org unit level fields. */
  public static final String ORG_UNIT_LEVEL_FIELDS = String.format("%s,level", ID_EXT_FIELDS);

  /** Map fields. */
  public static final String MAP_FIELDS = NAME_FIELDS;

  /** Me / current user fields. */
  public static final String ME_FIELDS =
      String.format(
          """
          %1$s,username,surname,firstName,email,settings,programs,\
          dataSets,authorities,organisationUnits[%2$s]""",
          ID_EXT_FIELDS, ORG_UNIT_FIELDS);

  /** Program stage data element fields. */
  public static final String PROGRAM_STAGE_DATA_ELEMENT_FIELDS =
      String.format(
          """
          %1$s,programStage[%2$s],dataElement[%3$s],\
          compulsory,displayInReports,skipSynchronization,skipAnalytics,sortOrder""",
          NAME_EXT_FIELDS, NAME_FIELDS, DATA_ELEMENT_FIELDS);

  /** Tracked entity attribute fields. */
  public static final String TRACKED_ENTITY_ATTRIBUTE_FIELDS =
      String.format("%s,valueType,aggregationType,confidential,unique", NAME_FIELDS);

  /** Tracked entity type fields. */
  public static final String TRACKED_ENTITY_TYPE_FIELDS =
      String.format(
          """
          %1$s,trackedEntityTypeAttributes[id,trackedEntityAttribute[%2$s],\
          displayInList,mandatory,searchable]""",
          NAME_EXT_FIELDS, TRACKED_ENTITY_ATTRIBUTE_FIELDS);

  /** Tracked entity enrollment fields. */
  public static final String TRACKED_ENTITY_ENROLLMENT_FIELDS =
      """
      enrollment,program,trackedEntity,status,orgUnit,createdAt,createdAtClient,updatedAt,\
      updatedAtClient,enrolledAt,occurredAt,completedAt,followUp,deleted,completedBy,storedBy""";

  /** Tracked entity type fields. */
  public static final String TRACKED_ENTITY_FIELDS =
      String.format(
          """
          trackedEntity,trackedEntityType,createdAt,createdAtClient,updatedAt,updatedAtClient,\
          orgUnit,inactive,deleted,potentialDuplicate,geometry,storedBy,\
          attributes[attribute,displayName,code,createdAt,updatedAt,valueType,value],\
          enrollments[%1$s]""",
          TRACKED_ENTITY_ENROLLMENT_FIELDS);

  /** Relationship type fields. */
  public static final String RELATIONSHIP_TYPE_FIELDS =
      String.format(
          """
          %1$s,fromConstraint,toConstraint,description,bidirectional,fromToName,\
          toFromName,referral""",
          ID_EXT_FIELDS);

  /** Program indicator fields. */
  public static final String PROGRAM_INDICATOR_FIELDS = NAME_FIELDS;

  /** Program section fields. */
  public static final String PROGRAM_SECTION_FIELDS =
      String.format(
          """
          %1$s,sortOrder,program[%2$s],trackedEntityAttributes[%2$s]""",
          NAME_EXT_FIELDS, NAME_FIELDS);

  /** Program stage section fields. */
  public static final String PROGRAM_STAGE_SECTION_FIELDS =
      String.format(
          """
          %1$s,programStage[%2$s],formName,sortOrder,dataElements[%2$s],programIndicators[%2$s]""",
          NAME_EXT_FIELDS, NAME_FIELDS);

  /** Program stage fields. */
  public static final String PROGRAM_STAGE_FIELDS =
      String.format(
          """
          %1$s,programStageDataElements[%2$s],\
          programStageSections[%3$s],\
          formName,executionDateLabel,dueDateLabel,programStageLabel,eventLabel,\
          repeatable,autoGenerateEvent,displayGenerateEventBox,blockEntryForm,preGenerateUID,\
          remindCompleted,generatedByEnrollmentDate,allowGenerateNextVisit,openAfterEnrollment,\
          hideDueDate,enableUserAssignment,referral""",
          NAME_EXT_FIELDS, PROGRAM_STAGE_DATA_ELEMENT_FIELDS, PROGRAM_STAGE_SECTION_FIELDS);

  public static final String PROGRAM_TRACKED_ENTITY_ATTRIBUTES_FIELDS =
      String.format(
          """
          id,code,name,program[%1$s],trackedEntityAttribute[%2$s],\
          sortOrder,displayInList,mandatory,allowFutureDate,searchable""",
          NAME_FIELDS, TRACKED_ENTITY_ATTRIBUTE_FIELDS);

  /** Program extended fields. */
  public static final String PROGRAM_EXT_FIELDS =
      String.format(
          """
          %1$s,formName,programType,trackedEntityType[%3$s],categoryCombo[%2$s,categories[%4$s]],\
          organisationUnits[%2$s],\
          programSections[%5$s],\
          programStages[%6$s],\
          programTrackedEntityAttributes[%7$s],\
          enrollmentDateLabel,incidentDateLabel,enrollmentLabel,followUpLabel,\
          orgUnitLabel,relationshipLabel,noteLabel,trackedEntityAttributeLabel,\
          programStageLabel,eventLabel,\
          version,displayIncidentDate,ignoreOverdueEvents,onlyEnrollOnce,\
          selectEnrollmentDatesInFuture,selectIncidentDatesInFuture,displayFrontPageList,\
          skipOffline,useFirstStageDuringRegistration,expiryDays,\
          minAttributesRequiredToSearch,maxTeiCountToReturn,accessLevel""",
          NAME_EXT_FIELDS,
          NAME_FIELDS,
          TRACKED_ENTITY_TYPE_FIELDS,
          CATEGORY_FIELDS,
          PROGRAM_SECTION_FIELDS,
          PROGRAM_STAGE_FIELDS,
          PROGRAM_TRACKED_ENTITY_ATTRIBUTES_FIELDS);

  /** Program fields. */
  public static final String PROGRAM_FIELDS =
      String.format(
          """
          %1$s,formName,programType,trackedEntityType[%2$s],categoryCombo[%2$s],\
          programSections[%2$s],\
          programStages[%2$s],\
          programTrackedEntityAttributes[%4$s],\
          enrollmentDateLabel,incidentDateLabel,enrollmentLabel,followUpLabel,\
          orgUnitLabel,relationshipLabel,noteLabel,trackedEntityAttributeLabel,\
          programStageLabel,eventLabel,\
          version,displayIncidentDate,ignoreOverdueEvents,onlyEnrollOnce,\
          selectEnrollmentDatesInFuture,selectIncidentDatesInFuture,displayFrontPageList,\
          skipOffline,useFirstStageDuringRegistration,expiryDays,\
          minAttributesRequiredToSearch,maxTeiCountToReturn,accessLevel""",
          NAME_EXT_FIELDS,
          NAME_FIELDS,
          TRACKED_ENTITY_ATTRIBUTE_FIELDS,
          PROGRAM_TRACKED_ENTITY_ATTRIBUTES_FIELDS);

  /** User fields. */
  public static final String USER_FIELDS =
      String.format(
          """
          %1$s,username,firstName,surname,email,phoneNumber,externalAuth,lastLogin,\
          organisationUnits[%2$s],\
          dataViewOrganisationUnits[%2$s],\
          teiSearchOrganisationUnits[%2$s]""",
          ID_EXT_FIELDS, NAME_FIELDS);

  /** UserGroup fields. */
  public static final String USER_GROUP_FIELDS =
      String.format("%1$s,users[%2$s]", ID_EXT_FIELDS, ID_FIELDS);

  /** UserRole fields. */
  public static final String USER_ROLE_FIELDS =
      String.format(
          "%1$s,description,authorities,restrictions,users[%2$s]", ID_EXT_FIELDS, ID_FIELDS);

  public static final String VALIDATION_SIDE_FIELDS =
      "expression,description,displayDescription,slidingWindow,missingValueStrategy";

  /** Validation rule fields. */
  public static final String VALIDATION_RULE_FIELDS =
      String.format(
          """
          %1$s,dimensionItem,instruction,importance,periodType,displayDescription,\
          displayInstruction,displayName,leftSide[%2$s],operator,rightSide[%2$s],\
          skipFormValidation,legendSets""",
          NAME_EXT_FIELDS, VALIDATION_SIDE_FIELDS);

  /** Data set validation fields. */
  public static final String DATA_SET_VALIDATION_FIELDS =
      String.format(
          """
          id,leftsideValue,rightsideValue,dayInPeriod,notificationSent,\
          validationRule[%1$s],period[%2$s],organisationUnit[%2$s],attributeOptionCombo[%2$s]""",
          VALIDATION_RULE_FIELDS, ID_FIELDS);

  /** Visualization fields. */
  public static final String VISUALIZATION_FIELDS = NAME_EXT_FIELDS;
}
