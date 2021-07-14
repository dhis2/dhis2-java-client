package org.hisp.dhis.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class SystemSettings
{
    @JsonProperty( value = "keyCalendar" )
    private String calendar;

    @JsonProperty( value = "keyDateFormat" )
    private String dateFormat;

    @JsonProperty( value = "keyDbLocale" )
    private String dbLocale;

    @JsonProperty( value = "keyUiLocale" )
    private String uiLocale;

    @JsonProperty( value = "keyLastSuccessfulAnalyticsTablesUpdate" )
    private Date lastSuccessfulAnalyticsTablesUpdate;

    @JsonProperty( value = "keyLastSuccessfulResourceTablesUpdate" )
    private Date lastSuccessfulResourceTablesUpdate;

    @JsonProperty( value = "lastSuccessfulSystemMonitoringPush" )
    private Date keyLastSuccessfulSystemMonitoringPush;
}
