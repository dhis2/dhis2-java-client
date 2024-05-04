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

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * Query filter.
 *
 * @author Lars Helge Overland
 */
@RequiredArgsConstructor
public class Filter {
  private final String property;

  private final Operator operator;

  private final Object value;

  /**
   * Creates an equals to {@link Filter}.
   *
   * @param property the filter property.
   * @param value the filter value.
   * @return a {@link Filter}.
   */
  public static Filter eq(String property, Object value) {
    return new Filter(property, Operator.EQ, value);
  }

  /**
   * Creates a greater than or equals to {@link Filter}.
   *
   * @param property the filter property.
   * @param value the filter value.
   * @return a {@link Filter}.
   */
  public static Filter ge(String property, Object value) {
    return new Filter(property, Operator.GE, value);
  }

  /**
   * Creates a greater than {@link Filter}.
   *
   * @param property the filter property.
   * @param value the filter value.
   * @return a {@link Filter}.
   */
  public static Filter gt(String property, Object value) {
    return new Filter(property, Operator.GT, value);
  }

  /**
   * Creates a less than or equals to {@link Filter}.
   *
   * @param property the filter property.
   * @param value the filter value.
   * @return a {@link Filter}.
   */
  public static Filter le(String property, Object value) {
    return new Filter(property, Operator.LE, value);
  }

  /**
   * Creates a less than {@link Filter}.
   *
   * @param property the filter property.
   * @param value the filter value.
   * @return a {@link Filter}.
   */
  public static Filter lt(String property, Object value) {
    return new Filter(property, Operator.LT, value);
  }

  /**
   * Creates an is true {@link Filter}.
   *
   * @param property the filter property.
   * @return a {@link Filter}.
   */
  public static Filter isTrue(String property) {
    return new Filter(property, Operator.EQ, "true");
  }

  /**
   * Creates an is false {@link Filter}.
   *
   * @param property the filter property.
   * @return a {@link Filter}.
   */
  public static Filter isFalse(String property) {
    return new Filter(property, Operator.EQ, "false");
  }

  /**
   * Creates a like {@link Filter}.
   *
   * @param property the filter property.
   * @param value the filter value.
   * @return a {@link Filter}.
   */
  public static Filter like(String property, Object value) {
    return new Filter(property, Operator.LIKE, value);
  }

  /**
   * Creates an ilike {@link Filter}.
   *
   * @param property the filter property.
   * @param value the filter value.
   * @return a {@link Filter}.
   */
  public static Filter ilike(String property, Object value) {
    return new Filter(property, Operator.ILIKE, value);
  }

  /**
   * Creates a token {@link Filter}.
   *
   * @param property the filter property.
   * @param value the filter value.
   * @return a {@link Filter}.
   */
  public static Filter token(String property, Object value) {
    return new Filter(property, Operator.TOKEN, value);
  }

  /**
   * Creates an in {@link Filter}.
   *
   * @param property the filter property.
   * @param values the filter values.
   * @return a {@link Filter}.
   */
  public static Filter in(String property, List<String> values) {
    return new Filter(property, Operator.IN, StringUtils.join(values, ','));
  }

  public String getProperty() {
    return property;
  }

  public Operator getOperator() {
    return operator;
  }

  public Object getValue() {
    return value;
  }
}
