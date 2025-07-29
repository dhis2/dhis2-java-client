package org.hisp.dhis.response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.hisp.dhis.response.objects.ObjectStatistics;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.UNIT)
class ObjectStatisticsTest {
  @Test
  void testIncrementExisting() {
    ObjectStatistics source = new ObjectStatistics();
    source.setCreated(4);
    source.setUpdated(2);
    source.setDeleted(1);
    source.setIgnored(0);
    source.setTotal(10);

    ObjectStatistics target = new ObjectStatistics();
    target.setCreated(2);
    target.setUpdated(3);
    target.setDeleted(1);
    target.setIgnored(1);
    target.setTotal(7);
    
    source.increment(target);
    
    assertEquals(6, source.getCreated());
    assertEquals(5, source.getUpdated());
    assertEquals(2, source.getDeleted());
    assertEquals(1, source.getIgnored());
    assertEquals(17, source.getTotal());
  }

  @Test
  void testIncrementNew() {
    ObjectStatistics source = new ObjectStatistics();

    ObjectStatistics target = new ObjectStatistics();
    target.setCreated(2);
    target.setUpdated(3);
    target.setDeleted(1);
    target.setIgnored(1);
    target.setTotal(7);
    
    source.increment(target);
    
    assertEquals(2, source.getCreated());
    assertEquals(3, source.getUpdated());
    assertEquals(1, source.getDeleted());
    assertEquals(1, source.getIgnored());
    assertEquals(7, source.getTotal());
  }
}
