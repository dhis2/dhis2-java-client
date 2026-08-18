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

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hisp.dhis.query.Direction;
import org.hisp.dhis.query.Order;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderingUtils {
  private static final Pattern ORDER_PATTERN = Pattern.compile("^([a-zA-Z0-9]+):([a-z]+)$");
  private static final String INVALID_ORDER_DIRECTION = "Direction is invalid: '%s'";
  private static final String INVALID_ORDER_FORMAT =
      "Order must be on the format '{property}:{direction}': '%s'";

  /**
   * Creates a list of {@link Order} from the given list of order. The values must be on the format
   * {@code property:direction}. Returns empty List if the given order list is null.
   *
   * @param order the list of order.
   * @return a list of {@link Order}.
   */
  public static List<Order> asOrders(List<String> order) {
    if (order == null) {
      return List.of();
    }

    return order.stream().map(OrderingUtils::asOrder).toList();
  }

  /**
   * Creates a {@link Order} from the given string. The value must be on the format {@code
   * property:direction}. Returns null if the given order string is null.
   *
   * @param string the filter string.
   * @return a {@link Order}.
   * @throws IllegalArgumentException if the string format is invalid.
   */
  public static Order asOrder(String string) {
    if (string == null) {
      return null;
    }

    Matcher matcher = ORDER_PATTERN.matcher(string);

    if (!matcher.matches()) {
      throw new IllegalArgumentException(String.format(INVALID_ORDER_FORMAT, string));
    }

    String property = matcher.group(1);
    Direction direction = Direction.fromValue(matcher.group(2));

    if (direction == null) {
      throw new IllegalArgumentException(String.format(INVALID_ORDER_DIRECTION, matcher.group(2)));
    }

    return Direction.DESC == direction ? Order.desc(property) : Order.asc(property);
  }

  /**
   * Creates a {@link String} from the given {@link Order}. The result value will have the format
   * {@code property:direction}. Returns null if the given {@link Order} is null.
   *
   * @param order the {@link Order}.
   * @return a string in the format {@code property:direction}.
   */
  public static String asString(Order order) {
    if (order == null) {
      return null;
    }

    return order.getProperty() + ":" + order.getDirection().value();
  }
}
