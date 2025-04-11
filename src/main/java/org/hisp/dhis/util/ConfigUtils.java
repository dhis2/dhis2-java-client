package org.hisp.dhis.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfigUtils {
  /**
   * Splits the given value on {@code ,} and returns the values as a list. Trims each value and
   * filters out null and empty values.
   *
   * @param value the value.
   * @return the values as a {@link List}.
   */
  public static List<String> getAsList(String value) {
    List<String> values =
        new ArrayList<String>(
            Arrays.asList(StringUtils.split(ObjectUtils.firstNonNull(value, ""), ',')));

    return values.stream()
        .map(String::trim)
        .filter(StringUtils::isNotEmpty)
        .collect(Collectors.toList());
  }

  /**
   * Splits the given value on {@code ,} and returns the values as an array. Trims each value and
   * filters out null and empty values.
   *
   * @param value the value.
   * @return the values as an array.
   */
  public static String[] getAsArray(String value) {
    List<String> values = getAsList(value);

    return values.toArray(String[]::new);
  }
}