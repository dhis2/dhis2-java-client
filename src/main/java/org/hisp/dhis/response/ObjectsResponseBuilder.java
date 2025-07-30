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
package org.hisp.dhis.response;

import static org.hisp.dhis.util.CollectionUtils.mapToList;
import static org.hisp.dhis.util.NumberUtils.toInt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.hisp.dhis.response.object.ObjectReport;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.response.objects.ObjectStatistics;
import org.hisp.dhis.response.objects.ObjectsReport;
import org.hisp.dhis.response.objects.ObjectsResponse;
import org.hisp.dhis.response.objects.TypeReport;

public class ObjectsResponseBuilder {
  private List<ObjectsResponse> objectsResponses;

  private List<ObjectResponse> objectResponses;

  public ObjectsResponseBuilder() {
    this.objectsResponses = new ArrayList<>();
    this.objectResponses = new ArrayList<>();
  }

  /**
   * Adds the given {@link ObjectsResponse}.
   *
   * @return this {@link ObjectsResponseBuilder}.
   */
  public ObjectsResponseBuilder add(ObjectsResponse response) {
    this.objectsResponses.add(response);
    return this;
  }

  /**
   * Adds the given list of {@link ObjectsResponse}.
   *
   * @return this {@link ObjectsResponseBuilder}.
   */
  public ObjectsResponseBuilder addAll(Collection<ObjectsResponse> responses) {
    this.objectsResponses.addAll(responses);
    return this;
  }

  /**
   * Adds the given {@link ObjectResponse}.
   *
   * @return this {@link ObjectsResponseBuilder}.
   */
  public ObjectsResponseBuilder add(ObjectResponse response) {
    this.objectResponses.add(response);
    return this;
  }

  /**
   * Builds an instance of {@link ObjectsResponse}.
   *
   * @return an {@link ObjectsResponse}.
   */
  public ObjectsResponse build() {
    ObjectsReport response = new ObjectsReport();
    response.setStatus(getHighestStatus().orElse(null));
    response.setStats(getStatistics());
    response.setTypeReports(getTypeReports());

    ObjectsResponse objectsResponse = new ObjectsResponse();
    objectsResponse.setHttpStatusCode(getHighestHttpStatusCode().orElse(0));
    objectsResponse.setStatus(getHighestStatus().orElse(null));
    objectsResponse.setResponse(response);

    return objectsResponse;
  }

  /**
   * Returns the optional HTTP status code with the highest value.
   *
   * @return an optional HTTP status code.
   */
  Optional<Integer> getHighestHttpStatusCode() {
    List<Integer> codes = new ArrayList<Integer>();
    codes.addAll(mapToList(objectsResponses, ObjectsResponse::getHttpStatusCode));
    codes.addAll(mapToList(objectResponses, ObjectResponse::getHttpStatusCode));
    return codes.stream().sorted(Comparator.reverseOrder()).findFirst();
  }

  /**
   * Returns the optional {@link Status} with the highest value.
   *
   * @return an optional {@link Status}.
   */
  Optional<Status> getHighestStatus() {
    List<Status> statuses = new ArrayList<Status>();
    statuses.addAll(mapToList(objectsResponses, ObjectsResponse::getStatus));
    statuses.addAll(mapToList(objectResponses, ObjectResponse::getStatus));
    return statuses.stream().sorted(Comparator.reverseOrder()).findFirst();
  }

  /**
   * Returns the aggregated object statistics.
   *
   * @return an {@link ObjectStatistics}.
   */
  ObjectStatistics getStatistics() {
    ObjectStatistics stats = new ObjectStatistics();
    objectsResponses.forEach(or -> stats.increment(or.getStats()));
    objectResponses.forEach(or -> stats.setCreated(toInt(stats.getCreated() + 1))); // TODO
    return stats;
  }

  /**
   * Returns a list of {@link TypeReport}.
   *
   * @return a list of {@link TypeReport}.
   */
  List<TypeReport> getTypeReports() {
    List<TypeReport> reports = new ArrayList<>();
    objectsResponses.forEach(or -> reports.addAll(or.getTypeReports()));
    objectResponses.forEach(or -> reports.add(toTypeReport(or.getResponse())));
    return reports;
  }

  /**
   * Converts the given {@link ObjectReport} to a {@link TypeReport}.
   *
   * @param report the {@link ObjectReport}.
   * @return a {@link TypeReport}.
   */
  TypeReport toTypeReport(ObjectReport report) {
    return new TypeReport(report.getKlass(), new ObjectStatistics(), List.of(report));
  }
}
