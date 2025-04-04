package org.hisp.dhis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import org.hisp.dhis.model.DataElement;
import org.junit.jupiter.api.Test;

class BaseDhis2Test {
  @Test
  void testDeserializeString() throws IOException {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);
    
    assertEquals("HkSu7IWlvrM", dhis2.readValue("HkSu7IWlvrM", String.class));
  }
  
  @Test
  void testDeserializeObject() throws IOException {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);
    
    
    String content = 
        """
        {
          "id": "fbfJHSPpUQD",
          "code": "DE_359596",
          "name": "ANC 1st visit",
          "created": "2010-02-05T10:58:43.646",
          "lastUpdated": "2022-03-22T08:59:44.053",
          "shortName": "ANC 1st visit",
          "aggregationType": "SUM",
          "valueType": "NUMBER",
          "domainType": "AGGREGATE"
        }    
        """;
    
    DataElement object = dhis2.readValue(content, DataElement.class);
  }
}
