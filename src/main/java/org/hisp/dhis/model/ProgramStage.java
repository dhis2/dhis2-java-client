package org.hisp.dhis.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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
            .filter( element -> element.getDataElement() != null )
            .map( ProgramStageDataElement::getDataElement )
            .collect( Collectors.toSet() );
    }

    public List<ProgramStageDataElement> getProgramStageDataElements()
    {
        return programStageDataElements;
    }

    public void setProgramStageDataElements( List<ProgramStageDataElement> programStageDataElements )
    {
        this.programStageDataElements = programStageDataElements;
    }
}
