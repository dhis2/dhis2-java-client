package org.hisp.dhis.model;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class ProgramStageDataElement
    extends NameableObject
{
    @JsonProperty
    private DataElement dataElement;
}
