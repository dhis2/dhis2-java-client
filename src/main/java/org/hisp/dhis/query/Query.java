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

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Metadata query.
 *
 * @author Lars Helge Overland
 */
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Query implements BaseQuery {
  private final List<Filter> filters = new ArrayList<>();

  private final List<Order> order = new ArrayList<>();

  private Paging paging;

  private RootJunction rootJunction;

  private boolean expandAssociations = false;

  /**
   * Produces a query instance.
   *
   * @return a {@link Query} instance.
   */
  public static Query instance() {
    return new Query();
  }

  /**
   * Adds a filter to this query.
   *
   * @param filter the {@link Filter}.
   * @return this {@link Query}.
   */
  public Query addFilter(Filter filter) {
    this.filters.add(filter);
    return this;
  }

  /**
   * Enables ordering for this query.
   *
   * @param order the {@link Order}.
   * @return this {@link Query}.
   */
  public Query addOrder(Order order) {
    this.order.add(order);
    return this;
  }

  /**
   * Sets ordering for this query.
   *
   * @param order the {@link Order}.
   * @return this {@link Query}.
   */
  public Query setOrder(Order order) {
    this.order.clear();
    this.order.add(order);
    return this;
  }

  /**
   * Returns the order of this query.
   *
   * @return the {@link Order}.
   */
  public List<Order> getOrder() {
    return order;
  }

  /**
   * Returns filters.
   *
   * @return a list of {@link Filter}.
   */
  public List<Filter> getFilters() {
    return filters;
  }

  /**
   * Enables paging for this query.
   *
   * @param page the page to return.
   * @param pageSize the page size to return.
   * @return this {@link Query}.
   */
  public Query setPaging(Integer page, Integer pageSize) {
    this.paging = new Paging(page, pageSize);
    return this;
  }

  /**
   * Returns the paging for this query.
   *
   * @return the {@link Paging}.
   */
  public Paging getPaging() {
    return paging != null ? paging : new Paging(null, null);
  }

  /**
   * Returns the root junction of this query.
   *
   * @return the {@link RootJunction}
   */
  public RootJunction getRootJunction() {
    return rootJunction != null ? rootJunction : RootJunction.AND;
  }

  /**
   * Enables expansion of associations, i.e. that all properties of associated objects will be
   * present. Applies to lists of objects only (not single objects).
   *
   * @return this {@link Query}.
   */
  public Query withExpandAssociations() {
    this.expandAssociations = true;
    return this;
  }

  /**
   * Determines the logic to use when combining filters.
   *
   * @param rootJunction the {@link RootJunction}.
   * @return this {@link Query}
   */
  public Query withRootJunction(RootJunction rootJunction) {
    this.rootJunction = rootJunction;
    return this;
  }

  /**
   * Indicates whether expansion of associations is enabled. Applies to lists of objects only (not
   * single objects).
   *
   * @return true if expansion of associations is enabled, false if not.
   */
  public boolean isExpandAssociations() {
    return expandAssociations;
  }
}
