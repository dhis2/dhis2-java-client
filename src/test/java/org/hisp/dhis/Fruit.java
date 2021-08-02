package org.hisp.dhis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Fruit
{
    @JsonProperty
    private String name;

    @JsonProperty
    private String color;
}
