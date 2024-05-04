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
package org.hisp.dhis.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.hisp.dhis.model.event.Event;
import org.hisp.dhis.model.event.EventDataValue;
import org.junit.jupiter.api.Test;

class EventTest {
  @Test
  void testAddEventDataValue() {
    Event event = new Event("q4Oloz9P8zj");
    event.addDataValue(new EventDataValue("i7LbGuGEana", "1"));
    event.addDataValue(new EventDataValue("hK06Hg9jDSB", "2"));
    event.addDataValue(new EventDataValue("i7LbGuGEana", "3")); // Exists
    event.addDataValue(new EventDataValue("b5VnIJbjioI", "4"));
    event.addDataValue(new EventDataValue("hK06Hg9jDSB", "5")); // Exists

    assertEquals(3, event.getDataValues().size());
    assertEquals("3", event.getEventDataValue("i7LbGuGEana").getValue());
    assertEquals("5", event.getEventDataValue("hK06Hg9jDSB").getValue());
    assertEquals("4", event.getEventDataValue("b5VnIJbjioI").getValue());
  }

  @Test
  void testGetEventDataValue() {
    Event event = new Event("q4Oloz9P8zj");
    event.addDataValue(new EventDataValue("i7LbGuGEana", "1"));
    event.addDataValue(new EventDataValue("hK06Hg9jDSB", "2"));
    event.addDataValue(new EventDataValue("KvIzgr6osJQ", "3"));
    event.addDataValue(new EventDataValue("b5VnIJbjioI", "4"));
    event.addDataValue(new EventDataValue("tbqtM7AQpnl", "5"));

    assertEquals("3", event.getEventDataValue("KvIzgr6osJQ").getValue());
    assertEquals("5", event.getEventDataValue("tbqtM7AQpnl").getValue());
    assertNull(event.getDataValue("w4wYmYSS8ng"));
  }

  @Test
  void testGetDataValue() {
    Event event = new Event("q4Oloz9P8zj");
    event.addDataValue(new EventDataValue("i7LbGuGEana", "1"));
    event.addDataValue(new EventDataValue("hK06Hg9jDSB", "2"));
    event.addDataValue(new EventDataValue("KvIzgr6osJQ", "3"));
    event.addDataValue(new EventDataValue("b5VnIJbjioI", "4"));
    event.addDataValue(new EventDataValue("tbqtM7AQpnl", "5"));

    assertEquals("3", event.getDataValue("KvIzgr6osJQ"));
    assertEquals("5", event.getDataValue("tbqtM7AQpnl"));
    assertNull(event.getDataValue("w4wYmYSS8ng"));
  }

  @Test
  void testGetValueAsBoolean() {
    EventDataValue value = new EventDataValue("rNwYgGgYzPA", "true");

    assertTrue(value.isBoolean());
    assertTrue(value.getBooleanValue());

    value = new EventDataValue("rNwYgGgYzPA", "TRUE");

    assertTrue(value.isBoolean());
    assertTrue(value.getBooleanValue());

    value = new EventDataValue("rNwYgGgYzPA", "false");

    assertTrue(value.isBoolean());
    assertFalse(value.getBooleanValue());

    value = new EventDataValue("rNwYgGgYzPA", "FALSE");

    assertTrue(value.isBoolean());
    assertFalse(value.getBooleanValue());

    value = new EventDataValue("rNwYgGgYzPA", "1");

    assertFalse(value.isBoolean());
    assertNull(value.getBooleanValue());

    value = new EventDataValue("rNwYgGgYzPA", null);

    assertFalse(value.isBoolean());
    assertNull(value.getBooleanValue());
  }

  @Test
  void testGetValueAsInteger() {
    EventDataValue value = new EventDataValue("rNwYgGgYzPA", "142");

    assertTrue(value.isInteger());
    assertEquals(142, value.getIntegerValue());

    value = new EventDataValue("rNwYgGgYzPA", "14.73");

    assertFalse(value.isInteger());
    assertNull(value.getIntegerValue());

    value = new EventDataValue("rNwYgGgYzPA", null);

    assertFalse(value.isInteger());
    assertNull(value.getIntegerValue());
  }

  @Test
  void testGetValueAsDouble() {
    EventDataValue value = new EventDataValue("rNwYgGgYzPA", "18.52");

    assertTrue(value.isDouble());
    assertEquals(18.52, value.getDoubleValue());

    value = new EventDataValue("rNwYgGgYzPA", "Yes");

    assertFalse(value.isDouble());
    assertNull(value.getDoubleValue());

    value = new EventDataValue("rNwYgGgYzPA", null);

    assertFalse(value.isDouble());
    assertNull(value.getDoubleValue());
  }

  @Test
  void testGetValueAsLocalDate() {
    EventDataValue value = new EventDataValue("rNwYgGgYzPA", "2018-10-02");

    assertTrue(value.isLocalDate());
    assertEquals(2018, value.getLocalDateValue().getYear());
    assertEquals(10, value.getLocalDateValue().getMonthValue());
    assertEquals(2, value.getLocalDateValue().getDayOfMonth());

    value = new EventDataValue("rNwYgGgYzPA", "20190804");

    assertFalse(value.isLocalDate());

    value = new EventDataValue("rNwYgGgYzPA", "2020-07-12T14:52:00.000");

    assertFalse(value.isLocalDate());
    assertTrue(value.isLocalDateTime());
    assertEquals(2020, value.getLocalDateValue().getYear());
    assertEquals(7, value.getLocalDateValue().getMonthValue());
    assertEquals(12, value.getLocalDateValue().getDayOfMonth());
  }

  @Test
  void testGetValueAsLocalDateTime() {
    EventDataValue value = new EventDataValue("rNwYgGgYzPA", "2018-10-02T14:52:00.000Z");

    assertTrue(value.isLocalDateTime());
    assertEquals(2018, value.getLocalDateTimeValue().getYear());
    assertEquals(10, value.getLocalDateTimeValue().getMonthValue());
    assertEquals(2, value.getLocalDateTimeValue().getDayOfMonth());

    value = new EventDataValue("rNwYgGgYzPA", "2018");

    assertFalse(value.isLocalDateTime());
    assertNull(value.getLocalDateTimeValue());
  }
}
