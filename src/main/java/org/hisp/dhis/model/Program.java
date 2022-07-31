package org.hisp.dhis.model;

import java.util.ArrayList;
import java.util.List;
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
public class Program
    extends NameableObject
{
    @JsonProperty
    private ProgramType programType;

    @JsonProperty
    private CategoryCombo categoryCombo;

    @JsonProperty
    private List<ProgramTrackedEntityAttribute> programTrackedEntityAttributes = new ArrayList<>();

    @JsonProperty
    private List<ProgramStage> programStages = new ArrayList<>();

    public Program( String id, String name )
    {
        this.id = id;
        this.name = name;
    }

    /**
     * Returns all tracked entity attributes which are part of the program.
     *
     * @return a list of {@link TrackedEntityAttribute}.
     */
    public List<TrackedEntityAttribute> getTrackedEntityAttributes()
    {
        return programTrackedEntityAttributes.stream()
            .map( ProgramTrackedEntityAttribute::getTrackedEntityAttribute )
            .collect( Collectors.toList() );
    }

    /**
     * Returns all tracked entity attributes which are not confidential and part
     * of the program.
     *
     * @return a list of {@link TrackedEntityAttribute}.
     */
    public List<TrackedEntityAttribute> getNonConfidentialTrackedEntityAttributes()
    {
        return programTrackedEntityAttributes.stream()
            .map( ProgramTrackedEntityAttribute::getTrackedEntityAttribute )
            .filter( java.util.Objects::nonNull )
            .filter( tea -> (tea.getConfidential() == null || tea.getConfidential() == false) )
            .collect( Collectors.toList() );
    }

    /**
     * Returns all data elements which are part of the stages of this program.
     *
     * @return a set of {@link DataElement}.
     */
    @JsonIgnore
    public Set<DataElement> getDataElements()
    {
        return programStages.stream()
            .flatMap( ps -> ps.getDataElements().stream() )
            .collect( Collectors.toSet() );
    }

    /**
     * Returns data elements which are part of the stages of this program which
     * have a legend set and is of numeric value type.
     *
     * @return a set of {@link DataElement}.
     */
    @JsonIgnore
    public Set<DataElement> getDataElementsWithLegendSet()
    {
        return getDataElements().stream()
            .filter( de -> !de.getLegendSets().isEmpty() && de.getValueType().isNumeric() )
            .collect( Collectors.toSet() );
    }

    public boolean hasCategoryCombo()
    {
        return categoryCombo != null;
    }
}
