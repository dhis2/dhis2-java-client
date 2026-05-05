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
package org.hisp.dhis.util;

import java.io.InputStream;
import java.io.OutputStream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.dataformat.yaml.YAMLMapper;
import tools.jackson.dataformat.yaml.YAMLWriteFeature;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JacksonYamlUtils {
  private static final YAMLMapper YAML_MAPPER;

  static {
    YAML_MAPPER = getMapper();
  }

  /**
   * Serializes the given object to YAML and returns the result as a String.
   *
   * @param value the object value to serialize.
   * @return an YAML representation of the given object as a String.
   */
  public static String toYamlString(Object value) {
    return YAML_MAPPER.writeValueAsString(value);
  }

  /**
   * Serializes the given object to YAML and writes the content to the given {@link OutputStream}.
   *
   * @param out the {@link OutputStream} to write to.
   * @param value the object value to serialize.
   */
  public static void toYaml(OutputStream out, Object value) {
    YAML_MAPPER.writeValue(out, value);
  }

  /**
   * Deserializes the given YAML string into an object of the specified type.
   *
   * @param string the YAML string to deserialize.
   * @param type the class of the target type.
   * @param <T> the type of the object to return.
   * @return an object of type T.
   */
  public static <T> T fromYaml(String string, Class<T> type) {
    return YAML_MAPPER.readValue(string, type);
  }

  /**
   * Deserializes YAML from the given {@link InputStream} into an object of the specified type.
   *
   * @param in the {@link InputStream} containing YAML data.
   * @param type the class of the target type.
   * @param <T> the type of the object to return.
   * @return an object of type T.
   */
  public static <T> T fromYaml(InputStream in, Class<T> type) {
    return YAML_MAPPER.readValue(in, type);
  }

  /**
   * Returns a new instance of an {@link YAMLMapper}.
   *
   * @return an {@link YAMLMapper}.
   */
  private static YAMLMapper getMapper() {
    return YAMLMapper.builder().disable(YAMLWriteFeature.WRITE_DOC_START_MARKER).build();
  }
}
