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
package org.hisp.dhis;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import lombok.Getter;
import org.apache.commons.lang3.Strings;
import org.apache.hc.core5.net.URIBuilder;
import org.hisp.dhis.auth.AccessTokenAuthentication;
import org.hisp.dhis.auth.Authentication;
import org.hisp.dhis.auth.BasicAuthentication;
import org.hisp.dhis.auth.CookieAuthentication;
import org.hisp.dhis.response.Dhis2ClientException;

/**
 * Configuration information about a DHIS 2 instance.
 *
 * @author Lars Helge Overland
 */
@Getter
public class Dhis2Config {
  private final String url;

  private final Authentication authentication;

  /**
   * Constructor. Uses basic authentication.
   *
   * @param url the URL to the DHIS 2 instance, do not include the {@code /api} part or a trailing
   *     {@code /}.
   * @param username the DHIS 2 account username.
   * @param password the DHIS 2 account password.
   */
  public Dhis2Config(String url, String username, String password) {
    Objects.requireNonNull(url);
    Objects.requireNonNull(username);
    Objects.requireNonNull(password);
    this.url = normalizeUrl(url);
    this.authentication = new BasicAuthentication(username, password);
  }

  /**
   * Constructor. Uses basic authentication.
   *
   * @param url the URL to the DHIS 2 instance, do not include the {@code /api} part or a trailing
   *     {@code /}.
   * @param authentication the {@link Authentication}, can be {@link BasicAuthentication}, {@link
   *     AccessTokenAuthentication} or {@link CookieAuthentication}.
   */
  public Dhis2Config(String url, Authentication authentication) {
    Objects.requireNonNull(url);
    Objects.requireNonNull(authentication);
    this.url = normalizeUrl(url);
    this.authentication = authentication;
  }

  /**
   * Normalizes the given URL.
   *
   * @param url the URL string.
   * @return a URL string.
   */
  String normalizeUrl(String url) {
    return Strings.CS.removeEnd(url, "/");
  }

  /**
   * Provides a fully qualified {@link URI} to the DHIS 2 instance API.
   *
   * @param path the URL path (the URL part after {@code /api/}.
   * @return a fully qualified {@link URI} to the DHIS 2 instance API.
   */
  public URI getResolvedUrl(String path) {
    Objects.requireNonNull(path);

    try {
      return new URIBuilder(url).appendPath("api").appendPath(path).build();
    } catch (URISyntaxException ex) {
      throw new Dhis2ClientException(String.format("Invalid URI syntax: '%s'", url), ex);
    }
  }

  /**
   * Provides a {@link URIBuilder} which is resolved to the DHIS 2 instance API.
   *
   * @return a {@link URIBuilder}.
   */
  public URIBuilder getResolvedUriBuilder() {
    try {
      return new URIBuilder(url).appendPath("api");
    } catch (URISyntaxException ex) {
      throw new Dhis2ClientException(String.format("Invalid URI syntax: '%s'", url), ex);
    }
  }
}
