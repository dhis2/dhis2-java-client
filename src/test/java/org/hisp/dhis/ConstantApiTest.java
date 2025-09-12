package org.hisp.dhis;

import static org.hisp.dhis.support.Assertions.assertNotBlank;
import static org.hisp.dhis.support.Assertions.assertNotEmpty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.List;
import org.hisp.dhis.model.Constant;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class ConstantApiTest {
  @Test
  void testGetConstants() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);
    
    List<Constant> constants = dhis2.getConstants(Query.instance());
    
    assertNotEmpty(constants);
    
    Constant first = constants.get(0);

    assertNotNull(first);
    assertNotBlank(first.getId());
    assertNotBlank(first.getName());
    assertNotNull(first.getValue());
    
  }
  
  @Test
  void testGetConstant() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);
    
    Constant constant = dhis2.getConstant("bCqvfPR02Im");
    
    assertNotNull(constant);
    assertEquals("bCqvfPR02Im", constant.getId());
    assertEquals("Pi", constant.getName());
    assertNotNull(constant.getValue());
  }
}
