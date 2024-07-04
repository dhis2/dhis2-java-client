package org.hisp.dhis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.hisp.dhis.model.CategoryOption;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class CategoryOptionApiTest {
  @Test
  void getCategoryOption() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    CategoryOption categoryOption = dhis2.getCategoryOption("K4gwuiVvW3z");

    assertNotNull(categoryOption);
    assertEquals("K4gwuiVvW3z", categoryOption.getId());
    assertFalse(categoryOption.getCategories().isEmpty());
    assertFalse(categoryOption.getCategoryOptionCombos().isEmpty());
  }

  @Test
  void testGetK4gwuiVvW3zs() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);

    List<CategoryOption> categoryOptions = dhis2.getCategoryOptions(Query.instance());

    assertNotNull(categoryOptions);
    assertFalse(categoryOptions.isEmpty());
    assertNotNull(categoryOptions.get(0).getId());
  }
}
