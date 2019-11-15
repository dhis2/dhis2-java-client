package org.hisp.dhis.response.job;

import java.util.Date;

import org.hisp.dhis.response.NotificationLevel;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    public String getUid()
    {
        return uid;
    }

    public void setUid( String uid )
    {
        this.uid = uid;
    }

    public NotificationLevel getLevel()
    {
        return level;
    }

    public void setLevel( NotificationLevel level )
    {
        this.level = level;
    }

    public JobCategory getCategory()
    {
        return category;
    }

    public void setCategory( JobCategory category )
    {
        this.category = category;
    }

    public Date getTime()
    {
        return time;
    }

    public void setTime( Date time )
    {
        this.time = time;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage( String message )
    {
        this.message = message;
    }

    public boolean isCompleted()
    {
        return completed;
    }

    public void setCompleted( boolean completed )
    {
        this.completed = completed;
    }
}
