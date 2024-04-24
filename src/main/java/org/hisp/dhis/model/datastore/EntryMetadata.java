package org.hisp.dhis.model.datastore;

import java.util.Date;

import org.hisp.dhis.model.UserMetadata;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EntryMetadata
{
    @JsonProperty
    private String id;

    @JsonProperty
    private String namespace;

    @JsonProperty
    private String key;

    @JsonProperty
    private Date created;

    @JsonProperty
    private Date lastUpdated;

    @JsonProperty
    private UserMetadata createdBy;

    @JsonProperty
    private UserMetadata lastUpdatedBy;
}
