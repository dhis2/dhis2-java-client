package org.hisp.dhis.model;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class ProgramTrackedEntityAttribute
    extends IdentifiableObject
{
    @JsonProperty
    private TrackedEntityAttribute trackedEntityAttribute;
}
