package org.hisp.dhis.model.event;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@NoArgsConstructor
public class EventsResult
{
    @JsonProperty
    private final List<Event> instances = new ArrayList<>();
}
