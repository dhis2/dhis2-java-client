package org.hisp.dhis.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgramStage
    extends NameableObject
{
    @JsonProperty
    private List<ProgramStageDataElement> programStageDataElements = new ArrayList<>();

    /**
     * Returns all data elements part of this program stage.
     *
     * @return a set of {@link DataElement}.
     */
    @JsonIgnore
    public Set<DataElement> getDataElements()
    {
        return programStageDataElements.stream()
            .map( ProgramStageDataElement::getDataElement )
            .filter( Objects::nonNull )
            .collect( Collectors.toSet() );
    }
}
