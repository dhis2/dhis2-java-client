package org.hisp.dhis.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@NoArgsConstructor
public class IndicatorGroup
    extends NameableObject
{
    @JsonProperty
    private List<Indicator> indicators = new ArrayList<>();

    @JsonProperty
    private List<IndicatorGroupSet> groupSets = new ArrayList<>();
}
