package org.hisp.dhis.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.hisp.dhis.model.Product;
import org.junit.jupiter.api.Test;

class JacksonYamUtilsTest {
  @Test
  void testToYamlA() {
    String expected = 
        """
        id: "YDb6ff4R3a8"
        name: "ThinkPadT14s"
        """;

    Product object = new Product();
    object.setId("YDb6ff4R3a8");
    object.setName("ThinkPadT14s");

    String actual = JacksonYamUtils.toYamlString(object);

    assertEquals(expected, actual);
    
    String yaml =
        """
            
        definitions:
          caches:
            sonar: ~/.sonar/cache
          services:
            docker:
              memory: 2048
            redis:
              image: redis:alpine
          steps:
            - step: &check-formatting
                size: 4x
                name: Check formatting
                caches:
                  - maven
                script:
                  # Check code formatting with Spotless
                  - mvn -B -f analytics-platform/pom.xml spotless:check
            """;
  }
}
