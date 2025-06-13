package org.hisp.dhis;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.List;
import org.hisp.dhis.model.Option;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class OptionApiTest {
  @Test
  void testGetOptions() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    List<Option> options = dhis2.getOptions(Query.instance());

    assertNotNull(options);
    assertFalse(options.isEmpty());
    
    Option option = options.get(0);
    assertNotNull(option);
    assertNotNull(option.getId());
    assertNotNull(option.getCode());
    assertNotNull(option.getName());
  }
}
