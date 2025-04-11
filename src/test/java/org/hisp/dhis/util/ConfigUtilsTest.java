package org.hisp.dhis.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ConfigUtilsTest {
  @Test
  void testGetAsListA() {
    List<String> expected =
        List.of("http://localhost", "http://localhost:3000", "https://localhost:3000");

    String actual = "http://localhost,http://localhost:3000, ,, https://localhost:3000";

    assertEquals(expected, ConfigUtils.getAsList(actual));
    assertEquals(List.of(), ConfigUtils.getAsList(null));
    assertEquals(List.of(), ConfigUtils.getAsList(""));
  }

  @Test
  void testGetAsArray() {
    String actual = "http://localhost,http://localhost:3000, ,, https://localhost:3000";

    assertEquals(3, ConfigUtils.getAsArray(actual).length);
    assertEquals("http://localhost", ConfigUtils.getAsArray(actual)[0]);
  }
}
