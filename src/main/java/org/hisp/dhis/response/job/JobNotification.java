package org.hisp.dhis.response.job;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.hisp.dhis.response.NotificationLevel;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class JobNotification
{
    @JsonProperty
    private String uid;

    @JsonProperty
    private NotificationLevel level;

    @JsonProperty
    private JobCategory category;

    @JsonProperty
    private Date time;

    @JsonProperty
    private String message;

    @JsonProperty
    private boolean completed;

    public JobNotification( String uid, NotificationLevel level, JobCategory category, String message )
    {
        this.uid = uid;
        this.level = level;
        this.category = category;
        this.message = message;
    }
}
