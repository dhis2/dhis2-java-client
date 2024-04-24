package org.hisp.dhis.model.event;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EventsResult
{
    @JsonProperty
    private final List<Event> instances = new ArrayList<>();
}
