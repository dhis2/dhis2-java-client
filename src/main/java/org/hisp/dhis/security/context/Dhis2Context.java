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

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.Objects;
import lombok.Getter;

/** Represents the security context for a DHIS2 session. */
@Getter
public class Dhis2Context {
  /** The DHIS2 session identifier. */
  private final String sessionId;

  /** The username of the currently authenticated user. */
  private final String username;

  /**
   * Constructor.
   *
   * @param sessionId the session identifier.
   */
  public Dhis2Context(String sessionId) {
    this.sessionId = sessionId;
    this.username = null;
    Objects.requireNonNull(sessionId);
  }

  /**
   * Constructor.
   *
   * @param sessionId the session identifier.
   * @param username the username of the currently authenticated user.
   */
  public Dhis2Context(String sessionId, String username) {
    this.sessionId = sessionId;
    this.username = username;
    Objects.requireNonNull(sessionId);
  }

  /** Indicates whether a session identifier is present. */
  public boolean hasSessionId() {
    return isNotBlank(sessionId);
  }

  /** Indicates whether a username is present. */
  public boolean hasUsername() {
    return isNotBlank(username);
  }
}
