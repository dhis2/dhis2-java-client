package org.hisp.dhis.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgramTrackedEntityAttribute
    extends IdentifiableObject
{
    @JsonProperty
    private TrackedEntityAttribute trackedEntityAttribute;
}
