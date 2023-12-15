package org.hisp.dhis.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@NoArgsConstructor
public class UserSettings
{
    @JsonProperty
    private String keyUiLocale;

    @JsonProperty
    private String keyDbLocale;
}
