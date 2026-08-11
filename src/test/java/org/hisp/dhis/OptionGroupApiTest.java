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

import static org.hisp.dhis.support.Assertions.assertSuccessResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import org.hisp.dhis.model.Option;
import org.hisp.dhis.model.OptionGroup;
import org.hisp.dhis.model.OptionSet;
import org.hisp.dhis.response.HttpStatus;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.support.TestTags;
import org.hisp.dhis.util.UidUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class OptionGroupApiTest {
  private static final String OPTION_SET_ID = "VQ2lai3OfVG";

  @Test
  void testIsNotOptionGroup() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);
    assertFalse(dhis2.isOptionGroup("NOT_AN_OPTION_GROUP_ID"));
  }

  @Test
  void testCreateGetAndDeleteOptionGroup() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);
    String uidA = UidUtils.generateUid();

    OptionSet optionSet = dhis2.getOptionSet(OPTION_SET_ID);
    assertNotNull(optionSet);
    assertFalse(optionSet.getOptions().isEmpty());

    Option memberOption = new Option();
    memberOption.setId(optionSet.getOptions().get(0).getId());

    OptionSet optionSetRef = new OptionSet();
    optionSetRef.setId(optionSet.getId());

    OptionGroup optionGroup = new OptionGroup();
    optionGroup.setId(uidA);
    optionGroup.setName("NAME-" + uidA);
    optionGroup.setShortName("SHORT_NAME-" + uidA);
    optionGroup.setCode("CODE-" + uidA);
    optionGroup.setOptionSet(optionSetRef);
    optionGroup.setOptions(Set.of(memberOption));

    // Create
    ObjectResponse createResp = dhis2.saveMetadataObject(optionGroup);
    assertEquals(201, createResp.getHttpStatusCode().intValue(), createResp.toString());
    assertEquals(HttpStatus.CREATED, createResp.getHttpStatus(), createResp.toString());
    assertEquals(Status.OK, createResp.getStatus(), createResp.toString());
    assertNotNull(createResp.getResponse());
    assertNotNull(createResp.getResponse().getUid());

    assertTrue(dhis2.isOptionGroup(uidA));

    OptionGroup savedOptionGroup = dhis2.getOptionGroup(optionGroup.getId());
    assertNotNull(savedOptionGroup);
    assertEquals(optionGroup.getId(), savedOptionGroup.getId());
    assertEquals(optionGroup.getName(), savedOptionGroup.getName());
    assertEquals(optionGroup.getCode(), savedOptionGroup.getCode());
    assertEquals(optionGroup.getOptionSet(), savedOptionGroup.getOptionSet());
    assertEquals(1, savedOptionGroup.getOptions().size());

    // Remove
    ObjectResponse removeRespA = dhis2.removeOptionGroup(uidA);
    assertSuccessResponse(removeRespA, HttpStatus.OK, 200);
  }
}
