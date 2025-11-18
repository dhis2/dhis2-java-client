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
package org.hisp.dhis.security.context;

import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/** Thread-local holder for {@link Dhis2Context}. */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Dhis2ContextHolder {
  private static final ThreadLocal<Dhis2Context> CONTEXT_HOLDER = new ThreadLocal<>();

  /**
   * Sets the {@link Dhis2Context} for the current thread.
   *
   * @param context the {@link Dhis2Context}.
   */
  public static void setContext(Dhis2Context context) {
    Objects.requireNonNull(context);
    Objects.requireNonNull(context.getSessionId());
    CONTEXT_HOLDER.set(context);
  }

  /**
   * Gets the {@link Dhis2Context} for the current thread.
   *
   * @return the {@link Dhis2Context}.
   */
  public static Dhis2Context getContext() {
    return CONTEXT_HOLDER.get();
  }

  /** Clears the {@link Dhis2Context} for the current thread. */
  public static void clearContext() {
    CONTEXT_HOLDER.remove();
  }
}
