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
package org.hisp.dhis.util;

import static org.hisp.dhis.support.Assertions.assertContainsExactlyInOrder;
import static org.hisp.dhis.support.TestObjects.set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.hisp.dhis.model.DataElement;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.UNIT)
class IdentifiableObjectUtilsTest {
  @Test
  void testToIdentifiers() {
    DataElement deA = set(new DataElement(), 'A');
    DataElement deB = set(new DataElement(), 'B');
    DataElement deC = set(new DataElement(), 'C');

    List<String> objects = IdentifiableObjectUtils.toIdentifiers(List.of(deA, deB, deC));

    assertContainsExactlyInOrder(objects, deA.getId(), deB.getId(), deC.getId());
  }

  @Test
  void testToCodes() {
    DataElement deA = set(new DataElement(), 'A');
    DataElement deB = set(new DataElement(), 'B');
    DataElement deC = set(new DataElement(), 'C');

    List<String> objects = IdentifiableObjectUtils.toCodes(List.of(deA, deB, deC));

    assertContainsExactlyInOrder(objects, deA.getCode(), deB.getCode(), deC.getCode());
  }

  @Test
  void testNewInstance() {
    DataElement instance = IdentifiableObjectUtils.newInstance(DataElement.class);

    assertNotNull(instance);
    assertTrue(instance instanceof DataElement);
  }

  @Test
  void testGetInstance() {
    DataElement de = set(new DataElement(), 'A');
    DataElement instance = IdentifiableObjectUtils.getInstance(de, DataElement.class);

    assertNotNull(instance);
    assertEquals(de.getId(), instance.getId());
  }

  @Test
  void testToIdObjects() {
    DataElement deA = set(new DataElement(), 'A');
    DataElement deB = set(new DataElement(), 'B');
    DataElement deC = set(new DataElement(), 'C');

    List<DataElement> objects =
        IdentifiableObjectUtils.toIdObjects(List.of(deA, deB, deC), DataElement.class);

    DataElement idA = new DataElement();
    idA.setId(deA.getId());
    DataElement idB = new DataElement();
    idB.setId(deB.getId());
    DataElement idC = new DataElement();
    idC.setId(deC.getId());

    assertContainsExactlyInOrder(objects, idA, idB, idC);
  }
}
