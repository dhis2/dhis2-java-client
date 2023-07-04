package org.hisp.dhis.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class CategoryOptionCombo
    extends NameableObject
{
    @JsonProperty
    private Boolean ignoreApproval;

    @JsonProperty
    private Set<CategoryOption> categoryOptions = new HashSet<>();
}
