package org.hisp.dhis.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgramStageDataElement
    extends NameableObject
{
    @JsonProperty
    private DataElement dataElement;
}
