package org.hisp.dhis.response.objects;

import java.util.ArrayList;
import java.util.List;

import org.hisp.dhis.response.object.ErrorReport;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TypeReport
{
    @JsonProperty
    private String klass;

    @JsonProperty
    private ObjectStatistics stats;

    @JsonProperty
    private List<ErrorReport> errorReports = new ArrayList<>();
}
