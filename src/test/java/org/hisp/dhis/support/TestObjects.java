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
package org.hisp.dhis.support;

import java.util.Date;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hisp.dhis.model.IdentifiableObject;
import org.hisp.dhis.model.NameableObject;
import org.hisp.dhis.util.UidUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestObjects {
  // Identifiers

  public static final String ID_A = "AghLTXtnopm";
  public static final String ID_B = "BLBOLoM3ZfV";
  public static final String ID_C = "CfVJ3xx9e60";
  public static final String ID_D = "D45cQxNLV7e";
  public static final String ID_E = "EjtjkvcwP6W";

  /**
   * Sets nameable properties on the given object.
   *
   * @param <T>
   * @param object the {@link NameableObject}.
   * @param chr the character to use for names and codes.
   * @return the object.
   */
  public static <T extends NameableObject> T set(T object, char chr) {
    setIdObject(object, chr);
    object.setShortName("ShortName" + chr);
    object.setDescription("Description" + chr);
    return object;
  }

  /**
   * Sets identifiable properties on the given object.
   *
   * @param <T>
   * @param object the {@link IdentifiableObject}.
   * @param chr the character to use for names and codes.
   * @return the object.
   */
  public static <T extends IdentifiableObject> T setIdObject(T object, char chr) {
    Date now = new Date();
    object.setId(UidUtils.generateCode(10) + chr);
    object.setCreated(now);
    object.setLastUpdated(now);
    object.setCode("Code" + chr);
    object.setName("Name" + chr);
    return object;
  }

  /**
   * Sets identifiable properties on the given object.
   *
   * @param <T>
   * @param object the {@link IdentifiableObject}.
   * @param id the identifier.
   * @param code the code.
   * @param name the name.
   * @return the object.
   */
  public static <T extends IdentifiableObject> T setIdObject(
      T object, String id, String code, String name) {
    Date now = new Date();
    object.setId(id);
    object.setCreated(now);
    object.setLastUpdated(now);
    object.setCode(code);
    object.setName(name);
    return object;
  }
}
