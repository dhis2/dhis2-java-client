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

import static org.hisp.dhis.util.NumberUtils.toInt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ObjectStatistics {
  @JsonProperty private Integer created;

  @JsonProperty private Integer updated;

  @JsonProperty private Integer deleted;

  @JsonProperty private Integer ignored;

  @JsonProperty private Integer total;

  /**
   * Increments all statistics property values with the property values of the given object
   * statistics.
   *
   * @param target the target {@link ObjectStatistics}.
   */
  public void increment(ObjectStatistics target) {
    setCreated(toInt(created) + toInt(target.getCreated()));
    setUpdated(toInt(updated) + toInt(target.getUpdated()));
    setDeleted(toInt(deleted) + toInt(target.getDeleted()));
    setIgnored(toInt(ignored) + toInt(target.getIgnored()));
    setTotal(toInt(total) + toInt(target.getTotal()));
  }

  /** Increments created and total count. */
  public void incrementCreated() {
    setCreated(toInt(created) + 1);
    incrementTotal();
  }

  /** Increments updated and total count with the given value. */
  public void incrementUpdated() {
    setUpdated(toInt(updated) + 1);
    incrementTotal();
  }

  /** Increments deleted and total count with the given value. */
  public void incrementDeleted() {
    setDeleted(toInt(deleted) + 1);
    incrementTotal();
  }

  /** Increments ignored and total count with the given value. */
  public void incrementIgnored() {
    setIgnored(toInt(ignored) + 1);
    incrementTotal();
  }

  /** Increments total count with the given value. */
  private void incrementTotal() {
    setTotal(toInt(total) + 1);
  }
}
