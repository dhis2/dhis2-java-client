package org.hisp.dhis.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectUtils {
  /**
   * Indicates whether the given object is not null.
   *
   * @param object the object.
   * @return true if the given object is not null, false otherwise.
   */
  public static boolean isNotNull(Object object) {
    return object != null;
  }

  /**
   * Indicates whether the given object is null.
   *
   * @param object the object.
   * @return true if the given object is null, false otherwise.
   */
  public static boolean isNull(Object object) {
    return object == null;
  }
}
