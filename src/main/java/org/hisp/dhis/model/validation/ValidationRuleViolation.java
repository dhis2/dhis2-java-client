package org.hisp.dhis.model.validation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hisp.dhis.model.CategoryOptionCombo;
import org.hisp.dhis.model.OrgUnit;

@Getter
@Setter
public class ValidationRuleViolation {
    @JsonProperty private Integer id;
    @JsonProperty private ValidationRule validationRule;
    @JsonProperty private Period period;
    @JsonProperty private OrgUnit orgUnit;
    @JsonProperty private CategoryOptionCombo attributeOptionCombo;
    @JsonProperty private Integer leftsideValue;
    @JsonProperty private Integer rightsideValue;
    @JsonProperty private Integer dayInPeriod;
    @JsonProperty private Boolean notificationSent;
}
