package org.hisp.dhis.model.analytics;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnalyticsDimension {
  /** Data dimension identifier. */
  public static final String DATA_X_DIM_ID = "dx";

  /** Collapsed event data dimensions. */
  public static final String DATA_COLLAPSED_DIM_ID = "dy";

  /** Category option combo dimension identifier. */
  public static final String CATEGORYOPTIONCOMBO_DIM_ID = "co";

  /** Attribute option combo dimension identifier. */
  public static final String ATTRIBUTEOPTIONCOMBO_DIM_ID = "ao";

  /** Period dimension identifier. */
  public static final String PERIOD_DIM_ID = "pe";

  /** Org unit dimension identifier. */
  public static final String ORGUNIT_DIM_ID = "ou";
}
