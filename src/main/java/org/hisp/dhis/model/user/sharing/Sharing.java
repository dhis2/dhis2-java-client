package org.hisp.dhis.model.user.sharing;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Sharing
{
    @JsonProperty
    private String owner;

    @JsonProperty( "public" )
    private String publicAccess;

    @JsonProperty
    private boolean external;

    @JsonProperty
    private Map<String, UserAccess> users = new HashMap<>();

    @JsonProperty
    private Map<String, UserGroupAccess> userGroups = new HashMap<>();
}
