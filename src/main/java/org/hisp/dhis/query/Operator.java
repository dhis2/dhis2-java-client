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

import lombok.RequiredArgsConstructor;

/**
 * Enumeration of query filter operators.
 *
 * @author Lars Helge Overland
 */
@RequiredArgsConstructor
public enum Operator {
  EQ("eq", "="),
  GE("ge", ">="),
  GT("gt", ">"),
  LE("le", "<="),
  LT("lt", "<"),
  LIKE("like", "like"),
  ILIKE("ilike", "ilike "),
  TOKEN("token", ""),
  IN("in", "in");

  private final String value;

  private final String sqlOperator;

  /**
   * Returns the operator with a value matching the given string.
   *
   * @param value the value.
   * @return an {@link Operator} or null if no match.
   */
  public static Operator fromValue(String value) {
    for (Operator operator : Operator.values()) {
      if (operator.value().equals(value)) {
        return operator;
      }
    }

    return null;
  }

  public String value() {
    return value;
  }

  public String sqlOperator() {
    return sqlOperator;
  }
}
