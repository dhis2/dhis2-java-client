package org.hisp.dhis.model.user.sharing;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AccessObject
{
    private String id;

    private String displayName;

    private String access;
}
