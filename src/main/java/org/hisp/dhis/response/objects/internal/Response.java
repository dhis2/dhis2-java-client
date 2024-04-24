package org.hisp.dhis.response.objects.internal;

import java.util.ArrayList;
import java.util.List;

import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.objects.ObjectStatistics;
import org.hisp.dhis.response.objects.TypeReport;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Response
{
    @JsonProperty
    private Status status;

    @JsonProperty
    private List<TypeReport> typeReports = new ArrayList<>();

    @JsonProperty
    private ObjectStatistics stats;
}
