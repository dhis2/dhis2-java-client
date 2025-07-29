package org.hisp.dhis.response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.support.JsonClassPathFile;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.UNIT)
class ObjectResponseText {
  @Test
  void testDeserialize() {
    ObjectResponse response =
        JsonClassPathFile.fromJson("response/object-response.json", ObjectResponse.class);

    assertNotNull(response);
    assertEquals(201, response.getHttpStatusCode());
    assertEquals(HttpStatus.CREATED, response.getHttpStatus());
    assertEquals(Status.OK, response.getStatus());
  }
}
