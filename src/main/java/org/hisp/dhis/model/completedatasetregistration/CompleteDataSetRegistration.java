package org.hisp.dhis.model.completedatasetregistration;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import static org.hisp.dhis.util.DateTimeUtils.JSON_DATE_FORMAT;
import static org.hisp.dhis.util.DateTimeUtils.JSON_DATE_TIME_FORMAT;

@Getter
@Setter
@NoArgsConstructor
public class CompleteDataSetRegistration {
    @JsonProperty private String dataSet;

    @JsonProperty private String period;

    @JsonProperty("organisationUnit") private String orgUnit;

    @JsonProperty private String attributeOptionCombo;

    @JsonProperty("date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = JSON_DATE_FORMAT)
    private Date created;

    @JsonProperty("storedBy") private String createdBy;

    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = JSON_DATE_TIME_FORMAT)
    private Date lastUpdated;

    @JsonProperty private String lastUpdatedBy;

    @JsonProperty private Boolean completed;
}