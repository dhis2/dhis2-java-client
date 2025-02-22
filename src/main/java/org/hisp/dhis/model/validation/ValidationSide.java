package org.hisp.dhis.model.validation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationSide {
    @JsonProperty private String expression;
    @JsonProperty private String description;
    @JsonProperty private Boolean slidingWindow;
    @JsonProperty private String missingValueStrategy;
}
