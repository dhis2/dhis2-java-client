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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UncheckedIOException;
import java.text.SimpleDateFormat;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.hisp.dhis.response.Dhis2ClientException;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/** Utilities for XML parsing and serialization. */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JacksonXmlUtils {
  /** Default date format. */
  private static final String DATE_FORMAT = "yyyy-MM-dd";

  /** Static XML object mapper. */
  private static final XmlMapper XML_MAPPER;

  static {
    XML_MAPPER = getMapper();
  }

  /**
   * Returns the static {@link XmlMapper}.
   *
   * @return the static{@link XmlMapper}.
   */
  public static XmlMapper getXmlMapper() {
    return XML_MAPPER;
  }

  /**
   * Returns the XML factory used by the static {@link XmlMapper}.
   *
   * @return the XML factory used by the static {@link XmlMapper}.
   */
  public static XmlFactory getXmlFactory() {
    XmlFactory xmlFactory = XML_MAPPER.getFactory();
    xmlFactory.getXMLInputFactory().setProperty(XMLInputFactory.SUPPORT_DTD, false);
    xmlFactory
        .getXMLInputFactory()
        .setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
    return xmlFactory;
  }

  /**
   * Creates and configures a new {@link XmlMapper} instance.
   *
   * @return a new {@link XmlMapper} instance.
   */
  private static XmlMapper getMapper() {
    XmlMapper xmlMapper = new XmlMapper();
    xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    xmlMapper.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
    xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
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
   * Serializes the given object to XML and writes the content to the given {@link OutputStream}.
   *
   * @param out the {@link OutputStream} to write to.
   * @param value the object value to serialize.
   */
  public static void toXml(OutputStream out, Object value) {
    try {
      XML_MAPPER.writeValue(out, value);
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

  /**
   * Extracts the root element name from an XML string using a StAX parser.
   *
   * @param string the XML string.
   * @return the name of the root element, or null if not found.
   */
  public static String getRootElementName(String string) {
    XMLStreamReader reader = null;
    try {
      reader = getXmlFactory().getXMLInputFactory().createXMLStreamReader(new StringReader(string));

      // Iterate through the stream until the first START_ELEMENT is found
      while (reader.hasNext()) {
        int event = reader.next();
        if (event == XMLStreamConstants.START_ELEMENT) {
          // The first START_ELEMENT is the root element
          return reader.getLocalName();
        }
      }
      return null;
    } catch (XMLStreamException ex) {
      log.error("Failed to find XML root element", ex);
      throw new Dhis2ClientException("Failed to find XML root element", ex);
    } finally {
      closeReader(reader);
    }
  }

  /**
   * Closes the given {@link XMLStreamReader} safely.
   *
   * @param reader the {@link XMLStreamReader} to close.
   */
  private static void closeReader(XMLStreamReader reader) {
    if (reader != null) {
      try {
        reader.close();
      } catch (XMLStreamException ex) {
        log.error("Failed to close XMLStreamReader", ex);
      }
    }
  }
}
