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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;
import org.hisp.dhis.model.DataElement;
import org.hisp.dhis.model.DataElementGroup;
import org.hisp.dhis.model.IdentifiableObject;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class DataElementGroupApiTest {
  @Test
  void testGetDataElementGroup() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    DataElementGroup deg = dhis2.getDataElementGroup("SriP0jBXMr6");

    assertEquals("SriP0jBXMr6", deg.getId());
    assertNull(deg.getCode());
    assertEquals("Lassa Fever", deg.getName());
    assertEquals("Lassa Fever", deg.getShortName());
    assertNotNull(deg.getCreated());
    assertNotNull(deg.getLastUpdated());
    assertNull(deg.getDescription());

    // Group members assertions

    List<DataElement> dataElements = deg.getDataElements();

    assertNotNull(dataElements);
    assertEquals(3, dataElements.size());

    List<String> deIds =
        dataElements.stream().map(IdentifiableObject::getId).collect(Collectors.toList());

    List<String> deNames =
        dataElements.stream().map(IdentifiableObject::getName).collect(Collectors.toList());

    assertTrue(deIds.contains("dVdxnTNL2jZ"));
    assertTrue(deIds.contains("NCteyX2xpMf"));
    assertTrue(deIds.contains("uz8dqEzuxyc"));

    assertTrue(deNames.contains("Lassa fever new"));
    assertTrue(deNames.contains("Lassa fever follow-up"));
    assertTrue(deNames.contains("Lassa fever referrals"));
  }
}
