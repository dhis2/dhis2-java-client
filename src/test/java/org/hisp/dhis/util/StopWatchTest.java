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
package org.hisp.dhis.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import org.apache.commons.lang3.ThreadUtils;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.UNIT)
class StopWatchTest {
  @Test
  void testStopGetTime() {
    StopWatch watch = StopWatch.createStarted();
    sleep();
    watch.stop();
    assertPositive(watch.getTime());
  }

  @Test
  void testSplitGetTime() {
    StopWatch watch = StopWatch.createStarted();
    sleep();
    watch.split();
    assertPositive(watch.getTime());
  }

  @Test
  void testSplitStopGetTime() {
    StopWatch watch = StopWatch.createStarted();
    sleep();
    watch.split();
    assertPositive(watch.getTime());
    sleep();
    watch.stop();
    assertPositive(watch.getTime());
  }

  @Test
  void testSplitGetLastSplitDuration() {
    StopWatch watch = StopWatch.createStarted();
    sleep();
    assertEquals(-1, watch.getLastSplitDuration());
    watch.split();
    assertPositive(watch.getLastSplitDuration());
  }

  @Test
  void testStopGetLastSplitDuration() {
    StopWatch watch = StopWatch.createStarted();
    sleep();
    assertEquals(-1, watch.getLastSplitDuration());
    watch.stop();
    assertPositive(watch.getLastSplitDuration());
  }

  @Test
  void testSuspendResume() {
    StopWatch watch = StopWatch.createStarted();
    sleep();
    watch.suspend();
    assertTrue(watch.getTime() > 0);
    long time = watch.getTime();
    sleep();
    assertEquals(time, watch.getTime());
    watch.resume();
    sleep();
    assertTrue(watch.getTime() > time);
    watch.stop();
  }

  @Test
  void testGetRunningState() {
    StopWatch watch = StopWatch.createStarted();
    assertTrue(watch.isRunning());
    assertFalse(watch.isSuspended());
    assertFalse(watch.isStopped());

    watch.stop();
    assertFalse(watch.isRunning());
    assertFalse(watch.isSuspended());
    assertTrue(watch.isStopped());

    watch.start();
    assertTrue(watch.isRunning());
    assertFalse(watch.isSuspended());
    assertFalse(watch.isStopped());

    watch.suspend();
    assertFalse(watch.isRunning());
    assertTrue(watch.isSuspended());
    assertFalse(watch.isStopped());

    watch.resume();
    assertTrue(watch.isRunning());
    assertFalse(watch.isSuspended());
    assertFalse(watch.isStopped());

    watch.stop();
    assertFalse(watch.isRunning());
    assertFalse(watch.isSuspended());
    assertTrue(watch.isStopped());
  }

  @Test
  void testToString() {
    StopWatch watch = StopWatch.createStarted();
    sleep();
    watch.stop();
    assertNotNull(watch.toString());
  }

  /** Sleeps for a short duration. */
  private void sleep() {
    ThreadUtils.sleepQuietly(Duration.ofMillis(20));
  }

  /** Asserts that the given long value is positive. */
  void assertPositive(long value) {
    assertTrue(value > 0, "Long value is not positive: " + value);
  }
}
