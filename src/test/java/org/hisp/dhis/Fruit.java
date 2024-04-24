package org.hisp.dhis;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
