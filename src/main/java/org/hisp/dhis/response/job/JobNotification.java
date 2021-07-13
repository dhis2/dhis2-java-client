package org.hisp.dhis.response.job;

import java.util.Date;

import org.hisp.dhis.response.NotificationLevel;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    public JobNotification()
    {
    }

    public JobNotification( String uid, NotificationLevel level, JobCategory category, String message )
    {
        this.uid = uid;
        this.level = level;
        this.category = category;
        this.message = message;
    }

    @Override
    public String toString()
    {
        return new StringBuilder( "[" )
            .append( "uid: " ).append( uid ).append( ", " )
            .append( "level: " ).append( level ).append( ", " )
            .append( "category: " ).append( category ).append( ", " )
            .append( "time:" ).append( time ).append( ", " )
            .append( "completed: " ).append( completed ).append( "]" ).toString();

    }
}
