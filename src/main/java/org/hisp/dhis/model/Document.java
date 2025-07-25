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
package org.hisp.dhis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.BooleanUtils;

@Getter
@Setter
@NoArgsConstructor
public class Document extends IdentifiableObject {
  /**
   * Refers to a URL if the {@code external} property is true. Refers to the UID of the associated
   * {@link FileResource} if the {@code external} property is false. Note that the {@link
   * FileResource} can be retrieved in a separate operation.
   */
  @JsonProperty private String url;

  @JsonProperty private Boolean external;

  @JsonProperty private Boolean attachment;

  @JsonIgnore
  public boolean isExternal() {
    return BooleanUtils.isTrue(external);
  }

  @JsonIgnore
  public boolean isAttachment() {
    return BooleanUtils.isTrue(attachment);
  }

  /**
   * Indicates whether the document represents a URL.
   *
   * @return true if the document represents a URL.
   */
  @JsonIgnore
  public boolean isUrl() {
    return isExternal();
  }

  /**
   * Indicates whether the document represents a file resource.
   *
   * @return true if the document represents a file resource.
   */
  @JsonIgnore
  public boolean isFile() {
    return !isExternal();
  }

  /**
   * Returns the file resource identifier, assuming the document represents a file resource.
   *
   * @return the file resource identifier, or null.
   */
  public String getFileResourceId() {
    return isFile() ? url : null;
  }
}
