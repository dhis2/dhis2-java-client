package org.hisp.dhis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.UNIT)
class ApiFieldsTest {
  @Test
  void testProgramStageFields() {
    String expected =
        """
        id,code,name,created,lastUpdated,attributeValues,shortName,description,\
        programStageDataElements[id,code,name,created,lastUpdated,attributeValues,shortName,description,\
        programStage[id,code,name,created,lastUpdated,attributeValues,shortName,description],\
        dataElement[id,code,name,created,lastUpdated,attributeValues,shortName,description,\
        aggregationType,valueType,domainType,url,legendSets[id,code,name,created,lastUpdated,\
        attributeValues,shortName,description],optionSet[id,code,name,created,lastUpdated,\
        attributeValues,valueType,version]],compulsory,displayInReports,skipSynchronization,\
        skipAnalytics,sortOrder],programStageSections[id,code,name,created,lastUpdated,\
        attributeValues,shortName,description,programStage[id,code,name,created,lastUpdated,\
        attributeValues,shortName,description],formName,sortOrder,\
        dataElements[id,code,name,created,lastUpdated,attributeValues,shortName,description],\
        programIndicators[id,code,name,created,lastUpdated,attributeValues,shortName,description]]""";

    assertEquals(expected, ApiFields.PROGRAM_STAGE_FIELDS);
  }

  @Test
  void testProgramExtFields() {
    String expected =
        """
        id,code,name,created,lastUpdated,attributeValues,shortName,description,programType,\
        trackedEntityType[id,code,name,created,lastUpdated,attributeValues,shortName,description,\
        trackedEntityTypeAttributes[id,trackedEntityAttribute[\
        id,code,name,created,lastUpdated,attributeValues,shortName,description,valueType,\
        aggregationType,confidential,unique],displayInList,mandatory,searchable]],\
        categoryCombo[id,code,name,created,lastUpdated,attributeValues,shortName,description,\
        categories[id,code,name,created,lastUpdated,attributeValues,shortName,description,dataDimensionType,dataDimension,\
        categoryOptions[id,code,name,created,lastUpdated,attributeValues],\
        categoryCombos[id,code,name,created,lastUpdated,attributeValues]]],\
        programSections[id,code,name,created,lastUpdated,attributeValues,shortName,description,sortOrder,\
        program[id,code,name,created,lastUpdated,attributeValues,shortName,description],\
        trackedEntityAttributes[id,code,name,created,lastUpdated,attributeValues,shortName,description]],\
        programStages[id,code,name,created,lastUpdated,attributeValues,shortName,description,\
        programStageDataElements[id,code,name,created,lastUpdated,attributeValues,shortName,\
        description,programStage[id,code,name,created,lastUpdated,attributeValues,shortName,description],\
        dataElement[id,code,name,created,lastUpdated,attributeValues,shortName,description,\
        aggregationType,valueType,domainType,url,legendSets[id,code,name,created,lastUpdated,\
        attributeValues,shortName,description],optionSet[id,code,name,created,lastUpdated,\
        attributeValues,valueType,version]],compulsory,displayInReports,skipSynchronization,skipAnalytics,sortOrder],\
        programStageSections[id,code,name,created,lastUpdated,attributeValues,shortName,\
        description,programStage[id,code,name,created,lastUpdated,attributeValues,shortName,description],\
        formName,sortOrder,dataElements[id,code,name,created,lastUpdated,attributeValues,shortName,description],\
        programIndicators[id,code,name,created,lastUpdated,attributeValues,shortName,description]]],\
        programTrackedEntityAttributes[id,code,name,program[id,code,name,created,lastUpdated,\
        attributeValues,shortName,description],\
        trackedEntityAttribute[id,code,name,created,lastUpdated,attributeValues,shortName,\
        description,valueType,aggregationType,confidential,unique],sortOrder,displayInList,mandatory]""";

    assertEquals(expected, ApiFields.PROGRAM_EXT_FIELDS);
  }

  @Test
  void testProgramFields() {
    String expected =
        """
        id,code,name,created,lastUpdated,attributeValues,shortName,description,programType,\
        trackedEntityType[id,code,name,created,lastUpdated,attributeValues,shortName,description],\
        categoryCombo[id,code,name,created,lastUpdated,attributeValues,shortName,description],\
        programSections[id,code,name,created,lastUpdated,attributeValues,shortName,description],\
        programStages[id,code,name,created,lastUpdated,attributeValues,shortName,description],\
        programTrackedEntityAttributes[id,code,name,\
        program[id,code,name,created,lastUpdated,attributeValues,shortName,description],\
        trackedEntityAttribute[id,code,name,created,lastUpdated,attributeValues,shortName,\
        description,valueType,aggregationType,confidential,unique],sortOrder,displayInList,mandatory]""";

    assertEquals(expected, ApiFields.PROGRAM_FIELDS);
  }
}
