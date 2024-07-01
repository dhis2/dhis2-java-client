package org.hisp.dhis.query;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class OrderTest {
  
  @Test
  void testOrderToValue() {
    Order oA = Order.asc("uid");
    Order oB = Order.desc("name");
    
    assertEquals("uid:asc", oA.toValue() );
    assertEquals("name:desc", oB.toValue() );
  }
}
