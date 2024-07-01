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

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.core5.http.message.BasicHttpRequest;
import org.apache.hc.core5.net.URIBuilder;
import org.hisp.dhis.Dhis2Config;
import org.hisp.dhis.auth.Authentication;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpUtils {
  /**
   * Adds a HTTP header for authentication based on the {@link Authentication} of the given {@link
   * Dhis2Config}.
   *
   * @param request the {@link HttpUriRequestBase}.
   * @param config the {@link Dhis2Config}.
   * @param <T> the request class type.
   * @return the request object.
   */
  public static <T extends HttpUriRequestBase> T withAuth(T request, Dhis2Config config) {
    Authentication auth = config.getAuthentication();
    request.setHeader(auth.getHttpHeaderAuthName(), auth.getHttpHeaderAuthValue());
    return request;
  }

  /**
   * Builds on URI without throwing checked exceptions.
   *
   * @param uriBuilder the {@link URIBuilder}.
   * @return a {@link URI}.
   */
  public static URI build(URIBuilder uriBuilder) {
    try {
      return uriBuilder.build();
    } catch (URISyntaxException ex) {
      throw new RuntimeException(ex);
    }
  }

  /**
   * Returns the string representing the given URI. The URI is decoded.
   *
   * @param uri the {@link URI}.
   * @return a URI string.
   */
  public static String asString(URI uri) {
    try {
      return URLDecoder.decode(uri.toString(), StandardCharsets.UTF_8.toString());
    } catch (UnsupportedEncodingException ex) {
      throw new RuntimeException(ex);
    }
  }
  
  /**
   * Returns the string representing the URI the given HTTP request. The URI is decoded.
   * 
   * @param request the {@link BasicHttpRequest}.
   * @return a URI string.
   */
  public static String getUriAsString(BasicHttpRequest request) {
    try {
      return asString(request.getUri());
    }
    catch (URISyntaxException ex) {
      throw new RuntimeException(ex);      
    }
  }
}
