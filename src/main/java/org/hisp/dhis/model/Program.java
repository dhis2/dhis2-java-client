package org.hisp.dhis.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Program
    extends NameableObject
{
    @JsonProperty
    private ProgramType programType;

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

    public ProgramType getProgramType()
    {
        return programType;
    }

    public void setProgramType( ProgramType programType )
    {
        this.programType = programType;
    }

    public List<ProgramStage> getProgramStages()
    {
        return programStages;
    }

    public void setProgramStages( List<ProgramStage> programStages )
    {
        this.programStages = programStages;
    }
}
