package org.hisp.dhis.model.validation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hisp.dhis.model.LegendSet;
import org.hisp.dhis.model.NameableObject;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ValidationRule extends NameableObject {
    @JsonProperty private String dimensionItem;

    @JsonProperty private String instruction;
    @JsonProperty private String importance;
    @JsonProperty private String periodType;

    @JsonProperty private ValidationSide leftSide;
    @JsonProperty private String operator;
    @JsonProperty private ValidationSide rightSide;

    @JsonProperty private Boolean skipFormValidation;

    @JsonProperty private List<LegendSet> legendSets;
}
