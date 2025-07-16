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

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hisp.dhis.ApiTestUtils.assertSuccessResponse;
import static org.hisp.dhis.support.Assertions.assertNotBlank;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.hisp.dhis.model.Document;
import org.hisp.dhis.response.HttpStatus;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.support.TestTags;
import org.hisp.dhis.util.UidUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class DocumentApiTest {
  @Test
  void testGetDocument() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    Document document = dhis2.getDocument("hKYLLpNinZR");

    assertNotNull(document);
    assertEquals("hKYLLpNinZR", document.getId());
    assertNotBlank(document.getName());
    assertNotBlank(document.getUrl());
    assertTrue(document.getExternal());
    assertTrue(document.isExternal());
    assertFalse(document.isAttachment());
  }

  @Test
  void testSaveWriteDocumentDataAndRemoveDocument() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);
    String uid = UidUtils.generateUid();

    String documentURL = TestFixture.DEFAULT_URL + "/api/options/Y1ILwhy5VDY.json";

    Document document = new Document();
    document.setName(uid);
    document.setCode(uid);
    document.setUrl(documentURL);
    document.setAttachment(true);
    document.setExternal(true);

    // Create
    ObjectResponse createRespA = dhis2.saveDocument(document);
    assertSuccessResponse(createRespA, HttpStatus.CREATED, 201);
    String documentUid = createRespA.getResponse().getUid();

    // Get
    document = dhis2.getDocument(documentUid);

    assertNotNull(document);
    assertEquals(documentUid, document.getId());
    assertEquals(uid, document.getName());
    assertEquals(uid, document.getCode());
    assertEquals(documentURL, document.getUrl());
    assertTrue(document.isExternal());
    assertTrue(document.isAttachment());

    // Write document data
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    dhis2.writeDocumentData(documentUid, out);

    String docValue = out.toString(UTF_8);
    assertNotNull(docValue);
    assertTrue(docValue.contains("Y1ILwhy5VDY"));

    // Remove
    ObjectResponse removeRespA = dhis2.removeDocument(documentUid);
    assertSuccessResponse(removeRespA, HttpStatus.OK, 200);
  }
}
