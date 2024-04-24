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
public class ImportCount
{
    @JsonProperty
    private int imported;

    @JsonProperty
    private int updated;

    @JsonProperty
    private int ignored;

    @JsonProperty
    private int deleted;
}
