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
package org.hisp.dhis.response.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.hisp.dhis.response.datavalueset.DataValueSetResponse;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.UNIT)
class AsyncSummaryResponseTest {
  @Test
  void testHasImportCountWhenNull() {
    DataValueSetResponse response = new DataValueSetResponse();

    assertFalse(response.hasImportCount());
  }

  @Test
  void testHasImportCountWhenPresent() {
    DataValueSetResponse response = new DataValueSetResponse();
    response.setImportCount(new ImportCount());

    assertTrue(response.hasImportCount());
  }

  @Test
  void testGetAffectedCountWhenNoImportCount() {
    DataValueSetResponse response = new DataValueSetResponse();

    assertEquals(0, response.getAffectedCount());
  }

  @Test
  void testGetAffectedCount() {
    ImportCount importCount = new ImportCount();
    importCount.setImported(5);
    importCount.setUpdated(3);
    importCount.setDeleted(2);
    importCount.setIgnored(4);

    DataValueSetResponse response = new DataValueSetResponse();
    response.setImportCount(importCount);

    assertEquals(10, response.getAffectedCount());
  }

  @Test
  void testGetAffectedCountExcludesIgnored() {
    ImportCount importCount = new ImportCount();
    importCount.setImported(2);
    importCount.setUpdated(1);
    importCount.setDeleted(0);
    importCount.setIgnored(10);

    DataValueSetResponse response = new DataValueSetResponse();
    response.setImportCount(importCount);

    assertEquals(3, response.getAffectedCount());
  }

  @Test
  void testStatusAndDescription() {
    DataValueSetResponse response = new DataValueSetResponse();
    response.setStatus(Status.SUCCESS);
    response.setDescription("Import successful");

    assertEquals(Status.SUCCESS, response.getStatus());
    assertEquals("Import successful", response.getDescription());
  }

  @Test
  void testConflictsDefaultEmpty() {
    DataValueSetResponse response = new DataValueSetResponse();

    assertTrue(response.getConflicts().isEmpty());
  }
}
