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
package org.hisp.dhis.model;

import java.util.Set;

public enum ValueType {
  TEXT,
  LONG_TEXT,
  MULTI_TEXT,
  LETTER,
  PHONE_NUMBER,
  EMAIL,
  BOOLEAN,
  TRUE_ONLY,
  DATE,
  DATETIME,
  TIME,
  NUMBER,
  UNIT_INTERVAL,
  PERCENTAGE,
  INTEGER,
  INTEGER_POSITIVE,
  INTEGER_NEGATIVE,
  INTEGER_ZERO_OR_POSITIVE,
  TRACKER_ASSOCIATE,
  USERNAME,
  COORDINATE,
  ORGANISATION_UNIT,
  REFERENCE,
  AGE,
  URL,
  FILE_RESOURCE,
  IMAGE,
  GEOJSON;

  public static final Set<ValueType> INTEGER_TYPES =
      Set.of(INTEGER, INTEGER_POSITIVE, INTEGER_NEGATIVE, INTEGER_ZERO_OR_POSITIVE);

  public static final Set<ValueType> DECIMAL_TYPES = Set.of(NUMBER, UNIT_INTERVAL, PERCENTAGE);

  public static final Set<ValueType> BOOLEAN_TYPES = Set.of(BOOLEAN, TRUE_ONLY);

  public static final Set<ValueType> TEXT_TYPES =
      Set.of(TEXT, LONG_TEXT, LETTER, TIME, USERNAME, EMAIL, PHONE_NUMBER, URL);

  public static final Set<ValueType> DATE_TYPES = Set.of(DATE, DATETIME, AGE);

  public static final Set<ValueType> FILE_TYPES = Set.of(FILE_RESOURCE, IMAGE);

  public static final Set<ValueType> GEO_TYPES = Set.of(COORDINATE);

  public static final Set<ValueType> NUMERIC_TYPES =
      Set.of(
          INTEGER,
          INTEGER_POSITIVE,
          INTEGER_NEGATIVE,
          INTEGER_ZERO_OR_POSITIVE,
          NUMBER,
          UNIT_INTERVAL,
          PERCENTAGE);

  public boolean isInteger() {
    return INTEGER_TYPES.contains(this);
  }

  public boolean isDecimal() {
    return DECIMAL_TYPES.contains(this);
  }

  public boolean isBoolean() {
    return BOOLEAN_TYPES.contains(this);
  }

  public boolean isText() {
    return TEXT_TYPES.contains(this);
  }

  public boolean isDate() {
    return DATE_TYPES.contains(this);
  }

  public boolean isFile() {
    return FILE_TYPES.contains(this);
  }

  public boolean isGeo() {
    return GEO_TYPES.contains(this);
  }

  public boolean isOrganisationUnit() {
    return ORGANISATION_UNIT == this;
  }

  public boolean isNumeric() {
    return NUMERIC_TYPES.contains(this);
  }
}
