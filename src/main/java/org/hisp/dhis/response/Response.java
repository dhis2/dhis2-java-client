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
package org.hisp.dhis.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.hc.core5.http.Header;

/**
 * Message providing information about a DHIS 2 web API response.
 *
 * @author Lars Helge Overland
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Response extends BaseHttpResponse {
  @JsonProperty protected Status status;

  @JsonProperty protected Integer code;

  @JsonProperty protected String message;

  @JsonProperty protected String errorCode;

  @JsonProperty protected String devMessage;

  @JsonProperty protected Object data;

  public Response(Status status, Integer httpStatusCode, String message) {
    this.status = status;
    this.httpStatusCode = httpStatusCode;
    this.message = message;
  }

  public Response(Status status, Integer httpStatusCode, String message, String errorCode) {
    this(status, httpStatusCode, message);
    this.errorCode = errorCode;
  }

  public HttpStatus getHttpStatus() {
    return HttpStatus.valueOf(httpStatusCode);
  }

  /**
   * Returns the value of the HTTP header with the given name, or null if not found.
   *
   * @param name the HTTP header name.
   * @return the HTTP header value.
   */
  public String getHeader(String name) {
    if (headers != null) {
      for (Header header : headers) {
        if (name.equals(header.getName())) {
          return header.getValue();
        }
      }
    }

    return null;
  }
}
