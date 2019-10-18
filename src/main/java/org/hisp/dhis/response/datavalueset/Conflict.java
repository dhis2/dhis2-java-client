package org.hisp.dhis.response.datavalueset;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Conflict
{
    @JsonProperty
    private String object;

    @JsonProperty
    private String value;

    public Conflict()
    {
    }

    public String getObject()
    {
        return object;
    }

    public void setObject( String object )
    {
        this.object = object;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue( String value )
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return "[object: " + object + ", value: " + value + "]";
    }
}
