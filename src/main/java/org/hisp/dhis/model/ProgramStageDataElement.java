package org.hisp.dhis.model;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class ProgramStageDataElement
    extends NameableObject
{
    @JsonProperty
    private DataElement dataElement;

    @JsonProperty
    private Boolean compulsory;

    @JsonProperty
    private Boolean displayInReports;

    @JsonProperty
    private Boolean skipSynchronization;

    @JsonProperty
    private Boolean skipAnalytics;
}
