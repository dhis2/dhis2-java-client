package org.hisp.dhis.util;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.hisp.dhis.model.IdentifiableObject;

@NoArgsConstructor( access = AccessLevel.PRIVATE )
public class IdentifiableObjectUtils
{
    /**
     * Returns a list of object identifiers.
     *
     * @param objects the list of {@link IdentifiableObject}.
     * @return a list of object identifiers.
     */
    public <T extends IdentifiableObject> List<String> getIds( Collection<T> objects )
    {
        return objects.stream()
            .filter( Objects::nonNull )
            .map( IdentifiableObject::getId )
            .collect( Collectors.toList() );
    }
}
