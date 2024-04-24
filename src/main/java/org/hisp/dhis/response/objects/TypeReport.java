package org.hisp.dhis.response.objects;

import java.util.ArrayList;
import java.util.List;

import org.hisp.dhis.response.object.ObjectReport;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TypeReport
{
    @JsonProperty
    private String klass;

    @JsonProperty
    private ObjectStatistics stats;

    @JsonProperty
    private List<ObjectReport> objectReports = new ArrayList<>();
}
