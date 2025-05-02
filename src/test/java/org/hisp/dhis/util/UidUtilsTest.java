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
package org.hisp.dhis.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.UNIT)
class UidUtilsTest {
  @Test
  void testGetUid() {
    String uid = UidUtils.generateUid();

    assertNotNull(uid);
    assertEquals(11, uid.length());
  }

  @Test
  void testUidIsValid() {
    assertTrue(UidUtils.isValidUid("mq4jAnN6fg3"));
    assertTrue(UidUtils.isValidUid("QX4LpiTZmUH"));
    assertTrue(UidUtils.isValidUid("rT1hdSWjfDC"));

    assertFalse(UidUtils.isValidUid("1T1hdSWjfDC"));
    assertFalse(UidUtils.isValidUid("QX4LpiTZmUHg"));
    assertFalse(UidUtils.isValidUid("1T1hdS_WjfD"));
  }

  @Test
  void testToUid() {
    assertToUid("PpZ!m3thN#sm8QVcOdwTcil4");
    assertToUid("5$tiq7K9zMmUX$9VFXaQLFK6d&ShHQUw");
    assertToUid("9ceyjK4b^Xoc0&lKCn0Bqz5xAsYz&$heWypB");
    assertToUid("B5*GfX&Yklr!OHIK1KdaGeXGUt97&#1U4hTAE*bA**ce7@#oO2lB^0Rs9E#G8sJe");
    assertToUid("!OGvawSH8fKIUtIpVl$9^TfMV%V08vHm%uDeT1hnh6d22q7OQSjS7csF05bFRATeUIN&8wX2");
    assertToUid("yjZ2ec#*s9RMpmt^svZN8LyBJUOt&mY8&7nHZ3u%13^ObekBDA!a8ov&enxPE$EuE$GPh1xiy6parm");
  }

  /**
   * Asserts that the method generates a valid UID based on the given identifier.
   *
   * @param uid
   */
  private void assertToUid(String id) {
    String msg = String.format("UID not valid: '%s'", id);
    assertTrue(UidUtils.isValidUid(UidUtils.toUid(id)), msg);
  }
}
