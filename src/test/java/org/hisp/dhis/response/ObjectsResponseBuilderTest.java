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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.response.objects.ObjectStatistics;
import org.hisp.dhis.response.objects.ObjectsReport;
import org.hisp.dhis.response.objects.ObjectsResponse;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.UNIT)
class ObjectsResponseBuilderTest {
  @Test
  void testGetHighestHttpStatusCode() {
    ObjectsResponseBuilder builder =
        new ObjectsResponseBuilder()
            .add(new ObjectResponse(Status.WARNING, 409, ""))
            .add(new ObjectsResponse(Status.OK, 200))
            .add(new ObjectResponse(Status.ERROR, 502, ""));

    assertEquals(502, builder.getHighestHttpStatusCode().get());
  }

  @Test
  void testGetHighestHttpStatusCodeEmpty() {
    ObjectsResponseBuilder builder = new ObjectsResponseBuilder();

    assertTrue(builder.getHighestHttpStatusCode().isEmpty());
  }

  @Test
  void testGetHighestStatus() {
    ObjectsResponseBuilder builder =
        new ObjectsResponseBuilder()
            .add(new ObjectResponse(Status.WARNING, 409, ""))
            .add(new ObjectsResponse(Status.OK, 200))
            .add(new ObjectResponse(Status.ERROR, 502, ""));

    assertEquals(Status.ERROR, builder.getHighestStatus().get());
  }

  @Test
  void testGetHighestStatusEmpty() {
    ObjectsResponseBuilder builder = new ObjectsResponseBuilder();

    assertTrue(builder.getHighestStatus().isEmpty());
  }

  @Test
  void testGetObjectStatistics() {
    ObjectsResponseBuilder builder =
        new ObjectsResponseBuilder()
            .add(
                new ObjectsResponse(
                    Status.OK, 200, toResponse(new ObjectStatistics(3, 2, 1, 0, 6))))
            .add(
                new ObjectsResponse(
                    Status.OK, 200, toResponse(new ObjectStatistics(2, 1, 5, 2, 10))))
            .add(new ObjectResponse(Status.ERROR, 502, ""))
            .add(new ObjectResponse(Status.ERROR, 502, ""));

    ObjectStatistics stats = builder.getStatistics();

    assertEquals(7, stats.getCreated());
    assertEquals(3, stats.getUpdated());
    assertEquals(6, stats.getDeleted());
    assertEquals(2, stats.getIgnored());
    assertEquals(18, stats.getTotal());
  }

  /**
   * Returns a {@link ObjectsReport} with the given {@link ObjectStatistics}.
   *
   * @param stats the {@link ObjectStatistics}.
   * @return a {@link ObjectsReport}.
   */
  private ObjectsReport toResponse(ObjectStatistics stats) {
    ObjectsReport response = new ObjectsReport();
    response.setStatus(Status.OK);
    response.setStats(stats);
    return response;
  }
}
