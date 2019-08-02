package org.hisp.dhis.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProgramStageDataElement
    extends NameableObject
{
    @JsonProperty
    private DataElement dataElement;

    public DataElement getDataElement()
    {
        return dataElement;
    }

    public void setDataElement( DataElement dataElement )
    {
        this.dataElement = dataElement;
    }
}
