package org.hisp.dhis.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataValue
{
    @JsonProperty
    private String dataElement;

    @JsonProperty
    private String period;

    @JsonProperty
    private String orgUnit;

    @JsonProperty
    private String categoryOptionCombo;

    @JsonProperty
    private String attributeOptionCombo;

    @JsonProperty
    private String value;

    @JsonProperty
    private String storedBy;

    @JsonProperty
    private String created;

    @JsonProperty
    private String lastUpdated;

    @JsonProperty
    private String comment;

    @JsonProperty
    private Boolean followup;

    @JsonProperty
    private Boolean deleted;

    public DataValue()
    {
    }
}
