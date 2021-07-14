package org.hisp.dhis.response.objects;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.hisp.dhis.response.object.ObjectReport;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class TypeReport
{
    @JsonProperty
    private String klass;

    @JsonProperty
    private ObjectStatistics stats;

    @JsonProperty
    private List<ObjectReport> objectReports = new ArrayList<>();
}
