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

import static org.hisp.dhis.support.Assertions.assertNotEmpty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import org.hisp.dhis.model.DataElement;
import org.hisp.dhis.model.Dhis2Objects;
import org.hisp.dhis.model.IdentifiableObject;
import org.hisp.dhis.model.Option;
import org.hisp.dhis.model.OptionSet;
import org.hisp.dhis.model.ValueType;
import org.hisp.dhis.model.metadata.ImportStrategy;
import org.hisp.dhis.model.metadata.MetadataEntity;
import org.hisp.dhis.model.metadata.MetadataImportParams;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.query.tracker.AtomicMode;
import org.hisp.dhis.response.HttpStatus;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.response.objects.ObjectStatistics;
import org.hisp.dhis.response.objects.ObjectsResponse;
import org.hisp.dhis.support.JsonClassPathFile;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class MetadataApiTest {
  @Test
  void testImportMetadata() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    OptionSet optionSet = getOptionSet();

    Dhis2Objects objects = new Dhis2Objects();
    objects.setOptionSets(List.of(optionSet));
    objects.setOptions(optionSet.getOptions());

    ObjectsResponse response = dhis2.saveMetadataObjects(objects);

    assertEquals(Status.OK, response.getStatus());
    assertEquals(200, response.getHttpStatusCode());

    ObjectStatistics stats = response.getStats();

    assertNotNull(stats);
    assertEquals(4, stats.getTotal());

    assertNotEmpty(response.getTypeReports());
    assertEquals(2, response.getTypeReports().size());

    ObjectResponse removeResponse =
        dhis2.removeMetadataObject(MetadataEntity.OPTION_SET, "gKdxTM8BcI7");

    assertEquals(Status.OK, removeResponse.getStatus());
    assertEquals(200, removeResponse.getHttpStatusCode());
  }

  @Test
  void testImportMetadataWithParams() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    OptionSet optionSet = getOptionSet();

    Dhis2Objects objects = new Dhis2Objects();
    objects.setOptionSets(List.of(optionSet));
    objects.setOptions(optionSet.getOptions());

    MetadataImportParams params =
        new MetadataImportParams()
            .setImportStrategy(ImportStrategy.CREATE)
            .setAtomicMode(AtomicMode.ALL);

    ObjectsResponse response = dhis2.saveMetadataObjects(objects, params);

    assertEquals(Status.OK, response.getStatus());
    assertEquals(200, response.getHttpStatusCode());

    ObjectResponse removeResponse =
        dhis2.removeMetadataObject(MetadataEntity.OPTION_SET, "gKdxTM8BcI7");

    assertEquals(Status.OK, removeResponse.getStatus());
    assertEquals(200, removeResponse.getHttpStatusCode());
  }

  @Test
  void testSaveRemoveMetadataObject() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    DataElement dataElement =
        JsonClassPathFile.fromJson("metadata/data-element-coffee-bag.json", DataElement.class);

    ObjectResponse saveResponse = dhis2.saveMetadataObject(dataElement);

    assertEquals(Status.OK, saveResponse.getStatus());
    assertEquals(HttpStatus.CREATED, saveResponse.getHttpStatus());

    ObjectResponse removeResponse =
        dhis2.removeMetadataObject(MetadataEntity.DATA_ELEMENT, dataElement.getId());

    assertEquals(Status.OK, removeResponse.getStatus());
    assertEquals(HttpStatus.OK, removeResponse.getHttpStatus());
  }

  @Test
  void testSaveIfNotExistsMetadataObject() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    DataElement dataElement =
        JsonClassPathFile.fromJson("metadata/data-element-coffee-bag.json", DataElement.class);

    Optional<ObjectResponse> saveResponse = dhis2.saveMetadataObjectIfNotExists(dataElement);

    assertTrue(saveResponse.isPresent());
    assertEquals(Status.OK, saveResponse.get().getStatus());
    assertEquals(HttpStatus.CREATED, saveResponse.get().getHttpStatus());

    saveResponse = dhis2.saveMetadataObjectIfNotExists(dataElement);

    assertTrue(saveResponse.isEmpty());

    ObjectResponse removeResponse =
        dhis2.removeMetadataObject(MetadataEntity.DATA_ELEMENT, dataElement.getId());

    assertEquals(Status.OK, removeResponse.getStatus());
    assertEquals(HttpStatus.OK, removeResponse.getHttpStatus());
  }

  @Test
  void testGetMetadataList() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    List<IdentifiableObject> list =
        dhis2.getMetadataList(MetadataEntity.DATA_ELEMENT, Query.instance().withDefaultPaging());

    assertNotEmpty(list);
  }

  /**
   * Returns an {@link OptionSet}.
   *
   * @return an {@link OptionSet}.
   */
  private OptionSet getOptionSet() {
    Option oA = new Option();
    oA.setId("AnxX8vfEzZE");
    oA.setCode("ESPRESSO");
    oA.setName("Espresso");

    Option oB = new Option();
    oB.setId("UxQR6MUFmhH");
    oB.setCode("AMERICANO");
    oB.setName("Americano");

    Option oC = new Option();
    oC.setId("kQYbXoTUVYL");
    oC.setCode("CAPPUCINO");
    oC.setName("Capuccino");

    List<Option> options = List.of(oA, oB, oC);

    OptionSet optionSet = new OptionSet();
    optionSet.setId("gKdxTM8BcI7");
    optionSet.setName("Coffee");
    optionSet.setCode("COFFEE");
    optionSet.setValueType(ValueType.TEXT);
    optionSet.setOptions(options);

    return optionSet;
  }
}
