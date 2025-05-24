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

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.text.SimpleDateFormat;

public class JacksonXmlUtils {
  /** Default date format. */
  private static final String DATE_FORMAT = "yyyy-MM-dd";

  /** Static XML object mapper. */
  private static final XmlMapper XML_MAPPER;

  static {
    XML_MAPPER = getXmlMapperInternal();
  }

  public static XmlMapper getXmlMapper() {
    return XML_MAPPER;
  }

  private static XmlMapper getXmlMapperInternal() {
    XmlMapper xmlMapper = new XmlMapper();
    xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    xmlMapper.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
    xmlMapper.setSerializationInclusion(Include.NON_NULL);
    xmlMapper.setDateFormat(new SimpleDateFormat(DATE_FORMAT));
    return xmlMapper;
  }

  /**
   * Serializes the given object to XML and returns the result as a String.
   *
   * @param value the object value to serialize.
   * @return an XML representation of the given object as a String.
   */
  public static String toXmlString(Object value) {
    try {
      return XML_MAPPER.writeValueAsString(value);
    } catch (IOException ex) {
      throw new UncheckedIOException(ex);
    }
  }

  /**
   * Deserializes the given XML string into an object of the specified type.
   *
   * @param string the XML string to deserialize.
   * @param type the class of the target type.
   * @param <T> the type of the object to return.
   * @return an object of type T.
   */
  public static <T> T fromXml(String string, Class<T> type) {
    try {
      return XML_MAPPER.readValue(string, type);
    } catch (IOException ex) {
      throw new UncheckedIOException(ex);
    }
  }

  /**
   * Deserializes XML from the given {@link InputStream} into an object of the specified type.
   *
   * @param input the {@link InputStream} containing XML data.
   * @param type the class of the target type.
   * @param <T> the type of the object to return.
   * @return an object of type T.
   */
  public static <T> T fromXml(InputStream input, Class<T> type) {
    try {
      return XML_MAPPER.readValue(input, type);
    } catch (IOException ex) {
      throw new UncheckedIOException(ex);
    }
  }
}
