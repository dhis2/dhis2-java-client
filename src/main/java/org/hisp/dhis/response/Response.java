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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.hc.core5.http.Header;

/** Response providing information about a DHIS 2 web API response. */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Response extends BaseHttpResponse {
  /** HTTP {@link Status} enum. */
  @JsonProperty protected Status status;

  /** DHIS2 code. Deprecated. */
  @JsonProperty protected Integer code;

  /** Response message. */
  @JsonProperty protected String message;

  /** DHIS2 error code. */
  @JsonProperty protected String errorCode;

  /** Developer response message. */
  @JsonProperty protected String devMessage;

  /**
   * Constructor.
   *
   * @param status the {@link Status} of the response.
   * @param httpStatusCode the HTTP status code of the response.
   * @param message the message of the response.
   */
  public Response(Status status, Integer httpStatusCode, String message) {
    super(httpStatusCode);
    this.status = status;
    this.code = httpStatusCode;
    this.message = message;
  }

  /**
   * Constructor.
   *
   * @param status the {@link Status} of the response.
   * @param httpStatus the {@link HttpStatus} of the response.
   * @param message the message of the response.
   */
  public Response(Status status, HttpStatus httpStatus, String message) {
    super(httpStatus.value());
    this.status = status;
    this.code = httpStatus.value();
    this.message = message;
  }

  /**
   * Constructor.
   *
   * @param status the {@link Status} of the response.
   * @param httpStatusCode the HTTP status code of the response.
   * @param message the message of the response.
   * @param errorCode the error code of the response, if any.
   */
  public Response(Status status, Integer httpStatusCode, String message, String errorCode) {
    this(status, httpStatusCode, message);
    this.errorCode = errorCode;
  }

  /**
   * Constructor.
   *
   * @param status the {@link Status} of the response.
   * @param httpStatus the {@link HttpStatus} of the response.
   * @param message the message of the response.
   * @param errorCode the error code of the response, if any.
   */
  public Response(Status status, HttpStatus httpStatus, String message, String errorCode) {
    this(status, httpStatus, message);
    this.errorCode = errorCode;
  }

  /**
   * Returns the {@link HttpStatus} of the response.
   *
   * @return an {@link HttpStatus}.
   */
  public HttpStatus getHttpStatus() {
    if (httpStatusCode != null) {
      return HttpStatus.valueOf(httpStatusCode);
    }

    return null;
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

  @JsonIgnore
  public boolean isStatusOk() {
    return Status.OK == getStatus();
  }
}
