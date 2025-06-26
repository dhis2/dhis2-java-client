package org.hisp.dhis;

import static org.hisp.dhis.support.Assertions.assertNotBlank;
import static org.hisp.dhis.support.Assertions.assertNotEmpty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.hisp.dhis.model.ProgramStage;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class ProgramStageApiTest {
  @Test
  void getGetProgramStage() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);
    
    ProgramStage programStage = dhis2.getProgramStage("A03MvHHogjR");
    
    assertNotNull(programStage);
    assertEquals("A03MvHHogjR", programStage.getId());
    assertNotBlank(programStage.getName());
    assertNotEmpty(programStage.getProgramStageDataElements());
  }
}
