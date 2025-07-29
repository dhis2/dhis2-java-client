package org.hisp.dhis.response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.response.objects.ObjectStatistics;
import org.hisp.dhis.response.objects.ObjectsResponse;
import org.junit.jupiter.api.Test;

class ObjectsResponseBuilderTest {
  @Test
  void testGetHighestStatus() {
    ObjectsResponseBuilder builder = new ObjectsResponseBuilder()
        .add(new ObjectResponse(Status.WARNING, 409, ""))
        .add(new ObjectsResponse(Status.OK, 200, new ObjectStatistics()))
        .add(new ObjectResponse(Status.ERROR, 502, ""));
    
    assertEquals(Status.ERROR, builder.getHighestStatus().get());
  }
  
  @Test
  void testGetHighestStatusEmpty() {
    ObjectsResponseBuilder builder = new ObjectsResponseBuilder();
    
    assertTrue(builder.getHighestStatus().isEmpty());
  }

  @Test
  void testGetHighestHttpStatusCode() {
    ObjectsResponseBuilder builder = new ObjectsResponseBuilder()
        .add(new ObjectResponse(Status.WARNING, 409, ""))
        .add(new ObjectsResponse(Status.OK, 200, new ObjectStatistics()))
        .add(new ObjectResponse(Status.ERROR, 502, ""));
    
    assertEquals(502, builder.getHighestHttpStatusCode().get());
  }

  @Test
  void testGetHighestHttpStatusCodeEmpty() {
    ObjectsResponseBuilder builder = new ObjectsResponseBuilder();
    
    assertTrue(builder.getHighestHttpStatusCode().isEmpty());
  }
  
}
