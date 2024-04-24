package org.hisp.dhis.response.datavalueset;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Conflict
{
    @JsonProperty
    private String object;

    @JsonProperty
    private String value;
}
