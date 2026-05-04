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

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StopWatch {
  /** Enumeration of watch running states. */
  private enum RunningState {
    RUNNING,
    STOPPED,
    SUSPENDED,
    UNSTARTED
  }

  /** Running state. */
  private RunningState runningState = RunningState.UNSTARTED;

  /** Start time. */
  private Instant start;

  /** Stop time. */
  private Instant stop;

  /** Duration of last split. */
  private Duration lastSplitDuration;

  // Factory

  /**
   * Creates and starts a stop watch.
   *
   * @return a {@link StopWatch}.
   */
  public static StopWatch createStarted() {
    final StopWatch sw = new StopWatch();
    sw.start();
    return sw;
  }

  // Operations

  /**
   * Starts stop watch.
   *
   * @return this {@link StopWatch}.
   */
  public StopWatch start() {
    start = Instant.now();
    runningState = RunningState.RUNNING;
    return this;
  }

  /**
   * Stops stop watch.
   *
   * @return this {@link StopWatch}.
   */
  public StopWatch stop() {
    Instant now = Instant.now();
    Instant previousSplit = ObjectUtils.firstNonNull(stop, start);

    lastSplitDuration = Duration.between(previousSplit, now);
    stop = now;
    runningState = RunningState.STOPPED;

    return this;
  }

  /**
   * Splits stop watch.
   *
   * @return this {@link StopWatch}.
   */
  public StopWatch split() {
    return stop();
  }

  /**
   * Suspends stop watch.
   *
   * @return this {@link StopWatch}.
   */
  public StopWatch suspend() {
    stop = Instant.now();
    runningState = RunningState.SUSPENDED;
    return this;
  }

  /**
   * Resumes stop watch.
   *
   * @return this {@link StopWatch}.
   */
  public StopWatch resume() {
    Duration suspendDuration = Duration.between(Instant.now(), stop);
    start = start.plus(suspendDuration);
    runningState = RunningState.RUNNING;
    return this;
  }

  // Running state

  /**
   * Indicates whether the stop watch is currently running.
   *
   * @return true if the stop watch is currently running, false otherwise.
   */
  public boolean isRunning() {
    return RunningState.RUNNING == runningState;
  }

  /**
   * Indicates whether the stop watch is currently stopped.
   *
   * @return true if the stop watch is currently stopped, false otherwise.
   */
  public boolean isStopped() {
    return RunningState.STOPPED == runningState;
  }

  /**
   * Indicates whether the stop watch is currently suspended.
   *
   * @return true if the stop watch is currently suspended, false otherwise.
   */
  public boolean isSuspended() {
    return RunningState.SUSPENDED == runningState;
  }

  // Reads

  /**
   * Returns the duration between start and stop in milliseconds.
   *
   * @return the duration between start and stop in milliseconds.
   */
  public long getTime() {
    return ChronoUnit.MILLIS.between(start, stop);
  }

  /**
   * Returns the formatted time as a string.
   *
   * @return the formatted time as a string.
   */
  public String getTimeFormatted() {
    return DurationFormatUtils.formatDurationHMS(getTime());
  }

  /**
   * Returns the duration of the last split in milliseconds.
   *
   * @return the duration of the last split in milliseconds, or -1 if watch is not split.
   */
  public long getLastSplitDuration() {
    return lastSplitDuration != null ? lastSplitDuration.toMillis() : -1;
  }

  /**
   * Returns the formatted last split time as a string.
   *
   * @return the formatted last split time.
   */
  public String getLastSplitDurationFormatted() {
    return DurationFormatUtils.formatDurationHMS(getLastSplitDuration());
  }

  /**
   * Splits the time and returns the formatted last split time.
   *
   * @return the formatted last split time.
   */
  public String splitAndGetLastFormatted() {
    return split().getLastSplitDurationFormatted();
  }

  /**
   * Returns a string representation of the time.
   *
   * @return a string representation of the time.
   */
  @Override
  public String toString() {
    return getTimeFormatted();
  }
}
