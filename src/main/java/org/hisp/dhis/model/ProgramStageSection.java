package org.hisp.dhis.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProgramStageSection extends NameableObject {
  @JsonProperty private String description;

  @JsonProperty private String formName;

  @JsonProperty private Integer sortOrder;

  @JsonProperty private ProgramStage programStage;

  @JsonProperty private List<DataElement> dataElements = new ArrayList<>();

  @JsonProperty private List<ProgramIndicator> programIndicators = new ArrayList<>();
}
