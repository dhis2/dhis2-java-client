package org.hisp.dhis.model.datastore;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class KeyMetadata
{
    @JsonProperty
    private String id;

    @JsonProperty
    private Date created;

    @JsonProperty
    private String namespace;

    @JsonProperty
    private String key;
}
