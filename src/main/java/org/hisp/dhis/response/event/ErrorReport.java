package org.hisp.dhis.response.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ErrorReport
{
    private String uid;

    private String errorCode;

    private String trackerType;

    private String message;
}
