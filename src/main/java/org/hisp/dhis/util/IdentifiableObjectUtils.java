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

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.hisp.dhis.model.BaseIdentifiableObject;
import org.hisp.dhis.model.IdentifiableObject;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/** Utilities for {@link IdentifiableObject}. */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IdentifiableObjectUtils {
  /**
   * Returns a list of object identifiers.
   *
   * @param <T> the type.
   * @param objects the collection of {@link IdentifiableObject}.
   * @return a list of object identifiers.
   */
  public static <T extends IdentifiableObject> List<String> toIdentifiers(Collection<T> objects) {
    return objects.stream()
        .filter(Objects::nonNull)
        .map(IdentifiableObject::getId)
        .collect(Collectors.toList());
  }

  /**
   * Returns a list of object codes.
   *
   * @param <T> the type.
   * @param objects the collection of {@link IdentifiableObject}.
   * @return a list of object codes.
   */
  public static <T extends IdentifiableObject> List<String> toCodes(Collection<T> objects) {
    return objects.stream()
        .filter(Objects::nonNull)
        .map(IdentifiableObject::getCode)
        .collect(Collectors.toList());
  }
  
  public static List<IdentifiableObject> toIdentifierObjects(
      Collection<? extends IdentifiableObject> objects) {
    return objects.stream()
        .filter(Objects::nonNull)
        .map(o -> new BaseIdentifiableObject(o.getId()))
        .toList();
  }
}
