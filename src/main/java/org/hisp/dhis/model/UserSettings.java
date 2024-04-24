package org.hisp.dhis.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
