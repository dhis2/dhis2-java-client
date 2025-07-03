package org.hisp.dhis.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator.Feature;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JacksonYamUtils {
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
    try {
      return YAML_MAPPER.writeValueAsString(value);
    } catch (IOException ex) {
      throw new UncheckedIOException(ex);
    }
  }

  /**
   * Serializes the given object to YAML and writes the content to the given {@link OutputStream}.
   *
   * @param out the {@link OutputStream} to write to.
   * @param value the object value to serialize.
   */
  public static void toYaml(OutputStream out, Object value) {
    try {
      YAML_MAPPER.writeValue(out, value);
    } catch (IOException ex) {
      throw new UncheckedIOException(ex);
    }
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
    try {
      return YAML_MAPPER.readValue(string, type);
    } catch (IOException ex) {
      throw new UncheckedIOException(ex);
    }
  }

  /**
   * Deserializes YAML from the given {@link InputStream} into an object of the specified type.
   *
   * @param input the {@link InputStream} containing YAML data.
   * @param type the class of the target type.
   * @param <T> the type of the object to return.
   * @return an object of type T.
   */
  public static <T> T fromYaml(InputStream in, Class<T> type) {
    try {
      return YAML_MAPPER.readValue(in, type);
    } catch (IOException ex) {
      throw new UncheckedIOException(ex);
    }
  }

  /**
   * Returns a new instance of an {@link YAMLMapper}.
   *
   * @return an {@link YAMLMapper}.
   */
  private static YAMLMapper getMapper() {
    YAMLMapper mapper = new YAMLMapper();
    mapper.disable(Feature.WRITE_DOC_START_MARKER);
    return mapper;
  }
}
