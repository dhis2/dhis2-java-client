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
package org.hisp.dhis.response.objects;

import static org.hisp.dhis.util.CollectionUtils.notEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hisp.dhis.response.BaseHttpResponse;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.objects.internal.Response;

@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class ObjectsResponse extends BaseHttpResponse {
  @JsonProperty private Status status;

  @JsonProperty private String message;

  /** From including DHIS 2.38. */
  @JsonProperty private Response response;

  /** Before excluding DHIS 2.38. */
  @JsonProperty private List<TypeReport> typeReports = new ArrayList<>();

  /** Before excluding DHIS 2.38. */
  @JsonProperty private ObjectStatistics stats;

  /**
   * Constructor.
   *
   * @param status the {@link Status}.
   * @param httpStatusCode the HTTP status code.
   * @param stats the {@link ObjectStatistics}.
   */
  public ObjectsResponse(Status status, Integer httpStatusCode) {
    this.status = status;
    this.httpStatusCode = httpStatusCode;
  }

  /**
   * Constructor.
   *
   * @param status the {@link Status}.
   * @param httpStatusCode the HTTP status code.
   * @param stats the {@link ObjectStatistics}.
   * @param response the {@link Response}.
   */
  public ObjectsResponse(Status status, Integer httpStatusCode, Response response) {
    this(status, httpStatusCode);
    this.response = response;
  }

  @JsonIgnore
  public TypeReport getTypeReport() {
    List<TypeReport> reports = getTypeReports();

    return notEmpty(reports) ? reports.get(0) : new TypeReport();
  }

  private boolean hasResponse() {
    return response != null;
  }

  public Status getStatus() {
    return hasResponse() ? response.getStatus() : status;
  }

  public List<TypeReport> getTypeReports() {
    return hasResponse() ? response.getTypeReports() : typeReports;
  }

  public ObjectStatistics getStats() {
    return hasResponse() ? response.getStats() : stats;
  }
}
