package org.hisp.dhis.util;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.hisp.dhis.model.IdentifiableObject;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor( access = AccessLevel.PRIVATE )
public class IdentifiableObjectUtils
{
    /**
     * Returns a list of object identifiers.
     *
     * @param objects the collection of {@link IdentifiableObject}.
     * @return a list of object identifiers.
     */
    public static <T extends IdentifiableObject> List<String> toIdentifiers( Collection<T> objects )
    {
        return objects.stream()
            .filter( Objects::nonNull )
            .map( IdentifiableObject::getId )
            .collect( Collectors.toList() );
    }

    /**
     * Returns a list of object codes.
     *
     * @param objects the collection of {@link IdentifiableObject}.
     * @return a list of object codes.
     */
    public static <T extends IdentifiableObject> List<String> toCodes( Collection<T> objects )
    {
        return objects.stream()
            .filter( Objects::nonNull )
            .map( IdentifiableObject::getCode )
            .collect( Collectors.toList() );
    }
}
