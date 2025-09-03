package org.hisp.dhis.util;

import static org.hisp.dhis.support.Assertions.assertSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Set;
import org.hisp.dhis.model.DataElement;
import org.hisp.dhis.model.Indicator;
import org.hisp.dhis.model.NameableObject;
import org.hisp.dhis.support.TestObjects;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.UNIT)
class SetBuilderTest {
  private final DataElement deA = TestObjects.set(new DataElement(), 'A');
  private final DataElement deB = TestObjects.set(new DataElement(), 'B');
  private final DataElement deC = TestObjects.set(new DataElement(), 'C');
  private final Indicator inA = TestObjects.set(new Indicator(), 'A');
  private final Indicator inB = TestObjects.set(new Indicator(), 'B');

  @Test
  void testAdd() {
    Set<String> actual =
        new SetBuilder<String>().add("one").addAll(Set.of("two", "three")).build();

    Set<String> expected = Set.of("one", "two", "three");

    assertEquals(expected, actual);
  }

  @Test
  void testAddAll() {
    Set<DataElement> dataElements = Set.of(deA, deB);
    Set<Indicator> indicators = Set.of(inA, inB);

    Set<NameableObject> actual =
        new SetBuilder<NameableObject>().addAll(dataElements).addAll(indicators).add(deC).build();

    assertSize(5, actual);
  }

  @Test
  void testAddWithInitial() {
    Set<String> actual =
        new SetBuilder<String>(Set.of("one")).addAll(Set.of("two", "three")).build();

    Set<String> expected = Set.of("one", "two", "three");

    assertEquals(expected, actual);
  }
}
