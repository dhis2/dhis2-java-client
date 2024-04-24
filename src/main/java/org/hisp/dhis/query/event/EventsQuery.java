package org.hisp.dhis.query.event;

import java.util.Date;

import org.hisp.dhis.model.IdScheme;
import org.hisp.dhis.model.event.EventStatus;
import org.hisp.dhis.model.event.ProgramStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors( chain = true )
public class EventsQuery
{
    private String program;

    private String programStage;

    private ProgramStatus programStatus;

    private Boolean followUp;

    private String trackedEntityInstance;

    private String orgUnit;

    private OrgUnitSelectionMode ouMode;

    private EventStatus status;

    private Date occurredAfter;

    private Date occurredBefore;

    private Date scheduledAfter;

    private Date scheduledBefore;

    private Date updatedAfter;

    private Date updatedBefore;

    private IdScheme dataElementIdScheme;

    private IdScheme categoryOptionComboIdScheme;

    private IdScheme orgUnitIdScheme;

    private IdScheme programIdScheme;

    private IdScheme programStageIdScheme;

    private IdScheme idScheme;

    private EventsQuery()
    {
    }

    public static EventsQuery instance()
    {
        return new EventsQuery();
    }
}
