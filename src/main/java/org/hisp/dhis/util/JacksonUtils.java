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

import com.bedatadriven.jackson.datatype.jts.JtsModule;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hisp.dhis.util.json.DateJsonDeserializer;

/** Utilities for JSON parsing and serialization. */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JacksonUtils {
  /** Static JSON object mapper. */
  private static final ObjectMapper OBJECT_MAPPER;

  static {
    OBJECT_MAPPER = getMapper();
  }

  /**
   * Returns an {@link ObjectMapper}.
   *
   * @return an {@link ObjectMapper}.
   */
  public static ObjectMapper getObjectMapper() {
    return OBJECT_MAPPER;
  }

  /**
   * Returns a new instance of an {@link ObjectMapper}.
   *
   * <p>Note that {@link Date} serialization handling is defined by {@link
   * ObjectMapper#setDateFormat(java.text.DateFormat)}, while {@link Date} deserialization handling
   * is defined by a {@link SimpleModule} with a custom date deserializer, where multiple formats
   * are supported.
   *
   * @return an {@link ObjectMapper}.
   */
  private static ObjectMapper getMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JtsModule());
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    objectMapper.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    objectMapper.setSerializationInclusion(Include.NON_NULL);
    objectMapper.setDateFormat(getDateFormatInternal());
    objectMapper.setTimeZone(DateTimeUtils.TZ_UTC);
    objectMapper.registerModule(getDateModule());
    return objectMapper;
  }

  /**
   * Returns a {@link SimpleModule} that contains a custom date deserializer.
   *
   * @return a {@link SimpleModule} with a custom date deserializer.
   */
  private static SimpleModule getDateModule() {
    SimpleModule module = new SimpleModule();
    module.addDeserializer(Date.class, new DateJsonDeserializer());
    return module;
  }

  /**
   * Returns a {@link SimpleDateFormat} instance for date time formatting.
   *
   * @return a {@link SimpleDateFormat} instance.
   */
  private static SimpleDateFormat getDateFormatInternal() {
    SimpleDateFormat format = new SimpleDateFormat(DateTimeUtils.DATE_TIME_FORMAT);
    format.setTimeZone(DateTimeUtils.TZ_UTC);
    return format;
  }

  /**
   * Serializes the given object to JSON and returns the result as a String.
   *
   * @param value the object value to serialize.
   * @return a JSON representation of the given object as a String.
   */
  public static String toJsonString(Object value) {
    try {
      return OBJECT_MAPPER.writeValueAsString(value);
    } catch (IOException ex) {
      throw new UncheckedIOException(ex);
    }
  }

  /**
   * Serializes the given object to JSON and returns the result as a formatted String.
   *
   * @param value the object value to serialize.
   * @return a JSON representation of the given object as a formatted String.
   */
  public static String toFormattedJsonString(Object value) {
    try {
      return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(value);
    } catch (IOException ex) {
      throw new UncheckedIOException(ex);
    }
  }

  /**
   * Converts the given JSON string into a {@link JsonNode}.
   *
   * @param value the JSON string to convert.
   * @return a {@link JsonNode} representation of the JSON string.
   */
  public static JsonNode toJsonNode(String value) {
    try {
      return OBJECT_MAPPER.readTree(value);
    } catch (IOException ex) {
      throw new UncheckedIOException(ex);
    }
  }

  /**
   * Serializes the given object to JSON and writes the content to the given {@link OutputStream}.
   *
   * @param out the {@link OutputStream} to write to.
   * @param value the object value.
   */
  public static void toJson(OutputStream out, Object value) {
    try {
      OBJECT_MAPPER.writeValue(out, value);
    } catch (IOException ex) {
      throw new UncheckedIOException(ex);
    }
  }

  /**
   * Deserializes the given JSON string into an object of the specified type.
   *
   * @param string the JSON string to deserialize.
   * @param type the class of the target type.
   * @param <T> the type of the object to return.
   * @return an object of type T.
   */
  public static <T> T fromJson(String string, Class<T> type) {
    try {
      return OBJECT_MAPPER.readValue(string, type);
    } catch (IOException ex) {
      throw new UncheckedIOException(ex);
    }
  }

  /**
   * Deserializes the given JSON string into a list of objects of the specified type.
   *
   * @param string the JSON string to deserialize.
   * @param <T> the type of the list items to return.
   * @return an list of items of type T.
   */
  public static <T> List<T> fromJsonToList(String string) {
    try {
      return OBJECT_MAPPER.readValue(string, new TypeReference<List<T>>() {});
    } catch (IOException ex) {
      throw new UncheckedIOException(ex);
    }
  }

  /**
   * Deserializes JSON from the given {@link InputStream} into an object of the specified type.
   *
   * @param in the {@link InputStream} containing JSON data.
   * @param type the class of the target type.
   * @param <T> the type of the object to return.
   * @return an object of type T.
   */
  public static <T> T fromJson(InputStream in, Class<T> type) {
    try {
      return OBJECT_MAPPER.readValue(in, type);
    } catch (IOException ex) {
      throw new UncheckedIOException(ex);
    }
  }
}
