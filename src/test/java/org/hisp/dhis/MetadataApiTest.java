package org.hisp.dhis;

import java.util.List;
import org.hisp.dhis.model.Dhis2Objects;
import org.hisp.dhis.model.Option;
import org.hisp.dhis.model.OptionSet;
import org.hisp.dhis.response.objects.ObjectsResponse;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class MetadataApiTest {
  @Test
  void testImportMetadata() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);
    
    Option oA = new Option();
    oA.setId("AnxX8vfEzZE");
    oA.setCode("ESPRESSO");
    oA.setName("Espresso");

    Option oB = new Option();
    oB.setId("UxQR6MUFmhH");
    oB.setCode("Americano");
    oB.setName("AMERICANO");

    Option oC = new Option();
    oC.setId("kQYbXoTUVYL");
    oC.setCode("CAPPUCINO");
    oC.setName("Capuccino");

    List<Option> options = List.of(oA, oB, oC);
    
    OptionSet optionSet = new OptionSet();
    optionSet.setId("gKdxTM8BcI7");
    optionSet.setName("Coffee");
    optionSet.setCode("COFFEE");
    optionSet.setOptions(options);
    
    List<OptionSet> optionSets = List.of(optionSet);
    
    Dhis2Objects objects = new Dhis2Objects();
    objects.setOptionSets(optionSets);
    objects.setOptions(options);
    
    ObjectsResponse response = dhis2.saveMetadataObjects(objects);
    
    System.out.println(response);
  }
}
