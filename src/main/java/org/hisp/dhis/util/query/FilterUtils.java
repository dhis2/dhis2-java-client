/*
 * Copyright (c) 2004-2026, University of Oslo
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
package org.hisp.dhis.util.query;

import static org.hisp.dhis.query.Operator.fromValue;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hisp.dhis.query.Filter;
import org.hisp.dhis.query.Operator;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilterUtils {
  private static final Pattern FILTER_PATTERN = Pattern.compile("^([a-zA-Z0-9]+):([a-z]+):(.*)$");
  private static final String INVALID_OPERATOR = "Filter operator is invalid: '%s'";
  private static final String INVALID_FILTER_FORMAT =
      "Filter must be on the format '{property}:{operator}:{value}': '%s'";

  /**
   * Creates a list of {@link Filter} from the given list of strings. The values must be on the
   * format {@code property:operator:value}. For "in" type filters, multiple values must be
   * separated by {@code ;}. Returns an empty list if the given list is null.
   *
   * @param strings the list of strings.
   * @return a list of {@link Filter}.
   */
  public static List<Filter> asFilters(List<String> strings) {
    if (strings == null) {
      return List.of();
    }

    return strings.stream().map(FilterUtils::asFilter).toList();
  }

  /**
   * Creates a {@link Filter} from the given string. The value must be on the format {@code
   * property:operator:value}. For "in" type filters, multiple values must be separated by {@code
   * ;}. Returns null if the given string is null.
   *
   * @param string the filter string.
   * @return a {@link Filter}.
   * @throws IllegalArgumentException if the string format is invalid.
   */
  public static Filter asFilter(String string) {
    if (string == null) {
      return null;
    }

    Matcher matcher = FILTER_PATTERN.matcher(string);

    if (!matcher.matches()) {
      throw new IllegalArgumentException(String.format(INVALID_FILTER_FORMAT, string));
    }

    String property = matcher.group(1);
    Operator operator = fromValue(matcher.group(2));
    Object value = matcher.group(3);

    if (operator == null) {
      throw new IllegalArgumentException(String.format(INVALID_OPERATOR, matcher.group(2)));
    }

    return new Filter(property, operator, value);
  }

  /**
   * Creates a {@link String} from the given {@link Filter}. The result value will have the format
   * {@code property:operator:value}. For "in" type filters, multiple values must be separated by
   * {@code ,}. Returns null if the given {@link Filter} is null.
   *
   * @param filter the {@link Filter}.
   * @return a string in the format {@code property:operator:value}.
   */
  public static String asString(Filter filter) {
    if (filter == null) {
      return null;
    }
    String value = asStringValue(filter);

    return filter.getProperty() + ":" + filter.getOperator().value() + ":" + value;
  }

  /**
   * Returns the value of the given {@link Filter} as a string. For "in" type filters with an {@link
   * Iterable} value, the values are joined with {@code ,} and wrapped in square brackets, e.g.
   * {@code [value_a,value_b]}. For all other filters, the value is returned via {@link
   * Object#toString()}.
   *
   * @param filter the {@link Filter}.
   * @return the filter value as a string.
   */
  public static String asStringValue(Filter filter) {
    Object value = filter.getValue();
    if (filter.getOperator() == Operator.IN && value instanceof Iterable<?> values) {
      String listValue = StringUtils.join(values, ',');
      value = String.format("[%s]", listValue);
    }
    return value.toString();
  }
}
