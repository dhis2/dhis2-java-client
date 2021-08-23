package org.hisp.dhis.response.event;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ValidationReport
{
    private List<ErrorReport> errorReports = new ArrayList<>();
}
