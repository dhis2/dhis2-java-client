package org.hisp.dhis.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Program
    extends NameableObject
{
    @JsonProperty
    private ProgramType programType;

    @JsonProperty
    private CategoryCombo categoryCombo;

    @JsonProperty
    private List<ProgramStage> programStages = new ArrayList<>();

    public Program()
    {
    }

    public Program( String id, String name )
    {
        this.id = id;
        this.name = name;
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
