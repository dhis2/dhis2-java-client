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

import static org.hisp.dhis.support.Assertions.assertNotBlank;
import static org.hisp.dhis.support.Assertions.assertNotEmpty;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import org.hisp.dhis.model.FileResource;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.response.fileresource.FileResourceReport;
import org.hisp.dhis.response.fileresource.FileResourceResponse;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class FileResourceApiTest {
  @Test
  void testGetFileResources() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    List<FileResource> resources = dhis2.getFileResources(Query.instance());

    assertNotEmpty(resources);

    FileResource file = resources.get(0);

    assertNotNull(file);
    assertNotBlank(file.getId());
    assertNotBlank(file.getContentType());
    assertNotNull(file.getContentLength());
    assertNotBlank(file.getContentMd5());
  }

  @Test
  void testGetFileResourceData() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    List<FileResource> resources = dhis2.getFileResources(Query.instance());

    assertNotEmpty(resources);

    FileResource file = resources.get(0);
    byte[] data = dhis2.getFileResourceData(file.getId());

    assertNotNull(data);
    assertTrue(data.length > 0);
  }

  /** Tests downloading a file from an aggregate data value. */
  @Test
  void testGetDataValueFile() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    String dataElement = "xk0krAO2KfJ";
    String period = "2025Q3";
    String orgUnit = "DiszpKrYNg8";

    byte[] data = dhis2.getDataValueFile(dataElement, period, orgUnit);

    assertNotNull(data);
    assertTrue(data.length > 0);
  }

  /** Tests downloading a file from an event data value. */
  @Test
  void testGetEventFile() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    String eventUid = "xk0krAO2KfJ";
    String dataElementUid = "DiszpKrYNg8";

    byte[] data = dhis2.getEventFile(eventUid, dataElementUid);

    assertNotNull(data);
    assertTrue(data.length > 0);
  }

  @Test
  void testSaveFileResource() throws IOException {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    File file = File.createTempFile("dhis2-java-client-test", ".txt");
    file.deleteOnExit();
    Files.writeString(file.toPath(), "Dhis2 Java client file resource test content");

    FileResourceResponse response = dhis2.saveFileResource(file);

    assertNotNull(response);
    assertTrue(response.isStatusOk());

    FileResourceReport report = response.getResponse();

    assertNotNull(report);

    FileResource fileResource = report.getFileResource();

    assertNotNull(fileResource);
    assertNotBlank(fileResource.getId());
  }

  @Test
  void testSaveFileResourceFromInputStream() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    InputStream input =
        new ByteArrayInputStream(
            "Dhis2 Java client file resource test content".getBytes(StandardCharsets.UTF_8));

    FileResourceResponse response =
        dhis2.saveFileResource("dhis2-java-client-test.txt", "text/plain", input);

    assertNotNull(response);
    assertTrue(response.isStatusOk());

    FileResourceReport report = response.getResponse();

    assertNotNull(report);

    FileResource fileResource = report.getFileResource();

    assertNotNull(fileResource);
    assertNotBlank(fileResource.getId());
  }
}
