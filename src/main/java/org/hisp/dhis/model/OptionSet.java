package org.hisp.dhis.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class OptionSet
    extends IdentifiableObject
{
    @JsonProperty
    private String name;

    @JsonProperty
    private ValueType valueType;

    @JsonProperty
    private String version;

    @JsonProperty
    private List<Option> options = new ArrayList<>();
}
