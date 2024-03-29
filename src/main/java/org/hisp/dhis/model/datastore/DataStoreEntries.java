package org.hisp.dhis.model.datastore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@NoArgsConstructor
public class DataStoreEntries
{
    @JsonProperty
    private List<Map<String, Object>> entries = new ArrayList<>();
}
