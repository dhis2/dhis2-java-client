package org.hisp.dhis.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Program
    extends NameableObject
{
    @JsonProperty
    private ProgramType programType;

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
}
