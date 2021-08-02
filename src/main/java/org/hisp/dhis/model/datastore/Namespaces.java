package org.hisp.dhis.model.datastore;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class Namespaces
{
    @JsonProperty
    private List<String> namespaces = new ArrayList<>();
}
