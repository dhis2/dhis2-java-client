package org.hisp.dhis.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@NoArgsConstructor
public class ProgramStage
    extends NameableObject
{
    @JsonProperty
    private List<ProgramStageDataElement> programStageDataElements = new ArrayList<>();

    public ProgramStage( String id, String name )
    {
        this.id = id;
        this.name = name;
    }

    /**
     * Returns all data elements part of this program stage as a read-only set.
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
