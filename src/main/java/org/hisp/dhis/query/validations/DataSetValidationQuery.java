package org.hisp.dhis.query.validations;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class DataSetValidationQuery {
    private String dataSet;

    private String pe;

    private String ou;
}