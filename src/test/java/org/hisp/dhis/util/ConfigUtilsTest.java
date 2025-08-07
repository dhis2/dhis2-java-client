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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class ConfigUtilsTest {
  @Test
  void testGetAsList() {
    List<String> expected =
        List.of("http://localhost", "http://localhost:3000", "https://localhost:3000");

    String actual = "http://localhost,http://localhost:3000, ,, https://localhost:3000";

    assertEquals(expected, ConfigUtils.getAsList(actual));
    assertEquals(List.of(), ConfigUtils.getAsList(null));
    assertEquals(List.of(), ConfigUtils.getAsList(""));
  }

  @Test
  void testGetAsIntListA() {
    List<Integer> expected = List.of(15, 22, 36);

    String actual = "15,22,,null,,foo,36,BAR,,";

    assertEquals(expected, ConfigUtils.getAsIntList(actual));
    assertEquals(List.of(), ConfigUtils.getAsIntList(null));
    assertEquals(List.of(), ConfigUtils.getAsIntList(""));
  }

  @Test
  void testGetAsIntListB() {
    assertEquals(List.of(3, 5, 9), ConfigUtils.getAsIntList("3,5,9"));
    assertEquals(List.of(14, 51, 92), ConfigUtils.getAsIntList("14,51,92"));
    assertEquals(List.of(512, 981, 431), ConfigUtils.getAsIntList("512,981,431"));
  }

  @Test
  void testGetAsSet() {
    Set<String> expected =
        Set.of("http://localhost", "http://localhost:3000", "https://localhost:3000");

    String actual = "http://localhost,http://localhost:3000, ,, https://localhost:3000";

    assertEquals(expected, ConfigUtils.getAsSet(actual));
    assertEquals(Set.of(), ConfigUtils.getAsSet(null));
    assertEquals(Set.of(), ConfigUtils.getAsSet(""));
  }

  @Test
  void testGetAsArray() {
    String actual = "http://localhost,http://localhost:3000, ,, https://localhost:3000";

    assertEquals(3, ConfigUtils.getAsArray(actual).length);
    assertEquals("http://localhost", ConfigUtils.getAsArray(actual)[0]);
  }

  @Test
  void testIsEnabled() {
    assertTrue(ConfigUtils.isEnabled("on"));
    assertFalse(ConfigUtils.isEnabled("off"));
    assertFalse(ConfigUtils.isEnabled("foo"));
    assertFalse(ConfigUtils.isEnabled(""));
    assertFalse(ConfigUtils.isEnabled(null));
  }

  @Test
  void testIsNotEnabled() {
    assertFalse(ConfigUtils.isNotEnabled("on"));
    assertTrue(ConfigUtils.isNotEnabled("off"));
    assertTrue(ConfigUtils.isNotEnabled("foo"));
    assertTrue(ConfigUtils.isNotEnabled(""));
    assertTrue(ConfigUtils.isNotEnabled(null));
  }

  @Test
  void testIsDisabled() {
    assertFalse(ConfigUtils.isDisabled("on"));
    assertTrue(ConfigUtils.isDisabled("off"));
    assertFalse(ConfigUtils.isDisabled("foo"));
    assertFalse(ConfigUtils.isDisabled(""));
    assertFalse(ConfigUtils.isDisabled(null));
  }
}
