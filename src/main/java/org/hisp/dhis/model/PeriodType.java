package org.hisp.dhis.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PeriodType
{
    @JsonProperty
    private String name;

    @JsonProperty
    private Integer frequencyOrder;

    @JsonProperty
    private String isoDuration;

    @JsonProperty
    private String isoFormat;

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public Integer getFrequencyOrder()
    {
        return frequencyOrder;
    }

    public void setFrequencyOrder( Integer frequencyOrder )
    {
        this.frequencyOrder = frequencyOrder;
    }

    public String getIsoDuration()
    {
        return isoDuration;
    }

    public void setIsoDuration( String isoDuration )
    {
        this.isoDuration = isoDuration;
    }

    public String getIsoFormat()
    {
        return isoFormat;
    }

    public void setIsoFormat( String isoFormat )
    {
        this.isoFormat = isoFormat;
    }
}
