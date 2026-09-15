/*
 * Copyright (c) 2004-2026, University of Oslo
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
package org.hisp.dhis.model.visualization.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hisp.dhis.model.Program;
import org.hisp.dhis.model.ProgramStage;
import org.hisp.dhis.model.analytics.Sorting;
import org.hisp.dhis.model.dimension.SimpleDimension;
import org.hisp.dhis.model.period.RelativePeriods;
import org.hisp.dhis.model.visualization.DigitGroupSeparator;
import org.hisp.dhis.model.visualization.DisplayDensity;
import org.hisp.dhis.model.visualization.FontSize;
import org.hisp.dhis.model.visualization.HideEmptyItemStrategy;
import org.hisp.dhis.model.visualization.RegressionType;
import org.hisp.dhis.model.visualization.VisualizationObject;

@Getter
@Setter
@NoArgsConstructor
public class EventVisualization extends VisualizationObject {
  /** Display name. */
  @JsonProperty private String displayName;

  /** Type of the event visualization. */
  @JsonProperty private EventVisualizationType type;

  /** Sort order state. */
  @JsonProperty private List<Sorting> sorting = new ArrayList<>();

  /** Program. Required. */
  @JsonProperty private Program program;

  /** Program stage. */
  @JsonProperty private ProgramStage programStage;

  /** Programs used in dimensions, i.e. columns, rows and filters. */
  @JsonProperty private List<Program> programDimensions = new ArrayList<>();

  /** Non-typed dimensions, such as period or org unit dimensions. */
  @JsonProperty private List<SimpleDimension> simpleDimensions = new ArrayList<>();

  /** Indicates the output type, i.e. event, enrollment or tracked entity instance. */
  @JsonProperty private EventOutputType outputType;

  /** Indicates whether to collapse all data dimensions into a single dimension. */
  @JsonProperty private boolean collapseDataDimensions;

  /** Indicates whether to hide n/a data. */
  @JsonProperty private boolean hideNaData;

  /** Indicates whether to fix the column headers of the pivot table. */
  @JsonProperty private boolean fixColumnHeaders;

  /** Indicates whether to fix the row headers of the pivot table. */
  @JsonProperty private boolean fixRowHeaders;

  /** Indicates whether this is a legacy event chart or event report. */
  @JsonProperty private boolean legacy;

  /** Indicates whether to include completed events only. */
  @JsonProperty private boolean completedOnly;

  /** Indicates whether to skip rounding of data values. */
  @JsonProperty private boolean skipRounding;

  /** Digit group (thousands) separator. */
  @JsonProperty private DigitGroupSeparator digitGroupSeparator;

  /** Strategy for hiding empty row items. */
  @JsonProperty private HideEmptyItemStrategy hideEmptyRowItems;

  /** Relative period settings. */
  @JsonProperty private RelativePeriods relativePeriods;

  /** Dimensions to use as filters. */
  @JsonProperty private List<String> filterDimensions = new ArrayList<>();

  /** Type of regression to apply. */
  @JsonProperty private RegressionType regressionType;

  /** Display density of the pivot table. */
  @JsonProperty private DisplayDensity displayDensity;

  /** Font size of the pivot table. */
  @JsonProperty private FontSize fontSize;

  /** Indicates whether to hide rows with no data values. */
  @JsonProperty private boolean hideEmptyRows;

  /** Indicates whether to show the organisation unit hierarchy. */
  @JsonProperty private boolean showHierarchy;
}
