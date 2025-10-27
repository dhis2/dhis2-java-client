package org.hisp.dhis.model.analytics;

import static org.hisp.dhis.model.analytics.AnalyticsDimension.DATA_X;
import static org.hisp.dhis.model.analytics.AnalyticsDimension.ORG_UNIT;
import static org.hisp.dhis.model.analytics.AnalyticsDimension.PERIOD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.hisp.dhis.model.ValueType;
import org.hisp.dhis.support.TestTags;
import org.hisp.dhis.util.MapBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.UNIT)
class AnalyticsDataSortTest {
  private AnalyticsData getDataA() {
    List<AnalyticsHeader> headers =
        List.of(
            new AnalyticsHeader(DATA_X, "Data", ValueType.TEXT, true),
            new AnalyticsHeader(PERIOD, "Period", ValueType.TEXT, true),
            new AnalyticsHeader(ORG_UNIT, "OrgUnit", ValueType.TEXT, true),
            new AnalyticsHeader("value", "Value", ValueType.NUMBER, false));

    AnalyticsMetaData metadata = new AnalyticsMetaData();
    metadata.setItems(
        new MapBuilder<String, MetaDataItem>()
            .put("A1", new MetaDataItem("Indicator 1"))
            .put("A2", new MetaDataItem("Indicator 2"))
            .put("A3", new MetaDataItem("Indicator 3"))
            .put("A4", new MetaDataItem("Indicator 4"))
            .put("A5", new MetaDataItem("Indicator 5"))
            .put("A6", new MetaDataItem("Indicator 6"))
            .put("A7", new MetaDataItem("Indicator 7"))
            .put("A8", new MetaDataItem("Indicator 8"))
            .put("B1", new MetaDataItem("Month 1"))
            .put("B2", new MetaDataItem("Month 2"))
            .put("B3", new MetaDataItem("Month 3"))
            .put("B4", new MetaDataItem("Month 4"))
            .put("C1", new MetaDataItem("Facility 1"))
            .put("C2", new MetaDataItem("Facility 2"))
            .build());
    metadata.setDimensions(
        new MapBuilder<String, List<String>>()
            .put("dx", List.of("A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8"))
            .put("pe", List.of("B1", "B2", "B3", "B4"))
            .put("ou", List.of("C1", "C2"))
            .build());

    AnalyticsData data = new AnalyticsData();

    data.setHeaders(headers);
    data.setMetaData(metadata);

    data.addRow(List.of("A1", "B1", "C1", "2"));
    data.addRow(List.of("A2", "B2", "C2", "4"));
    data.addRow(List.of("A4", "B4", "C2", "3"));
    data.addRow(List.of("A3", "B3", "C1", "7"));
    data.addRow(List.of("A5", "B1", "C1", "8"));
    data.addRow(List.of("A7", "B3", "C1", "1"));
    data.addRow(List.of("A6", "B2", "C2", "6"));
    data.addRow(List.of("A8", "B4", "C2", "9"));
    
    return data;
  }

  @Test
  void testSortDataA() {
    AnalyticsData data = getDataA();
    
    List<String> row1 = data.getRow(0);
    List<String> row3 = data.getRow(2);
    List<String> row5 = data.getRow(4);
    List<String> row7 = data.getRow(6);

    assertEquals("A1", row1.get(0));
    assertEquals("C1", row1.get(2));
    assertEquals("A4", row3.get(0));
    assertEquals("C2", row3.get(2));
    assertEquals("A5", row5.get(0));
    assertEquals("C1", row5.get(2));
    assertEquals("A6", row7.get(0));
    assertEquals("C2", row7.get(2));

    data.sortRows();

    row1 = data.getRow(0);
    row3 = data.getRow(2);
    row5 = data.getRow(4);
    row7 = data.getRow(6);

    assertEquals("A1", row1.get(0));
    assertEquals("C1", row1.get(2));
    assertEquals("A3", row3.get(0));
    assertEquals("C1", row3.get(2));
    assertEquals("A5", row5.get(0));
    assertEquals("C1", row5.get(2));
    assertEquals("A7", row7.get(0));
    assertEquals("C1", row7.get(2));
  }
}
