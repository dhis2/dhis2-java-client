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
package org.hisp.dhis.model.trackedentity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.Date;
import org.hisp.dhis.util.DateTimeUtils;
import org.hisp.dhis.util.JacksonUtils;
import org.junit.jupiter.api.Test;

class TrackedEntityTest {
  @Test
  void testDeserialize() {
    Date dateTime = DateTimeUtils.toDateTime("2023-05-10T16:12:51.251");

    TrackedEntity te = new TrackedEntity();
    te.setTrackedEntity("cJ5VL10VSlZ");
    te.setOrgUnit("DiszpKrYNg8");
    te.setTrackedEntityType("nEenWmSyUEp");
    te.setCreatedAt(dateTime);
    te.setUpdatedAt(dateTime);

    String actual = JacksonUtils.toJsonString(te);

    String expected =
        """
        {\
        "trackedEntity":"cJ5VL10VSlZ",\
        "trackedEntityType":"nEenWmSyUEp",\
        "createdAt":"2023-05-10T16:12:51.251",\
        "updatedAt":"2023-05-10T16:12:51.251",\
        "orgUnit":"DiszpKrYNg8",\
        "attributes":[],\
        "enrollments":[]}""";

    assertEquals(expected, actual);
  }
  
  @Test
  void testGetAttributeValue() {
    TrackedEntity te = new TrackedEntity();
    te.addAttributeValue("jTyx81h4qnD", "blue");
    te.addAttributeValue("nfWLML6SZ4G", "heavy");
    te.addAttributeValue("aug8T4IreCz", "large");

    assertEquals("blue", te.getAttributeValue("jTyx81h4qnD"));
    assertEquals("heavy", te.getAttributeValue("nfWLML6SZ4G"));
    assertEquals("large", te.getAttributeValue("aug8T4IreCz"));
    assertNull(te.getAttributeValue("dYSQ9kbfFQ8"));
  }

  @Test
  void testGetTrackedEntityAttributeValue() {
    TrackedEntity te = new TrackedEntity();
    te.addAttributeValue("jTyx81h4qnD", "blue");
    te.addAttributeValue("nfWLML6SZ4G", "heavy");
    te.addAttributeValue("aug8T4IreCz", "large");

    assertEquals("blue", te.getTrackedEntityAttributeValue("jTyx81h4qnD").getValue());
    assertEquals("heavy", te.getTrackedEntityAttributeValue("nfWLML6SZ4G").getValue());
    assertEquals("large", te.getTrackedEntityAttributeValue("aug8T4IreCz").getValue());
    assertNull(te.getTrackedEntityAttributeValue("dYSQ9kbfFQ8"));
  }

  @Test
  void testAddTrackedEntityAttributeValue() {
    TrackedEntity te = new TrackedEntity();
    te.addAttributeValue("jTyx81h4qnD", "blue");
    assertEquals("blue", te.getAttributeValue("jTyx81h4qnD"));
    assertEquals(1, te.getAttributes().size());

    te.addAttributeValue("nfWLML6SZ4G", "heavy");
    assertEquals("heavy", te.getAttributeValue("nfWLML6SZ4G"));
    assertEquals(2, te.getAttributes().size());

    te.addAttributeValue("nfWLML6SZ4G", "light");
    assertEquals("light", te.getAttributeValue("nfWLML6SZ4G"));
    assertEquals(2, te.getAttributes().size());

    te.addAttributeValue("nfWLML6SZ4G", "super_light");
    assertEquals("super_light", te.getAttributeValue("nfWLML6SZ4G"));
    assertEquals(2, te.getAttributes().size());

    te.addAttributeValue("aug8T4IreCz", "large");
    assertEquals("large", te.getAttributeValue("aug8T4IreCz"));
    assertEquals(3, te.getAttributes().size());

    te.addAttributeValue("aug8T4IreCz", "small");
    assertEquals("small", te.getAttributeValue("aug8T4IreCz"));
    assertEquals(3, te.getAttributes().size());
  }
}
