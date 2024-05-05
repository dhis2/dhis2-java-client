package org.hisp.dhis.model;

import static org.hisp.dhis.support.Assertions.assertContainsExactly;
import static org.hisp.dhis.support.TestObjects.set;
import java.util.List;
import org.junit.jupiter.api.Test;

class ProgramTest {
  
  
  @Test
  void testGetAnalyticsDataElements() {
    DataElement deA = set(new DataElement(),'A');
    DataElement deB = set(new DataElement(),'B');
    DataElement deC = set(new DataElement(),'C');
    
    ProgramStageDataElement psdeA = new ProgramStageDataElement();
    psdeA.setDataElement(deA);
    psdeA.setSkipAnalytics(false);
    ProgramStageDataElement psdeB = new ProgramStageDataElement();
    psdeB.setDataElement(deB);
    psdeB.setSkipAnalytics(true);
    ProgramStageDataElement psdeC = new ProgramStageDataElement();
    psdeC.setDataElement(deC);
    psdeC.setSkipAnalytics(false);
    
    ProgramStage psA = set(new ProgramStage(), 'A');
    psA.getProgramStageDataElements().addAll(List.of(psdeA, psdeB, psdeC));
    
    Program prA = set(new Program(), 'A');
    prA.getProgramStages().add(psA);
    
    assertContainsExactly(prA.getAnalyticsDataElements(), deA, deC);    
  }

  @Test
  void testGetAnalyticsDataElementsWithLegendSet() {
    LegendSet lsA = set(new LegendSet(), 'A');
    
    DataElement deA = set(new DataElement(),'A');
    deA.setValueType(ValueType.NUMBER);
    DataElement deB = set(new DataElement(),'B');
    deB.setValueType(ValueType.NUMBER);
    DataElement deC = set(new DataElement(),'C');
    deC.setValueType(ValueType.NUMBER);
    deC.setLegendSets(List.of(lsA));
    
    ProgramStageDataElement psdeA = new ProgramStageDataElement();
    psdeA.setDataElement(deA);
    psdeA.setSkipAnalytics(false);
    ProgramStageDataElement psdeB = new ProgramStageDataElement();
    psdeB.setDataElement(deB);
    psdeB.setSkipAnalytics(true);
    ProgramStageDataElement psdeC = new ProgramStageDataElement();
    psdeC.setDataElement(deC);
    psdeC.setSkipAnalytics(false);
    
    ProgramStage psA = set(new ProgramStage(), 'A');
    psA.getProgramStageDataElements().addAll(List.of(psdeA, psdeB, psdeC));
    
    Program prA = set(new Program(), 'A');
    prA.getProgramStages().add(psA);
    
    assertContainsExactly(prA.getAnalyticsDataElementsWithLegendSet(), deC);  
  }
}
