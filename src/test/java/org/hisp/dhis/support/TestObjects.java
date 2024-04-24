package org.hisp.dhis.support;

import java.util.Date;

import org.hisp.dhis.model.NameableObject;
import org.hisp.dhis.util.UidUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor( access = AccessLevel.PRIVATE )
public class TestObjects
{
    public static <T extends NameableObject> T set( T object, char chr )
    {
        object.setId( UidUtils.generateUid() );
        object.setCreated( new Date() );
        object.setLastUpdated( new Date() );
        object.setCode( "Code" + chr );
        object.setName( "Name" + chr );
        object.setShortName( "ShortName" + chr );
        object.setDescription( "Description" + chr );
        return object;
    }
}
