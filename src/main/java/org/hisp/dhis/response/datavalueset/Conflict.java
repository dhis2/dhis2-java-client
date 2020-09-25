package org.hisp.dhis.response.datavalueset;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Conflict
{
    @JsonProperty
    private String object;

    @JsonProperty
    private String value;

    public Conflict()
    {
    }

    @Override
    public String toString()
    {
        return "[object: " + object + ", value: " + value + "]";
    }
}
