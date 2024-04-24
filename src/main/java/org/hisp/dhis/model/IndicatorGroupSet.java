package org.hisp.dhis.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IndicatorGroupSet
    extends NameableObject
{
    @JsonProperty
    private Boolean compulsory;

    @JsonProperty
    private List<IndicatorGroup> indicatorGroups = new ArrayList<>();
}
