/*
 * Copyright (c) 2004-2024, University of Oslo
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
package org.hisp.dhis.model;

import static org.hisp.dhis.support.Assertions.assertContainsExactlyInOrder;
import static org.hisp.dhis.support.Assertions.assertNotEmpty;
import static org.hisp.dhis.support.Assertions.assertSize;
import static org.hisp.dhis.support.TestObjects.set;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import org.hisp.dhis.support.JsonClassPathFile;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.UNIT)
class ProgramTest {
  @Test
  void testDeserializeProgramObjects() {
    ProgramObjects objects =
        JsonClassPathFile.fromJson("metadata/program-address-book.json", ProgramObjects.class);

    assertNotNull(objects);
    assertSize(1, objects.getPrograms());
    assertNotEmpty(objects.getProgramSections());
    assertNotEmpty(objects.getProgramStages());
    assertNotEmpty(objects.getProgramStageSections());
  }

  @Test
  void testGetDataElements() {
    DataElement deA = set(new DataElement(), 'A');
    DataElement deB = set(new DataElement(), 'B');
    DataElement deC = set(new DataElement(), 'C');

    ProgramStageDataElement psdeA = new ProgramStageDataElement();
    psdeA.setDataElement(deA);
    ProgramStageDataElement psdeB = new ProgramStageDataElement();
    psdeB.setDataElement(deB);
    ProgramStageDataElement psdeC = new ProgramStageDataElement();
    psdeC.setDataElement(deC);

    ProgramStage psA = set(new ProgramStage(), 'A');
    psA.getProgramStageDataElements().addAll(List.of(psdeA, psdeB, psdeC));

    Program prA = set(new Program(), 'A');
    prA.getProgramStages().add(psA);

    assertContainsExactlyInOrder(prA.getDataElements(), deA, deB, deC);
  }

  @Test
  void testGetAnalyticsDataElements() {
    DataElement deA = set(new DataElement(), 'A');
    DataElement deB = set(new DataElement(), 'B');
    DataElement deC = set(new DataElement(), 'C');

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

    assertContainsExactlyInOrder(prA.getAnalyticsDataElements(), deA, deC);
  }

  @Test
  void testGetAnalyticsDataElementsWithLegendSet() {
    LegendSet lsA = set(new LegendSet(), 'A');

    DataElement deA = set(new DataElement(), 'A');
    deA.setValueType(ValueType.NUMBER);
    DataElement deB = set(new DataElement(), 'B');
    deB.setValueType(ValueType.NUMBER);
    DataElement deC = set(new DataElement(), 'C');
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

    assertContainsExactlyInOrder(prA.getAnalyticsDataElementsWithLegendSet(), deC);
  }
}
