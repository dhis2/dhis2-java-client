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

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.Validate;
import org.hisp.dhis.model.exception.IllegalArgumentFormatException;

public class IdScheme {
  public static final IdScheme UID = new IdScheme(ObjectProperty.UID);

  public static final IdScheme CODE = new IdScheme(ObjectProperty.CODE);

  public static final IdScheme NAME = new IdScheme(ObjectProperty.NAME);

  private static final Pattern ATTRIBUTE_ID_SCHEME_PATTERN =
      Pattern.compile("^(ATTRIBUTE|attribute):([a-zA-Z]{1}[a-zA-Z0-9]{10})$");

  private static final Pattern UID_PATTERN = Pattern.compile("^[a-zA-Z]{1}[a-zA-Z0-9]{10}$");

  private final ObjectProperty objectProperty;

  private final String attribute;

  // -------------------------------------------------------------------------
  // Constructors
  // -------------------------------------------------------------------------

  private IdScheme(ObjectProperty objectProperty) {
    this.objectProperty = objectProperty;
    this.attribute = null;
    Objects.requireNonNull(objectProperty);
  }

  private IdScheme(ObjectProperty objectProperty, String attribute) {
    this.objectProperty = objectProperty;
    this.attribute = attribute;
    Objects.requireNonNull(objectProperty);
    Objects.requireNonNull(attribute);
  }

  public IdScheme(String idScheme) {
    IdScheme scheme = IdScheme.createIdScheme(idScheme);
    this.objectProperty = scheme.objectProperty;
    this.attribute = scheme.attribute;
    Objects.requireNonNull(objectProperty);
  }

  // -------------------------------------------------------------------------
  // Create methods
  // -------------------------------------------------------------------------

  /**
   * Creates an {@link IdScheme} with object property ATTRIBUTE for the given attribute ID (UID).
   * The attribute must be a valid UID and cannot be null.
   *
   * @param attribute the attribute ID (UID).
   * @return an {@link IdScheme}.
   */
  public static IdScheme createAttributeIdScheme(String attribute) {
    Validate.isTrue(isValidUid(attribute));
    return new IdScheme(ObjectProperty.ATTRIBUTE, attribute);
  }

  /**
   * Creates an {@link IdScheme} based on the given string. The string can be on the following
   * formats:
   *
   * <ul>
   *   <li>UID
   *   <li>CODE
   *   <li>ATTRIBUTE:{attribute-ID}
   * </ul>
   *
   * @param idScheme the ID scheme string.
   * @return an {@link IdScheme}.
   * @throws IllegalArgumentException if the ID scheme string is invalid.
   */
  public static IdScheme createIdScheme(String idScheme) {
    if (idScheme == null) {
      return null;
    }

    if (ObjectProperty.UID.name().equalsIgnoreCase(idScheme)) {
      return UID;
    }

    if (ObjectProperty.CODE.name().equalsIgnoreCase(idScheme)) {
      return CODE;
    }

    Matcher attributeMatcher = ATTRIBUTE_ID_SCHEME_PATTERN.matcher(idScheme);

    if (attributeMatcher.matches()) {
      String attribute = attributeMatcher.group(2);

      return new IdScheme(ObjectProperty.ATTRIBUTE, attribute);
    }

    throw new IllegalArgumentFormatException("ID scheme is invalid: '{}'", idScheme);
  }

  /**
   * Indicates whether the given UID is valid.
   *
   * @param uid the UID.
   * @return true if the given UID is valid.
   */
  private static boolean isValidUid(String uid) {
    return uid != null && UID_PATTERN.matcher(uid).matches();
  }

  // -------------------------------------------------------------------------
  // Get methods
  // -------------------------------------------------------------------------

  public ObjectProperty getObjectProperty() {
    return objectProperty;
  }

  public String getAttribute() {
    return attribute;
  }

  // -------------------------------------------------------------------------
  // Hash code and equals
  // -------------------------------------------------------------------------

  @Override
  public int hashCode() {
    return Objects.hash(objectProperty, attribute);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || !getClass().isAssignableFrom(o.getClass())) {
      return false;
    }

    IdScheme other = (IdScheme) o;

    return Objects.equals(this.objectProperty, other.objectProperty)
        && Objects.equals(this.attribute, other.attribute);
  }

  /**
   * Returns a string representation of this {@link IdScheme}. Alias for {@link
   * IdScheme#toString()}.
   *
   * @return a string representation of this {@link IdScheme}.
   */
  public String name() {
    return this.toString();
  }

  /**
   * Returns a string representation of this {@link IdScheme}.
   *
   * @return a string representation of this {@link IdScheme}.
   */
  @Override
  public String toString() {
    if (ObjectProperty.ATTRIBUTE == objectProperty) {
      return "attribute:" + attribute;
    } else {
      return objectProperty.name().toLowerCase();
    }
  }

  // -------------------------------------------------------------------------
  // ObjectProperty enum
  // -------------------------------------------------------------------------

  enum ObjectProperty {
    UID,
    CODE,
    NAME,
    ATTRIBUTE;
  }
}
