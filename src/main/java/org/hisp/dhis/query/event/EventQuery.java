package org.hisp.dhis.query.event;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.hisp.dhis.model.IdScheme;
import org.hisp.dhis.model.event.EventStatus;
import org.hisp.dhis.model.event.ProgramStatus;

@Getter
@Setter
@Accessors( chain = true )
public class EventQuery
{
    private String program;

    private String programStage;

    private ProgramStatus programStatus;

    private Boolean followUp;

    private String trackedEntityInstance;

    private String orgUnit;

    private OrgUnitSelectionMode ouMode;

    private Date occuredAfter;

    private Date occuredBefore;

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

    private EventQuery()
    {
    }

    public static EventQuery instance()
    {
        return new EventQuery();
    }
}
