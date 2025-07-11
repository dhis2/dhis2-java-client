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

import static org.hisp.dhis.util.DateTimeUtils.toDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;
import org.hisp.dhis.model.event.Event;
import org.hisp.dhis.model.event.EventStatus;
import org.hisp.dhis.support.TestTags;
import org.hisp.dhis.util.DateTimeUtils;
import org.hisp.dhis.util.JacksonUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

@Tag(TestTags.UNIT)
class EventTest {
  @Test
  void testSetPointGeometry() {
    PrecisionModel precision = new PrecisionModel(PrecisionModel.FLOATING);

    Event event = new Event("fq7DInE403B");
    event.setProgramStage("Zj7UnCAulEk");
    event.setOrgUnit("DiszpKrYNg8");
    event.setOccurredAt(toDate(2021, 7, 12));
    event.setPointGeometry(10.752, 59.914);

    Geometry geometry = event.getGeometry();

    assertNotNull(geometry);
    assertTrue(geometry instanceof Point);

    Point point = (Point) geometry;

    assertEquals(10.752, point.getX());
    assertEquals(59.914, point.getY());
    assertEquals(Geometry.TYPENAME_POINT, point.getGeometryType());
    assertEquals(precision, point.getPrecisionModel());
  }

  @Test
  void testSerializeGeometryPoint() {
    Event event = new Event("fq7DInE403B");
    event.setPointGeometry(10.752, 59.914);

    String actual = JacksonUtils.toJsonString(event);

    String expected =
        """
        {\
        "status":"ACTIVE",\
        "geometry":{"type":"Point","coordinates":[10.752,59.914]},\
        "dataValues":[],\
        "event":"fq7DInE403B"}""";

    assertEquals(expected, actual);
  }

  @Test
  void testDeserializeGeometryPoint() {
    String json =
        """
        {\
        "status":"ACTIVE",\
        "geometry":{"type":"Point","coordinates":[10.752,59.914]},\
        "dataValues":[],\
        "event":"fq7DInE403B"}""";

    Event event = JacksonUtils.fromJson(json, Event.class);

    assertNotNull(event);
    assertEquals("fq7DInE403B", event.getId());
    assertEquals(EventStatus.ACTIVE, event.getStatus());
    assertTrue(event.isGeometryPoint());

    Point point = (Point) event.getGeometry();

    assertNotNull(point);
    assertEquals(10.752, point.getX());
    assertEquals(59.914, point.getY());
  }

  @Test
  void testSerializeOccurredAt() {
    Date dateTime = DateTimeUtils.toDateTime("2025-03-10T14:35:22.314");

    Event event =
        new Event(
            "fq7DInE403B",
            "Zj7UnCAulEk",
            "DiszpKrYNg8",
            EventStatus.COMPLETED,
            dateTime,
            List.of());
    event.setCreatedAt(dateTime);
    event.setUpdatedAt(dateTime);
    event.setCompletedAt(dateTime);

    String actual = JacksonUtils.toJsonString(event);

    String expected =
        """
        {\
        "programStage":"Zj7UnCAulEk",\
        "orgUnit":"DiszpKrYNg8",\
        "status":"COMPLETED",\
        "createdAt":"2025-03-10T14:35:22.314",\
        "updatedAt":"2025-03-10T14:35:22.314",\
        "occurredAt":"2025-03-10T14:35:22.314",\
        "completedAt":"2025-03-10T14:35:22.314",\
        "dataValues":[],\
        "event":"fq7DInE403B"}""";

    assertEquals(expected, actual);
  }
}
