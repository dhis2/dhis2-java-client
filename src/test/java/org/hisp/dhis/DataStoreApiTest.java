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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Map;
import org.hisp.dhis.model.datastore.EntryMetadata;
import org.hisp.dhis.response.Response;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class DataStoreApiTest {
  @Test
  void testCrud() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    // Save

    Fruit banana = new Fruit("Banana", "Yellow");
    Fruit grape = new Fruit("Grape", "Green");

    Response rA = dhis2.saveDataStoreEntry("fruits", "banana", banana);
    Response rB = dhis2.saveDataStoreEntry("fruits", "grape", grape);

    assertEquals(Status.OK, rA.getStatus());
    assertEquals(Status.OK, rB.getStatus());

    banana = dhis2.getDataStoreEntry("fruits", "banana", Fruit.class);
    grape = dhis2.getDataStoreEntry("fruits", "grape", Fruit.class);

    assertNotNull(banana);
    assertEquals("Banana", banana.getName());
    assertEquals("Yellow", banana.getColor());

    assertNotNull(grape);
    assertEquals("Grape", grape.getName());
    assertEquals("Green", grape.getColor());

    // Get namespaces

    List<String> namespaces = dhis2.getDataStoreNamespaces();

    assertNotNull(namespaces);
    assertFalse(namespaces.isEmpty());

    // Get keys

    List<String> keys = dhis2.getDataStoreKeys("fruits");

    assertNotNull(keys);
    assertFalse(keys.isEmpty());

    List<Map<String, Object>> fruits =
        dhis2.getDatastoreEntries("fruits", List.of("name", "color"));

    assertNotNull(fruits);
    assertEquals(2, fruits.size());
    assertNotNull(fruits.get(0));
    assertNotNull(fruits.get(0).get("name"));
    assertNotNull(fruits.get(0).get("color"));

    // Update

    Fruit redBanana = new Fruit("Banana", "Red");

    Response rC = dhis2.updateDataStoreEntry("fruits", "banana", redBanana);

    assertEquals(Status.OK, rC.getStatus());

    redBanana = dhis2.getDataStoreEntry("fruits", "banana", Fruit.class);

    assertNotNull(redBanana);
    assertEquals("Red", redBanana.getColor());

    // Remove

    Response rD = dhis2.removeDataStoreEntry("fruits", "banana");
    Response rE = dhis2.removeDataStoreEntry("fruits", "grape");

    assertEquals(Status.OK, rD.getStatus());
    assertEquals(Status.OK, rE.getStatus());
  }

  @Test
  void testGetMetadata() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    Fruit mango = new Fruit("Mango", "Yellow");

    Response rA = dhis2.saveDataStoreEntry("fruits", "mango", mango);

    assertEquals(Status.OK, rA.getStatus());

    mango = dhis2.getDataStoreEntry("fruits", "mango", Fruit.class);

    assertNotNull(mango);

    EntryMetadata mangoMeta = dhis2.getDataStoreEntryMetadata("fruits", "mango");

    assertNotNull(mangoMeta);
    assertNotNull(mangoMeta.getId());
    assertNotNull(mangoMeta.getCreated());
    assertNotNull(mangoMeta.getLastUpdated());
    assertNotNull(mangoMeta.getLastUpdatedBy());
    assertEquals("fruits", mangoMeta.getNamespace());
    assertEquals("mango", mangoMeta.getKey());

    Response rB = dhis2.removeDataStoreNamespace("fruits");

    assertEquals(Status.OK, rB.getStatus());
  }
}
