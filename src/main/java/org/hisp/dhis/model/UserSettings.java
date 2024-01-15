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
    @JsonProperty( value = "keyUiLocale" )
    private String uiLocale;

    @JsonProperty( value = "keyDbLocale" )
    private String dbLocale;
}
