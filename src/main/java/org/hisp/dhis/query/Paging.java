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
package org.hisp.dhis.query;

import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.Validate;

/**
 * Query paging.
 *
 * @author Lars Helge Overland
 */
@Getter
@ToString
public class Paging {
  private static final int DEFAULT_PAGE_SIZE = 50;

  private static final int DEFAULT_PAGE = 1;

  private final Integer page;

  private final Integer pageSize;

  /** Constructor. */
  public Paging() {
    this.page = DEFAULT_PAGE;
    this.pageSize = DEFAULT_PAGE_SIZE;
  }

  /**
   * Constructor.
   *
   * @param page the page number, starting on 1, can be null, cannot be negative.
   * @param pageSize the page size, can be null, cannot be negative.
   */
  public Paging(Integer page, Integer pageSize) {
    Validate.isTrue(!(page != null && page < 1), "Page must be greater than zero if specified");
    Validate.isTrue(
        !(pageSize != null && pageSize < 1), "Page size must be greater than zero if specified");
    this.page = page;
    this.pageSize = pageSize;
  }

  /**
   * Returns a {@link Paging} with page and page size set to <code>null</code>.
   *
   * @return a {@link Paging}.
   */
  public static Paging empty() {
    return new Paging(null, null);
  }

  public boolean hasPage() {
    return page != null && page >= 0;
  }

  public int getPageOrDefault() {
    return hasPage() ? page : DEFAULT_PAGE;
  }

  public int getPageSizeOrDefault() {
    return hasPageSize() ? pageSize : DEFAULT_PAGE_SIZE;
  }

  public boolean hasPageSize() {
    return pageSize != null && pageSize >= 0;
  }

  public boolean hasPaging() {
    return hasPage() || hasPageSize();
  }

  public int getOffset() {
    return getPageSizeOrDefault() * (getPageOrDefault() - 1);
  }
}
