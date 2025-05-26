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

import org.hisp.dhis.model.ValueType;
import org.junit.jupiter.api.Test;

class TrackedEntityTest {
  @Test
  void testGetAttributeValue() {
    TrackedEntity te = new TrackedEntity();
    te.addAttributeValue(getAttributeValue("jTyx81h4qnD", ValueType.TEXT, "blue"));
    te.addAttributeValue(getAttributeValue("nfWLML6SZ4G", ValueType.TEXT, "green"));
    te.addAttributeValue(getAttributeValue("aug8T4IreCz", ValueType.TEXT, "grey"));

    assertEquals("blue", te.getAttributeValue("jTyx81h4qnD"));
    assertEquals("green", te.getAttributeValue("nfWLML6SZ4G"));
    assertEquals("grey", te.getAttributeValue("aug8T4IreCz"));
  }

  /**
   * Creates a TrackedEntityAttributeValue instance.
   *
   * @param attribute the attribute identifier.
   * @param valueType the {@link ValueType}.
   * @param value the value of the attribute.
   * @return a {@link TrackedEntityAttributeValue} instance.
   */
  private TrackedEntityAttributeValue getAttributeValue(
      String attribute, ValueType valueType, String value) {
    TrackedEntityAttributeValue attributeValue = new TrackedEntityAttributeValue();
    attributeValue.setAttribute(attribute);
    attributeValue.setValueType(valueType);
    attributeValue.setValue(value);
    return attributeValue;
  }
}
