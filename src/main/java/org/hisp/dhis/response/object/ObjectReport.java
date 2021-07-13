package org.hisp.dhis.response.object;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ObjectReport
{
    @JsonProperty
    private String klass;

    @JsonProperty
    private Integer index;

    @JsonProperty
    private String uid;

    @JsonProperty
    private List<ErrorReport> errorReports = new ArrayList<>();

    /**
     * Alias for UID.
     *
     * @return the identifier.
     */
    public String getId()
    {
        return uid;
    }

    /**
     * Returns the number of errors.
     *
     * @return the number of errors.
     */
    public int getErrorCount()
    {
        return errorReports != null ? errorReports.size() : 0;
    }
}
