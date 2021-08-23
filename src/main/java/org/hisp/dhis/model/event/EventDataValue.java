package org.hisp.dhis.model.event;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@NoArgsConstructor
public class EventDataValue
{
    @JsonProperty
    private String dataElement;

    @JsonProperty
    private String value;

    @JsonProperty
    private Boolean providedElsewhere;

    @JsonProperty
    private Date createdAt;

    @JsonProperty
    private Date updatedAt;

    @JsonProperty
    private String storedBy;

    public EventDataValue( String dataElement, String value )
    {
        this.dataElement = dataElement;
        this.value = value;
    }
}
