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
package org.hisp.dhis.util;

import static java.lang.String.format;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hisp.dhis.model.IdentifiableObject;

/** Utilities for {@link IdentifiableObject}. */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IdentifiableObjectUtils {
  /**
   * Converts the given collection of identifiable objects to a new list of object identifiers.
   *
   * @param <T> the type.
   * @param objects the collection of {@link IdentifiableObject}.
   * @return a list of object identifiers.
   */
  public static <T extends IdentifiableObject> List<String> toIdentifiers(Collection<T> objects) {
    return objects.stream().filter(Objects::nonNull).map(IdentifiableObject::getId).toList();
  }

  /**
   * Converts the given collection of identifiable objects to a new list of object codes.
   *
   * @param <T> the type.
   * @param objects the collection of {@link IdentifiableObject}.
   * @return a list of object codes.
   */
  public static <T extends IdentifiableObject> List<String> toCodes(Collection<T> objects) {
    return objects.stream().filter(Objects::nonNull).map(IdentifiableObject::getCode).toList();
  }

  /**
   * Converts the given collection of identifiable objects to a new list of object names.
   *
   * @param <T> the type.
   * @param objects the collection of {@link IdentifiableObject}.
   * @return a list of object codes.
   */
  public static <T extends IdentifiableObject> List<String> toNames(Collection<T> objects) {
    return objects.stream().filter(Objects::nonNull).map(IdentifiableObject::getName).toList();
  }

  /**
   * Converts the given collection of identifiable objects to a new list of identifiable objects of
   * the specified type.
   *
   * @param <T> the type.
   * @param objects the collection of {@link IdentifiableObject}.
   * @param type the class type.
   * @return a list of identifiable objects.
   */
  public static <T extends IdentifiableObject> List<T> toIdObjects(
      Collection<T> objects, Class<T> type) {
    return objects.stream().filter(Objects::nonNull).map(o -> getInstance(o, type)).toList();
  }

  /**
   * Generates a fingerprint for the given collection of identifiable objects.
   *
   * @param <T> the type.
   * @param objects the collection of {@link IdentifiableObject}.
   * @return a fingerprint string.
   */
  public static <T extends IdentifiableObject> String getFingerprint(Collection<T> objects) {
    return objects.stream()
        .map(IdentifiableObject::getId)
        .sorted()
        .collect(Collectors.joining("-"));
  }

  /**
   * Creates a new instance of the given identifiable object type and sets the identifier based on
   * the given object.
   *
   * @param <T> the type.
   * @param object the {@link IdentifiableObject}.
   * @param type the class type.
   * @return an {@link IdentifiableObject}.
   */
  static <T extends IdentifiableObject> T getInstance(T object, Class<T> type) {
    T idObject = newInstance(type);
    idObject.setId(object.getId());
    return idObject;
  }

  /**
   * Creates a new instance of the given identifiable object type.
   *
   * @param <T> the type.
   * @param type the class type.
   * @return an {@link IdentifiableObject}.
   */
  static <T extends IdentifiableObject> T newInstance(Class<T> type) {
    try {
      return type.getDeclaredConstructor().newInstance();
    } catch (ReflectiveOperationException ex) {
      String msg = format("Could not instantiate instance of type: %s", type.getSimpleName());
      throw new IllegalArgumentException(msg);
    }
  }
}
