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
        organisationUnits[id,code,name,created,lastUpdated,attributeValues,shortName,description],\
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
