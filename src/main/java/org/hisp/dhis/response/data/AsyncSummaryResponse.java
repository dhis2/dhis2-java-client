package org.hisp.dhis.response.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hisp.dhis.response.BaseHttpResponse;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public abstract class AsyncSummaryResponse extends BaseHttpResponse {
    @JsonProperty protected Status status;

    @JsonProperty protected String description;

    @JsonProperty protected ImportCount importCount;

    @JsonProperty protected List<Conflict> conflicts = new ArrayList<>();

    @JsonProperty private String dataSetComplete;

    /**
     * Indicates whether an import count exists.
     *
     * @return true if an import count exists.
     */
    @JsonIgnore
    public boolean hasImportCount() {
        return importCount != null;
    }

    /**
     * Returns the total count including imported, updated, deleted and ignored data values.
     *
     * @return a total count.
     */
    @JsonIgnore
    private long getTotalCount() {
        return hasImportCount()
                ? (importCount.getImported()
                + importCount.getUpdated()
                + importCount.getDeleted()
                + importCount.getIgnored())
                : 0;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("status", status)
                .append("description", description)
                .append("importCount", importCount)
                .append("conflicts", conflicts)
                .append("dataSetComplete", dataSetComplete)
                .append("httpStatusCode", httpStatusCode)
                .toString();
    }
}
