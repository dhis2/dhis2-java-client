package org.hisp.dhis.model.validation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hisp.dhis.model.IdentifiableObject;
import org.hisp.dhis.model.datavalueset.DataValue;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Validation {
    @JsonProperty private List<ValidationRuleViolation> validationRuleViolations = new ArrayList<>();
    @JsonProperty private List<IdentifiableObject> commentRequiredViolations = new ArrayList<>();

    public boolean addValidationRuleViolation(ValidationRuleViolation validationRuleViolation) {
        return this.validationRuleViolations.add(validationRuleViolation);
    }

    public boolean addCommentRequiredViolation(IdentifiableObject commentRequiredViolation) {
        return this.commentRequiredViolations.add(commentRequiredViolation);
    }
}
