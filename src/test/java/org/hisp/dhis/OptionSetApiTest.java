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
package org.hisp.dhis;

import static org.hisp.dhis.support.Assertions.assertNotBlank;
import static org.hisp.dhis.support.Assertions.assertNotEmpty;
import static org.hisp.dhis.support.Assertions.assertSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.hisp.dhis.model.Dhis2Objects;
import org.hisp.dhis.model.Option;
import org.hisp.dhis.model.OptionSet;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.response.objects.ObjectsResponse;
import org.hisp.dhis.support.JsonClassPathFile;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class OptionSetApiTest {
  @Test
  void testGetOptionSet() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    OptionSet optionSet = dhis2.getOptionSet("VQ2lai3OfVG");

    assertNotNull(optionSet);
    assertEquals("VQ2lai3OfVG", optionSet.getId());
    assertNotBlank(optionSet.getName());
    assertNotNull(optionSet.getCreated());
    assertNotNull(optionSet.getLastUpdated());
    assertNotNull(optionSet.getSharing());
    assertNotNull(optionSet.getAccess());
    assertNotNull(optionSet.getValueType());
    assertNotNull(optionSet.getOptions());
    assertNotEmpty(optionSet.getOptions());

    Option option = optionSet.getOptions().get(0);

    assertNotNull(option);
    assertNotNull(option.getId());
    assertNotNull(option.getCode());
    assertNotNull(option.getName());
  }

  @Test
  void testGetOptionSets() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    List<OptionSet> optionSets = dhis2.getOptionSets(Query.instance());

    assertNotNull(optionSets);
    assertTrue(optionSets.size() > 1);

    OptionSet optionSet = optionSets.get(0);

    assertNotNull(optionSet);
    assertNotNull(optionSet.getId());
    assertNotNull(optionSet.getName());

    optionSets = dhis2.getOptionSets(Query.instance().setPaging(1, 10).withExpandAssociations());

    assertNotNull(optionSets);
    assertTrue(optionSets.size() > 1);

    optionSet = optionSets.get(0);

    assertNotNull(optionSet);
    assertNotNull(optionSet.getId());
    assertNotNull(optionSet.getName());
    assertNotNull(optionSet.getOptions());
    assertFalse(optionSet.getOptions().isEmpty());
  }

  @Test
  void testSaveGetOptionSet() {
    Dhis2Objects optionSet =
        JsonClassPathFile.fromJson("metadata/option-set-color.json", Dhis2Objects.class);

    assertSize(1, optionSet.getOptionSets());
    assertSize(3, optionSet.getOptions());

    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    ObjectsResponse saveResponse = dhis2.saveMetadataObjects(optionSet);

    assertNotNull(saveResponse);
    assertEquals(Status.OK, saveResponse.getStatus());
    assertEquals(200, saveResponse.getHttpStatusCode());

    OptionSet retrieved = dhis2.getOptionSet("qszOn4ydMDE");

    assertNotNull(retrieved);
    assertEquals("qszOn4ydMDE", retrieved.getId());
    assertEquals("DJC_COLOR", retrieved.getCode());
    assertEquals("DJC: Color", retrieved.getName());
    assertSize(3, retrieved.getOptions());

    ObjectResponse removeResponse = dhis2.removeOptionSet("qszOn4ydMDE");

    assertNotNull(removeResponse);
    assertEquals(Status.OK, removeResponse.getStatus());
    assertEquals(200, removeResponse.getHttpStatusCode());
  }
}
