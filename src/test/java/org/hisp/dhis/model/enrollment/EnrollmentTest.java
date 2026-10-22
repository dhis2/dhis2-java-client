/*
 * Copyright (c) 2004-2026, University of Oslo
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
package org.hisp.dhis.model.enrollment;

import static org.hisp.dhis.support.Assertions.assertSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.hisp.dhis.support.TestTags;
import org.hisp.dhis.util.DateTimeUtils;
import org.hisp.dhis.util.JacksonUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.UNIT)
class EnrollmentTest {
  @Test
  void testDeserializeAttributes() {
    String json =
        """
        {\
        "enrollment":"MNWZ6hnuhSw",\
        "attributes":[\
        {"attribute":"jTyx81h4qnD","value":"blue"},\
        {"attribute":"nfWLML6SZ4G","value":"heavy"}]}\
        """;

    Enrollment enrollment = JacksonUtils.fromJson(json, Enrollment.class);

    assertEquals("MNWZ6hnuhSw", enrollment.getEnrollment());
    assertTrue(enrollment.hasAttributes());
    assertSize(2, enrollment.getAttributes());
    assertEquals("blue", enrollment.getAttributeValue("jTyx81h4qnD"));
    assertEquals("heavy", enrollment.getAttributeValue("nfWLML6SZ4G"));
  }

  @Test
  void testSerializeAttributes() {
    Enrollment enrollment = new Enrollment("MNWZ6hnuhSw");
    enrollment.addAttributeValue("jTyx81h4qnD", "blue");

    String actual = JacksonUtils.toJsonString(enrollment);

    assertTrue(actual.contains("\"attributes\":[{\"attribute\":\"jTyx81h4qnD\""));
    assertTrue(actual.contains("\"value\":\"blue\""));
  }

  @Test
  void testHasAttributes() {
    Enrollment enrollment = new Enrollment();
    assertNotNull(enrollment.getAttributes());
    assertFalse(enrollment.hasAttributes());

    enrollment.addAttributeValue("jTyx81h4qnD", "blue");
    assertTrue(enrollment.hasAttributes());
  }

  @Test
  void testGetAttributeValue() {
    Enrollment enrollment = new Enrollment();
    enrollment.addAttributeValue("jTyx81h4qnD", "blue");
    enrollment.addAttributeValue("nfWLML6SZ4G", "heavy");
    enrollment.addAttributeValue("aug8T4IreCz", "large");

    assertEquals("blue", enrollment.getAttributeValue("jTyx81h4qnD"));
    assertEquals("heavy", enrollment.getAttributeValue("nfWLML6SZ4G"));
    assertEquals("large", enrollment.getAttributeValue("aug8T4IreCz"));
    assertNull(enrollment.getAttributeValue("dYSQ9kbfFQ8"));
  }

  @Test
  void testGetTrackedEntityAttributeValue() {
    Enrollment enrollment = new Enrollment();
    enrollment.addAttributeValue("jTyx81h4qnD", "blue");
    enrollment.addAttributeValue("nfWLML6SZ4G", "heavy");

    assertEquals("blue", enrollment.getTrackedEntityAttributeValue("jTyx81h4qnD").getValue());
    assertEquals("heavy", enrollment.getTrackedEntityAttributeValue("nfWLML6SZ4G").getValue());
    assertNull(enrollment.getTrackedEntityAttributeValue("dYSQ9kbfFQ8"));
  }

  @Test
  void testGetDateAttributeValue() {
    Enrollment enrollment = new Enrollment();
    enrollment.addAttributeValue("jTyx81h4qnD", "2023-05-10T16:12:51.251");
    enrollment.addAttributeValue("nfWLML6SZ4G", "");

    assertEquals(
        DateTimeUtils.toDateTime("2023-05-10T16:12:51.251"),
        enrollment.getDateAttributeValue("jTyx81h4qnD"));
    assertNull(enrollment.getDateAttributeValue("nfWLML6SZ4G"));
    assertNull(enrollment.getDateAttributeValue("dYSQ9kbfFQ8"));
  }

  @Test
  void testAddAttributeValue() {
    Enrollment enrollment = new Enrollment();
    enrollment.addAttributeValue("jTyx81h4qnD", "blue");
    assertEquals("blue", enrollment.getAttributeValue("jTyx81h4qnD"));
    assertSize(1, enrollment.getAttributes());

    enrollment.addAttributeValue("nfWLML6SZ4G", "heavy");
    assertEquals("heavy", enrollment.getAttributeValue("nfWLML6SZ4G"));
    assertSize(2, enrollment.getAttributes());

    enrollment.addAttributeValue("nfWLML6SZ4G", "light");
    assertEquals("light", enrollment.getAttributeValue("nfWLML6SZ4G"));
    assertSize(2, enrollment.getAttributes());
  }
}
