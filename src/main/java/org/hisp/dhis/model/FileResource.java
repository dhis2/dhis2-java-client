package org.hisp.dhis.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FileResource extends NameableObject{
    @JsonProperty private String contentType;
    @JsonProperty private Integer contentLength;
    @JsonProperty private String contentMd5;
    @JsonProperty private String domain;
    @JsonProperty private String hasMultipleStorageFiles;
    @JsonProperty private String storageStatus;
}
